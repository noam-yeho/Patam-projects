package test;

import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String asText) {
        this.asText = asText;
        this.data = asText.getBytes();
        try {
            this.asDouble = Double.parseDouble(asText);
        } catch (NumberFormatException e) {
            this.asDouble = Double.NaN;
        }
        this.date = new Date();
    }
}
