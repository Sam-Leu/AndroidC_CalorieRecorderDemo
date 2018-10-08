package com.example.one.calorierecorderdemo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private static DBHelper dbHelper;
    private String dateText;

    private Button update_btn;
    private EditText calorie_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DBHelper(this,2);
        update_btn = (Button)findViewById(R.id.update_btn);
        //date_text = (EditText)findViewById(R.id.date_text);
        calorie_text = (EditText)findViewById(R.id.calorie_text);

        initliaze();

    }


    private void initliaze(){
        dbHelper.getWritableDatabase();

        calendarView = (CalendarView)findViewById(R.id.cal);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateText = year+"/"+month+"/"+dayOfMonth;
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getDB();
                ContentValues values = new ContentValues();
                values.put("date",dateText);
                values.put("calorie",calorie_text.getText().toString());
                db.insert("DAILY",null,values);
            }
        });
    }

    public static SQLiteDatabase getDB(){
        return dbHelper.getWritableDatabase();
    }
}
