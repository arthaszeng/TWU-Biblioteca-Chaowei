package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class CheckOutMovieOperation implements OperationInterface {

    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.checkOutOneResource("MOVIE");
        return true;
    }
}
