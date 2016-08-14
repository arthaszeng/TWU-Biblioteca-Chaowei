package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

class ListMovieOperation implements OperationInterface {
    @Override
    public boolean doOperation(Biblioteca biblioteca) {
        biblioteca.listRepositoryWithAllAttributes("MOVIE");
        return true;
    }
}
