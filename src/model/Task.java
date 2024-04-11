package model;

public class Task {
   protected String name;
   protected String description;
   protected int id;
   protected Status status;
   public Task(String name, String description, Status status, int id) {
      this.name = name;
      this.description = description;
      this.status= status;
      this.id = id;
   }

   public Task(String name, String description) {
      this.name = name;
      this.description = description;
   }



   public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public String getName() {
      return name;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   @Override
   public String toString(){
      return "Task{" +"name=' " + name + '\'' +
              ", description='" + description + '\'' +
              ", status='" + status + '\'' +
              ", id='" + id +
              '}';
   }

}
