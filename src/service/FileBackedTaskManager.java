package service;

import exception.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (var bufferedReader = new BufferedReader(new FileReader(file))) {
            int maxId = 0;
            Task task;
            String fileText;
            List<String> linesInFile = new ArrayList<>();
            while ((fileText = bufferedReader.readLine()) != null) {
                if (!fileText.isEmpty()) {
                    linesInFile.add(fileText);
                    for (int i = 1; i < linesInFile.size(); i++) {
                        task = initFromString(linesInFile.get(i));
                        if (task.getId() > maxId) {
                            maxId = task.getId();
                        }
                        switch (task.getType()) {
                            case TASK -> fileBackedTaskManager.tasks.put(task.getId(), task);
                            case EPIC -> fileBackedTaskManager.epics.put(task.getId(), (Epic) task);
                            case SUBTASK -> {
                                fileBackedTaskManager.subTasks.put(task.getId(), (SubTask) task);
                                Epic epic = fileBackedTaskManager.epics.get(task.getEpicId());
                                epic.addSubTask((SubTask) task);
                            }
                        }
                    }

                }
            }
            fileBackedTaskManager.counter = maxId;
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при чтении файла");
        }
        return fileBackedTaskManager;
    }

    protected static Task initFromString(String value) {
        String[] columns = value.split(",");
        int taskId = Integer.parseInt(columns[0]);
        String name = columns[1];
        String description = columns[2];
        Status status = Status.valueOf(columns[3]);
        TaskType type = TaskType.valueOf(columns[5]);
        LocalDateTime startTime = LocalDateTime.parse(columns[6]);
        Duration duration = Duration.ofMinutes(Long.parseLong(columns[7]));

        switch (type) {
            case TASK:
                return new Task(taskId, name, description, status, startTime, duration);
            case SUBTASK:
                return new SubTask(taskId, name, description, status, startTime, duration, Integer.parseInt(columns[4]));
            case EPIC:
                return new Epic(taskId, name, description);
        }
        return null;
    }

    private void save() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(getAllTasks());
        allTasks.addAll(getAllEpics());
        allTasks.addAll(getAllSubTasks());
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
            fileWriter.write("id,name,description,status,epic,type" + "\n");
            for (Task task : allTasks) {
                fileWriter.write(toString(task) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Сохранение файла прошло не удачно");
        }
    }

    private String toString(Task task) {
        String result;

        result =
                task.getId() + ","
                        + task.getName() + ","
                        + task.getDescription() + ","
                        + task.getStatus() + ","
                        + task.getEpicId() + ","
                        + task.getType() + ","
                        + task.getStartTime() + ","
                        + task.getDuration().toMinutes() + ",";

        return result;
    }


    @Override
    public Task addNewTask(Task task) {
        Task result = super.addNewTask(task);
        save();
        return result;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Epic result = super.addNewEpic(epic);
        save();
        return result;
    }

    @Override
    public SubTask addNewSubtask(SubTask subTask) {
        SubTask result = super.addNewSubtask(subTask);
        save();
        return result;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(SubTask subTask) {
        super.updateSubtask(subTask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }
}