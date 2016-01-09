package com.psyndicate.skitter.model;

 /**
  * Model of a single Skeet message
  **/
public class Skeet {
    public static int MAX_SKEET_LENGTH = 160;

    private String text;
    private long timestamp;

    public void setText(String text) {
        if(text == null)
            return;

        // Truncate Skeets over 160 characters
        if(text.length() > MAX_SKEET_LENGTH) {
            this.text = text.substring(0, MAX_SKEET_LENGTH);
        } else {
            this.text = text;
        }
    }

    public String getText() {
        return this.text;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
};

