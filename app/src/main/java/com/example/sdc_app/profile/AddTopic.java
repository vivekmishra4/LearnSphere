package com.example.sdc_app.profile;

import java.util.List;

public class AddTopic {
    private String name;
    private String fileLink;
    private List<AddQuestion> testQuestions;

    public AddTopic() {
        //Firebase
    }

    public AddTopic(String name, String fileLink, List<AddQuestion> testQuestions) {
        this.name = name;
        this.fileLink = fileLink;
        this.testQuestions = testQuestions;
    }

    public String getName() {
        return name;
    }

    public List<AddQuestion> getTestQuestions() {
        return testQuestions;
    }

    public String getFileLink() {
        return fileLink;
    }


}
