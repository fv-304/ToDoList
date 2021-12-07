package comps413f.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by kwtse on 9/6/2019.
 */

// Activity for editing or adding job
public class AddJobActivity extends AppCompatActivity {
    private EditText titleEditText, detailsEditText, spendEditText;
    private Button dateSelect, timeSelect;
    private Spinner categorySpinner;
    private final int DEFAULT_JOB_ID = -1;
    static final String JOB_ADD = "JOB_ADD";
    static final String JOB = "JOB";
    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_layout);
        spendEditText = findViewById(R.id.spend);
        titleEditText = findViewById(R.id.title);
        detailsEditText = findViewById(R.id.details);
        dateSelect = findViewById(R.id.date_button);
        timeSelect = findViewById(R.id.time_button);

        // Setup categorySpinner
        String[] categories = Arrays.toString(Job.Category.values()).replaceAll("^.|.$", "").split(", ");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        categorySpinner = findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(categoryAdapter);

        // Try to retrieve Job instance from activity "MainActivity"
        // If job edit, the corresponding job is sent to this activity
        // Otherwise, no job can be obtained
        jobId = "";
        Intent data = getIntent();
        if (data != null && data.hasExtra(AddJobActivity.JOB)) {
            Job job = null;
            // Get job returned from parent activity for edition
            job = (Job) data.getExtras().getSerializable(AddJobActivity.JOB);

            // If job existed, update the ui with the corresponding job details
            if (job != null) {
                jobId = job.getJobid();
                // Show the received job information to the ui
                spendEditText.setText(job.getTitle());
                titleEditText.setText(job.getTitle());
                detailsEditText.setText(job.getDetails());
                dateSelect.setText(job.getDate());
                timeSelect.setText(job.getTime());

                int i;
                for (i=0; i<categories.length; i++) {
                    if (job.getCategoryString().equals(categories[i])) {
                        categorySpinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    // Handler of "Clear" button click
    public void clearJob(View view) {
        spendEditText.setText("");
        titleEditText.setText("");
        detailsEditText.setText("");
        dateSelect.setText("");
        timeSelect.setText("");
    }

    // Handler of "Cancel" button click
    public void cancelJob(View view) {
        // Add code here
        // Task 1: No job is returned to the parent activity and finish this activity
        // i. Pass "null" to the parent activity with the setResult method
        // ii. Closing the current activity with the finish method
        setResult(RESULT_CANCELED, null);
        finish();
    }

    // Handler of "Save" button click
    public void saveJob(View view) {
        // Add code here
        // Task 2: Return resulted job to the parent activity and finish this activity
        // i. Get all related information from ui widgets
        // ii. Create a job object with the retrieved information
        // iii. Create an Intent object
        // iv. Put the created job object to the intent object with the putExtra method
        // v. Pass the data to the parent activity with the setResult method
        // vi. Closing the current activity with the finish method
        String spend = spendEditText.getText().toString();
        String title = titleEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String date = dateSelect.getText().toString();
        String time = timeSelect.getText().toString();
        Job.Category category = Job.Category.valueOf(categorySpinner.getSelectedItem().toString());
        Job resultJob = new Job(jobId, spend, title, details, date, time, category);

        Intent intent = new Intent();
        intent.putExtra(JOB_ADD, resultJob);
        setResult(RESULT_OK, intent);
        finish();
    }

    // Handler of "Date Selection" click
    public void dateSelection(final View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                ((Button)view).setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
            }
        };
        DatePickerDialog datePicker = new DatePickerDialog(AddJobActivity.this, dateListener, year, month, day);
        datePicker.show();
    }

    // Handler of "Time Selection" click
    public void timeSelection(final View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                ((Button)view).setText(selectedHour + ":" + selectedMinute);
            }
        };
        TimePickerDialog timePicker = new TimePickerDialog(AddJobActivity.this, timeListener, hour, minute, true);//Yes 24 hour time
        timePicker.show();
    }
}