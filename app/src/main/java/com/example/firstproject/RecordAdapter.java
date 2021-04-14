package com.example.firstproject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private static final String TAG = "gg";
    private final LayoutInflater inflater;
    private final List<Record> records;
    private Context context;

    RecordAdapter(Context context, List<Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {
        Record record = records.get(position);
        Log.d(TAG, "onBindViewHolder: "+getItemCount());
        holder.itemView.setBackgroundColor(record.getColor());
        holder.nameView.setText(record.getName());
        holder.dateView.setText(record.getDate());
        ColorDrawable cd = (ColorDrawable) holder.itemView.getBackground();
        int colorRec = cd.getColor();
        if (colorRec == context.getResources().getColor(R.color.black)){
            holder.nameView.setTextColor(context.getResources().getColor(R.color.white));
            holder.dateView.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick: "+record.getRecId());
                MainFragment mainFragment = FragmentManager.findFragment(v);
                mainFragment.goToEdit(false, record.getRecId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, dateView;
        final RelativeLayout itemView;
        public ViewHolder(View view) {
            super(view);
            itemView = (RelativeLayout)view.findViewById(R.id.record_item);
            nameView = (TextView)view.findViewById(R.id.record_name);
            dateView = (TextView)view.findViewById(R.id.record_date);
        }
    }
}
