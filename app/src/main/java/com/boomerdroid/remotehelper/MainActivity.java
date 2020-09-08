package com.boomerdroid.remotehelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//        Intent addIntent =new Intent(getApplicationContext(),AddContact.class);
//        startActivity(addIntent);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigationView=findViewById(R.id.navigation);
        final RecentFragment recentFragment=new RecentFragment();
        final TrusredFragment trusredFragment = new TrusredFragment();
        final HowToFragment howToFragment =new HowToFragment();
        //final RequestFragment requestFragment =new RequestFragment();
        final RecentFragment requestFragment=new RecentFragment();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id== R.id.recent_request){
                    setFragment(recentFragment);
                    return  true;
                }
                else if(id==R.id.trusted_contacts){
                    setFragment(trusredFragment);
                    return true;
                }
                else if(id == R.id.how_to_use){
                    setFragment(howToFragment);
                    return true;
                }
                else{
                    setFragment(requestFragment);
                    return true;
                }
            }
        });
        if(savedInstanceState==null){
            navigationView.setSelectedItemId(R.id.recent_request);
        }

    }
    void setFragment(Fragment frag){
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,frag);
        fragmentTransaction.commit();
    }


}
