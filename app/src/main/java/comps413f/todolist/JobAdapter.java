package comps413f.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by kwtse on 9/6/2019.
 */

// Adapter class
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder>{
    private Context context;
    private JobList jobList;

    // RecyclerView recyclerView;
    public JobAdapter(Context context, JobList toDoList) {
        this.context = context;
        this.jobList = toDoList;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recycleview_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Job job = jobList.getItems()[position];
        holder.spendTextView.setText(job.getTitle());
        holder.titleTextView.setText(job.getTitle());
        holder.detailsTextView.setText(job.getDetails());
        holder.dateTextView.setText(job.getDate());
        holder.timeTextView.setText(job.getTime());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).editJob(Integer.parseInt(job.getJobid()));
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).deleteJob(Integer.parseInt(job.getJobid()));
            }
        });
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return jobList.getItems().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView spendTextView, titleTextView, detailsTextView, dateTextView, timeTextView;
        private ImageView editButton, deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_textview);
            detailsTextView = itemView.findViewById(R.id.details_textview);
            dateTextView = itemView.findViewById(R.id.date_textview);
            timeTextView = itemView.findViewById(R.id.time_textview);

            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}