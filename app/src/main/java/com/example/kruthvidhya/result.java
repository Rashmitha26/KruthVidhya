package com.example.kruthvidhya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class result extends AppCompatActivity {
    TextView t;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent=getIntent();
       // score=intent.getIntExtra("score",0);
        score=intent.getIntExtra("score",0);
        t=findViewById(R.id.res);
       // Toast.makeText(this,"Sssss: "+score,Toast.LENGTH_LONG).show();
        t.setText(score+"");
    }
}
