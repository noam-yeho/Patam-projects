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
        double tempDouble;
        try {
            tempDouble = Double.parseDouble(asText);
        } catch (NumberFormatException e) {
            tempDouble = Double.NaN;
        }
        this.asDouble = tempDouble;
        this.date = new Date();
    }

    public Message(byte[] data) {
        this.data = data;
        this.asText = new String(data);
        double tempDouble;
        try {
            tempDouble = Double.parseDouble(this.asText);
        } catch (NumberFormatException e) {
            tempDouble = Double.NaN;
        }
        this.asDouble = tempDouble;
        this.date = new Date();
    }

    public Message(double asDouble) {
        this.asDouble = asDouble;
        this.asText = Double.toString(asDouble);
        this.data = this.asText.getBytes();
        this.date = new Date();
    }
}
