package com.example.keepnotes.models;

public class Notes {

    private int id;
    private String text;

    public Notes(int id, String text) {
        this.setId(id);
        this.setText(text);
    }

    public Notes(String text) {
        this.setText(text);
    }

    public Notes() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
