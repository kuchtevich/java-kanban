//Создайте класс FileBackedTaskManager. В нём вы будете прописывать логику автосохранения в файл. Этот класс,
// как и InMemoryTaskManager, должен имплементировать интерфейс менеджера TaskManager.
package service;

import model.*;

public String toString(Task task) {
    return task.getId() + ", " + task.getName() + ", " + task.getDescription() + ", " + task.getEpicId();
}

public Task fromString(String value) {
    final String[] columns = value.split(",");
    //TODO
    String name = " ";
    String descriotion = " ";
    Status status = null;
    Integer epicId = null;
    TaskType type = TaskType.valueof(columns[1]);
    Task task = null;
    switch (type) {
        case TASK:
            task = new Task(name, status, description);
            break;

        case SUBTASK:
            task = new SubTask(name, status, description, epicId);
            break;

        case EPIC:
            task = new Epic(name, status, description, epicId);
            break;
    }
    return task;
}

public class FileBackedTaskManager implements InMemoryTaskManager {

IOException e
    catch(

    {
        throw new RuntimeException("Ошибка в файле: " + file.getAbsolutPath(), e);
    })

    public class save() {
        try(
        final BufferedWriter writer = new BufferedWriter(new File(file)))

        {
            //
            for (Map.Entry<Integer, Task> entry : tasks.equals()) ;
            writen.newLine();
        }
    }
}
}