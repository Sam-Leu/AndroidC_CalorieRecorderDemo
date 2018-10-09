package com.example.one.calorierecorderdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SecondActivity extends AppCompatActivity {

    private static DBHelper dbHelper;
    SQLiteDatabase db;

    private ListView data_list;
    private Vector<String> entries;
    private String selectText;

    //定义一个列表集合
    List<Map<String,Object>> listItems;

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

        listItems=new ArrayList<Map<String, Object>>();

        data_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出删除确认对话框
                selectText = (String)parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                builder.setMessage("确定删除这项记录？");
                builder.setTitle("提示");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = Integer.parseInt(selectText.split(":")[0]);
                        db.execSQL("DELETE FROM " + "DAILY" + " WHERE id=" + index);
                        Toast.makeText(SecondActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                        showRecord();
                        create_list();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
        create_list();
    }

    //显示记录
    private void showRecord(){
        entries.clear();
        db = dbHelper.getWritableDatabase();

        String sql = "select * from DAILY order by date ASC";
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                entries.add(id+":    " + date + ",    " + calorie);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    //加载刷新列表
    public void create_list() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, entries);
        data_list.setAdapter(adapter);
    }
}



