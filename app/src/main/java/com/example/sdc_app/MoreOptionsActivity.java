package com.example.sdc_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoreOptionsActivity extends AppCompatActivity {
    String aboutUsTxt;
    TextView aboutUs;
    String aboutUsLnk;
    DatabaseReference aboutDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);
        EditText feedbackText=findViewById(R.id.feedback_text);
        Button feedbackButton=findViewById(R.id.feedback_btn);
        Button signOut=findViewById(R.id.more_signOut_btn);
        aboutUs=findViewById(R.id.about_us);
        getAboutUs();
        Button deleteAccount=findViewById(R.id.delete_account_btn);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userFeedback=FirebaseDatabase.getInstance().getReference("feedback/"+user.getUid());
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedbackText.getText().toString().equals("")){
                    Toast.makeText(MoreOptionsActivity.this,"Enter Feedback",Toast.LENGTH_SHORT).show();
                }
                else {
                    String key=userFeedback.push().getKey();
                    userFeedback.child(key).setValue(feedbackText.getText().toString());
                    Toast.makeText(MoreOptionsActivity.this,"Enter Feedback",Toast.LENGTH_SHORT).show();
                    feedbackText.setText("");

                }
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                setSignIn();

            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] proceed = new boolean[1];
                showAlertDialog(userClickedYes -> {
                    // Handle user's response
                    proceed[0] = userClickedYes;
                    if (proceed[0]) {
                        if(user!=null){
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MoreOptionsActivity.this, "Deleted Account Successfully", Toast.LENGTH_SHORT).show();
                                        setSignIn();
                                    }
                                    else {
                                        Toast.makeText(MoreOptionsActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
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
        builder.setMessage("Do you want to Delete the Account?");
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
    private void setSignIn(){
        Intent intent=new Intent(MoreOptionsActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void getAboutUs(){
        aboutDatabase=FirebaseDatabase.getInstance().getReference();
        aboutDatabase.child("about/text").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aboutUsTxt=snapshot.getValue(String.class);
                String myText=aboutUs.getText().toString()+" "+aboutUsTxt;
                aboutUs.setText(myText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        aboutDatabase.child("about/link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aboutUsLnk=snapshot.getValue(String.class);
                String myText=aboutUs.getText().toString()+"\n"+aboutUsLnk;
                aboutUs.setText(myText);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}