package com.example.sdc_app.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.example.sdc_app.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileSection extends Fragment {
    TextView email,name;
    Button signOut;
    RecyclerView listOfCourses;
    ImageView addCourse;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    boolean admin;
    public ProfileSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Setting View IDs
        View view=LayoutInflater.from(getContext()).inflate(R.layout.profile_section,container,false);
        setAllIds(view);

        setUserProfile();

        //Setting Sign Out Button

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }
    //This is for Normal Users
    public void setNormalUser(){
        addCourse.setVisibility(View.GONE);

    }
    //This is for Admins
    public void setAdminFunctionality(){
        addCourse.setVisibility(View.VISIBLE);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CourseAddActivity.class);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
                getActivity().startActivity(intent);
            }
        });

    }
    public void setAllIds(View view){
        email=view.findViewById(R.id.profile_email);
        name=view.findViewById(R.id.profile_name);
        signOut=view.findViewById(R.id.sign_out_btn);
        listOfCourses=view.findViewById(R.id.profile_list_of_topics);
        addCourse=view.findViewById(R.id.add_new_course_icon);
        addCourse.setVisibility(View.GONE);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();


    }
    public void setUserProfile(){

        if(user!=null){
            email.setText(user.getEmail());
            name.setText(user.getDisplayName());
            String uid=user.getUid();
            database.getReference("user_roles").child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String role=snapshot.child("role").getValue(String.class);
                            if(snapshot.exists()) {
                                if("admin".equals(role)){
                                    //User is admin
                                    admin=true;
                                    setAdminFunctionality();
                                }
                                else {
                                    //User is not admin
                                    admin=false;
                                    setNormalUser();
                                }

                            }
                            else {
                                //User is not admin
                                admin=false;
                                setNormalUser();
                            }
                        }
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Unable to get role of user", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }
}
