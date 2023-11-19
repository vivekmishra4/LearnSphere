package com.example.sdc_app.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdc_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewCourse extends Fragment {
    EditText courseName,courseDescription;
    Button submitCourse;
    ImageView addNewTopicIcon;
    private SharedViewModel sharedViewModel;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    List<AddTopic> topicList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        topicList=new ArrayList<>();
    }

    public NewCourse(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Setting View IDs
        View view = LayoutInflater.from(getContext()).inflate(R.layout.new_course, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("course");
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        courseName=view.findViewById(R.id.edit_course_name);
        courseDescription=view.findViewById(R.id.edit_course_description);
        submitCourse=view.findViewById(R.id.submit_course_btn);
        addNewTopicIcon=view.findViewById(R.id.add_new_topic_icon);
        addNewTopicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=requireActivity().getSupportFragmentManager().beginTransaction();
                NewTopic topic=new NewTopic();
                transaction.replace(R.id.add_course_frame,topic);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        //Same-as Below method
//        LiveData<AddTopic> liveData=sharedViewModel.getSharedTopicData();
//        liveData.observe(getViewLifecycleOwner(), new Observer<AddTopic>() {
//            @Override
//            public void onChanged(AddTopic addTopic) {
//                if(addTopic!=null){
//                    topicList.add(addTopic);
//                    Toast.makeText(getContext(),"Topic added",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
        sharedViewModel.getSharedTopicData().observe(getViewLifecycleOwner(), new Observer<AddTopic>() {
            @Override
            public void onChanged(AddTopic addTopic) {
                if(addTopic!=null){
                    topicList.add(addTopic);
                    Toast.makeText(getContext(),"Topic added",Toast.LENGTH_LONG).show();

                }
            }
        });
        submitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCourse course=new AddCourse(courseName.getText().toString(),courseDescription.getText().toString(),user.getDisplayName(),"123","4.5","CS",topicList);
                databaseReference.child("c2").setValue(course);
                getActivity().finish();


            }
        });

        return view;
    }
}
