package model;


import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private int epicId;


    public SubTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" + "name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                ", epicId='" + epicId +
                '}';
    }
}
