package jovan.ftn.taskreminder.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.adapters.TasksListAdapter;
import jovan.ftn.taskreminder.entities.Task;

public class TasksActivity extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });








        lv = (ListView) findViewById(R.id.taskslist);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = (Task) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("id", task.getId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        TasksListAdapter adapter = new TasksListAdapter(this, new Select().from(Task.class).execute());
        lv.setAdapter(adapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_task) {
            Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
