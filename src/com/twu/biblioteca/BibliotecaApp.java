package com.twu.biblioteca;

import com.twu.biblioteca.Operation.Context;
import com.twu.biblioteca.Resource.Resource;
import com.twu.biblioteca.Resource.ResourceManager;
import com.twu.biblioteca.User.User;
import com.twu.biblioteca.User.UserDataManager;

import java.util.ArrayList;
import java.util.List;


public class BibliotecaApp {
    private List<String> optionList = new ArrayList<>();
    private MockedIO mockedIO;
    private UserDataManager userDataManager;
    private ResourceManager resourceManager;
    private User currentUser;

    BibliotecaApp(MockedIO mockedIO, UserDataManager userDataManager, ResourceManager resourceManager) {
        this.mockedIO = mockedIO;
        this.userDataManager = userDataManager;
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

    public void listRepositoryWithAllAttributes(String whichResource) {
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

    boolean selectOperation() {
        Context context = new Context();
        String operationType = mockedIO.input();
        return context.doOperation(this, operationType);
    }

    void keepCycle() {
        showOptions();
        while (true) {
            if (!selectOperation()) {
                mockedIO.output("Over!");
                break;
            }
        }
    }

    public boolean checkOutOneResource(String whichResource) {
        if (!verifyUser()) return false;

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

    public boolean returnOneResource(String whichResource) {
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

        currentUser = userDataManager.login(account, password);

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
