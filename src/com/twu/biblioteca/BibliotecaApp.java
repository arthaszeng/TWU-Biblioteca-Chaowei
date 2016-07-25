package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;


class BibliotecaApp {
    List<Resource> bookList = new ArrayList<>();
    List<Resource> movieList = new ArrayList<>();
    List<Resource> checkedBookList = new ArrayList<>();
    List<Resource> checkedMovieList = new ArrayList<>();
    private List<String> optionList = new ArrayList<>();
    private MockedIO mockedIO;
    private CustomerDataManager customerDataManager;
    private User currentUser = null;

    BibliotecaApp(MockedIO mockedIO, CustomerDataManager customerDataManager) {
        this.mockedIO = mockedIO;
        this.customerDataManager = customerDataManager;
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

    void listRepository(List<Resource> list) {
        for (Resource resource : list) {
            mockedIO.output(resource.getName());
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

        if (resources.isEmpty() || !verifyUser()) {
            return false;
        }

        for (Resource resource : resources) {
            if (resource.getName().equals(InputResourceName)) {
                resources.remove(resource);
                resource.updateHolder(getCurrentUser().getLibraryNumber());
                checkedResources.add(resource);

                mockedIO.output("Thank you! Enjoy the " + whichResource.toLowerCase() + ".");
                return true;
            }
        }

        mockedIO.output("That " + whichResource.toLowerCase() + " is not available.");
        return false;
    }

    private boolean verifyUser() {
        return currentUser != null;
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
        if (inputOfName.isEmpty() || !verifyUser()) {
//            mockedIO.output("error input");
            return false;
        } else {
            Resource queriedOneResource = queryOneResource(inputOfName, checkedResources);
            if (queriedOneResource == null) {
                mockedIO.output("That is not a valid " + whichResource.toLowerCase() + " to return.");
                return false;
            } else {
                checkedResources.remove(queriedOneResource);
                queriedOneResource.updateHolder("PLACEHOLDER");
                resources.add(queriedOneResource);
                mockedIO.output("Thank you for returning the " + whichResource.toLowerCase() + ".");
                return true;
            }
        }
    }

    boolean login() {
        mockedIO.output("Please input your account:");
        String account = mockedIO.input();
        mockedIO.output("Please input your password:");
        String password = mockedIO.input();

        currentUser = customerDataManager.login(account, password);

        return currentUser != null;
    }

    User getCurrentUser() {
        return currentUser;
    }

    void logOut() {
        currentUser = null;
    }

    boolean showProfile() {
        if (verifyUser()) {
            mockedIO.output(currentUser.showProfile());
            return true;
        } else
            return false;
    }

}
