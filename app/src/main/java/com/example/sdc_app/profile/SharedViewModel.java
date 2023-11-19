package com.example.sdc_app.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<AddTopic> sharedTopicData=new MutableLiveData<>();
    public void setSharedTopicData(AddTopic addTopic){
        sharedTopicData.setValue(addTopic);
    }
    public LiveData<AddTopic> getSharedTopicData(){
        return sharedTopicData;
    }
}
