package service;

import exception.ValidationException;
import model.*;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, SubTask> subTasks;
    public HashMap<Integer, Epic> epics;
    protected int counter = 0;
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private int generateId() {
        return ++counter;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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
        for (Task task : tasks.values()) {
            prioritizedTasks.remove(task);
        }
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
        }
        for (SubTask subTask : subTasks.values()) {
            prioritizedTasks.remove(subTask);
        }
        subTasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            prioritizedTasks.remove(epic);
        }
        for (SubTask subTask : subTasks.values()) {
            prioritizedTasks.remove(subTask);
        }
        subTasks.clear();
        epics.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public SubTask getSubtask(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Task addNewTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        if (task.getStartTime() != null) {
            checkTaskTime(task);
            prioritizedTasks.add(task);
        }
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(generateId());
        }
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask addNewSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            checkTaskTime(subTask);
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
            prioritizedTasks.add(subTask);
        }
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        final Task saved = tasks.get(task.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            checkTaskTime(task);
            tasks.put(task.getId(), task);
            prioritizedTasks.remove(saved);
            prioritizedTasks.add(task);
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
            epic.calculateAllFields();
        }
    }

    @Override
    public void updateSubtask(SubTask subTask) {
        final SubTask saved = subTasks.get(subTask.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            if (subTask.getEpicId() != saved.getEpicId()) {
                System.out.println("Ошибка");
            } else {
                checkTaskTime(subTask);
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getId());
                epic.calculateAllFields();
                prioritizedTasks.remove(saved);
                prioritizedTasks.add(subTask);
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            prioritizedTasks.remove(tasks.get(id));
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
            prioritizedTasks.remove(subTask);
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
        prioritizedTasks.remove(subTask);
        subTasks.remove(id);
    }

    @Override
    public List<SubTask> getAllEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }
        return epic.getSubTasks();
    }

    private boolean checkTasksOverlapTime(Task task, Task existTask) {
        return !(task.getStartTime().isAfter(existTask.getEndTime()) || task.getEndTime().isBefore(existTask.getStartTime()));
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private void checkTaskTime(Task task) {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        prioritizedTasks.stream()
                .filter(existTask -> existTask.getId() != task.getId())
                .filter(existTask -> checkTasksOverlapTime(task, existTask))
                .findFirst()
                .ifPresent(existTask -> {
                    throw new ValidationException("Задача " + task.getName() +
                            " пересекается с задачей " + existTask.getName());
                });
    }
}


