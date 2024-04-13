import model.*;
import service.*;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

    TaskManager taskManager = new TaskManager();
    Task task = taskManager.addNewTask(new Task("Cоздание новой задачи",
            "сменить профессию", Status.NEW,1));
    System.out.println("Создана новая задача: " + task);


    Task task2 = taskManager.addNewTask(new Task("Cоздание новой задачи",
                "сменить город", Status.IN_PROGRESS,2));
    System.out.println("Создана новая задача: " + task2);


    Epic epic =  taskManager.addNewEpic(new Epic("Создание нового эпика", "учеба", 3));
    System.out.println("Новый эпик создан: " + epic);

    Epic epic2 =  taskManager.addNewEpic(new Epic("Создание нового", "сдать ТЗ", 6));
    System.out.println("Новый эпик создан: " + epic2);

    SubTask subTask = taskManager.addNewSubtask(new SubTask("Создание новой подзадачи",
            "учеба", Status.IN_PROGRESS, 4, 3));
    System.out.println("Подзадача " + subTask);

    SubTask subTask2 = taskManager.addNewSubtask(new SubTask("Создание новой подзадачи",
                "учеба", Status.IN_PROGRESS, 5, 3));
    System.out.println("Подзадача " + subTask2);


    SubTask subTask3 = taskManager.addNewSubtask( new SubTask("Создание нового",
                "сдать ТЗ", Status.IN_PROGRESS, 7, 4));
    System.out.println("Подзадача " + subTask3);


   System.out.println(taskManager.getAllTasks());
   System.out.println(taskManager.getSubtask(7));
   System.out.println(taskManager.getAllEpics());


   task2.setName("Задача.");
   task2.setStatus(Status.IN_PROGRESS);
   taskManager.updateTask(task2);
   System.out.println(task2);

   System.out.println(taskManager.getAllEpicSubtasks(4));

   epic2.setStatus(Status.IN_PROGRESS);
   taskManager.updateEpic(epic2);
   System.out.println(epic2);

   taskManager.removeAllTask();
   System.out.println(taskManager.getAllTasks());

    }
}