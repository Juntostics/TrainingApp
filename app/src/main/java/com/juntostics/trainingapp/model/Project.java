package com.juntostics.trainingapp.model;

import android.net.Uri;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Juntostics on 6/13/15.
 */
public class Project {

    private String mTitle;
    public Uri imageUri;

    public Project(String mTitle) {
        this.mTitle = mTitle;

        //TODO:erase below
        this.imageUri = Uri.parse("https://lh5.googleusercontent.com/-7fb5ybQhUbo/VGLWjIL4RmI/AAAAAAAAACM/2jLe_msj_tk/w600-no/IMG_0049.JPG");

    }

    public String getTitle() {
        return mTitle;
    }

}
