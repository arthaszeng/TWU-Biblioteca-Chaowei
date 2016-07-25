package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class ReturnMovieOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.returnOneResource("MOVIE");
        return true;
    }
}
