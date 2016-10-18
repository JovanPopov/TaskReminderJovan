package jovan.ftn.taskreminder.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import jovan.ftn.taskreminder.entities.Task;


public class AddTaskService extends IntentService {



    public AddTaskService() {
        super("AddTaskService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

            Task nt = new Task(title, content, false);
            nt.save();


        }
    }


}
