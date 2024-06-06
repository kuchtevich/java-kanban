package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTaskStatus(int subTaskId, Status status) {
        for (SubTask subTask : subTasks) {
            if (subTask.getId() == subTaskId) {
                subTasks.get(subTaskId).setStatus(status);
                calcStatus();
            }
        }
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        calcStatus();
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        calcStatus();
    }

    public void removeAllSubtasks() {
        subTasks.clear();
        calcStatus();
    }

    @Override
    public Integer getEpicId() {
        return super.getEpicId();
    }


    @Override
    public String toString() {
        return "Epic{" + "name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                '}';
    }

    public void calcStatus() {
        if (subTasks.isEmpty()) {
            status = Status.NEW;
        } else {
            int newStatusCount = 0;
            int doneStatusCount = 0;

            for (SubTask subTask : subTasks) {
                if (subTask.getStatus().equals(Status.DONE)) {
                    doneStatusCount++;
                } else if (subTask.getStatus().equals(Status.NEW)) {
                    newStatusCount++;
                }
            }

            if (newStatusCount == subTasks.size()) {
                status = Status.NEW;
            } else if (doneStatusCount == subTasks.size()) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
        }
    }
}
