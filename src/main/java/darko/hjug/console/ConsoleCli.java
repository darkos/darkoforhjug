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
        options.addOption("newemptycard", false, "Creates new FLash card with no title or body. \n" +
                "Before adding card to subject, title and body need to be set.\n" +
                "To add card to subject, use \" addcurrentcard \" command");
        options.addOption("addcurrentcard", false, "Will add current card to the Subject.");
        options.addOption("setcardtitle", true, "Sets title of the current card");
        options.addOption("setcardbody", true, "Sets body of the current card");
        options.addOption("carddetails", false, "Prints current card details");
        options.addOption("saveastimestamp",false,"Saves current subject as xml file. \nName of the file will be time stamp");
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
        } else if (cmd.hasOption("newemptycard")) {
            workbench.createNewCard();
        } else if (cmd.hasOption("addcurrentcard")) {
            workbench.addCurrentCard();
        } else if (cmd.hasOption("setcardtitle")) {
            String title = cmd.getOptionValue("setcardtitle");
            workbench.setCurrentCardTitle(title);
        } else if (cmd.hasOption("setcardbody")) {
            String body = cmd.getOptionValue("setcardbody");
            workbench.setCurrentCardBody(body);
        } else if (cmd.hasOption("carddetails")) {
            workbench.showCurrentCard();
        } else if (cmd.hasOption("saveastimestamp")) {
            workbench.saveDateStampedFileForcefully();
        }
    }

    public Options getOptions() {
        return options;
    }

}
