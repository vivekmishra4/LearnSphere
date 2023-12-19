package com.example.sdc_app.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdc_app.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

public class NewTopic extends Fragment {
    private SharedViewModel sharedViewModel;
    EditText topicName;
    TextView numQuestions;
    int numQs=0;
    Spinner selectRightOption;
    Button submitQuestion,submitTopic;
    private ActivityResultLauncher<String> mGetContent;
    String pdfLink="unselected";
    String rightOption;
    ImageView uploadPDF;
    StorageReference storageReference;
    FirebaseStorage storage;
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
        setAllFields(view);

        //setting Submit Question Button
        List<AddQuestion> questionList=new ArrayList<>();
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numQs++;
                numQuestions.setText(String.valueOf(numQs));
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
        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri ->{
                    uploadFile(uri);
                }
        );
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePdfFile();

            }
        });
        submitTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfLink.equals("unselected")||pdfLink.equals("")){
                    Toast.makeText(getActivity(), "Please Select a PDF file for upload", Toast.LENGTH_SHORT).show();
                } else if ((topicName.getText().toString().equals(""))||numQs==0) {
                    Toast.makeText(getActivity(),"Enter Topic details before Submitting",Toast.LENGTH_LONG).show();

                } else{
                    AddTopic topic=new AddTopic(topicName.getText().toString(),pdfLink,questionList);
                    sharedViewModel.setSharedTopicData(topic);
                    //This will navigate back to previous fragment
                    requireActivity().getSupportFragmentManager().popBackStack();
                    //This removes the current fragment,but makes the screen blank(without fragment)
//                requireActivity().getSupportFragmentManager().beginTransaction().remove(NewTopic.this).commit();

                }

            }
        });

        return view;
    }
    public void setAllFields(View view){
        topicName=view.findViewById(R.id.edit_topic_name);
        uploadPDF=view.findViewById(R.id.upload_pdf_file_icon);
        question=view.findViewById(R.id.edit_question);
        option1=view.findViewById(R.id.edit_option_1);
        option2=view.findViewById(R.id.edit_option_2);
        option3=view.findViewById(R.id.edit_option_3);
        option4=view.findViewById(R.id.edit_option_4);
        numQuestions=view.findViewById(R.id.number_of_questions);
        submitQuestion=view.findViewById(R.id.submit_question_btn);
        submitTopic=view.findViewById(R.id.submit_topic_btn);
        selectRightOption=view.findViewById(R.id.correct_option_spinner);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

    }
    public void choosePdfFile(){
        mGetContent.launch("application/pdf");

    }

    public void uploadFile(Uri filePath){
        if(filePath!=null){
            StorageReference ref=storageReference.child("content/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            pdfLink=uri.toString();
                        });

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Unable to upload file", Toast.LENGTH_SHORT).show();

                    });
        }
        else{
            Toast.makeText(getActivity(), "Please Select a Valid PDF file for upload", Toast.LENGTH_SHORT).show();
        }



    }
}
