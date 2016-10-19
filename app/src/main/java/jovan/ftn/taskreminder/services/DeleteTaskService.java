package jovan.ftn.taskreminder.services;

import android.app.IntentService;
import android.content.Intent;

import com.activeandroid.query.Select;

import jovan.ftn.taskreminder.entities.Task;

/**
 * Created by Jovan on 10/19/2016.
 */

public class DeleteTaskService extends IntentService {

    public DeleteTaskService() {
        super("DeleteTaskService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Long id = intent.getLongExtra("id", 0);
        Task task = new Select().from(Task.class).where("id = ?", id).executeSingle();
        task.delete();

    }
}
