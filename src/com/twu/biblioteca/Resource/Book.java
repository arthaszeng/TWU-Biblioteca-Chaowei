package com.twu.biblioteca.resource;

import java.util.Stack;

public class Book implements Resource {
    private String author;
    private String publishDate;
    private String name;
    private Stack<String> holdersStack = new Stack<>();

    public Book(String bookName, String author, String publishDate) {
        this.name = bookName;
        this.author = author;
        this.publishDate = publishDate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAll() {
        return getName()+"\t"+author+"\t"+publishDate;
    }

    @Override
    public void updateHolder(String libraryNumber) {
        holdersStack.push(libraryNumber);
    }
}
