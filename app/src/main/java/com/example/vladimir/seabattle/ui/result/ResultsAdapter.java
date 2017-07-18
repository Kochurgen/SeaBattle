package com.example.vladimir.seabattle.ui.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.logic.models.Result;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Result> results;

    ResultsAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public int getItemViewType(int position) {
        return results.get(position).getContactType() == ContentType.COMPUTER ?
                R.layout.template_computer_info : R.layout.template_human_info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Result result = results.get(position);
        if (result != null) {
            ((ResultsViewHolder) holder).name.setText(result.getUserName());
            ((ResultsViewHolder) holder).time.setText(TimeFormat.convertTimeStampToDate(result.getTimestamp()));
            ((ResultsViewHolder) holder).steps.setText(String.valueOf(result.getStepsCount()));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private static class ResultsViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView steps;
        private final TextView time;

        ResultsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            steps = (TextView) itemView.findViewById(R.id.steps);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
