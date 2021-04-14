package com.example.firstproject;

public class Record {
    private String name;
    private String date;
    private int color;
    private final int id;
    public static int newid = 1;


    public Record(String name, String date, int color, int id) {
        this.name = name;
        this.date = date;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public String getDate() {
        return this.date;
    }
    public int getColor() {
        return this.color;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getRecId() {
        return this.id;
    }

}
