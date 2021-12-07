package comps413f.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kwtse on 9/9/2019.
 */

// Performing sql command to the supported database
public class JobDb extends SQLiteOpenHelper {
    private	static final int DATABASE_VERSION =	1;
    private static final String DATABASE_NAME = "job.db"; // db name
    private static final String TABLE_NAME = "job"; // table name
    private static final String[] COLUMNS = { "jobid", "spend" ,"title", "details", "date", "time", "category" }; // column names

    public JobDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table creation sql statement
        final String TABLE_CREATION = "create table if not exists " + TABLE_NAME + "( "
                + COLUMNS[0] + " integer primary key autoincrement, "
                + COLUMNS[1] + " text not null, "
                + COLUMNS[2] + " text not null, "
                + COLUMNS[3] + " text not null, "
                + COLUMNS[4] + " text not null, "
                + COLUMNS[5] + " text not null, "
                + COLUMNS[6] + " text not null" + ")";
        db.execSQL(TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Get all jobs
    public void getAllJob(JobList jobList) {
        jobList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String jobId = cursor.getString(0);
            String spend = cursor.getString(1);
            String title = cursor.getString(2);
            String details = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String category = cursor.getString(6);
            jobList.addJob(jobId, spend, title, details, date, time, Job.Category.valueOf(category));
        }
        cursor.close();
    }

    // Add job
//    public void addJob(String title, String details, String date, String time, Job.Priority priority, Job.Category category) {
    public void addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Add code here
        // Task 1: Add a new job to database
        // i. Create an ContentValues object
        // ii. Setup the ContentValues object with provided job information and the "put" method
        // iii. Employ the "insert" method with the "db" object
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], job.getTitle());
        values.put(COLUMNS[2], job.getSpend());
        values.put(COLUMNS[3], job.getDetails());
        values.put(COLUMNS[4], job.getDate());
        values.put(COLUMNS[5], job.getTime());
        values.put(COLUMNS[6], job.getCategoryString());

        db.insert(TABLE_NAME, null, values);
    }

    // Update job details
    public void updateJob(Job job){
        SQLiteDatabase db = this.getWritableDatabase();
        // Add code here
        // Task 2: Update an existing job corresponding to the given job
        // i. Create an ContentValues object
        // ii. Setup the ContentValues object with provided job information from the input job and the "put" method
        // iii. Employ the "update" method with the "db" object with respect to the job id of the input job object

        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], job.getTitle());
        values.put(COLUMNS[2], job.getSpend());
        values.put(COLUMNS[3], job.getDetails());
        values.put(COLUMNS[4], job.getDate());
        values.put(COLUMNS[5], job.getTime());
        values.put(COLUMNS[6], job.getCategoryString());

        db.update(TABLE_NAME, values, COLUMNS[0]	+ "	= ?", new String[] { String.valueOf(job.getJobid())});
    }

    // Search a job with job id
    public Job searchJob(int jobId){
        SQLiteDatabase db = this.getReadableDatabase();
        String jobIdStr = Integer.toString(jobId);
        String[] args = { jobIdStr };
        Cursor cursor = db.query(TABLE_NAME, null, COLUMNS[0] + " =?", args, null, null, null);
        Job job = null;
        if	(cursor.moveToFirst()){
            String title = cursor.getString(1);
            String spend = cursor.getString(2);
            String details = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String category = cursor.getString(6);
            job = new Job(jobIdStr, spend, title, details, date, time, Job.Category.valueOf(category));
        }
        cursor.close();
        return job;
    }

    // Delete job with job id
    public void deleteJob(int jobId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Add code here
        // Task 3: Delete an existing job corresponding to the given job id
        // Employ the "delete" method with the "db" object with respect to the given job id
        db.delete(TABLE_NAME, COLUMNS[0]	+ "	= ?", new String[] { String.valueOf(jobId)});
    }
}
