package darko.hjug.flashcards;

import junit.framework.TestCase;
import org.dom4j.Document;

public class SubjectTest extends TestCase{

    private Subject subject;

    protected void setUp() {
        subject = new Subject("Test Subject in setUp()");
        for(int i=1; i<11; i++) {
            subject.addCard(new FlashCard("Card Title" + i,"Card Body" + i));
        }
    }

    protected void tearDown() {
        subject = null;
    }

    public void testSubjectOne() {
        Subject subject1 = new Subject("Test Subject");
        assertEquals("Test Subject", subject1.getName());
    }

    public void testAddCards() {
        assertEquals(10, subject.getCardCount());
    }

    public void testGetCardByIndex() {
        FlashCard card = subject.getCard(7);
        assertEquals("Card Title8", card.getTitle());
    }

    public void testGetCardByTitle() {
        FlashCard card = subject.getCard("cArd tiTLe7");
        assertEquals("Card Title7", card.getTitle());
    }

    public void testCardIndexGottenByTitle() {
        FlashCard card1 = subject.getCard("cArd tiTLe7");
        FlashCard card2 = subject.getCard(6);
        assertEquals(card1, card2);
    }

    public void testRemoveCardByIndex() {
        int index = 4;
        FlashCard card1 = subject.getCard(index);
        assertEquals("Card Title5", card1.getTitle());
        subject.removeCard(index);
        FlashCard card2 = subject.getCard(index);
        assertEquals("Card Title6", card2.getTitle());
        assertNotSame(card1, card2);
    }

    public void testRemoveCardByObject() {
        FlashCard card = subject.getCard("Card Title5");
        subject.removeCard(card);
        FlashCard card1 = subject.getCard("Card Title5");
        assertNull(card1);
    }

    public void testSwitchCards() {
        FlashCard card1 = subject.getCard(5);
        FlashCard card2 = subject.getCard(8);
        subject.switchCards(card1, card2);
        FlashCard card1a = subject.getCard(5);
        FlashCard card2a = subject.getCard(8);
        assertEquals(card1.getTitle(), card2a.getTitle());
        assertEquals(card2.getTitle(), card1a.getTitle());
    }

    public void testCardUpByObject() {
        FlashCard card1 = subject.getCard(1);
        this.subject.cardUp(card1);
        FlashCard card2 = subject.getCard(0);
        assertEquals(card1.getTitle(), card2.getTitle());
        this.subject.cardUp(card2);
        FlashCard card3 = subject.getCard(0);
        assertEquals(card2.getTitle(), card3.getTitle());
    }

    public void testCardUpByIndex() {
        FlashCard card1 = subject.getCard(1);
        this.subject.cardUp(1);
        FlashCard card2 = subject.getCard(0);
        assertEquals(card1.getTitle(), card2.getTitle());
        this.subject.cardUp(0);
        FlashCard card3 = subject.getCard(0);
        assertEquals(card2.getTitle(), card3.getTitle());
    }

    public void testCardDownByIndex() {
        int oneBeforeLast = this.subject.getCardCount()-2;
        FlashCard card1 = subject.getCard(oneBeforeLast);
        this.subject.cardDown(oneBeforeLast);
        FlashCard card2 = subject.getCard(oneBeforeLast+1);
        assertEquals(card1.getTitle(), card2.getTitle());
        this.subject.cardDown(oneBeforeLast+1);
        FlashCard card3 = subject.getCard(oneBeforeLast+1);
        assertEquals(card2.getTitle(), card3.getTitle());
    }

    public void testSetSubjectName() {
        subject.setName("Change Subject Name");
        assertEquals("Change Subject Name", subject.getName());
    }

    public void testSubjectDefaultConstructor() {
        Subject noName = new Subject();
        assertEquals("unnamed", noName.getName());
    }

    public void testCardDownByObject() {
        int oneBeforeLast = this.subject.getCardCount()-2;
        FlashCard card1 = subject.getCard(oneBeforeLast);
        this.subject.cardDown(card1);
        FlashCard card2 = subject.getCard(oneBeforeLast+1);
        assertEquals(card1.getTitle(), card2.getTitle());
        this.subject.cardDown(card2);
        FlashCard card3 = subject.getCard(oneBeforeLast+1);
        assertEquals(card2.getTitle(), card3.getTitle());
    }

    public void testSubjectToXml() {
        XmlSubject xmlSubject = new XmlSubject(subject);
        xmlSubject.writeToFile("/home/darko/tmp/darkotest.xml");
//        xmlSubject.outToSystemOut();
//        System.out.println(xmlSubject.getDocument().asXML());
        assertNotNull(xmlSubject);
    }

    public void testXmlToSubject() {
        XmlSubject xmlSubject = new XmlSubject("/home/darko/tmp/darkotest.xml");
        Subject subjectFromXml = xmlSubject.getSubject();
        FlashCard card = subjectFromXml.getCard("Card Title7");
        assertEquals("Card Title7", card.getTitle());
    }

}
