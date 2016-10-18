package jovan.ftn.taskreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.entities.Task;

public class TaskDetailActivity extends AppCompatActivity {

    private Task task;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        long id = extras.getLong("id");
        task = new Select().from(Task.class).where("id = ?", id).executeSingle();

        setTitle(task.getTitle());




        fillData(id);



    }


    public void fillData(long id){

        TextView title = (TextView) findViewById(R.id.detailTitle);
        title.setText(task.getTitle());

        TextView content = (TextView) findViewById(R.id.detailContent);
        content.setText(task.getContent());


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_tasks, menu);
        menuItem = menu.findItem(R.id.detail_done);

        int iconValue = task.isTaskDone()? R.drawable.ic_check_box_black_24dp:R.drawable.ic_check_box_outline_blank_black_24dp;
        menuItem.setIcon(iconValue);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.detail_done) {
            if(!task.isTaskDone()){
                task.setTaskDone(true);
                task.save();

                menuItem.setIcon(R.drawable.ic_check_box_black_24dp);

                Toast.makeText(this,"Task is done"
                        , Toast.LENGTH_LONG).show();
            }else{
                task.setTaskDone(false);
                task.save();

                menuItem.setIcon(R.drawable.ic_check_box_outline_blank_black_24dp);

                Toast.makeText(this,"Task is not done"
                        , Toast.LENGTH_LONG).show();
            }


        }else if (id == R.id.detail_delete){

            task.delete();
            Toast.makeText(this,"Task deleted"
                    , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}