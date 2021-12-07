package comps413f.todolist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwtse on 9/9/2019.
 */

// Model of job list
public class JobList {
    // ArrayList for storing jobs
    List<Job> jobList = new ArrayList<Job>();

    // Add a new job to the list
    public void addJob(String jobId, String spend, String title, String details, String date, String time, Job.Category category) {
        Job job = new Job(jobId, spend, title, details, date, time, category);
        jobList.add(job);
    }

    // Return all job in array format
    public Job[] getItems() {
        // View all jobs
        return jobList.toArray(new Job[jobList.size()]);
    }

    // Empty job list
    public void clear() {
        jobList.clear();
    }
}
