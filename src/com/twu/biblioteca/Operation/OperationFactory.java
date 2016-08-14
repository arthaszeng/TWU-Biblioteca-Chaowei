package com.twu.biblioteca.operation;

import java.util.HashMap;
import java.util.Map;

class OperationFactory {
    private static OperationFactory factory = new OperationFactory();
    private OperationFactory(){}

    private static Map<String, OperationInterface> operationMap = new HashMap<>();

    static{
        operationMap.put("LB", new ListBookOperation());
        operationMap.put("LM", new ListMovieOperation());
        operationMap.put("CB", new CheckOutBookOperation());
        operationMap.put("CM", new CheckOutMovieOperation());
        operationMap.put("RB", new ReturnBookOperation());
        operationMap.put("RM", new ReturnMovieOperation());
        operationMap.put("QUIT", new QuitOperation());
    }

    OperationInterface creator(String type){
        return operationMap.get(type);
    }

    static OperationFactory getInstance(){
        return factory;
    }

}
