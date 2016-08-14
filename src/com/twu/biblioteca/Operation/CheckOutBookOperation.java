package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class CheckOutBookOperation implements OperationInterface {
    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.checkOutOneResource("BOOK");
        return true;
    }
}
