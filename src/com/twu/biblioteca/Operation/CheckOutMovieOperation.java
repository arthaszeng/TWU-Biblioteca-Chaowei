package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;
import com.twu.biblioteca.Resource.ResourceManager;

public class CheckOutMovieOperation implements Operation{

    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.checkOutOneResource("MOVIE");
        return true;
    }
}
