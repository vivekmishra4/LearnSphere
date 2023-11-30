package com.example.sdc_app.explore;

public class MyCourse {
   // private int imageResource;
    private String name;
    private String description;
    private String offeredBy;
    private String courseType;
    public MyCourse(){
        //For Firebase
    }

    public MyCourse(String name, String description, String offeredBy, String courseType) {
        this.name = name;
        this.description = description;
        this.offeredBy = offeredBy;
        this.courseType = courseType;
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


}
