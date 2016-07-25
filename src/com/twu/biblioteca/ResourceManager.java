package com.twu.biblioteca;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    private List<Resource> bookList = new ArrayList<>();
    private List<Resource> checkedBookList = new ArrayList<>();
    private List<Resource> movieList = new ArrayList<>();
    private List<Resource> checkedMovieList = new ArrayList<>();

    void addResource(Resource resource, String whichResources) {
        fetchResources(whichResources).add(resource);
    }

    List<Resource> fetchResources(String whichResource) {
        switch (whichResource.toUpperCase()) {
            case "BOOK":
                return bookList;
            case "CHECKEDBOOK":
                return checkedBookList;
            case "MOVIE":
                return movieList;
            case "CHECKEDMOVIE":
                return checkedMovieList;
            default:
                return null;
        }
    }

    boolean checkOutOneResource(String resourceName, String whichResource, String whichUser) {
        List<Resource> resources = whichResource.toUpperCase().equals("BOOK") ? bookList : movieList;
        List<Resource> checkedResources = whichResource.toUpperCase().equals("BOOK") ? checkedBookList : checkedMovieList;

        if (resources.isEmpty()) {
            return false;
        }

        for (Resource resource : resources) {
            if (resource.getName().equals(resourceName)) {
                resources.remove(resource);
                resource.updateHolder(whichUser);
                checkedResources.add(resource);
                return true;
            }
        }

        return false;
    }

    boolean queryOneResource(String resourceName, String whichResources) {
        List<Resource> queriedResources;
        switch (whichResources.toUpperCase()) {
            case "BOOK":
                queriedResources = bookList;
                break;
            case "CHECKEDBOOK":
                queriedResources = checkedBookList;
                break;
            case "MOVIE":
                queriedResources = movieList;
                break;
            case "CHECKEDMOVIE":
                queriedResources = checkedMovieList;
                break;
            default:
                return false;
        }

        for (Resource resource : queriedResources) {
            if (resource.getName().equals(resourceName)) {
                return true;
            }
        }
        return false;
    }

    boolean returnOneResource(String returnedResourceName, String whichResources) {
        if (returnedResourceName == null) return false;

        List<Resource> resources = whichResources.toUpperCase().equals("BOOK") ? bookList : movieList;
        List<Resource> checkedResources = whichResources.toUpperCase().equals("BOOK") ? checkedBookList : checkedMovieList;

        for (Resource checkedResource : checkedResources) {
            if(checkedResource.getName().equals(returnedResourceName)) {
                checkedResources.remove(checkedResource);
                checkedResource.updateHolder(null);
                resources.add(checkedResource);
                return true;
            }
        }

        return  false;
    }
}
