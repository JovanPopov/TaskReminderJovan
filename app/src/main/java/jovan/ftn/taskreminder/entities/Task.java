package jovan.ftn.taskreminder.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jovan on 10/17/2016.
 */

@Table(name = "Tasks")
public class Task extends Model {

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "taskDone")
    private boolean taskDone;

    public Task() {
    }

    public Task(String title, String content, boolean taskDone) {
        this.title = title;
        this.content = content;
        this.taskDone = taskDone;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}
