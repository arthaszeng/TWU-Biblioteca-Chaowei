package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class ReturnBookOperation implements Operation{
    @Override
    public boolean doOperation(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.returnOneResource("BOOK");
        return true;
    }
}
