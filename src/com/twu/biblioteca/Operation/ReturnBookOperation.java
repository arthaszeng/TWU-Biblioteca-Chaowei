package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class ReturnBookOperation implements OperationInterface {
    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.returnOneResource("BOOK");
        return true;
    }
}
