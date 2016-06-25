package darko.hjug.flashcards;

public class Workbench {

    private Subject subject;
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

}
