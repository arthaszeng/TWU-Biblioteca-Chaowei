package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class ListBookOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.listRepositoryWithAllAttributes("BOOK");
        return true;
    }
}
