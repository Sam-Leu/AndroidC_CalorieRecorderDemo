package com.example.one.calorierecorderdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class SecondActivity extends AppCompatActivity {

    private static DBHelper dbHelper;

    private ListView data_list;
    private Vector<String> entries;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        Button addBtn = (Button)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,UpdateActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this,2);

        data_list = (ListView)findViewById(R.id.data_list);
        entries = new Vector<String>();

        showRecord();
        create_list();

        data_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                String[] parts = item.split(",");

                String calorie=parts[1];
                String whereclause = "calorie = ?" + " and date = ?";

                db.delete("STUDENT", whereclause, new String[] {parts[1], parts[0]});

                showRecord();
                create_list();
            }
        });

    }


    //显示记录
    private void showRecord(){
        entries.clear();
        db = dbHelper.getWritableDatabase();

        String sql = "select * from DAILY";
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                entries.add("" + date + "," + calorie);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    //加载刷新列表
    private void create_list() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, entries);
        data_list.setAdapter(adapter);
    }

}



