package com.example.one.calorierecorderdemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private static DBHelper dbHelper;
    SQLiteDatabase db;
    private String dateText;
    private Button update_btn;
    private EditText calorie_text;
    String strMonth = new String();
    String strDay = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DBHelper(this,2);
        update_btn = (Button)findViewById(R.id.update_btn);
        calorie_text = (EditText)findViewById(R.id.calorie_text);

        initliaze();
    }

    private void initliaze(){
        dbHelper.getWritableDatabase();

        calendarView = (CalendarView)findViewById(R.id.cal);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(month < 10){
                    strMonth = "0"+String.valueOf(month);
                }
                else {
                    strMonth = String.valueOf(month);
                }

                if(dayOfMonth < 10){
                    strDay = "0"+String.valueOf(dayOfMonth);
                }
                else {
                    strDay = String.valueOf(dayOfMonth);
                }
                dateText = year+"-"+strMonth+"-"+strDay;
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = getDB();
                ContentValues values = new ContentValues();
                values.put("date",dateText);
                values.put("calorie",calorie_text.getText().toString());
                db.insert("DAILY",null,values);
                Toast.makeText(UpdateActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                calorie_text.setText("");
            }
        });
    }

    public static SQLiteDatabase getDB(){
        return dbHelper.getWritableDatabase();
    }

}
