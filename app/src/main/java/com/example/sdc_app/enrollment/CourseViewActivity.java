package com.example.sdc_app.enrollment;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.example.sdc_app.assessment.TestActivity;
import com.example.sdc_app.profile.AddCourse;
import com.example.sdc_app.profile.AddQuestion;
import com.example.sdc_app.profile.AddTopic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CourseViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference courseDatabase;
    List<AddTopic> topics;
    TextView courseName;
    Button unEnroll;
    TextView courseOfferedBy;
    CourseViewAdapter courseViewAdapter;
    AddCourse myCourse;
    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        //Setting all fields
        setAllFields();

        //Set Adapter and listener for each ite,
        setAdapterWithListener();

        //Setting Course Details
        setCourseDetails();

        //Set listener for unEnroll and feedback
        addButtonListeners();



    }
    public void setAllFields(){
        courseName=findViewById(R.id.my_course_name);
        courseOfferedBy=findViewById(R.id.my_course_offered_by);
        recyclerView=findViewById(R.id.list_of_topics);
        unEnroll=findViewById(R.id.enrollment_unEnroll);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        topics=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);

    }
    public void setAdapterWithListener(){
        courseViewAdapter=new CourseViewAdapter(topics, new CourseViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Button button, int position) {
                AddTopic topic=topics.get(position);
                String topicName=topic.getName();
                if(button.getId()==R.id.topic_test_btn){
                    List<AddQuestion> questions=topic.getTestQuestions();
                    Intent intent=new Intent(CourseViewActivity.this, TestActivity.class);
                    intent.putExtra("questions",(Serializable) questions);
                    intent.putExtra("topicName",topicName);
                    intent.putExtra("courseName",myCourse.getName());
                    intent.putExtra("courseId",myCourse.getCourseId());
                    intent.putExtra("topicId",String.valueOf(position));
                    intent.putExtra("courseOfferedBy",myCourse.getOfferedBy());
                    startActivity(intent);


                }
                else{
                    String contentLink=topic.getFileLink();
                    Intent intent=new Intent(CourseViewActivity.this, PDFActivity.class);
                    intent.putExtra("topicName",topicName);
                    intent.putExtra("contentLink",contentLink);
                    startActivity(intent);


                }

            }


        });
        recyclerView.setAdapter(courseViewAdapter);

    }

    public void setCourseDetails(){
        courseId= getIntent().getStringExtra("courseId");
        courseDatabase=databaseReference.child("course/"+courseId);
        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    myCourse=snapshot.getValue(AddCourse.class);
                    courseName.setText(myCourse.getName());
                    courseOfferedBy.setText(myCourse.getOfferedBy());
                    topics.addAll(myCourse.getTopics());

                }

                courseViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void addButtonListeners(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        unEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] proceed = new boolean[1];
                showAlertDialog( userClickedYes -> {
                    // Handle user's response
                    proceed[0]=userClickedYes;
                    if(proceed[0]){
                        try {
                            databaseReference.child("user/"+user.getUid())
                                    .child("courses/"+courseId).removeValue();
                            databaseReference.child("enrollment/user/"+user.getUid())
                                    .child("completed/"+courseId).removeValue();
                            databaseReference.child("enrollment/user/"+user.getUid())
                                    .child("incomplete/"+courseId).removeValue();
                            databaseReference.child("enrollment/user/"+user.getUid())
                                    .child("temporary/"+courseId).removeValue();
                        }catch (Exception e){

                        }
                        Toast.makeText(CourseViewActivity.this,"UnEnrolled from course:"
                                +courseName.getText().toString(),Toast.LENGTH_LONG).show();
                        finish();

                    }
                });


            }
        });

    }
    public interface AlertDialogCallback {
        void onUserResponse(boolean userClickedYes);
    }
    public void showAlertDialog(final AlertDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to UnEnroll from Course?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            if (callback != null) {
                callback.onUserResponse(true);
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("NO", (dialog, which) -> {
            if (callback != null) {
                callback.onUserResponse(false);
            }
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}