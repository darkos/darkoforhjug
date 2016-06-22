package darko.hjug.flashcards;

import org.dom4j.*;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class XmlSubject {

    private Subject subject;
    private Document document;
    private DocumentFactory factory;
    private OutputFormat outFormat;

    public XmlSubject(Subject subject) {
        this.subject = subject;
        initXml();
    }

    public XmlSubject(String xmlFile) {
        initDocumentFromXml(xmlFile);
    }

    public void initDocumentFromXml(String xmlFile) {
        SAXReader reader = new SAXReader();
        try {
            this.document = reader.read(xmlFile);
        } catch(DocumentException de) {
            de.printStackTrace();
        }
        Element subjectElement = this.document.getRootElement();
        Attribute nameAttr = subjectElement.attribute(Subject.nameXmlAttribute);
        this.subject = new Subject(nameAttr.getText());
        Element cards = subjectElement.element(Subject.cardsXmlNode);
        List elementList = cards.elements();
        for(Object obj : elementList) {
            Element cardElement = (Element)obj;
            this.subject.addCard(readCard(cardElement));
        }
    }

    private FlashCard readCard(Element element) {
        FlashCard card = new FlashCard();
        Element titleElement = element.element(FlashCard.titleXmlNode);
        card.setTitle(titleElement.getText());
        Element bodyElement = element.element(FlashCard.bodyXmlNode);
        card.setBody(bodyElement.getText());
        return card;
    }

    private void initXml() {
        this.factory = DocumentFactory.getInstance();
        this.document = DocumentFactory.getInstance().createDocument();
        Element root = this.document.addElement(Subject.nameXmlNode);
        root.addAttribute(Subject.nameXmlAttribute, subject.getName());
        Element cardsElement = root.addElement(Subject.cardsXmlNode);
        for(FlashCard card : this.subject.getCards()) {
            cardsElement.add(getElement(card));
        }
    }

    private Element getElement(FlashCard card) {
        Element cardElement = this.factory.createElement(FlashCard.cardXmlNode);
        Element titleElement = cardElement.addElement(FlashCard.titleXmlNode);
        titleElement.addText(card.getTitle());
        Element bodyElement = cardElement.addElement(FlashCard.bodyXmlNode);
        bodyElement.setText(card.getBody());
        return cardElement;
    }

    public Document getDocument() {
        return this.document;
    }

    public void outToSystemOut() {
        this.outFormat = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(System.out, this.outFormat);
        } catch(UnsupportedEncodingException exc) {
            exc.printStackTrace();
        }
        try {
            writer.write(this.getDocument());
            writer.flush();
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public void writeToFile(String filePath) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        File file = new File(filePath);
        try {
            writer = new XMLWriter(
                    new FileWriter(file), format);
        } catch (UnsupportedEncodingException exc) {
            exc.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write(this.getDocument());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToTmpFile() {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + "/output.xml");
        System.out.println("TEMPORARY FILE:" + tmpFile.getAbsolutePath());
        try {
            writer = new XMLWriter(
                    new FileWriter(tmpFile), format);
        } catch (UnsupportedEncodingException exc) {
            exc.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write(this.getDocument());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Subject getSubject() {
        return this.subject;
    }
}
