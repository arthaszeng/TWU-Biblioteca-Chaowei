package com.twu.biblioteca;

public interface Resource {
    String getName();
    String getAll();
    void updateHolder(String libraryNumber);
    String getHolder();
}
