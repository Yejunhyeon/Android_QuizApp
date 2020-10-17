package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class QuestionDBHelper extends SQLiteOpenHelper {

    private ArrayList<QuestionUserBean> data;

    public QuestionDBHelper(@Nullable Context context, @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        data = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table Problem(id integer primary key autoincrement, ";
        sql += "problemname text, problemscore integer, problemex1 text,problemex2 text, problemex3 text, problemex4 text, answer inreger,type text )";
        db.execSQL(sql);                            // SQL코드
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table memo";
        db.execSQL(sql);
        onCreate(db);
    }

    public long insert(QuestionUserBean Bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("problemname", Bean.getQuestion());
        values.put("problemscore", Bean.getScore());
        values.put("type", Bean.getType());
        values.put("problemex1", Bean.getEx1());
        values.put("problemex2", Bean.getEx2());
        values.put("problemex3", Bean.getEx3());
        values.put("problemex4", Bean.getEx4());
        values.put("answer", Bean.getAnswer());
        Log.i("20",Bean.getQuestion());
        Log.i("20",Bean.getType());
        Log.i("20",Bean.getAnswer()+"");
        Log.i("20",Bean.getScore()+"");
        Log.i("20",Bean.getEx1());
        Log.i("20",Bean.getEx2());
        Log.i("20",Bean.getEx3());
        Log.i("20",Bean.getEx4());

        return db.insert("Problem", null, values);     // problem  값을 넣는다
    }

    public int update(QuestionUserBean Bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("problemname", Bean.getQuestion());      // 아이디 제외하고 다!
        values.put("problemscore", Bean.getScore());
        values.put("problemex1", Bean.getEx1());
        values.put("problemex2", Bean.getEx2());
        values.put("problemex3", Bean.getEx3());
        values.put("problemex4", Bean.getEx4());
        values.put("answer", Bean.getAnswer());

        String idStr = String.valueOf(Bean.getQid());
        return db.update("Problem", values, "id=?", new String[] {idStr});
    }

    public int delete(int id){                  //삭제
        SQLiteDatabase db = getWritableDatabase();
        String idStr = String.valueOf(id);
        return db.delete("Problem", "id=?", new String[] {idStr});
    }

    public QuestionUserBean get(int id){
        SQLiteDatabase db = getReadableDatabase();
        String idStr = String.valueOf(id);
        Cursor c = db.query("Problem", null,
                "id=?", new String[] {idStr},
                null, null, null);
        if(c.moveToNext()){
            QuestionUserBean bean = new QuestionUserBean();
            bean.setQid(c.getInt(c.getColumnIndex("id")));
            bean.setQuestion(c.getString(c.getColumnIndex("problemname")));
            bean.setScore(c.getInt(c.getColumnIndex("problemscore")));
            bean.setType(c.getString(c.getColumnIndex("type")));
            bean.setEx1(c.getString(c.getColumnIndex("problemex1")));
            bean.setEx2(c.getString(c.getColumnIndex("problemex2")));
            bean.setEx3(c.getString(c.getColumnIndex("problemex3")));
            bean.setEx4(c.getString(c.getColumnIndex("problemex4")));
            bean.setAnswer(c.getInt(c.getColumnIndex("answer")));
            Log.i("10",bean.getQuestion());
            Log.i("10",bean.getType());
            Log.i("10",bean.getAnswer()+"");
            Log.i("10",bean.getScore()+"");
            Log.i("10",bean.getEx1());
            Log.i("10",bean.getEx2());
            Log.i("10",bean.getEx3());
            Log.i("10",bean.getEx4());
            return bean; // 다 썼으면 보내
        } else {
            return null;
        }
}

    public ArrayList<QuestionUserBean> get(){
    SQLiteDatabase db = getReadableDatabase();
    Cursor c = db.query("Problem", null,
            null, null,
            null, null, null);
    data.clear();
    while(c.moveToNext()){
        QuestionUserBean bean = new QuestionUserBean();
        bean.setQid(c.getInt(c.getColumnIndex("id")));
        bean.setQuestion(c.getString(c.getColumnIndex("problemname")));
        bean.setScore(c.getInt(c.getColumnIndex("problemscore")));
        bean.setType(c.getString(c.getColumnIndex("type")));
        bean.setEx1(c.getString(c.getColumnIndex("problemex1")));
        bean.setEx2(c.getString(c.getColumnIndex("problemex2")));
        bean.setEx3(c.getString(c.getColumnIndex("problemex3")));
        bean.setEx4(c.getString(c.getColumnIndex("problemex4")));
        bean.setAnswer(c.getInt(c.getColumnIndex("answer")));
        data.add(bean);
    }
    return data;
    }
}