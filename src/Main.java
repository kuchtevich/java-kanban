import model.*;
import service.*;

import java.time.Duration;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new InMemoryTaskManager();

        Task task = taskManager.addNewTask(new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW, LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(1)));

        System.out.println("Создана новая задача: " + task);


        Task task2 = taskManager.addNewTask(new Task("Cоздание новой задачи",
                "сменить город", Status.IN_PROGRESS, LocalDateTime.now(), Duration.ofMinutes(1)));
        System.out.println("Создана новая задача: " + task2);


        Epic epic = taskManager.addNewEpic(new Epic("Создание нового эпика", "учеба"));
        System.out.println("Новый эпик создан: " + epic);

        Epic epic2 = taskManager.addNewEpic(new Epic("Создание нового", "сдать ТЗ"));
        System.out.println("Новый эпик создан: " + epic2);

        SubTask subTask = taskManager.addNewSubtask(new SubTask("Создание новой подзадачи",
                "учеба", Status.IN_PROGRESS, LocalDateTime.of(2023, 12, 12, 12, 12), Duration.ofMinutes(1), 3));
        System.out.println("Подзадача " + subTask);

        SubTask subTask2 = taskManager.addNewSubtask(new SubTask("Создание новой подзадачи",
                "учеба", Status.IN_PROGRESS, LocalDateTime.of(2025, 1, 12, 12, 12), Duration.ofMinutes(1), 3));
        System.out.println("Подзадача " + subTask2);
//
//
        SubTask subTask3 = taskManager.addNewSubtask(new SubTask("Создание нового",
                "сдать ТЗ", Status.IN_PROGRESS, LocalDateTime.of(2025, 12, 12, 12, 12), Duration.ofMinutes(1), 4));
        System.out.println("Подзадача " + subTask3);

        SubTask subTask4 = taskManager.addNewSubtask(new SubTask("Создание нового",
                "ТЗ спринт 5", Status.IN_PROGRESS, LocalDateTime.of(2026, 12, 12, 12, 12), Duration.ofMinutes(1), 4));
        System.out.println("Подзадача " + subTask3);

        System.out.println("История добавления информации: " + taskManager.getHistory());

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

//        taskManager.removeAllTask();
//        System.out.println(taskManager.getAllTasks());
//
//        taskManager.removeAllEpics();
//        System.out.println(taskManager.getAllEpics());
//
//        taskManager.deleteSubtask(7);
//        System.out.println(taskManager.getEpic(4));
//
//
//        taskManager.deleteEpic(4);
//        System.out.println("Удалено. " + taskManager.getEpic(4));
//
//        System.out.println("История добавления информации: " + taskManager.getHistory());
    }
}