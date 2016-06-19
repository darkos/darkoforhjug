package darko.hjug.flashcards;

import junit.framework.TestCase;

public class FlashCardTest extends TestCase {

    protected void setUp() {
    }

    protected void tearDown() {
    }

    public void testCardArgsConstructor() {
        FlashCard card = new FlashCard("Card Title", "Card Body");
        assertEquals("Card Title", card.getTitle());
        assertEquals("Card Body", card.getBody());
    }

    public void testCardNoArgsConstructor() {
        FlashCard card = new FlashCard();
        card.setTitle("Card Title");
        card.setBody("Card Body");
        assertEquals("Card Title", card.getTitle());
        assertEquals("Card Body", card.getBody());
    }

}
