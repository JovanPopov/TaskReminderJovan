package jovan.ftn.taskreminder.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.entities.Task;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Add a new task");


        Button okButton = (Button) findViewById(R.id.okInsert);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit1 = (EditText) findViewById(R.id.inputTitle);
                EditText edit2 = (EditText) findViewById(R.id.inputContent);

                if(TextUtils.isEmpty(edit1.getText().toString())) {
                    edit1.setError("You must at least enter the title of the task");

                }else {




            /*    Intent si = new Intent(getApplicationContext(), AddTaskService.class);
                si.putExtra("title", edit1.getText().toString());
                Log.i("adapter", " in edit field title is " + edit1.getText());
                si.putExtra("content", edit2.getText().toString());
                startService(si);*/

                    Task nt = new Task(edit1.getText().toString(), edit2.getText().toString(), false);
                    nt.save();

                    onBackPressed();
                }
            }
        });


    }

}
