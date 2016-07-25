package com.twu.biblioteca.Operation;

import com.twu.biblioteca.BibliotecaApp;

public class Context {
    private Operation operation;

    public boolean doOperation(BibliotecaApp bibliotecaApp, String operationType) {
        operation = OperationFactory.getInstance().creator(operationType);
        return operation != null && operation.doOperation(bibliotecaApp);
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
