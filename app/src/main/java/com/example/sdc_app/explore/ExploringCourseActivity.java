package com.example.sdc_app.explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ExploringCourseActivity extends AppCompatActivity {
    TextView courseName;
    TextView courseDescription;
    TextView offeredBy;
    Intent intent;
    List<String> topics;
    String myCourseName;
    String myCourseDescription;
    String myCourseId;
    String myOfferedBy;
    ListView listView;
    ArrayAdapter<String> adapter;
    Button enroll;
    DatabaseReference databaseReference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploring_course);
        setAllFields();
        getIntentItems();
        setFieldData();

    }
    public void setAllFields(){
        courseName=findViewById(R.id.explore_course_name);
        courseDescription=findViewById(R.id.explore_course_description);
        offeredBy=findViewById(R.id.explore_offered_by);
        listView=findViewById(R.id.explore_list_of_topics);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        enroll=findViewById(R.id.enroll_course_btn);
        listView.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        intent=getIntent();

    }
    private void getIntentItems(){
        myCourseName=intent.getStringExtra("name");
        myCourseDescription=intent.getStringExtra("description");
        myCourseId=intent.getStringExtra("courseId");
        myOfferedBy=intent.getStringExtra("offeredBy");
        topics=intent.getStringArrayListExtra("topics");

    }
    private void setFieldData(){
        courseName.setText(myCourseName);
        courseDescription.setText(myCourseDescription);
        offeredBy.setText(myOfferedBy);
        adapter.clear();
        for (int i = 0; i < topics.size(); i++) {
            adapter.add(topics.get(i));
        }
        adapter.notifyDataSetChanged();

    }
    public void onEnrollButton(View view){
        DatabaseReference courseReference=databaseReference.child("user/"+user.getUid()+"/courses").child(myCourseId);
        courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(getBaseContext(),"Already Enrolled",Toast.LENGTH_LONG).show();
                }
                else {
                    courseReference.setValue("pending");
                    Toast.makeText(getBaseContext(),"Course Added",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}