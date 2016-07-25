package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class CheckOutMovieOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.checkOutOneResource("MOVIE");
        return true;
    }
}
