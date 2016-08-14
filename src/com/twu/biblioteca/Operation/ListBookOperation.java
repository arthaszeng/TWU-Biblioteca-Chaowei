package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class ListBookOperation implements OperationInterface {
    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.listRepositoryWithAllAttributes("BOOK");
        return true;
    }
}
