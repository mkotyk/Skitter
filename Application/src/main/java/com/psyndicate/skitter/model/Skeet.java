package com.psyndicate.skitter.model;

 /**
  * Model of a single Skeet message
  **/
public class Skeet implements Comparable {
    public static int MAX_SKEET_LENGTH = 160;

    private String poster;
    private String text;
    private long timestamp;

    public Skeet() {
        text = null;
        timestamp = -1;
    }

    public Skeet(String text) {
        setText(text);
        this.timestamp = System.currentTimeMillis();
    }

    public Skeet(String poster, String text, long timestamp) {
        this.poster = poster;
        setText(text);
        this.timestamp = timestamp;
    }

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

    public int compareTo(Object other) {
        Skeet otherSkeet = (Skeet) other;
        return (int)(otherSkeet.timestamp - this.timestamp);
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPoster() {
     return this.poster;
    }
};

