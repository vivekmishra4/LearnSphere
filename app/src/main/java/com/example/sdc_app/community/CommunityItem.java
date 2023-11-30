package com.example.sdc_app.community;

public class CommunityItem {
    private String name;
    private String communityId;

    public CommunityItem(String name, String communityId) {
        this.name = name;
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public String getCommunityId() {
        return communityId;
    }
}
