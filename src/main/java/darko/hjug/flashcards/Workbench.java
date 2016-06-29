package darko.hjug.flashcards;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Workbench {

    private Subject subject;
    private FlashCard currentCard;
    private String xmlFilePath;
    private XmlSubject xmlSubject;
    private Player player;

    public Workbench() {
        this.subject = new Subject();
    }

    public Workbench(String subjectName) {
        this.subject = new Subject(subjectName);
    }

    public void loadSubjectFromXmlFile(String xmlFilePath) {
        this.xmlSubject = new XmlSubject(xmlFilePath);
        this.subject = this.xmlSubject.getSubject();
    }

    public void loadSubjectFromXmlFile(File dir, String xmlFilePath) {
        File file = new File(dir, xmlFilePath);
        this.xmlSubject = new XmlSubject(file);
        this.subject = this.xmlSubject.getSubject();
    }

    public void subjectDetails() {
        this.subject.details();
    }

    public void setSubjectName(String name) {
        this.subject.setName(name);
    }

    public void createNewBareBoneCard(String title, String body) {
        this.currentCard = new FlashCard(title, body);
    }

    public void createNewCard() {
        this.currentCard = new FlashCard();
    }

    public void addCurrentCard() {
        if (this.currentCard.canBeAdded()) {
            this.subject.addCard(this.currentCard);
        } else {
            System.out.println("Couldn't add card. Flash card needs both title and body before it can be added to the Subject");
        }
    }

    public void setCurrentCardTitle(String title) {
        this.currentCard.setTitle(title);
    }

    public void setCurrentCardBody(String body) {
        this.currentCard.setBody(body);
    }

    public void showCurrentCard() {
        if (this.currentCard != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("Flash Card details:\n");
            sb.append(this.currentCard.getTitle());
            sb.append("\n----------\n");
            sb.append(this.currentCard.getBody());
            sb.append("\n----------");
            System.out.println(sb.toString());
        } else {
            System.out.println("No card was loaded. Create card or load existing");
        }
    }

    public void createSubjectsDir() {
        String userDir = System.getProperty("user.dir");
        try {
            FileUtils.forceMkdir(new File(userDir + File.separator + "subjects"));
        } catch (IOException e) {
            // TODO
            // Log this exception
            e.printStackTrace();
        }
    }

    public void saveDateStampedFileForcefully() {
        String fileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss'.subject.xml'").format(new Date());
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        XmlSubject tmpXmlSubject = new XmlSubject(this.subject);
        File outFile = new File(dir, fileName);
        try {
            FileUtils.forceMkdirParent(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tmpXmlSubject.writeToFile(outFile);
    }

    public void saveDateStampedAndTitledFileForcefully() {
        String fileName = this.subject.getName() + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss'.subject.xml'").format(new Date());
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        XmlSubject tmpXmlSubject = new XmlSubject(this.subject);
        File outFile = new File(dir, fileName);
        try {
            FileUtils.forceMkdirParent(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tmpXmlSubject.writeToFile(outFile);
    }

    public boolean saveSubjectAsFilename(String fileName) {
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        File file = new File(dir, fileName);
        if(file.exists()) {
            System.out.println("File " + fileName + " already exist in subjects folder");
            return false;
        }
        XmlSubject tmpXmlSubject = new XmlSubject(this.subject);
        File outFile = new File(dir, fileName);
        try {
            FileUtils.forceMkdirParent(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tmpXmlSubject.writeToFile(outFile);
        return true;
    }

    public void saveFile(String fileName) {
        if(saveSubjectAsFilename(fileName + ".xml")) {
            System.out.println("Current Subject saved as " + fileName + ".xml in subjects directory.");
        }
    }

    public void listSaved() {
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        Collection<File> files = FileUtils.listFiles(dir, new String[]{"xml"}, false);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    public void listSavedWithNames() {
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        Collection<File> files = FileUtils.listFiles(dir, new String[]{"xml"}, false);
        for (File file : files) {
            XmlSubject subject = new XmlSubject(file);
            System.out.println(file.getName() + ",Subject name: " + subject.getSubject().getName());
        }
    }

    public void loadSavedSubjectFile(String xmlFileName) {
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        this.loadSubjectFromXmlFile(dir, xmlFileName);
    }

    public void loadCard(int cardNumber) {
        if (cardNumber >= this.subject.getCards().size()) {
            System.out.println("Cards from 1 - " + this.subject.getCards().size() + " available");
        } else {
            this.currentCard = this.subject.getCards().get(cardNumber - 1);
        }
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void playSubject(String xmlFileName) {
        File dir = new File(System.getProperty("user.dir") + File.separator + "subjects");
        File subjectFile = new File(dir, xmlFileName);
        if(this.player == null) {
            this.player = new Player();
            this.player.load(subjectFile);
        }
    }

    public void playNext() {
        if(this.player != null) {
            this.player.next();
        }
    }

    public void playPrevious() {
        if(this.player != null) {
            this.player.previous(

            );
        }
    }

}
