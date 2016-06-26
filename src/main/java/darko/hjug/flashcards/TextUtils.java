package darko.hjug.flashcards;

import org.apache.commons.lang3.SystemUtils;

public class TextUtils {

    public TextUtils() {
    }

    public void wrapAndEncloseLongLineOfText(String text, int wrapLength, char charEncloser) {
        String wrapped = TextUtils.wrapAndEnclose(text, wrapLength, null, true, charEncloser, wrapLength + 4);
        System.out.println(wrapped);
    }

    public static String wrap(final String str, int wrapLength, String newLineStr, final boolean wrapLongWords) {
        if (str == null) {
            return null;
        }
        if (newLineStr == null) {
            newLineStr = SystemUtils.LINE_SEPARATOR;
        }
        if (wrapLength < 1) {
            wrapLength = 1;
        }
        final int inputLineLength = str.length();
        int offset = 0;
        final StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);

        while (offset < inputLineLength) {
            if (str.charAt(offset) == ' ') {
                offset++;
                continue;
            }
            // only last line without leading spaces is left
            if (inputLineLength - offset <= wrapLength) {
                break;
            }
            int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);

            if (spaceToWrapAt >= offset) {
                // normal case
                wrappedLine.append(str.substring(offset, spaceToWrapAt));
                wrappedLine.append(newLineStr);
                offset = spaceToWrapAt + 1;

            } else {
                // really long word or URL
                if (wrapLongWords) {
                    // wrap really long word one line at a time
                    wrappedLine.append(str.substring(offset, wrapLength + offset));
                    wrappedLine.append(newLineStr);
                    offset += wrapLength;
                } else {
                    // do not wrap really long word, just extend beyond limit
                    spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
                    if (spaceToWrapAt >= 0) {
                        wrappedLine.append(str.substring(offset, spaceToWrapAt));
                        wrappedLine.append(newLineStr);
                        offset = spaceToWrapAt + 1;
                    } else {
                        wrappedLine.append(str.substring(offset));
                        offset = inputLineLength;
                    }
                }
            }
        }

        // Whatever is left in line is short enough to just pass through
        wrappedLine.append(str.substring(offset));

        return wrappedLine.toString();
    }

    public static String wrapAndEnclose(final String str, int wrapLength, String newLineStr, final boolean wrapLongWords, char encloser, int enclosingWidth) {
        if (str == null) {
            return null;
        }
        if (newLineStr == null) {
            newLineStr = SystemUtils.LINE_SEPARATOR;
        }
        if (wrapLength < 1) {
            wrapLength = 1;
        }
        final int inputLineLength = str.length();
        int offset = 0;
        final StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);

        while (offset < inputLineLength) {
            if (str.charAt(offset) == ' ') {
                offset++;
                continue;
            }
            // only last line without leading spaces is left
            if (inputLineLength - offset <= wrapLength) {
                break;
            }
            int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);

            if (spaceToWrapAt >= offset) {
                // normal case
                String enclosedStr = enclose(str.substring(offset, spaceToWrapAt), encloser, enclosingWidth);
                wrappedLine.append(enclosedStr);
                wrappedLine.append(newLineStr);
                offset = spaceToWrapAt + 1;

            } else {
                // really long word or URL
                if (wrapLongWords) {
                    // wrap really long word one line at a time
                    String enclosedStr = enclose(str.substring(offset, wrapLength + offset), encloser, enclosingWidth);
                    wrappedLine.append(enclosedStr);
                    wrappedLine.append(newLineStr);
                    offset += wrapLength;
                } else {
                    // do not wrap really long word, just extend beyond limit
                    spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
                    if (spaceToWrapAt >= 0) {
                        String enclosedStr = enclose(str.substring(offset, spaceToWrapAt), encloser, enclosingWidth);
                        wrappedLine.append(str.substring(offset, spaceToWrapAt));
                        wrappedLine.append(newLineStr);
                        offset = spaceToWrapAt + 1;
                    } else {
                        String enclosedStr = enclose(str.substring(offset), encloser, enclosingWidth);
                        wrappedLine.append(enclosedStr);
                        offset = inputLineLength;
                    }
                }
            }
        }

        // Whatever is left in line is short enough to just pass through
        String enclosedStr = enclose(str.substring(offset), encloser, enclosingWidth);
        wrappedLine.append(enclosedStr);

        return wrappedLine.toString();
    }

    public static String enclose(String text, char encloser, int width) {
        StringBuffer buffer = new StringBuffer();
        int spaceAvailable = width - 4;
        int spaceChars = spaceAvailable - text.length();
        buffer.append(encloser);
        buffer.append(' ');
        buffer.append(text);
        for (int i = 0; i < spaceChars; i++) {
            buffer.append(' ');
        }
        buffer.append(' ');
        buffer.append(encloser);
        return buffer.toString();
    }

    public static void main(String[] args) {
        String text = "Immutable classes are Java classes whose objects can not be modified once created. Any modification in Immutable object result in new object. For example is String is immutable in Java. Mostly Immutable are also final in Java, in order to prevent sub class from overriding methods in Java which can compromise Immutability. You can achieve same functionality by making member as non final but private and not modifying them except in constructor.";
        TextUtils utils = new TextUtils();
        utils.wrapAndEncloseLongLineOfText(text, 60, '#');
    }

}
