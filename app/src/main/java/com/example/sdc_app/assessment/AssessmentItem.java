package com.example.sdc_app.assessment;

public class AssessmentItem {
    private String courseId;
    private String courseName;
    private String score;
    private String offeredBy;

    public AssessmentItem() {
    }

    public AssessmentItem(String courseId, String courseName,String offeredBy, String score) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.offeredBy=offeredBy;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getScore() {
        return score;
    }

    public String getOfferedBy() {
        return offeredBy;
    }
}
