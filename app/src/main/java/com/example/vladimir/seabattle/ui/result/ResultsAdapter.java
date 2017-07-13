package com.example.vladimir.seabattle.ui.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.logic.models.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Result> results;

    public ResultsAdapter(List<Result> results) {
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
            DateFormat dateFormat = new SimpleDateFormat("mm:ss");
            ((ResultsViewHolder) holder).steps.setText(dateFormat.format(result.getStepsCount()));
            ((ResultsViewHolder) holder).time.setText(String.valueOf(result.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView steps;
        private TextView time;

        public ResultsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            steps = (TextView) itemView.findViewById(R.id.steps);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
