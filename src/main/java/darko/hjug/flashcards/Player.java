package darko.hjug.flashcards;

import java.util.List;

public class Player {

    public Player() {

    }

    // load
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

}
