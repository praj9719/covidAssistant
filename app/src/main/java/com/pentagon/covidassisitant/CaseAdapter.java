package com.pentagon.covidassisitant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {
    private Activity context;
    private List<Case> mList;

    CaseAdapter(Activity context, List<Case> mList){
        this.context = context;
        this.mList = mList;
    }
    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.case_layout, parent, false);
        return new CaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseViewHolder holder, int position) {
        Case newCase = mList.get(position);
        holder.mState.setText(newCase.getState());
        holder.mRecovered.setText(newCase.getRecovered());
        holder.mDeath.setText(newCase.getDeath());
        holder.mTotal.setText(newCase.getTotal());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        public TextView mState, mRecovered, mDeath, mTotal;
        public CaseViewHolder(@NonNull View itemView) {
            super(itemView);
            mState = itemView.findViewById(R.id.case_layout_text_view_state);
            mRecovered = itemView.findViewById(R.id.case_layout_text_view_recovered);
            mDeath = itemView.findViewById(R.id.case_layout_text_view_death);
            mTotal = itemView.findViewById(R.id.case_layout_text_view_total);
        }
    }
}
