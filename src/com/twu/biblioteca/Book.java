package com.twu.biblioteca;

public class Book {
    private String author;
    private String publishDate;
    private String name;

    public Book(String bookName, String author, String publishDate) {
        this.name = bookName;
        this.author = author;
        this.publishDate = publishDate;
    }

    String getName() {
        return this.name;
    }

    private String getAuthor() {
        return author;
    }

    private String getPublishDate() {
        return publishDate;
    }

    String getAll() {
        return getName()+"\t"+getAuthor()+"\t"+getPublishDate();
    }
}
