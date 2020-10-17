package com.example.quizapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private ConstraintLayout imageProblem ,CheckProblem,  HardLayout;
    private ImageView imageView2, imageView3, imageView4, imageView5;
    private RadioButton radioButton7, radioButton8, radioButton9, radioButton10, TextCh1, TextCh2, TextCh3, TextCh4, easymode, hardmode;
    private EditText  HardTest, problemscroe;
    private TextView hapScore, startProblem, answerWindow, TextProblem1, TextProblem2, TextProblem3, TextProblem4;
    private Button SubmitButton;
    private String trueAnswer="";
    private int problemNum = 0;
    private int score =0;
    private QuestionUserBean bean;
    private ArrayList<QuestionUserBean> beans = new ArrayList<>();
    private QuestionDBHelper dbHelper;
    String startmode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        easymode = findViewById(R.id.easymode);
        hardmode = findViewById(R.id.hardmode);
        imageProblem = findViewById(R.id.imageProblem);
        CheckProblem = findViewById(R.id.CheckProblem);
        HardLayout = findViewById(R.id.HardLayout);

        hapScore = findViewById(R.id.hapScore);
        startProblem = findViewById(R.id.startProblem);

        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);

        radioButton7 = findViewById(R.id.radioButton7);
        radioButton8 = findViewById(R.id.radioButton8);
        radioButton9 = findViewById(R.id.radioButton9);
        radioButton10 = findViewById(R.id.radioButton10);

        TextCh1 = findViewById(R.id.TextCh1);
        TextCh2 = findViewById(R.id.TextCh2);
        TextCh3 = findViewById(R.id.TextCh3);
        TextCh4 = findViewById(R.id.TextCh4);

        TextProblem1 = findViewById(R.id.TextProblem1);
        TextProblem2 = findViewById(R.id.TextProblem2);
        TextProblem3 = findViewById(R.id.TextProblem3);
        TextProblem4 = findViewById(R.id.TextProblem4);
        answerWindow = findViewById(R.id.answerWindow);

        problemscroe = findViewById(R.id.ProblemScore);
        HardTest = findViewById(R.id.HardTest);

        SubmitButton = findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(this);

        startmode = getIntent().getStringExtra("mode");
        boolean getBollean = getIntent().getBooleanExtra("bollean-keyword",false);


        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        beans = dbHelper.get();

        problem();


    }

    public void problem(){
        bean = beans.get(problemNum);


        hapScore.setText(bean.getScore() + "");
        startProblem.setText(bean.getQuestion());

        radioButton7.setChecked(false);
        radioButton8.setChecked(false);
        radioButton9.setChecked(false);
        radioButton10.setChecked(false);
        TextCh1.setChecked(false);
        TextCh2.setChecked(false);
        TextCh3.setChecked(false);
        TextCh4.setChecked(false);
        HardTest.setText("");
        if (bean.getType().equals(QuestionUserBean.TYPE_TEXT) && startmode.equals("easy")) {         // 텍스트 형식일때
            CheckProblem.setVisibility(View.VISIBLE);
            imageProblem.setVisibility(View.GONE);
            HardLayout.setVisibility(View.GONE);
            TextProblem1.setText(bean.getEx1());
            TextProblem2.setText(bean.getEx2());
            TextProblem3.setText(bean.getEx3());
            TextProblem4.setText(bean.getEx4());


        } else if(bean.getType().equals(QuestionUserBean.TYPE_IMAGE)){      // image형식일때

            CheckProblem.setVisibility(View.GONE);
            imageProblem.setVisibility(View.VISIBLE);
            HardLayout.setVisibility(View.GONE);

            try {
                Uri imageUri = Uri.parse(bean.getEx1());
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView2.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

            }


            try {
                Uri imageUri = Uri.parse(bean.getEx2());
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bean.setEx2(imageUri.toString());

                imageView3.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
            }

            try {
                Uri imageUri = Uri.parse(bean.getEx3());
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bean.setEx3(imageUri.toString());

                imageView4.setImageBitmap(selectedImage);

            } catch (FileNotFoundException a) {

            }

            try {
                Uri imageUri = Uri.parse(bean.getEx4());
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bean.setEx4(imageUri.toString());

                imageView5.setImageBitmap(selectedImage);

            } catch (FileNotFoundException b) {

            }

        }
        else {                                      //주관식 문제일때
            CheckProblem.setVisibility(View.GONE);
            imageProblem.setVisibility(View.GONE);
            HardLayout.setVisibility(View.VISIBLE);
        }
    }
