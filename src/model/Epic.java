package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }
    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }


    @Override
    public String toString(){
        return "Epic{" +"name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                '}';
    }
}
