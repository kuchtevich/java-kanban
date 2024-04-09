package model;


public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }
    public Epic getEpic() { //можно получить новое значение
        return epic;
    }

    public void setEpic(Epic epic) { //для сохранения нового значения
        this.epic = epic;
    }

    @Override
    public String toString(){
        return "SubTask{" +"name=' " + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id +
                '}';
    }
}
