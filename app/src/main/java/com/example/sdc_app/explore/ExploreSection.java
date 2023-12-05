package com.example.sdc_app.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddCourse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreSection extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    List<AddCourse> courseList;
    CourseAdapter courseAdapter;
    private SearchView searchView;
    public ExploreSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.explore_section,container,false);

        setAllFields(view);

        courseAdapter=new CourseAdapter(getContext(), courseList, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AddCourse item) {
                ArrayList<String> topics=new ArrayList<>();
                for (int i = 0; i < item.getTopics().size(); i++) {
                    topics.add(item.getTopics().get(i).getName());

                }
                Intent intent=new Intent(getContext(), ExploringCourseActivity.class);
                intent.putStringArrayListExtra("topics", topics);
                intent.putExtra("name",item.getName());
                intent.putExtra("offeredBy",item.getOfferedBy());
                intent.putExtra("description",item.getDescription());
                intent.putExtra("courseId",item.getCourseId());
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
                getActivity().startActivity(intent);

            }
        });

        recyclerView.setAdapter(courseAdapter);
        getCourse();

        return view;
    }
    private void setAllFields(View view){
        recyclerView=view.findViewById(R.id.explore_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        database= FirebaseDatabase.getInstance().getReference("course");
        searchView=view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        courseList=new ArrayList<>();
    }

    private void filterList(String text) {
        List<AddCourse> filteredList=new ArrayList<>();
        for (AddCourse course:courseList) {
            if(course.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(course);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(getContext(),"No data Found",Toast.LENGTH_LONG).show();
        }else {
            courseAdapter.setFilteredList(filteredList);

        }
    }

    private void getCourse(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AddCourse course=dataSnapshot.getValue(AddCourse.class);
                    courseList.add(course);
                }
                courseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
