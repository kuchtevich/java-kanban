package model;


public class SubTask extends Task {
    private Epic epic;

    private int epicId;

    public SubTask(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }
    public int getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString(){
        return "SubTask{" +"name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                //", epicId='" + epicId +
                '}';
    }
}
