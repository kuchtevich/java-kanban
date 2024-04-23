package service;

import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class InMemoryTaskManager implements TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, SubTask> subTasks;
    public HashMap<Integer, Epic> epics;
    private int counter = 0;

    private int generateId() {
        return ++counter;
    }


    private HistoryManager historyManager = Manager.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
        }
    }

    @Override
    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        return task;
    }

    @Override
    public SubTask getSubtask(int id) {
        SubTask subTask = subTasks.get(id);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        return epic;
    }

    @Override
    public Task addNewTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        historyManager.add(task); //добавление в историю
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        historyManager.add(epic); //добавление в историю
        return epic;
    }

    @Override
    public SubTask addNewSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
            historyManager.add(subTask); //добавление в историю
        }
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        Task saved = tasks.get(task.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            saved.setName(epic.getName());
            saved.setDescription(epic.getDescription());
        }
    }

    @Override
    public void updateSubtask(SubTask subTask) {
        SubTask saved = subTasks.get(subTask.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            if (subTask.getEpicId() != saved.getEpicId()) {
                System.out.println("Ошибка!");
            } else {
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getId());
                epic.calcStatus();
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Ошибка.");
        }

    }

    @Override
    public void deleteEpic(int id) {
        if (!epics.containsKey(id)) {
            return;
        }

        Epic epic = epics.get(id);

        for (SubTask subTask : epic.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }

        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (!subTasks.containsKey(id)) {
            return;
        }

        SubTask subTask = subTasks.get(id);

        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);

        epic.removeSubTask(subTask);
        subTasks.remove(id);

    }

    @Override
    public List<SubTask> getAllEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubTasks();
    }


}


