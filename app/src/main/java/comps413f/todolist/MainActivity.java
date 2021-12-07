package comps413f.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Activity for showing jobs
public class MainActivity extends AppCompatActivity {
    private JobDb jobDb;
    private JobList jobList = new JobList();
    private JobAdapter adapter;
    private static final int JOB_ADD_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get all stored jobs from db
        jobDb = new JobDb(this);
        jobDb.getAllJob(jobList);

        // Setup RecycleView
        RecyclerView recyclerView = findViewById(R.id.job_list_recycler_view);
        adapter = new JobAdapter(this, jobList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.notifyDataSetChanged();
    }

    // Floating action button click
    public void addJob(View view) {
        // Add code here
        // Task 1: Floating action button click event handling
        // i. Create an Intent object
        // ii. Setup the Intent object with the setClassName method
        // iii. Start another activity with startActivityForResult method
        // iv. Perform explicit transition animation

        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, getPackageName() + ".AddJobActivity");
        startActivityForResult(intent, JOB_ADD_ID);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    // Override the onActivityResult method for getting resulted job
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == JOB_ADD_ID) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra(AddJobActivity.JOB_ADD)) {
                    Job job = null;
                    // Add code here
                    // Task 2: Add returned new job to database
                    // i. Get job returned from AddJobActivity
                    job = (Job) data.getExtras().getSerializable(AddJobActivity.JOB_ADD);

                    // ii. Check if resulted job is  not null
                    // If so
                    // - Obtain related information of the job
                    // - Check if the job id of the resulted job equals to empty string
                    //   - If so, it is a new job and add it to the database with the "addJob" method of the "jobDb" object
                    //   - Otherwise, update the job with the "updateJob" method of the"jobDb" object

                    // iii. Update display
                    if (job != null) {
                        // ii. Add the new job to job list
                        if (job.getJobid().equals("")) {  // Add a new job
                            jobDb.addJob(job);
                        }
                        else {  // Update an existing job
                            jobDb.updateJob(job);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    // Delete job with provided job id
    public void deleteJob(int jobId) {
        jobDb.deleteJob(jobId);
        jobDb.getAllJob(jobList);
        adapter.notifyDataSetChanged();
    }

    // Edit job details
    public void editJob(int jobId) {
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, getPackageName() + ".AddJobActivity");
        intent.putExtra(AddJobActivity.JOB, jobDb.searchJob(jobId));
        startActivityForResult(intent, JOB_ADD_ID);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        jobDb.getAllJob(jobList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(jobDb != null){
            jobDb.close();
        }
    }
}
