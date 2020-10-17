package com.example.quizapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity implements View.OnClickListener ,ItemClickListener{

    EditText LogineditText;
    Button Loginbutton, Addbutton;
    ConstraintLayout GmaeList;
    ConstraintLayout LoginPage;

    private QuestionDBHelper dbHelper;
    private RecyclerView listView;
    private QuestionAdapter adapter;
    private ArrayList<QuestionUserBean> data;
    private


    final int pw = 1234;
    private String pw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        data = dbHelper.get();
//리사이클 뷰
        adapter = new QuestionAdapter(data, this);      //어뎁터 클래스가져오기
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView = findViewById(R.id.recycler);
        listView.setAdapter(adapter);
        listView.setLayoutManager(manager);

        Loginbutton = findViewById(R.id.pwCheck);   //로그인 확인버튼
        Loginbutton.setOnClickListener(this);
        Addbutton = findViewById(R.id.AddProblem);  //문제 추가확인 버튼
        Addbutton.setOnClickListener(this);


        LogineditText = findViewById(R.id.Userpw);


       GmaeList = findViewById(R.id.GameList);
       LoginPage = findViewById(R.id.loginPage);


       dbHelper = new QuestionDBHelper(this, "DB",null, 1);
       data = dbHelper.get();



    }

    public void onItemClick(View v, int index) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("id", data.get(index).getQid());
        startActivityForResult(intent, 1);
}
        @Override
    public void onClick(View v) {
        pw2 = LogineditText.getText().toString();   //패스워드 변환
 if(v.getId() == R.id.pwCheck) {
     if(!pw2.equals(null) && pw == Integer.parseInt(pw2)) {         // 패스워드 비교
         GmaeList.setVisibility(View.VISIBLE);
         LoginPage.setVisibility(View.GONE);
     }
     else   {
         Toast.makeText(this, "패스워드를 확인해주세요!", Toast.LENGTH_SHORT).show();
            }
      }

      if(v.getId() == R.id.AddProblem) {
          Intent intent = new Intent(this,QuestionActivity.class);
          startActivityForResult(intent,1);

      }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            this.data = dbHelper.get();
            adapter.notifyDataSetChanged();
        }
    }
}
