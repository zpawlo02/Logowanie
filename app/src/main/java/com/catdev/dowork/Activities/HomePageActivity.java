package com.catdev.dowork.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.catdev.dowork.Fragments.AddTaskFragment;
import com.catdev.dowork.Fragments.HomePageFragment;
import com.catdev.dowork.Fragments.MessengerPageFragment;
import com.catdev.dowork.Fragments.ProfilePageFragment;
import com.catdev.dowork.R;
import com.catdev.dowork.Fragments.YourTasksPageFragment;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Components
    ImageView photoView;
    TextView drawerUsername;
    TextView drawerEmail;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    ActionBarDrawerToggle mToggle;
    Toolbar toolbar;

    //Variables
    private static String profileViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ParseUser user = ParseUser.getCurrentUser();
        Log.i("admin", user.getBoolean("isAdmin") + "");

        //Components
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.usernameView);
        drawerEmail = headerView.findViewById(R.id.emailView);

        //Variables
        profileViewId = ParseUser.getCurrentUser().getObjectId();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomePageFragment()).commit();
            navigationView.setCheckedItem(R.id.goToHomePage);
        }

        navigationView.setNavigationItemSelectedListener(this);

        //Header Setup
        drawerUsername.setText(ParseUser.getCurrentUser().getUsername());
        drawerEmail.setText(ParseUser.getCurrentUser().getEmail());
    }

    public static String getProfileIdToShow() {
        return profileViewId;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Log.i("item", "selected");

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.goToHomePage:
                profileViewId = ParseUser.getCurrentUser().getObjectId();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomePageFragment()).commit();
                navigationView.setCheckedItem(R.id.goToHomePage);
                break;
            case R.id.goToProfileButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilePageFragment()).commit();
                navigationView.setCheckedItem(R.id.goToProfileButton);
                break;
            case R.id.messengerButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MessengerPageFragment()).commit();
                navigationView.setCheckedItem(R.id.messengerButton);
                break;
            case R.id.yourTasksButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new YourTasksPageFragment()).commit();
                navigationView.setCheckedItem(R.id.yourTasksButton);
                break;
            case R.id.supportButton:

                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "support@dowork.pl"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Zgłoszenie - " + ParseUser.getCurrentUser().getUsername());
                    startActivity(intent);
                }catch(ActivityNotFoundException e){

                }

                break;
            case R.id.logoutButton:
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToAddTask(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new AddTaskFragment()).commit();
    }

    public void addTaskCancel(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomePageFragment()).commit();
    }

    public void addTaskAdd(View view) {
        EditText title = findViewById(R.id.addTaskTitleText);
        EditText desc = findViewById(R.id.addTaskDescText);
        EditText city = findViewById(R.id.addTaskCityText);
        EditText reward = findViewById(R.id.addTaskRewardText);

        String titleStr = title.getText().toString();
        String descStr = desc.getText().toString();
        String cityStr = city.getText().toString();
        String rewardStr = reward.getText().toString();

        if( !(titleStr.equals("") || descStr.equals("") || cityStr.equals("") || rewardStr.equals("")) ) {

            ParseObject taskData = new ParseObject("Tasks");
            taskData.put("title", titleStr);
            taskData.put("desc", descStr);
            taskData.put("city", cityStr);
            taskData.put("reward", rewardStr);
            taskData.put("reservationBy", "");
            taskData.put("isCompleted", false);
            taskData.put("addedBy", ParseUser.getCurrentUser().getUsername());

            taskData.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        Toast.makeText(HomePageActivity.this, "Dodano zlecenie", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomePageFragment()).commit();
                    } else {
                        Toast.makeText(HomePageActivity.this, "Wystąpił błąd - spróbuj ponownie później :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Wszystkie pola muszą być wypełnione", Toast.LENGTH_SHORT).show();
        }
    }
}
