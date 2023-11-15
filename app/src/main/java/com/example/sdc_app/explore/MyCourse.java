package com.example.sdc_app.explore;

public class MyCourse {
   // private int imageResource;
    private String name;
    private String description;
    private String offeredBy;
    private Object numRatings;
    private Object rating;
    private String courseType;
    public MyCourse(){
        //For Firebase
    }

    public MyCourse(String name, String description, String offeredBy,Object numRatings, Object rating, String courseType) {
        this.name = name;
        this.description = description;
        this.offeredBy = offeredBy;
        this.numRatings = numRatings;
        this.rating = rating;
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

    public Object getNumRatings() {
        return numRatings;
    }

    public Object getRating() {
        return rating;
    }

    public String getCourseType() {
        return courseType;
    }
}
