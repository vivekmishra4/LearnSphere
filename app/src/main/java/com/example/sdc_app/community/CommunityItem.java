package com.example.sdc_app.community;

public class CommunityItem {
    private String name;
    private String courseId;
    private String communityLink;
    private String offeredBy;

    public CommunityItem() {
        //For Firebase
    }

    public CommunityItem(String name, String courseId, String communityLink, String offeredBy) {
        this.name = name;
        this.courseId = courseId;
        this.communityLink = communityLink;
        this.offeredBy = offeredBy;
    }

    public String getName() {
        return name;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCommunityLink() {
        return communityLink;
    }

    public String getOfferedBy() {
        return offeredBy;
    }
}
