package service;

import exception.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        try {
            FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

            String fileContent = Files.readString(Path.of(file.getAbsolutePath()));

            if (!fileContent.isBlank()) {
                fileBackedTaskManager.initFromString(fileContent);
            }

            return fileBackedTaskManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла: " + file.getAbsolutePath(), e);
        }

    }

    private void save() {
        final BufferedWriter writer;
        int countId = 1;

        try {
            writer = new BufferedWriter(new FileWriter(file));
            String header = "Let's go!";
            for (int i : tasks.keySet()) {
                if (countId < i) {
                    countId = i;
                }
            }
            for (int i : epics.keySet()) {
                if (countId < i) {
                    countId = i;
                }
            }
            for (SubTask subTask : subTasks.values()) {
                Epic epic = epics.get(subTask.getEpicId());
                int id = subTask.getId();
                if (countId < id) {
                    countId = id;
                }
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }

            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                var s = toString(entry.getValue());
                writer.write(s);
                writer.newLine();
            }

            for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла: " + file, e);
        }
    }

    public String toString(Task task) {
        String result;

        result = task.getId() + ","
                + task.getClass().getSimpleName().toUpperCase() + ","
                + task.getName() + ","
                + task.getStatus() + ","
                + task.getDescription() + ",";

        if (task instanceof SubTask) {
            return result + task.getEpicId();
        }

        return result;
    }

    private void initFromString(String value) {

        final String[] tasks = value.split(System.lineSeparator());

        for (String taskString : tasks) {

            final String[] columns = taskString.split(",");
            int taskId = Integer.parseInt(columns[1]);
            TaskType type = TaskType.valueOf(columns[2]);
            String name = columns[3];
            Status status = Status.valueOf(columns[4]);
            String description = columns[5];
            Integer epicId = null; // columns
            if (type == TaskType.SUBTASK) {
                epicId = Integer.parseInt(columns[6]);
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
        }
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