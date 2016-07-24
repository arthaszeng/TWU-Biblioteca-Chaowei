package com.twu.biblioteca;

public class Book implements Resource {
    private String author;
    private String publishDate;
    private String name;

    public Book(String bookName, String author, String publishDate) {
        this.name = bookName;
        this.author = author;
        this.publishDate = publishDate;
    }

    public String getName() {
        return this.name;
    }

    public String getAll() {
        return getName()+"\t"+author+"\t"+publishDate;
    }
}
