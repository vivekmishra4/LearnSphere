package com.example.sdc_app.enrollment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.MyAdapter;
import com.example.sdc_app.MyItem;
import com.example.sdc_app.R;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSection extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
    }

    public EnrollmentSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.enrollment_section,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<MyItem> itemList = new ArrayList<>();
// Populate itemList with your data (image resource, text1, text2)

        MyAdapter adapter = new MyAdapter(itemList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyItem item) {
                // Handle item click here
                Toast.makeText(getContext(), "Clicked: " + item.getText1(), Toast.LENGTH_SHORT).show();
            }
        });
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));
        itemList.add(new MyItem(R.drawable.app_logo_icon_foreground,"Hack Your System","offered by sheru University"));

        recyclerView.setAdapter(adapter);
        return view;
    }
}
