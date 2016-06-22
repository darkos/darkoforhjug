package darko.hjug.console;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class InteractiveTestConsole {

    private ConsoleReader reader;
    private PrintWriter out;
    private String promptStr;
    private List<Completer> completors;

    public InteractiveTestConsole() {
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
    }

    private void usage() {

    }

    private void loop() throws IOException {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                out.println("======>\"" + line + "\"");
                out.flush();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                else if (line.equalsIgnoreCase("cls") || line.equalsIgnoreCase("clear")) {
                    reader.clearScreen();
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

    }

    public static void main(String[] args) {
        new InteractiveTestConsole();
    }

}
