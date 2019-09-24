package com.example.weightloss;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText mAge, mHeight, mCurrent_Weight, mGoal_Weight, mTime;
    private Spinner mGender, mLevel;
    private Person mPerson;
    private View mSBContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setuoToolbar();
        setupFAB();
        setupViews();
        initializeFields();
        setupGenAdaptor();
        setupLevelAdaptor();
    }

    private void setupLevelAdaptor() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activityLevel_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mLevel.setAdapter(adapter);
    }

    private void setupGenAdaptor() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mGender.setAdapter(adapter);
    }

    private void initializeFields() {
        mPerson = new Person();
    }

    private void setupViews() {
        mAge = findViewById(R.id.input_age);
        mHeight = findViewById(R.id.input_height);
        mCurrent_Weight = findViewById(R.id.input_current_weight);
        mGoal_Weight = findViewById(R.id.input_goal_weight);
        mTime = findViewById(R.id.input_time);
        mGender = (Spinner) findViewById(R.id.gender_spinner);
        mLevel = (Spinner) findViewById(R.id.level_spinner);
        mSBContainer = findViewById(R.id.activity_main);


    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    populateFields();
                    calcAndDisplayResults(view);

                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                } catch (NullPointerException ex) {
                    showError(ex.getMessage());
                }
            }
        });
    }

    private void calcAndDisplayResults(View view) {
        final int dailyCalories;
        final boolean recommend;
        String msg;

        dailyCalories = mPerson.getDailyCalories();
        recommend = mPerson.getRecommendation();

        if (recommend) {
            msg = "Daily Calories: " + dailyCalories;
        } else {
            msg = "Daily Calories: " + dailyCalories + " This is unhealthy and not recommended. Consider giving yourself more time.";
        }

        //Maybe make a results activity here?
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();


    }

    private void populateFields() {
            int age = Integer.parseInt(mAge.getText().toString());
            double cHeight = Double.parseDouble(mHeight.getText().toString());
            double cWeight = Double.parseDouble(mCurrent_Weight.getText().toString());
            double gWeight = Double.parseDouble(mGoal_Weight.getText().toString());
            int days = Integer.parseInt(mTime.getText().toString());
            String gender = mGender.getSelectedItem().toString();
            String level = mLevel.getSelectedItem().toString();


            mPerson.setAge(age);
            mPerson.setHeight(cHeight);
            mPerson.setCurrentWeight(cWeight);
            mPerson.setGoalWeight(gWeight);
            mPerson.setTime_frame(days);
            mPerson.setGender(gender);
            mPerson.setLevel(level);
    }

    private void showError(String msg) {

        Snackbar.make(mSBContainer, msg, Snackbar.LENGTH_SHORT).show();
    }


    private void setuoToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}



