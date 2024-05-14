import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

    TaskManager taskManager = Manager.getDefault();
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

    SubTask subTask4 = taskManager.addNewSubtask( new SubTask("Создание нового",
                "ТЗ спринт 5", Status.IN_PROGRESS, 7, 4));
        System.out.println("Подзадача " + subTask3);

   System.out.println(taskManager.getAllTasks());
   System.out.println(taskManager.getSubtask(7));
   System.out.println(taskManager.getAllEpics());


   task2.setName("Задача.");
   task2.setStatus(Status.IN_PROGRESS);
   taskManager.updateTask(task2);
   System.out.println(taskManager.getEpic(3));

   System.out.println(taskManager.getAllEpicSubtasks(4));

   subTask3.setStatus(Status.DONE);
   taskManager.updateEpic(epic2);
   System.out.println(epic2);

        System.out.println("История добавления информации: " + taskManager.getHistory());
/*
   taskManager.removeAllTask();
   System.out.println(taskManager.getAllTasks());

   taskManager.removeAllEpics();
   System.out.println(taskManager.getAllEpics());

  taskManager.deleteSubtask(7);
  System.out.println(taskManager.getEpic(4));


  taskManager.deleteEpic(4);
  System.out.println("Удалено. " + taskManager.getEpic(4));
  */
    }
}