package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final static LocalDateTime defaultStartTime = LocalDateTime.of(2000, 12, 12, 12, 12);

    private final List<SubTask> subTasks = new ArrayList<>();

    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW, defaultStartTime, Duration.ZERO);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW, defaultStartTime, Duration.ZERO);
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
        calculateAllFields();
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        calculateAllFields();
    }

    public void removeAllSubtasks() {
        subTasks.clear();
        calculateAllFields();
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

    public void calculateAllFields() {
        calcStatus();
        calculateStartTime();
        calculateDuration();
        calculateEndTime();
    }

    public void calculateDuration() {
        Duration result = Duration.ZERO;

        for (SubTask subTask : subTasks) {
            result = result.plus(subTask.getDuration());
        }

        duration = result;
    }

    public void calculateStartTime() {
        // search min startTime
        LocalDateTime result = defaultStartTime;

        if (!subTasks.isEmpty()) {
            result = subTasks.getFirst().getStartTime();
        }

        for (SubTask subTask : subTasks) {
            if (result.toEpochSecond(ZoneOffset.UTC) > subTask.getStartTime().toEpochSecond(ZoneOffset.UTC))
                result = subTask.getStartTime();
        }

        startTime = result;
    }

    public void calculateEndTime() {
        LocalDateTime result = defaultStartTime;

        if (!subTasks.isEmpty()) {
            result = subTasks.getFirst().getEndTime();
        }

        for (SubTask subTask : subTasks) {
            if (result.toEpochSecond(ZoneOffset.UTC) < subTask.getEndTime().toEpochSecond(ZoneOffset.UTC))
                result = subTask.getEndTime();
        }

        endTime = result;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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
