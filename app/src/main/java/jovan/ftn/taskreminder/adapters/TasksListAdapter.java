package jovan.ftn.taskreminder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Select;

import java.util.List;

import jovan.ftn.taskreminder.R;
import jovan.ftn.taskreminder.entities.Task;

import static android.R.attr.data;

/**
 * Created by Jovan on 10/17/2016.
 */

public class TasksListAdapter extends ArrayAdapter {

    private Context context;


    public TasksListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;

    }


    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {



        final Task task = (Task) getItem(position);


        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listadapterlayout, parent, false);

        }


        final TextView title = (TextView) convertView.findViewById(R.id.noteName);


        title.setText(task.getTitle());
        title.setTextColor(Color.BLACK);
        Log.i("adapter", " title is " + task.getTitle());
        // Return the completed view to render on screen

        //final int value = task.isTaskDone()? ContextCompat.getColor(context, R.color.colorAccent):Color.BLACK;
        //title.setTextColor(value);
        if(task.isTaskDone())  title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final ImageView image = (ImageView) convertView.findViewById(R.id.imageDone);
        image.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));

        int iconValue = task.isTaskDone()? R.drawable.ic_check_box_black_24dp:R.drawable.ic_check_box_outline_blank_black_24dp;
        image.setImageResource(iconValue);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!task.isTaskDone()){
                    task.setTaskDone(true);
                    task.save();
                    //title.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                    title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    image.setImageResource(R.drawable.ic_check_box_black_24dp);

                    Toast.makeText(context,"Task is done"
                            , Toast.LENGTH_LONG).show();
                }else{
                    task.setTaskDone(false);
                    task.save();


                    image.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    title.setPaintFlags(title.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    Toast.makeText(context,"Task is not done"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;

    }


}
