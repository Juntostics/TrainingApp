package com.juntostics.trainingapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Juntostics on 6/13/15.
 */
public class Project implements Serializable{

    public static final String ENGLISH = "Learning English";
    public static final String CODING = "Programming";
    public static final String WORKOUT = "Working out";
    public static final String SOCIAL = "Social Activity";

    public static final Set<String> types = new HashSet<String>();

    static{
        types.add(ENGLISH);
        types.add(CODING);
        types.add(WORKOUT);
        types.add(SOCIAL);
    }

    private final String mType;
    private int mImageResourceId;
    private List<String> mTasks = new ArrayList<String>();

    public Project(String type, int imageResourceId) {
        this.mType = type;
        this.mImageResourceId = imageResourceId;
        createSampleTask();
    }

    public String getType() {
        return mType;
    }

    public List<String> getTasks() {
        return mTasks;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    //TODO:should be replaced
    private void createSampleTask(){
        for (int i=0; i<3;i++){
            mTasks.add("Task" + i + " for Today");
        }
        for (int i=0; i<3;i++){
            mTasks.add("Task" + i + " for Tomorrow");
        }
        for (int i=0; i<3;i++){
            mTasks.add("Task" + i  + " for This Weekend");
        }
    }





}
