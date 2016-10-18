package jovan.ftn.taskreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.services.AddTaskService;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Add new task");


        Button okButton = (Button) findViewById(R.id.okInsert);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit1 = (EditText ) findViewById(R.id.inputTitle);
                EditText  edit2 = (EditText ) findViewById(R.id.inputContent);


                Intent si = new Intent(getApplicationContext(), AddTaskService.class);
                si.putExtra("title", edit1.getText().toString());
                Log.i("adapter", " in edit field title is " + edit1.getText());
                si.putExtra("content", edit2.getText().toString());
                startService(si);

                Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
                startActivity(intent);

            }
        });


    }

}