package darko.hjug.flashcards;

import java.util.ArrayList;

public class Subject {

    public final static String nameXmlNode="subject";
    public final static String cardsXmlNode="cards";
    public final static String nameXmlAttribute="name";
    private String name;

    private ArrayList<FlashCard> cards;

    public Subject(String name) {
        this.name = name;
        cards = new ArrayList<FlashCard>();
    }

    public void addCard(FlashCard card) {
        this.cards.add(card);
    }

    public void removeCard(FlashCard card) {
        this.cards.remove(card);
    }

    public void removeCard(int index) {
        this.cards.remove(index);
    }

    public void insertCard(FlashCard card, int index) {
        this.cards.add(index, card);
    }

    public void switchCards(FlashCard card1, FlashCard card2) {
        int index1 = this.cards.indexOf(card1);
        int index2 = this.cards.indexOf(card2);
        this.cards.remove(card1);
        this.cards.remove(card2);
        if(index1 < index2) {
            insertCard(card2, index1);
            insertCard(card1, index2);
        }
        else{
            insertCard(card1, index2);
            insertCard(card2, index1);
        }
    }

    public void switchCards(int index1, int index2) {
        FlashCard card1 = this.cards.get(index1);
        FlashCard card2 = this.cards.get(index2);
        switchCards(card1, card2);
    }

    public void cardUp(FlashCard card) {
        int index = this.cards.indexOf(card);
        if(index <= 0) {
            return;
        }
        switchCards(index, index-1);
    }

    public void cardUp(int index) {
        if(index <= 0) {
            return;
        }
        switchCards(index, index-1);
    }

    public void cardDown(int index) {
        if(index >= this.cards.size()-1) {
            return;
        }
        switchCards(index, index+1);
    }

    public void cardDown(FlashCard card) {
        int index = this.cards.indexOf(card);
        if(index >= this.cards.size()-1) {
            return;
        }
        switchCards(index, index+1);
    }



    public FlashCard getCard(int index) {
        FlashCard card = this.cards.get(index);
        return card;
    }

    public FlashCard getCard(String title) {
        for(FlashCard card : this.cards) {
            if(card.getTitle().equalsIgnoreCase(title)) {
                return card;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<FlashCard> getCards() {
        return cards;
    }

    public int getCardCount() {
        return this.cards.size();
    }

}
