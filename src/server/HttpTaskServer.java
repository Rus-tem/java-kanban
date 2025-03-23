package server;

import com.sun.net.httpserver.HttpServer;
import httphandler.*;
import taskmanager.Manager;
import taskmanager.TaskManager;
import typeoftasks.Epic;
import typeoftasks.Subtask;
import typeoftasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.*;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private static HttpServer server;
    private TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        server = HttpServer.create(
                new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler(taskManager));
        server.createContext("/subtasks", new SubtaskHandler(taskManager));
        server.createContext("/epics", new EpicHandler(taskManager));
        server.createContext("/history", new HistoryHandler(taskManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));

    }

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        taskManager.addTask(new Task("TASK", "T1", LocalDateTime.now(), Duration.ofMinutes(40)));
        taskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.of(2000, 2, 2, 10, 0), Duration.ofMinutes(1)));
        taskManager.addSubtask(new Subtask("SUBTASK", "Sub1", LocalDateTime.of(2000, 1, 1, 10, 0), Duration.ofMinutes(20), 2));
        startServer();
        System.out.println("Server get started");
        //stopServer(10);
    }

    public static void startServer() {
        System.out.println("Запускаем HTTP сервер на порту " + PORT);
        server.start();
    }

    public static void stopServer(int time) {
        System.out.println("Останавливаем HTTP сервер ");
        server.stop(time);
    }
}
