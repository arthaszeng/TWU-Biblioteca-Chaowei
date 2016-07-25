package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class CheckOutBookOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.checkOutOneResource("BOOK");
        return true;
    }
}
