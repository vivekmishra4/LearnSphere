package com.example.sdc_app.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sdc_app.R;
import com.example.sdc_app.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSection extends Fragment {
    TextView email,name;
    Button signOut;
    FirebaseAuth mAuth;
    public ProfileSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.profile_section,container,false);
        email=view.findViewById(R.id.profile_email);
        name=view.findViewById(R.id.profile_name);
        signOut=view.findViewById(R.id.sign_out_btn);
        mAuth=FirebaseAuth.getInstance();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            email.setText(user.getEmail());
            name.setText(user.getDisplayName());

        }
        return view;
    }
}
