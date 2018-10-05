package com.example.one.calorierecorderdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;


public class MainActivity extends Activity {

    private EditText bEditText;
    private EditText eEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);

        Button editBtn = (Button)findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        setBeginDate();
        setEndDate();

    }

    public void queryOnClick(View view){
        if(judge() == false){
            Toast.makeText(MainActivity.this,"日期有误，请校对！",Toast.LENGTH_LONG).show();
        }
    }

    public void editOnClick(View view){
        Toast.makeText(this,"edit",Toast.LENGTH_LONG).show();
    }

    public void setBeginDate(){
        bEditText = (EditText) findViewById(R.id.begin_text);
        bEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showBeginDate();
                    return true;
                }
                return false;
            }
        });
        bEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showBeginDate();
                }
            }
        });
    }

    public void setEndDate(){
        eEditText = (EditText) findViewById(R.id.end_text);
        eEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showEndDate();
                    return true;
                }
                return false;
            }
        });
        eEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showEndDate();
                }
            }
        });

    }

    protected void showBeginDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.bEditText.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void showEndDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.eEditText.setText(year + "/" + monthOfYear + "/" + dayOfMonth);

            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public boolean judge(){
        String begin = bEditText.getText().toString();
        String end = eEditText.getText().toString();

        String[] beginArray = begin.split("/");
        String[] endArray = end.split("/");

        if(Integer.parseInt(beginArray[0]) > Integer.parseInt(endArray[0])){
            return false;
        }
        else{
            if(Integer.parseInt(beginArray[1]) > Integer.parseInt(endArray[1])){
                return false;
            }
            else {
                if(Integer.parseInt(beginArray[2]) >= Integer.parseInt(endArray[2])) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
    }
}

