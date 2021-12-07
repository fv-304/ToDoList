package comps413f.todolist;

import java.io.Serializable;

/**
 * Created by kwtse on 9/6/2019.
 */
// Model represents a job
public class Job implements Serializable {
    enum Category { Food, Bills, Transportation, Entertainment, Shopping, Insurance};
    private String jobid;
    private String spend;
    private String title;
    private String details;
    private String date;
    private String time;
    private Category category;

    public Job(String jobid, String spend, String title, String details, String date, String time, Category category) {
        this.jobid = jobid;
        this.spend = spend;
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public String getJobid() {
        return jobid;
    }

    public String getSpend() {return spend; }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryString() {
        return category.name();
    }
}
