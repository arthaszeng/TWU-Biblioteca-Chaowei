package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class ReturnMovieOperation implements OperationInterface {
    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.returnOneResource("MOVIE");
        return true;
    }
}
