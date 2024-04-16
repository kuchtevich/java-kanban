package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(String name, String description, int id) {
        super(name, description, Status.NEW, id);
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

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        calcStatus();
    }

    //удалить единичную подзадачу из хранилища subTasks
    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        calcStatus();
    }

    //очистить все данные хранилища subTasks
    public void removeAllSubtasks() {
        subTasks.clear();
        calcStatus();
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
