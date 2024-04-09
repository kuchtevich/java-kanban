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

    Epic epic2 =  taskManager.addNewEpic(new Epic("Создание нового эпика", "сдать ТЗ", 3));
    System.out.println("Новый эпик создан: " + epic2);

    SubTask subTask = taskManager.addNewSubtask(new SubTask("Создание новой подзадачи",
            "учеба", Status.IN_PROGRESS, 4));
    System.out.println("Подзадача выполнена: " + subTask);

   System.out.println(taskManager.getAllTasks());
   System.out.println(taskManager.getAllSubTasks());
   System.out.println(taskManager.getAllEpics());

   System.out.println(taskManager.getAllEpicSubtasks(2));


    }
}