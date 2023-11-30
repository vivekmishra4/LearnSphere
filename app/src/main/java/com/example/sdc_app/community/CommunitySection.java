package com.example.sdc_app.community;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sdc_app.R;

public class CommunitySection extends Fragment {
    public CommunitySection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.community_section,container,false);
//        String groupLink = "https://chat.whatsapp.com/HQFL3EneCK15Q3ouPuwcDg";
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(groupLink));
//        startActivity(intent);

        return view;
    }
}

