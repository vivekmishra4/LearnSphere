package com.example.sdc_app.profile;

import java.util.List;

public class AddCourse {
    private String name;
    private String description;
    private String offeredBy;
    private List<AddTopic> topics;
    private String courseId;
    public AddCourse(){
        //For Firebase
    }

    public AddCourse(String courseId,String name, String description, String offeredBy,List<AddTopic> topics) {
        this.courseId=courseId;
        this.name = name;
        this.description = description;
        this.offeredBy = offeredBy;
        this.topics=topics;
    }

    public String getCourseId() {
        return courseId;
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

    public List<AddTopic> getTopics() {
        return topics;
    }
}
