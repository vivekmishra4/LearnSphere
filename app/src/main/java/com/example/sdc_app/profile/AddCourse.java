package com.example.sdc_app.profile;

import java.util.List;

public class AddCourse {
    // private int imageResource;
    private String name;
    private String description;
    private String offeredBy;
    private Object numRatings;
    private Object rating;
    private String courseType;
    private List<AddTopic> topics;
    public AddCourse(){
        //For Firebase
    }

    public AddCourse(String name, String description, String offeredBy,Object numRatings, Object rating, String courseType,List<AddTopic> topics) {
        this.name = name;
        this.description = description;
        this.offeredBy = offeredBy;
        this.numRatings = numRatings;
        this.rating = rating;
        this.courseType = courseType;
        this.topics=topics;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public Object getNumRatings() {
        return numRatings;
    }

    public Object getRating() {
        return rating;
    }

    public String getCourseType() {
        return courseType;
    }

    public List<AddTopic> getTopics() {
        return topics;
    }
}
