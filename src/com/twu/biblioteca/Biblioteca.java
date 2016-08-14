package com.twu.biblioteca;

import com.twu.biblioteca.operation.OperationContext;
import com.twu.biblioteca.resource.Resource;
import com.twu.biblioteca.resource.ResourceManager;
import com.twu.biblioteca.user.User;
import com.twu.biblioteca.user.UserDataManager;

import java.util.ArrayList;
import java.util.List;


public class Biblioteca {
    private List<String> optionList = new ArrayList<>();
    private IO IO;
    private UserDataManager userDataManager;
    private ResourceManager resourceManager;
    private User currentUser;

    Biblioteca(IO IO, UserDataManager userDataManager, ResourceManager resourceManager) {
        this.IO = IO;
        this.userDataManager = userDataManager;
        this.resourceManager = resourceManager;
    }

    void showWelcome() {
        IO.output("Welcome, App started");
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
            IO.output(resource.getName());
        }
    }

    public void listRepositoryWithAllAttributes(String whichResource) {
        List<Resource> resources = resourceManager.fetchResources(whichResource);
        for (Resource resource : resources) {
            IO.output(resource.getAll());
        }
    }

    void showOptions() {
        for (String option : optionList) {
            IO.output(option);
        }
    }

    boolean selectOperation() {
        OperationContext operationContext = new OperationContext();
        String operationType = IO.input();
        return operationContext.doOperation(this, operationType);
    }

    void keepCycle() {
        showOptions();
        while (true) {
            if (!selectOperation()) {
                IO.output("Over!");
                break;
            }
        }
    }

    public boolean checkOutOneResource(String whichResource) {
        if (!verifyUser()) return false;

        String resourceName = IO.input();

        boolean result = resourceManager.checkOutOneResource(resourceName, whichResource, currentUser.getLibraryNumber());

        String message = result ? "Thank you! Enjoy the " + whichResource.toLowerCase() + "." : "That " + whichResource.toLowerCase() + " is not available.";
        IO.output(message);

        return result;
    }

    private boolean verifyUser() {
        return currentUser != null;
    }

    boolean queryOneResource(String resourceName, String whichResources) {
        return resourceManager.queryOneResource(resourceName, whichResources);
    }

    public boolean returnOneResource(String whichResource) {
        String returnedResourceName = IO.input();
        boolean result = resourceManager.returnOneResource(returnedResourceName, whichResource);
        String message = result ? "Thank you for returning the " + whichResource.toLowerCase() + "." :
                "That is not a valid " + whichResource.toLowerCase() + " to return.";
        IO.output(message);
        return result;
    }

    boolean login() {
        IO.output("Please input your account:");
        String account = IO.input();
        IO.output("Please input your password:");
        String password = IO.input();

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
            IO.output(currentUser.showProfile());
            return true;
        } else
            return false;
    }

}
