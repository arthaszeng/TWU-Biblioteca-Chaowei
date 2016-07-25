package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;


class BibliotecaApp {
    private List<String> optionList = new ArrayList<>();
    private MockedIO mockedIO;
    private CustomerDataManager customerDataManager;
    private ResourceManager resourceManager;
    private User currentUser;

    BibliotecaApp(MockedIO mockedIO, CustomerDataManager customerDataManager, ResourceManager resourceManager) {
        this.mockedIO = mockedIO;
        this.customerDataManager = customerDataManager;
        this.resourceManager = resourceManager;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    void addResource(Resource resource, String whichResource) {
        resourceManager.addResource(resource, whichResource);
    }

    void addOption(String option) {
        this.optionList.add(option);
    }

    void listRepository(String whichResource) {
        List<Resource> resources = resourceManager.fetchResources(whichResource);
        for (Resource resource : resources) {
            mockedIO.output(resource.getName());
        }
    }

    void listRepositoryWithAllAttributes(String whichResource) {
        List<Resource> resources = resourceManager.fetchResources(whichResource);
        for (Resource resource : resources) {
            mockedIO.output(resource.getAll());
        }
    }

    void showOptions() {
        for (String option : optionList) {
            mockedIO.output(option);
        }
    }

    boolean selectOption() {
        switch (mockedIO.input().toUpperCase()) {
            case "LB":
                listRepositoryWithAllAttributes("book");
                return true;
            case "CB":
                checkOutOneResource("Book");
                return true;
            case "CM":
                checkOutOneResource("movie");
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

    boolean checkOutOneResource(String whichResource) {
        verifyUser();///////////////////////////

        String resourceName = mockedIO.input();

        boolean result = resourceManager.checkOutOneResource(resourceName, whichResource, currentUser.getLibraryNumber());

        String message = result ? "Thank you! Enjoy the " + whichResource.toLowerCase() + "." : "That " + whichResource.toLowerCase() + " is not available.";
        mockedIO.output(message);

        return result;
    }

    private boolean verifyUser() {
        return currentUser != null;
    }

    boolean queryOneResource(String resourceName, String whichResources) {
        return resourceManager.queryOneResource(resourceName, whichResources);
    }

    boolean returnOneResource(String whichResource) {
        String returnedResourceName = mockedIO.input();
        boolean result = resourceManager.returnOneResource(returnedResourceName, whichResource);
        String message = result ? "Thank you for returning the " + whichResource.toLowerCase() + "." :
                "That is not a valid " + whichResource.toLowerCase() + " to return.";
        mockedIO.output(message);
        return result;
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
