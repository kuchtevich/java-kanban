package service;
import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, SubTask> subTasks;
    public HashMap<Integer, Epic> epics;
    private int counter = 0;

    private int generateId() {
        return ++counter;
    }
    public TaskManager() {
        this.tasks= new HashMap<>();
        this.epics= new HashMap<>();
        this.subTasks= new HashMap<>();
    }


    public List<Task> getAllTasks() {
    return new ArrayList<>(tasks.values());
    }
    public List<SubTask> getAllSubTasks() {
    return new ArrayList<>(subTasks.values());
    }

    public List<Epic> getAllEpics() {
    return new ArrayList<>(epics.values());
    }

    public List<SubTask> getAllEpicSubtasks(int epicId) {
   Epic epic = epics.get(epicId);
        return epic.getSubTasks();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public SubTask getSubtask(int id) {
        return subTasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Task addNewTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addNewEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask addNewSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
            calcEpicStatus(epic);
        }
            return subTask;
    }

    public void updateTask(Task task) {
        Task saved = tasks.get(task.getId());
        saved.setName(task.getName());
        saved.setDescription(task.getDescription());
        saved.setStatus(task.getStatus());
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        saved.setId(epic.getId());
        epic.setStatus(epic.getStatus());
        }

    public void updateSubtask(SubTask subTask) {
            SubTask saved = subTasks.get(subTask.getId());
            saved.setName(subTask.getName());
            saved.setDescription(subTask.getDescription());
            saved.setId(subTask.getId());
            saved.setStatus(subTask.getStatus());
    }
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Ошибка.");
        }

    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public void  deleteSubtask(int id) {
        subTasks.remove(id);
     }

    public void removeAllTask() {
        tasks.clear();
        for (Task task : tasks.values()) {
            tasks.clear();
        }
    }

    public void removeAllSubtasks() {
        subTasks.clear();
        for(Epic epic : epics.values()) {
            List<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask : subTasks) {
                subTasks.remove(subTask.getId());
            }
           epic.getSubTasks().clear();
            calcEpicStatus(epic);
        }
        }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            List<SubTask> subTasks = epic.getSubTasks();
            for (SubTask subTask : subTasks) {
                subTasks.remove(subTask.getId());
            }
            epic.getSubTasks().clear();
        }
    }
    private void calcEpicStatus(Epic epic) {
        List<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus().equals(Status.DONE)) {
                Status status = Status.DONE;
                epic.setStatus(epic.getStatus());
            } else if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
                Status status = Status.IN_PROGRESS;
                epic.setStatus(epic.getStatus());
            } else if (subTask.getStatus().equals(Status.NEW)) {
                Status status = Status.NEW;
                epic.setStatus(epic.getStatus());
            }
        }
    }

    }


