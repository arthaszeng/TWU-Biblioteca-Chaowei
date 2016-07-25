package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class ListMovieOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.listRepositoryWithAllAttributes("MOVIE");
        return true;
    }
}
