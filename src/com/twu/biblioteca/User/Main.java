package com.twu.biblioteca.User;

import com.twu.biblioteca.Operation.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]) {
        Map<String, Operation> operationMap = new HashMap<>();

        operationMap.put("LB", new ListBookOperation());
        operationMap.put("LM", new ListMovieOperation());
        operationMap.put("CB", new CheckOutBookOperation());
        operationMap.put("CM", new CheckOutMovieOperation());
        operationMap.put("RB", new ReturnBookOperation());
        operationMap.put("RM", new ReturnMovieOperation());
        operationMap.put("QUIT", new ReturnMovieOperation());

        System.out.println(operationMap.get("LB") == null);
    }
}