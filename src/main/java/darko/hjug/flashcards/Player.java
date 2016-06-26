package darko.hjug.flashcards;

import java.io.File;
import java.util.List;
public class Player {

    private Subject subject;
    private XmlSubject xmlSubject;

    // player data
    private int currentCardNum;
    private FlashCard currentCard;

    public Player() {
    }

    public void load(String xmlFilePath) {
        this.xmlSubject = new XmlSubject(xmlFilePath);
        this.setSubject(this.xmlSubject.getSubject());
        reset();
    }

    public void load(File xmlFile) {
        this.xmlSubject = new XmlSubject(xmlFile);
        this.setSubject(this.xmlSubject.getSubject());
        reset();
    }

    public void reset() {
        setCurrentCardNum(0);
        this.displayCurrentCard();
    }

    public void next() {
        if(this.getCurrentCardNum() >= this.getSubject().getCards().size()-1) {
            return;
        }
        this.setCurrentCardNum(this.getCurrentCardNum()+1);
        displayCurrentCard();
    }

    public void previous() {
        if(this.getCurrentCardNum() <= 0) {
            return;
        }
        this.setCurrentCardNum(this.getCurrentCardNum()-1);
        displayCurrentCard();
    }
    // play
    // next
    // rewind
    // slower, faster
    // set time
    // slideshow
    // studying mode: show question on >, show answer on next >
    public void printSlide(String title, List<String> lines) {

    }

    public void printEmptyBox(int width, int height, char symbol) {
        StringBuffer buffer = new StringBuffer();
        int bodyLinesCount = height - 2;
        fillFullLine(buffer, width, symbol);
        fillEmptyLines(buffer, width, symbol, bodyLinesCount);
        fillFullLine(buffer, width, symbol);
        System.out.println(buffer.toString());
    }

    public void displayCard(FlashCard card) {
        // w, h, char
        // (64, 15, '#');
        StringBuffer buffer = new StringBuffer();
        fillFullLine(buffer, 64, '#');
        String wrappedTitle = TextUtils.wrapAndEnclose(card.getTitle(), 60, null, true, '#', 64);
        buffer.append(wrappedTitle);
        buffer.append('\n');
        fillFullLine(buffer, 64, '#');
        String wrappedBody = TextUtils.wrapAndEnclose(card.getBody(), 60, null, true, '#', 64);
        buffer.append(wrappedBody);
        buffer.append('\n');
        fillFullLine(buffer, 64, '#');
//        System.out.println(card.getTitle());
//        System.out.println(card.getBody());
        System.out.println(buffer.toString());
    }

    public void displayCurrentCard() {
        this.setCurrentCard(this.subject.getCard(this.getCurrentCardNum()));
        this.displayCard(this.getCurrentCard());
    }

    private void fillEmptyLines(StringBuffer buffer, int width, char symbol, int linesCount) {
        for (int i = 0; i < linesCount; i++) {
            fillEmptyBodyLine(buffer, width, symbol);
        }
    }

    private void fillFullLine(StringBuffer buffer, int width, char symbol) {
        for(int i=0; i<width; i++) {
            buffer.append(symbol);
        }
        buffer.append("\n");
    }

    private void fillEmptyBodyLine(StringBuffer buffer, int width, char symbol) {
        buffer.append(symbol);
        for (int i = 0; i < width-2 ; i++) {
            buffer.append(' ');
        }
        buffer.append(symbol);
        buffer.append("\n");
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.printEmptyBox(64, 15, '#');
    }

    public FlashCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(FlashCard currentCard) {
        this.currentCard = currentCard;
    }

    public int getCurrentCardNum() {
        return currentCardNum;
    }

    public void setCurrentCardNum(int currentCardNum) {
        this.currentCardNum = currentCardNum;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public XmlSubject getXmlSubject() {
        return xmlSubject;
    }

    public void setXmlSubject(XmlSubject xmlSubject) {
        this.xmlSubject = xmlSubject;
    }

}
