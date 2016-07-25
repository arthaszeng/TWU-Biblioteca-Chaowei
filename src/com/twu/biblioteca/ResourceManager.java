package com.twu.biblioteca;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    List<Resource> bookList = new ArrayList<>();
    List<Resource> checkedBookList = new ArrayList<>();
    List<Resource> movieList = new ArrayList<>();
    List<Resource> checkedMovieList = new ArrayList<>();

    void addResource(Resource resource, List<Resource> resourceList) {
        resourceList.add(resource);
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
}
