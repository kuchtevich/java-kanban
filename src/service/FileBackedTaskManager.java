package service;

import exception.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private String filePath = "file.csv";

    public void init() {
        try {
            // должен быть цикл по всем строкам в файле
            // while (true) {

            String fileContent = Files.readString(Path.of(filePath));

            Task task = fromString(fileContent);

            // }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла: " + filePath, e);
        }

    }

    public void save() {
        final BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(filePath));

            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                var s = toString(entry.getValue());
                writer.write(s);
                writer.newLine();
            }

            for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла: " + filePath, e);
        }
    }

    public String toString(Task task) {
        String result;

        result = task.getId() + ", " + task.getName() + ", " + task.getDescription() + ",";

        if (task instanceof SubTask subTask) {
            return result + subTask.getEpicId();
        }

        return result;
    }

    public Task fromString(String value) {
        // id,type,name,status,description,epic
        final String[] columns = value.split(",");
        //TODO
        int taskId = Integer.parseInt(columns[0]);
        TaskType type = TaskType.valueOf(columns[1]);
        String name = columns[2];
        Status status = null;
        String description = columns[4];
        Integer epicId = null; // columns
        if (type == TaskType.SUBTASK) {
            epicId = Integer.parseInt(columns[5]);
        }

        Task task;
        switch (type) {
            case TASK:
                task = new Task(name, description, status, taskId);
                super.addNewTask(task);
                break;
            case SUBTASK:
                task = new SubTask(name, description, status, epicId);
                super.addNewSubtask((SubTask) task);
                break;
            case EPIC:
                task = new Epic(name, description, taskId);
                super.addNewEpic((Epic) task);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return task;
    }


    // override InMemoryTaskManager
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