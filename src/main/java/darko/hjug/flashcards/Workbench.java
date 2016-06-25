package darko.hjug.flashcards;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Workbench {

    private Subject subject;
    private FlashCard currentCard;
    private String xmlFilePath;
    private XmlSubject xmlSubject;

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

    public void subjectDetails() {
        this.subject.details();
    }

    public void setSubjectName(String name) {
        this.subject.setName(name);
    }

    public void createNewCard() {
        this.currentCard = new FlashCard();
    }

    public void addCurrentCard() {
        if(this.currentCard.canBeAdded()) {
            this.subject.addCard(this.currentCard);
        }
        else {
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
        StringBuffer sb = new StringBuffer();
        sb.append("Flash Card details:\n");
        sb.append(this.currentCard.getTitle());
        sb.append("\n----------\n");
        sb.append(this.currentCard.getBody());
        sb.append("\n----------");
        System.out.println(sb.toString());
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
        String fileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.subject.xml'").format(new Date());
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

}
