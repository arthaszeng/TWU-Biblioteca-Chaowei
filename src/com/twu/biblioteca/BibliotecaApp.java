package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;


class BibliotecaApp {
    List<Resource> bookList;
    List<Resource> movieList;
    List<Resource> checkedBookList;
    List<Resource> checkedMovieList;
    private List<String> optionList;
    private MockedIO mockedIO;
    private CustomerDataManagement customerDataManagement;

    BibliotecaApp(List<Resource> bookList,
                  List<Resource> movieList,
                  List<Resource> checkedBookList,
                  List<Resource> checkedMovieList,
                  List<String> optionList,
                  MockedIO mockedIO,
                  CustomerDataManagement customerDataManagement) {
        this.bookList = bookList;
        this.movieList = movieList;
        this.checkedBookList = checkedBookList;
        this.checkedMovieList = checkedMovieList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
        this.customerDataManagement = customerDataManagement;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    boolean addResource(Resource resource, List<Resource> list) {
        if (resource != null) {
            list.add(resource);
            return true;
        } else
            return false;
    }

    void addOption(String option) {
        this.optionList.add(option);
    }

    boolean listRepository(List<Resource> list) {
        if (list.isEmpty()) {
            return false;
        } else {
            for (Resource resource : list) {
                mockedIO.output(resource.getName());
            }
            return true;
        }
    }

    boolean listRepositoryWithAllAttributes(List<Resource> list) {
        if (list.isEmpty()) {
            return false;
        } else {
            for (Resource resource : list) {
                mockedIO.output(resource.getAll());
            }
            return true;
        }
    }

    boolean showOptions() {
        if (optionList.isEmpty()) {
            return false;
        } else {
            for (String option : optionList) {
                mockedIO.output(option);
            }
            return true;
        }
    }

    boolean selectOption() {
        switch (mockedIO.input().toUpperCase()) {
            case "LB":
                listRepositoryWithAllAttributes(bookList);
                return true;
            case "CB":
                checkoutOneResource("Book");
                return true;
            case "CM":
                checkoutOneResource("movie");
            case "QUIT":
                mockedIO.output("Over!");
                return false;
            default:
                mockedIO.output("Select a valid option!");
                return true;
        }
    }

    void keepCycle() {
        showOptions();
        while (true) {
            if (!selectOption()) {
                break;
            }
        }
    }

    boolean checkoutOneResource(String whichResource) {
        List<Resource> resources = whichResource.toUpperCase().equals("BOOK") ? bookList : movieList;
        List<Resource> checkedResources = whichResource.toUpperCase().equals("BOOK") ? checkedBookList : checkedMovieList;
        String InputResourceName = mockedIO.input();

        if (resources.isEmpty()) {
            return false;
        }

        for (Resource resource : resources) {
            if (resource.getName().equals (InputResourceName) && resources.remove(resource) && checkedResources.add(resource)) {
                    mockedIO.output("Thank you! Enjoy the " + whichResource.toLowerCase() + ".");
                    return true;
            }
        }

        mockedIO.output("That " + whichResource.toLowerCase() + " is not available.");
        return false;
    }

    Resource queryOneResource(String resourceName, List<Resource> resources) {
        for (Resource resource : resources) {
            if (resource.getName().equals(resourceName)) {
                return resource;
            }
        }
        return null;
    }

    boolean returnOneResource(String whichResource) {
        List<Resource> resources = whichResource.toUpperCase().equals("BOOK") ? bookList : movieList;
        List<Resource> checkedResources = whichResource.toUpperCase().equals("MOVIE") ? checkedMovieList : checkedBookList;


        String inputOfName = mockedIO.input();
        if (inputOfName.isEmpty()) {
//            mockedIO.output("error input");
            return false;
        } else {
            Resource queryOneResource = queryOneResource(inputOfName, checkedResources);
            if (queryOneResource == null) {
                mockedIO.output("That is not a valid " + whichResource.toLowerCase() + " to return.");
                return false;
            } else {
                if (checkedResources.remove(queryOneResource) && resources.add(queryOneResource)) {
                    mockedIO.output("Thank you for returning the " + whichResource.toLowerCase() + ".");
                    return true;
                } else
                    return false;
            }
        }
    }

    boolean login() {
        mockedIO.output("Please input your account:");
        String account = mockedIO.input();
        mockedIO.output("Please input your password:");
        String password = mockedIO.input();

        User user = customerDataManagement.login(account, password);
        return user != null;
    }


}
