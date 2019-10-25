package com.maheswor.spinnerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Spinner spincountry;
    private Spinner countryplayer;
    public String indianplayer;
    public String[] Country = {"Nepal", "India"};
    public String[] indiannplayer = {"raman", "manish", "mahesh"};
    public String[] nepaliplayer = {"rajesh", "nikhil"};
    public String[] autosearch = {"hari", "jamal", "rajesh", "shiva", "haresh"};
    public AutoCompleteTextView searchview;
    protected TextView showdate, showtime, txtView;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//for no title
        getSupportActionBar().hide();//hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
//        for spinner
        spincountry = findViewById(R.id.spincountry);
        countryplayer = findViewById(R.id.countryplayer);
//        for autocomplete textview
        searchview = findViewById(R.id.search);
//        for date paicker
        showdate = findViewById(R.id.showdate);
        showtime = findViewById(R.id.showtime);
        txtView = findViewById(R.id.txtView);
        progressBar = findViewById(R.id.progressbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus > 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            txtView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
//        for time picker
        showtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadtime();
            }
        });

//        endoftime
//        for date picker
        showdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataPicker();
            }
        });
//end of date picker

//        for search

        ArrayAdapter searchadapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, autosearch);

        searchview.setAdapter(searchadapter);

        searchview.setThreshold(1);
//endof search
//for spinner
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                Country
        );
        spincountry.setAdapter(adapter);

        spincountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spincountry.getSelectedItem().toString().equals("India")) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            indiannplayer);
                    countryplayer.setAdapter(arrayAdapter);
                } else {
                    ArrayAdapter adapterarray = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, nepaliplayer);
                    countryplayer.setAdapter(adapterarray);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//end of spinner

    }

    //for datepicker
    public void loadDataPicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = "Year/Month/Day : " + i + "/" + i1 + "/" + i2;
        showdate.setText(date);
    }

    //    endof date picker
//    for time picker
    public void loadtime() {
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String ampm;
                if (i >= 12) {
                    i -= 12;
                    ampm = "PM";
                } else {
                    ampm = "AM";
                }
                showtime.setText("Time is : " + i + ":" + i1 + "" + ampm);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

}
