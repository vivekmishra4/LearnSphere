package com.example.sdc_app.assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddQuestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private TextView questionText;
    private TextView questionId;
    private TextView courseName;
    private TextView topicName;
    private TextView numQuestions;
    private RadioGroup answerOptionsGroup;
    private int score;
    private Button nextButton;
    private Button clearButton;
    private List<String> responses;
    Intent intent;

    private List<AddQuestion> questions;
    private int currentQuestionIndex = 0;
    private String[] options;
    private String myTopicName;
    private String myCourseName;
    private String myNumQuestions;
    private String courseId;
    private String topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Setting All fields
         setAllFields();

        // Get Topic Data from Intent
        getTopicDetails();

        //set Test Header
        courseName.setText(myCourseName);
        topicName.setText(myTopicName);
        numQuestions.setText(myNumQuestions);

        // Display the first question
        displayQuestion();
    }
    public void setAllFields(){
        questionId=findViewById(R.id.question_id);
        questionText = findViewById(R.id.question_text);
        courseName=findViewById(R.id.test_course_name);
        topicName=findViewById(R.id.test_topic_name);
        clearButton=findViewById(R.id.test_clear_btn);
        numQuestions=findViewById(R.id.num_questions);
        answerOptionsGroup = findViewById(R.id.answerOptionsGroup);
        nextButton = findViewById(R.id.test_next_btn);
        options =new String[5];
        responses=new ArrayList<>();
        score=0;
        intent=getIntent();

    }

    private void getTopicDetails() {
        questions=(List<AddQuestion>) intent.getSerializableExtra("questions");
        myTopicName=intent.getStringExtra("topicName");
        myCourseName=intent.getStringExtra("courseName");
        myNumQuestions=String.valueOf(questions.size());
        courseId=intent.getStringExtra("courseId");
        topicId=intent.getStringExtra("topicId");

    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            AddQuestion currentQuestion = questions.get(currentQuestionIndex);
            options[0]=currentQuestion.getOption1();
            options[1]=currentQuestion.getOption2();
            options[2]=currentQuestion.getOption3();
            options[3]=currentQuestion.getOption4();
            options[4]=currentQuestion.getRightOption();
            // Set the question text
            questionId.setText(String.valueOf(currentQuestionIndex+1)+".");
            questionText.setText(currentQuestion.getQuestion());

            // Clear existing RadioButtons
            answerOptionsGroup.removeAllViews();
            for (int i = 0; i < 4; i++) {
                RadioButton radioButton= new RadioButton(this);
                radioButton.setText(options[i]);
                radioButton.setTag(i);
                answerOptionsGroup.addView(radioButton);
            }

        } else {
            // Quiz completed, show a message or handle accordingly

            Intent intent=new Intent(this,ResultActivity.class);
            intent.putExtra("questions",(Serializable) questions);
            intent.putExtra("responses",(Serializable) responses);
            intent.putExtra("score",score);
            intent.putExtra("courseId",courseId);
            intent.putExtra("topicId",topicId);
            startActivity(intent);
            finish();

        }
    }
    public void onNextButtonClick(View view){
        int selectedRadioButtonIndex= answerOptionsGroup.indexOfChild(findViewById(answerOptionsGroup.getCheckedRadioButtonId()));
        String selectedAnswer;
        if(selectedRadioButtonIndex!=-1){
            selectedAnswer=options[selectedRadioButtonIndex];
            if(selectedAnswer.equals(options[4])){
                score++;
            }
            else{
                //Wrong option selected
            }
            responses.add(selectedAnswer);
            currentQuestionIndex++;
            displayQuestion();
        }
        else{
            //No option selected
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        }

    }
    public void onClearButtonClick(View view){
        answerOptionsGroup.clearCheck();

    }
}