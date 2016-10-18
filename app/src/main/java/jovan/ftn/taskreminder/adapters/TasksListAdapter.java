package jovan.ftn.taskreminder.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.i("adapter", " title is " + task.getTitle());
        // Return the completed view to render on screen

        final int value = task.isTaskDone()? Color.GREEN:Color.RED;
        title.setTextColor(value);

        final ImageView image = (ImageView) convertView.findViewById(R.id.imageDone);
        int iconValue = task.isTaskDone()? R.drawable.ic_check_box_black_24dp:R.drawable.ic_check_box_outline_blank_black_24dp;
        image.setImageResource(iconValue);
       // image.setTag(task.getId());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Long id = (Long) v.getTag();
                //Task task1 = new Select().from(Task.class).where("id = ?", id).executeSingle();
                if(!task.isTaskDone()){
                    task.setTaskDone(true);
                    task.save();
                    title.setTextColor(Color.GREEN);
                    image.setImageResource(R.drawable.ic_check_box_black_24dp);

                    Toast.makeText(context,"Task is done"
                            , Toast.LENGTH_LONG).show();
                }else{
                    task.setTaskDone(false);
                    task.save();

                    title.setTextColor(Color.RED);
                    image.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);

                    Toast.makeText(context,"Task is not done"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;

    }

}
