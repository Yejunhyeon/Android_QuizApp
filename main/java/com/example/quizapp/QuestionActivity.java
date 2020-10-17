package com.example.quizapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity  implements  View.OnClickListener , CompoundButton.OnCheckedChangeListener {
    private  QuestionUserBean bean;
    private  QuestionDBHelper deHelper;
    private  int id;
    private static final int REQ_IMGAE = 1;
    private ArrayList<QuestionUserBean> beans = new ArrayList<>();

   private ToggleButton toggleButton;
   private ConstraintLayout picturegroup, checkgroup;
   private EditText ProblemName, ProblemScroe ,EditProblem1, EditProblem2, EditProblem3, EditProblem4;
   private RadioButton editRadioButton1, editRadioButton2, editRadioButton3, editRadioButton4, imageRadioButton1,imageRadioButton2,imageRadioButton3,imageRadioButton4;
   private ImageView imageView1, imageView2,imageView3,imageView4;


    //선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        imageView1 = findViewById(R.id.imageView2);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 1);
            }
        });

        imageView2 = findViewById(R.id.imageView3);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 2);
            }
        });
        imageView3 = findViewById(R.id.imageView4);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 3);
            }
        });
        imageView4 = findViewById(R.id.imageView5);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 4);
            }
        });

      toggleButton = findViewById(R.id.toggleButton);
      toggleButton.setOnCheckedChangeListener(this);

      picturegroup = findViewById(R.id.PictureProblem);
      picturegroup.setVisibility(View.GONE);
      checkgroup = findViewById(R.id.CheckProblem);
      checkgroup.setVisibility(View.VISIBLE);

      ProblemName = findViewById(R.id.ProblemName);
      ProblemScroe = findViewById(R.id.ProblemScore);
        EditProblem1 = findViewById(R.id.TextProblem1);
        EditProblem2 = findViewById(R.id.TextProblem2);
        EditProblem3 = findViewById(R.id.TextProblem3);
        EditProblem4 = findViewById(R.id.TextProblem4);

        editRadioButton1 = findViewById(R.id.TextCh1); //객관식 버튼
        editRadioButton2 = findViewById(R.id.TextCh2);
        editRadioButton3 = findViewById(R.id.TextCh3);
        editRadioButton4 = findViewById(R.id.TextCh4);

        imageRadioButton1 = findViewById(R.id.imbutton1);   //이미지 버튼
        imageRadioButton2 = findViewById(R.id.imbutton2);
        imageRadioButton3 = findViewById(R.id.imbutton3);
        imageRadioButton4 = findViewById(R.id.imbutton4);

        imageView1 = findViewById(R.id.imageView2);
        imageView2 = findViewById(R.id.imageView3);
        imageView3 = findViewById(R.id.imageView4);
        imageView4 = findViewById(R.id.imageView5);



                  deHelper = new QuestionDBHelper(this, "DB", null,1); //DB불러오기
            beans  = deHelper.get();
            id = getIntent().getIntExtra("id", -1);
            if(id > -1){
                bean = deHelper.get(id);
                ProblemName.setText(bean.getQuestion());
                ProblemScroe.setText(bean.getScore()+"");


            if(bean.getType().equals(QuestionUserBean.TYPE_TEXT)){          // 버튼  edit와 image 따로따로
                EditProblem1.setText(bean.getEx1());
                EditProblem2.setText(bean.getEx2());
                EditProblem3.setText(bean.getEx3());
                EditProblem4.setText(bean.getEx4());
                    switch (bean.getAnswer()) {

                        case 1:
                            editRadioButton1.setChecked(true);
                            break;

                        case 2:
                            editRadioButton2.setChecked(true);
                            break;

                        case 3:
                            editRadioButton3.setChecked(true);
                            break;

                        case 4:
                            editRadioButton4.setChecked(true);
                            break;
                    }
                }
                else
                {

                    picturegroup.setVisibility(View.VISIBLE);
                    checkgroup.setVisibility(View.GONE);

                    try {
                        Uri imageUri = Uri.parse(bean.getEx1());
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx1(imageUri.toString());
                        imageView1.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {

                    }


                    try {
                        Uri imageUri = Uri.parse(bean.getEx2());
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx2(imageUri.toString());

                        imageView2.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                    }

                    try {
                        Uri imageUri = Uri.parse(bean.getEx3());
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx3(imageUri.toString());

                        imageView3.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException a) {

                    }

                    try {
                        Uri imageUri = Uri.parse(bean.getEx4());
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx4(imageUri.toString());

                        imageView4.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException b) {

                    }


                    switch (bean.getAnswer()) {
                        case 1:
                            imageRadioButton1.setChecked(true);
                            break;

                        case 2:
                            imageRadioButton2.setChecked(true);
                            break;

                        case 3:
                            imageRadioButton3.setChecked(true);
                            break;

                        case 4:
                            imageRadioButton4.setChecked(true);
                            break;
                    }
                }
            }

        else {
            bean = new QuestionUserBean();
        }



    }

    public void onSave(View v){                                         // 저장버튼
        bean.setQuestion(ProblemName.getText().toString());
        bean.setScore(Integer.parseInt(ProblemScroe.getText().toString()));
       if(!toggleButton.isChecked()) {
           bean.setType(QuestionUserBean.TYPE_TEXT);
           bean.setEx1(EditProblem1.getText().toString());
           bean.setEx2(EditProblem2.getText().toString());
           bean.setEx3(EditProblem3.getText().toString());
           bean.setEx4(EditProblem4.getText().toString());

           if(editRadioButton1.isChecked())
           {
               bean.setAnswer(1);
           }
           else  if(editRadioButton2.isChecked())
           {
               bean.setAnswer(2);
           }
           else  if(editRadioButton3.isChecked())
           {
               bean.setAnswer(3);
           }
           else  if(editRadioButton4.isChecked())
           {
               bean.setAnswer(4);
           }

       }
       else {
           //  bean.setEx1.;
           bean.setType(QuestionUserBean.TYPE_IMAGE);

           if (imageRadioButton1.isChecked()) {
               bean.setAnswer(1);
           } else if (imageRadioButton2.isChecked()) {
               bean.setAnswer(2);
           } else if (imageRadioButton3.isChecked()) {
               bean.setAnswer(3);
           } else if (imageRadioButton4.isChecked()) {
               bean.setAnswer(4);
           }
       }

        if(id > -1)
            deHelper.update(bean);
        else
            deHelper.insert(bean);
        setResult(RESULT_OK);
        finish();



    }
    public void onDelete(View v){       // 삭제버튼
        if(id == -1) finish();
        deHelper.delete(id);
        setResult(RESULT_OK);
        finish();
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   //toggle 버튼 클릭 이벤트
        if(isChecked) {
            picturegroup.setVisibility(buttonView.VISIBLE);
            checkgroup.setVisibility(buttonView.GONE);
        }
        else{
            checkgroup.setVisibility(buttonView.VISIBLE);
            picturegroup.setVisibility(buttonView.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK){
                if(data != null) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx1(imageUri.toString());
                        imageView1.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this,"그림을 선택하지 않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 2) {
            if(resultCode == RESULT_OK){
                if(data != null) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx2(imageUri.toString());

                        imageView2.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this,"그림을 선택하지 않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 3) {
            if(resultCode == RESULT_OK){
                if(data != null) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx3(imageUri.toString());

                        imageView3.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this,"그림을 선택하지 않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 4 ){
            if(resultCode == RESULT_OK){
                if(data != null) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bean.setEx4(imageUri.toString());

                        imageView4.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this,"그림을 선택하지 않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
 }


