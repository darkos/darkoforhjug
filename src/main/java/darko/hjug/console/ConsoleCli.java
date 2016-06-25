package darko.hjug.console;

import darko.hjug.flashcards.Workbench;
import org.apache.commons.cli.*;

public class ConsoleCli {

    private Workbench workbench;
    private Options options;
    private CommandLine cmd;
    private CommandLineParser parser;

    public ConsoleCli(Workbench workbench) {
        this.workbench = workbench;
        init();
    }

    private void init() {
        parser = new DefaultParser();
        options = new Options();
        options.addOption("help", false, "Prints help (usage)");
        options.addOption("details", false, "Prints out details of current Subject");
        options.addOption("setsubjectname", true, "Sets the Subject name");
    }

    public void help() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Workbench", options);
    }

    public void processInput(String[] args) throws ParseException {
        cmd = parser.parse(options, args);
        processCommand(cmd);
    }

    public void processCommand(CommandLine cmd) {
        if (cmd.hasOption("help")) {
            help();
        } else if (cmd.hasOption("details")) {
            workbench.subjectDetails();
        } else if (cmd.hasOption("setsubjectname")) {
            String name = cmd.getOptionValue("setsubjectname");
            workbench.setSubjectName(name);
        }
    }

    public Options getOptions() {
        return options;
    }

}
