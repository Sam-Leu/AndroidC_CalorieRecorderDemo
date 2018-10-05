package com.example.one.calorierecorderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        Button btn = (Button)findViewById(R.id.btn) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

    }

    public void create(){
        LinearLayout mainLinerLayout = (LinearLayout) this.findViewById(R.id.MyTable);
        TextView textview=new TextView(this);
        textview.setText("你好！");
        mainLinerLayout.addView(textview);
    }

}

