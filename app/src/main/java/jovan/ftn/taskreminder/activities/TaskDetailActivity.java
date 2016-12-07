package jovan.ftn.taskreminder.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import jovan.ftn.taskreminder.services.ChangeTaskStateService;
import jovan.ftn.taskreminder.services.DeleteTaskService;

public class TaskDetailActivity extends AppCompatActivity {

    private Task task;
    private MenuItem menuItem;
    private ImageView imageDone;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id");

        new loadData().execute();





    }


    public void fillData(long id){

        TextView title = (TextView) findViewById(R.id.detailTitle);
        title.setText(task.getTitle());

        TextView content = (TextView) findViewById(R.id.detailContent);
        content.setText(task.getContent());

        imageDone = (ImageView) findViewById(R.id.imageDone);

        if(!task.isTaskDone()) imageDone.setVisibility(View.GONE);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_tasks, menu);
        menuItem = menu.findItem(R.id.detail_done);

        int iconValue = task.isTaskDone()? R.drawable.ic_check_box_white_24dp:R.drawable.ic_check_box_outline_blank_white_24dp;
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

            Intent si = new Intent(getApplicationContext(), ChangeTaskStateService.class);
            si.putExtra("id", task.getId());
            startService(si);

            if(!task.isTaskDone()){
                imageDone.setVisibility(View.VISIBLE);
                menuItem.setIcon(R.drawable.ic_check_box_white_24dp);

                Toast.makeText(this,"Task is done"
                        , Toast.LENGTH_LONG).show();
            }else{
                imageDone.setVisibility(View.GONE);
                menuItem.setIcon(R.drawable.ic_check_box_outline_blank_white_24dp);

                Toast.makeText(this,"Task is not done"
                        , Toast.LENGTH_LONG).show();
            }


        }else if (id == R.id.detail_delete){


            AlertDialog.Builder alert = new AlertDialog.Builder(
                    this);
            alert.setTitle("Confirm action");
            alert.setMessage("Are you sure you want to delete this task?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do your work here
                    Intent si = new Intent(getApplicationContext(), DeleteTaskService.class);
                    si.putExtra("id", task.getId());
                    startService(si);



                    dialog.dismiss();
                    onBackPressed();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();



        }

        return super.onOptionsItemSelected(item);
    }

    class loadData extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            task = new Select().from(Task.class).where("id = ?", id).executeSingle();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setTitle(task.getTitle());
            fillData(id);


        }
    }


}
