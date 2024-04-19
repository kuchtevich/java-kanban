package model;


public class SubTask extends Task {

    private int epicId;

    public SubTask(final String name, final String description, final Status status, final int id, final int epicId) {
        super(name, description, status, id);
        this.epicId=epicId;
    }

    public SubTask(final String name, final String description, final Status status, final int epicId) {
        super(name, description, status);
        this.epicId=epicId;
    }
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString(){
        return "SubTask{" +"name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                ", epicId='" + epicId +
                '}';
    }
}
