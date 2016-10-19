package jovan.ftn.taskreminder.activities;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.activeandroid.loaders.ModelLoader;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.adapters.TasksListAdapter;
import jovan.ftn.taskreminder.entities.Task;

public class TasksActivity extends AppCompatActivity {

    private TasksListAdapter mAdapter;
    private ListView lv;
    private ContentResolver mResolver;
    private ContentObserver mObserver;
    private PostLoader mLoader;
    private Account mAccount;

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
                addTask();
            }
        });








        lv = (ListView) findViewById(R.id.taskslist);
        mAdapter = new TasksListAdapter(this,  new ArrayList<Task>());
        lv.setAdapter(mAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = (Task) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("id", task.getId());
                startActivity(intent);
            }
        });

        mLoader = new PostLoader();
        getSupportLoaderManager().initLoader(0, null, mLoader);


        mResolver = getContentResolver();
        mObserver = new ContentObserver(new Handler(getMainLooper()))
        {
            @Override
            public void onChange(boolean selfChange)
            {
                onChange(selfChange, null);
            }


            @Override
            public void onChange(boolean selfChange, Uri changeUri)
            {
                Bundle settingsBundle = new Bundle();
                ContentResolver.requestSync(mAccount, "jovan.ftn.taskreminder.provider", settingsBundle);
            }
        };

        mResolver.registerContentObserver(createUri(), true, mObserver);




    }

    public Uri createUri()
    {
        StringBuilder uri = new StringBuilder();
        uri.append("content://");
        uri.append(getPackageName());
        uri.append("/");
        return Uri.parse(uri.toString());
    }


    class PostLoader implements LoaderManager.LoaderCallbacks<List<Task>>
    {

        @Override
        public Loader<List<Task>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<Task>(TasksActivity.this, Task.class, true);
        }


        @Override
        public void onLoadFinished(Loader<List<Task>> loader, List<Task> data)
        {
            mAdapter.clear();
            mAdapter.addAll(data);
            mAdapter.notifyDataSetChanged();

        }


        @Override
        public void onLoaderReset(Loader<List<Task>> loader)
        {
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();





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
           addTask();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mResolver.unregisterContentObserver(mObserver);
    }

    private void addTask(){
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivity(intent);
    }

}
