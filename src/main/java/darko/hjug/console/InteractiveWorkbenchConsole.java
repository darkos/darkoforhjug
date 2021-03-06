package darko.hjug.console;

import darko.hjug.flashcards.Workbench;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class InteractiveWorkbenchConsole {

    private ConsoleReader reader;
    private PrintWriter out;
    private String promptStr;
    private List<Completer> completors;

    private Workbench workbench;
    private ConsoleCli cli;

    public InteractiveWorkbenchConsole() {
        try {
            init();
            usage();
            loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        promptStr = "prompt> ";
        reader = new ConsoleReader();
        reader.setPrompt(promptStr);
        completors = new LinkedList<Completer>();

        out = new PrintWriter(reader.getOutput());

        workbench = new Workbench();
        cli = new ConsoleCli(workbench);

        String[] completerArray = getArrayForCompleter(cli.getOptions());
        completors.add(new StringsCompleter(completerArray));
        for (Completer c : completors) {
            reader.addCompleter(c);
        }
    }

    private String[] getArrayForCompleter(Options opt) {
        ArrayList<String> completerList = new ArrayList<String>();
        Collection options = opt.getOptions();
        Iterator iter = options.iterator();
        while(iter.hasNext()) {
            Option option = (Option)iter.next();
            completerList.add(option.getOpt());
        }
        String[] completerArray = new String[completerList.size()];
        for(int i=0; i<completerArray.length; i++) {
            completerArray[i] = "-" + completerList.get(i);
        }
        return completerArray;
    }

    private void usage() {
        System.out.println("######################################################");
        System.out.println("Hello\nThis is a command line application called\nFlashCards.");
        System.out.println("It gives you ability to create flash cards for study \nand group them under subject");
        System.out.println("It also, includes a player with commands Next and Previous, \nso you can browse through your cardsand test/study your knowledge.");
        System.out.println("######################################################");
        cli.help();
    }

    private void loop() throws IOException {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                out.println("======>\"" + line + "\"");
                out.flush();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    if(workbench.getSubject().getCards().size() > 0) {
                        workbench.saveDateStampedFileForcefully();
                    }
                    break;
                }
                else if (line.equalsIgnoreCase("cls") || line.equalsIgnoreCase("clear")) {
                    reader.clearScreen();
                }
                else if (line.equalsIgnoreCase(">>")) {
                    workbench.playNext();
                }
                else if (line.equalsIgnoreCase("<<")) {
                    workbench.playPrevious();
                }
                else {
                    processInputLine(line);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(0);
    }

    private void processInputLine(String line) {
        if (line.isEmpty()) {
            cli.help();
        } else {
            String[] args = null;
            if (line.indexOf('"') > 0) {
                args = line.split("\"");
                for (int i = 0; i < args.length; i++) {
                    args[i] = args[i].trim();
                }
            } else {
                args = line.split(" ");
            }
            try {
                processInput(args);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void processInput(String[] args) throws ParseException {
        cli.processInput(args);
    }

    public static void main(String[] args) {
        new InteractiveWorkbenchConsole();
    }

}
