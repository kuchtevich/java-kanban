import com.sun.net.httpserver.HttpServer;
import handler.EpicHandler;
import handler.SubTaskHandler;
import handler.TaskHandler;
import service.Manager;
import service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    static final int PORT = 8080;
    private final TaskManager taskManager;
    private HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpServer1 = new HttpTaskServer(Manager.getDefault());
        httpServer1.start();
        //httpServer1.stop();
    }

    public void start() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.createContext("/subtasks", new SubTaskHandler(taskManager));
        httpServer.createContext("/epics", new EpicHandler(taskManager));
        httpServer.createContext("/prioritized", new TaskHandler(taskManager));

        httpServer.start();
        System.out.println("Server start on the PORT: " + PORT);
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Server stop on the PORT " + PORT);
    }
}