//함수 끝
    private boolean bool = false;
    @Override
    public void onClick(View v) {
        if(bool){
            answerWindow.setVisibility(View.GONE);
            bool = false;

            return;
        }else bool = true;

        if(startmode.equals("easy")) {
            if(v.getId() == R.id.SubmitButton) {
                // 정답이 맞는 지 확인 후 팝업 출력
              //객관식
                if (bean.getType().equals(QuestionUserBean.TYPE_TEXT)) {
                    if (TextCh1.isChecked() && bean.getAnswer() == 1) {
                        answerWindow.setVisibility(v.VISIBLE);
                        answerWindow.setText("정답!" + bean.getScore() + "획득!");
                        score += bean.getScore();
                    } else if (TextCh2.isChecked() && bean.getAnswer() == 2) {
                        answerWindow.setVisibility(v.VISIBLE);
                        answerWindow.setText("정답!" + bean.getScore() + "획득!");
                        score += bean.getScore();
                    } else if (TextCh3.isChecked() && bean.getAnswer() == 3) {
                        answerWindow.setVisibility(v.VISIBLE);
                        answerWindow.setText("정답!" + bean.getScore() + "획득!");
                        score += bean.getScore();
                    } else if (TextCh4.isChecked() && bean.getAnswer() == 4) {
                        answerWindow.setVisibility(v.VISIBLE);
                        answerWindow.setText("정답!" + bean.getScore() + "획득!");
                        score += bean.getScore();
                    } else {
                        answerWindow.setVisibility(View.VISIBLE);
                        answerWindow.setText("오답입니다!");

                    }
                }
                else{
                if (radioButton7.isChecked() && bean.getAnswer() == 1) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton8.isChecked() && bean.getAnswer() == 2) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton9.isChecked() && bean.getAnswer() == 3) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton10.isChecked() && bean.getAnswer() == 4) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("오답입니다!");
                }
            }}
        }
        //하드모드
        else{
            if (bean.getType().equals(QuestionUserBean.TYPE_TEXT)) {
                if (radioButton7.isChecked() && bean.getAnswer() == 1) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton8.isChecked() && bean.getAnswer() == 2) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton9.isChecked() && bean.getAnswer() == 3) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else if (radioButton10.isChecked() && bean.getAnswer() == 4) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += bean.getScore();
                } else {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("오답입니다!");
                }
            }else {
                //주관식문제
                switch (bean.getAnswer()) {
                    case 1:
                        trueAnswer = bean.getEx1();
                        break;
                    case 2:
                        trueAnswer = bean.getEx2();
                        break;
                    case 3:
                        trueAnswer = bean.getEx3();
                        break;
                    case 4:
                        trueAnswer = bean.getEx4();
                        break;
                }
                if (HardTest.getText().toString().equals(trueAnswer)) {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("정답!" + bean.getScore() + "획득!");
                    score += score;
                } else {
                    answerWindow.setVisibility(v.VISIBLE);
                    answerWindow.setText("오답입니다!");
                }
            }
            }



            problemNum++;


        if(beans.size()== problemNum){
            answerWindow.setVisibility(v.VISIBLE);
            answerWindow.setText("최종점수는 "+score+"입니다!");

            return;
        }


         problem();

    }
}

