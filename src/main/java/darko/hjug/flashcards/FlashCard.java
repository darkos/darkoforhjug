package darko.hjug.flashcards;

public class FlashCard {

    public final static String cardXmlNode="card";
    public final static String titleXmlNode="title";
    public final static String bodyXmlNode="body";

    private String title="";
    private String body="";

    public FlashCard(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public FlashCard() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean canBeAdded() {
        return (this.getTitle().length() > 0 && this.getBody().length() > 0);
    }

}
