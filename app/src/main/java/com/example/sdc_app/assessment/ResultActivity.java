package com.example.sdc_app.assessment;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddQuestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    ListView listView;
    List<String> responses;
    List<AddQuestion> questions;
    ArrayAdapter<String> adapter;
    TextView testResultPercent;
    DatabaseReference databaseReference;
    DatabaseReference myCompletedReference;
    DatabaseReference myIncompleteTopicReference;
    DatabaseReference myTemporaryReference;
    FirebaseUser user;
    TextView numRightAns;
    Button closeResultButton;
    private String courseId;
    private String topicId;
    private boolean forward;
    private String courseName;
    private String courseOfferedBy;
    int percentInt;
    private long numAllTopics;
    private int numCompletedTopics=0;
    private int totalPercent=0;
    Intent intent;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Set All Fields
        setAllFields();

        //Load Intent Data
        loadIntentData();

        //Show All Responses
        showResponses();

        //Setting Score in Firebase
        setTopicScoreInDatabase();



    }
    public void setAllFields(){
        listView=findViewById(R.id.test_result_list);
        testResultPercent=findViewById(R.id.test_result_percent);
        numRightAns=findViewById(R.id.num_right_ans);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        closeResultButton=findViewById(R.id.close_result_btn);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

    }
    public void loadIntentData(){
        //Getting Intent data
        intent=getIntent();
        responses=(List<String>)intent.getSerializableExtra("responses");
        questions=(List<AddQuestion>) intent.getSerializableExtra("questions");
        courseId=intent.getStringExtra("courseId");
        topicId=intent.getStringExtra("topicId");
        score=intent.getIntExtra("score",0);
        courseName=intent.getStringExtra("courseName");
        courseOfferedBy=intent.getStringExtra("courseOfferedBy");

        //Setting Intent data
        float percent=((float) score/questions.size())*100;
        percentInt=Math.round(percent);
        testResultPercent.setText(String.valueOf(percentInt));
        numRightAns.setText(String.valueOf(score)+"/"+String.valueOf(questions.size()));
        myCompletedReference =databaseReference.child("enrollment/user/"+user.getUid()).child("completed/"+courseId);
        myTemporaryReference=databaseReference.child("enrollment/user/"+user.getUid()).child("temporary/"+courseId);
        myIncompleteTopicReference=databaseReference.child("enrollment/user/"+user.getUid()).child("incomplete/"+courseId)
                .child(topicId);

    }
    public void showResponses(){
        String item;
        String question;
        String options;
        String selectedOption;
        String rightOption;
        AddQuestion qTemp;
        for (int i = 0; i < questions.size(); i++) {
            item="";
            question="";
            options="";
            selectedOption="";
            rightOption="";
            qTemp=questions.get(i);
            question=qTemp.getQuestion()+"\n";
            options="A."+qTemp.getOption1()+"\t\t"+"B."+qTemp.getOption2()+"\n"+
                    "C."+qTemp.getOption3()+"\t\t"+"D."+qTemp.getOption4()+"\n";
            selectedOption="SelectedOption:"+responses.get(i)+"\n";
            rightOption="rightOption:"+qTemp.getRightOption();
            item=String.valueOf(i+1)+"."+question+options+selectedOption+rightOption+"\n";
            adapter.add(item);
        }
        adapter.notifyDataSetChanged();

    }

    public void onCloseResultButton(View view){
        finish();

    }
    public void setTopicScoreInDatabase(){
        myIncompleteTopicReference.child("score").setValue(percentInt);
        boolean[] receivedFlags = new boolean[2];
        myTemporaryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.getKey().equals("completed")){
                                numCompletedTopics=dataSnapshot.getValue(Integer.class);
                                Log.i("numCompletedTopics","numComp:"+numCompletedTopics);
                                receivedFlags[0] = true;
                            }
                            else if(dataSnapshot.getKey().equals("percent")){
                                totalPercent=dataSnapshot.getValue(Integer.class);
                                Log.i("totalPercent","totalPercent:"+totalPercent);
                                receivedFlags[1] = true;

                            }

                        }

                    }
                    updateTemporary();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                updateTemporary();

            }
        });

    }
    public void updateTemporary(){
        numCompletedTopics++;
        totalPercent=totalPercent+percentInt;
        Log.i("numCompletedTopics","increment:"+numCompletedTopics);
        myTemporaryReference.child("percent").setValue(totalPercent);
        myTemporaryReference.child("completed").setValue(numCompletedTopics);
        Log.i("DataReceived", "Both values received. numComp: " + numCompletedTopics + ", totalPercent: " + totalPercent);
        getNumAllTopics();

    }
    public void getNumAllTopics(){
        databaseReference.child("course/"+courseId+"/topics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    numAllTopics= snapshot.getChildrenCount();
                    Log.i("numAllTopics","numAll:"+numAllTopics);
                    checkAndUpdateCourse();

                }
                else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkAndUpdateCourse(){
        if(numAllTopics<=numCompletedTopics){
            Log.i("numAllTopics","numCom:"+numCompletedTopics);
            myTemporaryReference.getParent().removeValue();
            float temp=(float)totalPercent/(float)numAllTopics;
            myIncompleteTopicReference.getParent().removeValue();
            myCompletedReference.child("courseId").setValue(courseId);
            myCompletedReference.child("courseName").setValue(courseName);
            myCompletedReference.child("offeredBy").setValue(courseOfferedBy);
            myCompletedReference.child("score").setValue(Math.round(temp));
            databaseReference.child("user/"+user.getUid()+"/courses").child(courseId).setValue("completed");

        }

    }

}
