package com.example.quizapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder>  {

    private ArrayList<QuestionUserBean> data;
    private ItemClickListener listener;

    public QuestionAdapter(ArrayList<QuestionUserBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_question, viewGroup, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        QuestionUserBean bean = data.get(i);

        questionViewHolder.textViewTitle.setText(bean.getType());

        questionViewHolder.textViewTime.setText(bean.getQuestion());

        questionViewHolder.itemView.setOnClickListener(v->{
            listener.onItemClick(v, i);
        });
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;

        return data.size();
    }
}
