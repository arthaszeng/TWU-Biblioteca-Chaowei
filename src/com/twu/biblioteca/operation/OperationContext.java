package com.twu.biblioteca.operation;

import com.twu.biblioteca.Biblioteca;

public class OperationContext {
    private OperationInterface operation;

    public boolean doOperation(Biblioteca biblioteca, String operationType) {
        operation = OperationFactory.getInstance().creator(operationType);
        return operation != null && operation.doOperation(biblioteca);
    }

}
