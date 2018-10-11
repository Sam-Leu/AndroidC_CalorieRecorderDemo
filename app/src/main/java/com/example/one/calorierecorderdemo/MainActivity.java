package com.example.one.calorierecorderdemo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.sql.Date;
import java.util.Calendar;


public class MainActivity extends Activity {

    private static DBHelper dbHelper;
    SQLiteDatabase db;

    private EditText bEditText;
    private EditText eEditText;

    String strMonth = new String();
    String strDay = new String();
    String date = new String();
    String dateText = new String();
    String[] str = new String[4];
    String[] bstr = new String[4];
    String[] estr = new String[4];

    int beginDate = 0;
    int endDate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this,2);

        Button editBtn = (Button) findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        setBeginDate();
        setEndDate();
    }

    //查询点击事件
    public void queryOnClick(View view) {
        if (judge() == false) {
            Toast.makeText(MainActivity.this, "日期有误，请校对！", Toast.LENGTH_LONG).show();
            return;
        }
        setGraphView();
    }

    //管理点击事件
    public void editOnClick(View view) {
        Toast.makeText(this, "edit", Toast.LENGTH_LONG).show();
    }

    //设置开始时间
    public void setBeginDate() {
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

    //设置结束时间
    public void setEndDate() {
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

    //显示开始时间
    protected void showBeginDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(monthOfYear < 10){
                    strMonth = "0"+String.valueOf(monthOfYear);
                }
                else {
                    strMonth = String.valueOf(monthOfYear);
                }

                if(dayOfMonth < 10){
                    strDay = "0"+String.valueOf(dayOfMonth);
                }
                else {
                    strDay = String.valueOf(dayOfMonth);
                }
                dateText = year+"-"+strMonth+"-"+strDay;
                MainActivity.this.bEditText.setText(dateText);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //显示结束时间
    protected void showEndDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(monthOfYear < 10){
                    strMonth = "0"+String.valueOf(monthOfYear);
                }
                else {
                    strMonth = String.valueOf(monthOfYear);
                }

                if(dayOfMonth < 10){
                    strDay = "0"+String.valueOf(dayOfMonth);
                }
                else {
                    strDay = String.valueOf(dayOfMonth);
                }
                dateText = year+"-"+strMonth+"-"+strDay;
                MainActivity.this.eEditText.setText(dateText);

            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //判断查询时间是否符合前后规律
    public boolean judge() {
        String begin = bEditText.getText().toString();
        String end = eEditText.getText().toString();

        String[] beginArray = begin.split("-");
        String[] endArray = end.split("-");

        if (Integer.parseInt(beginArray[0]) > Integer.parseInt(endArray[0])) {
            return false;
        } else {
            if (Integer.parseInt(beginArray[1]) > Integer.parseInt(endArray[1])) {
                return false;
            } else {
                if (Integer.parseInt(beginArray[2]) >= Integer.parseInt(endArray[2])) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public void setGraphView() {
        db = dbHelper.getReadableDatabase();
        bEditText = (EditText) findViewById(R.id.begin_text);
        eEditText = (EditText) findViewById(R.id.end_text);

        bstr[0] = bEditText.getText().toString().split("-")[0];
        bstr[1] = bEditText.getText().toString().split("-")[1];
        bstr[2] = bEditText.getText().toString().split("-")[2];
        estr[0] = eEditText.getText().toString().split("-")[0];
        estr[1] = eEditText.getText().toString().split("-")[1];
        estr[2] = eEditText.getText().toString().split("-")[2];

        beginDate = Integer.parseInt(bstr[0].substring(2) + bstr[1] + bstr[2]);
        endDate = Integer.parseInt(estr[0].substring(2) + estr[1] + estr[2]);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        String sql = "select * from DAILY order by date ASC";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));

                str[0] = date.split("-")[0];
                str[1] = date.split("-")[1];
                str[2] = date.split("-")[2];

                date = str[0].substring(2)+str[1]+str[2];
                int x = Integer.parseInt(date);
                int y = Integer.parseInt(calorie);

                if(x > beginDate && x < endDate){
                    DataPoint dp = new DataPoint(x, y);
                    series.appendData(dp, true, 100, true);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }

        graph.addSeries(series);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
}

