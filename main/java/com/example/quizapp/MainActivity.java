package com.example.quizapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    TextView textView;
    Button button , StartoGAME;
    RadioButton radioButton, radioButton2;
    String mode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    if (Build.VERSION.SDK_INT >= 23) {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

    button  =findViewById(R.id.creatProblem);
    button.setOnClickListener(this);

    StartoGAME = findViewById(R.id.StartToGame);
    StartoGAME.setOnClickListener(this);

    radioButton = findViewById(R.id.easymode);
    radioButton.setOnClickListener(this);
    radioButton2 = findViewById(R.id.hardmode);
    radioButton2.setOnClickListener(this);

    textView   =  findViewById(R.id.explainText);



    }

    @Override //list 이동
    public void onClick(View v) {
       if(v.getId() == R.id.creatProblem) {
           Intent intent = new Intent(this,QuestionListActivity.class);
           startActivity(intent);
       }
        if(v.getId() == R.id.StartToGame) {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);
        }

        if(v.getId() == R.id.easymode) {
         textView.setText("모든 문제가 객관식으로 제출됩니다." );
         mode="easy";
           }
          else if(v.getId() == R.id.hardmode) {
               textView.setText(" 객관식과 주관식으로 제출됩니다." );
               mode="hard";
           }
       }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1) return;

        if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "권한이 있습니다", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show();



    }}