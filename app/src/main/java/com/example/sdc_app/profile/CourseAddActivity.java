package com.example.sdc_app.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.sdc_app.R;

public class CourseAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        setFragment(new NewCourse());
    }
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.add_course_frame,fragment).commit();
    }
}