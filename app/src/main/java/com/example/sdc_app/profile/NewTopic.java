package com.example.sdc_app.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdc_app.R;

import java.util.ArrayList;
import java.util.List;

public class NewTopic extends Fragment {
    private SharedViewModel sharedViewModel;
    EditText topicName,contentLink;
    Spinner selectRightOption;
    Button submitQuestion,submitTopic;
    String rightOption;
    EditText question,option1,option2,option3,option4;
    public NewTopic(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Setting View IDs
        View view = LayoutInflater.from(getContext()).inflate(R.layout.new_topic, container, false);
        topicName=view.findViewById(R.id.edit_topic_name);
        contentLink=view.findViewById(R.id.edit_content_link);
        question=view.findViewById(R.id.edit_question);
        option1=view.findViewById(R.id.edit_option_1);
        option2=view.findViewById(R.id.edit_option_2);
        option3=view.findViewById(R.id.edit_option_3);
        option4=view.findViewById(R.id.edit_option_4);
        submitQuestion=view.findViewById(R.id.submit_question_btn);
        submitTopic=view.findViewById(R.id.submit_topic_btn);
        selectRightOption=view.findViewById(R.id.correct_option_spinner);
        //setting Submit Question Button
        List<AddQuestion> questionList=new ArrayList<>();
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightOption=selectRightOption.getSelectedItem().toString();
                switch (rightOption) {
                    case "Option 1":
                        rightOption = option1.getText().toString();
                        break;
                    case "Option 2":
                        rightOption = option2.getText().toString();
                        break;
                    case "Option 3":
                        rightOption = option3.getText().toString();
                        break;
                    case "Option 4":
                        rightOption = option4.getText().toString();
                        break;
                    default:
                        rightOption="Wrong Question";
                }
                questionList.add(new AddQuestion(question.getText().toString(),
                        option1.getText().toString(),
                        option2.getText().toString(),
                        option3.getText().toString(),
                        option4.getText().toString(),
                        rightOption));
                question.setText("");
                option1.setText("");
                option2.setText("");
                option3.setText("");
                option4.setText("");

            }
        });
        submitTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTopic topic=new AddTopic(topicName.getText().toString(),contentLink.getText().toString(),questionList);
                sharedViewModel.setSharedTopicData(topic);
                //This will navigate back to previous fragment
                requireActivity().getSupportFragmentManager().popBackStack();
                //This removes the current fragment,but makes the screen blank(without fragment)
//                requireActivity().getSupportFragmentManager().beginTransaction().remove(NewTopic.this).commit();
            }
        });

        return view;
    }
}
