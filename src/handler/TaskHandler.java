package handler;

import com.sun.net.httpserver.HttpExchange;
import exception.NotFoundException;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.util.Optional;

public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch (method) {
            case "GET":
                tasksGet(httpExchange);
                break;
            case "POST":
                tasksPost(httpExchange);
                break;
            case "DELETE":
                tasksDelete(httpExchange);
                break;
            default:
                sendNotFound(httpExchange, "Error");
        }
    }

    private void tasksGet(HttpExchange httpExchange) throws IOException {
        String[] splitStrings = httpExchange.getRequestURI().getPath().split("/");
        String response;
        Optional<Integer> postId = getIdFromPath(httpExchange);

        if (splitStrings.length == 2) {
            response = gson.toJson(taskManager.getAllTasks());
            try {
                sendText(httpExchange, response);
            } catch (Exception e) {
                sendInternalServerError(httpExchange, e.getMessage());
            }
        } else if (postId.isPresent()) {
            try {
                Task task = taskManager.getTask(postId.get());
                response = gson.toJson(task);
                sendText(httpExchange, response);
            } catch (Exception e) {
                sendNotFound(httpExchange, e.getMessage());
            }
        } else {
            sendNotFound(httpExchange, "Не найдено");
        }
    }

    private void tasksPost(HttpExchange httpExchange) throws IOException {
        String[] splitStrings = httpExchange.getRequestURI().getPath().split("/");
        String requestBody = readText(httpExchange);
        try {

            Task newTask = gson.fromJson(requestBody, Task.class);
            Optional<Integer> postId = getIdFromPath(httpExchange);

            if (splitStrings.length == 2) {
                try {
                    taskManager.addNewTask(newTask);
                    sendSuccess(httpExchange, gson.toJson(taskManager.getAllTasks()));
                } catch (RuntimeException e) {
                    sendHasInteractions(httpExchange, e.getMessage());
                } catch (Exception e) {
                    sendInternalServerError(httpExchange, e.getMessage());
                }
            } else if (newTask.getId() != 0 && postId.isPresent()) { //update Task
                try {
                    taskManager.updateTask(newTask);
                    sendSuccess(httpExchange, gson.toJson(taskManager.getTask(postId.get())));
                } catch (NotFoundException e) {
                    sendNotFound(httpExchange, e.getMessage());
                } catch (RuntimeException e) {
                    sendHasInteractions(httpExchange, e.getMessage());
                } catch (Exception e) {
                    sendInternalServerError(httpExchange, e.getMessage());
                }
            } else {
                sendNotFound(httpExchange, "Not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendInternalServerError(httpExchange, e.getMessage());
        }
    }

    private void tasksDelete(HttpExchange httpExchange) throws IOException {
        Optional<Integer> postId = getIdFromPath(httpExchange);

        if (postId.isPresent()) {
            try {
                taskManager.deleteTask(postId.get());
                sendText(httpExchange, gson.toJson(taskManager.getTask(postId.get())));
            } catch (NotFoundException e) {
                sendNotFound(httpExchange, e.getMessage());
            } catch (Exception e) {
                sendInternalServerError(httpExchange, e.getMessage());
            }
        } else {
            sendNotFound(httpExchange, "Not found");
        }
    }
}



