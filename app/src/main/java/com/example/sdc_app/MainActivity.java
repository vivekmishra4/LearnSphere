package com.example.sdc_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sdc_app.assessment.AssessmentSection;
import com.example.sdc_app.community.CommunitySection;
import com.example.sdc_app.enrollment.EnrollmentSection;
import com.example.sdc_app.explore.ExploreSection;
import com.example.sdc_app.profile.ProfileSection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    MenuItem settings;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.more){
            Intent intent=new Intent(getBaseContext(), MoreOptionsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        setFragment(new EnrollmentSection());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.profile){
                    setFragment(new ProfileSection());
                }
                else if(id==R.id.assessment){
                    setFragment(new AssessmentSection());
                }
                else if(id==R.id.explore){
                    setFragment(new ExploreSection() );
                } else if (id==R.id.community) {
                    setFragment(new CommunitySection());

                }
                else if(id==R.id.enrollment){
                    setFragment(new EnrollmentSection());
                }
                return true;
            }
        });

    }
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout,fragment).commit();
    }
}