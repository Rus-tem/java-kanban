
import com.google.gson.Gson;
import httphandler.BaseHttpHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import server.HttpTaskServer;
import taskmanager.InMemoryTaskManager;
import taskmanager.TaskManager;
import typeoftasks.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerTasksTest {

    // создаём экземпляр InMemoryTaskManager
    TaskManager taskManager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = new Gson();// HttpTaskServer.getGson();
    BaseHttpHandler baseHttpHandler = new BaseHttpHandler();

    public HttpTaskManagerTasksTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        taskManager.clearAllTasks();
        taskManager.clearAllSubtasks();
        taskManager.clearAllTasks();
        HttpTaskServer.startServer();
    }

    @AfterEach
    public void shutDown() {
        HttpTaskServer.stopServer(2);
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        taskManager.addTask(new Task("TASK", "t1desc", LocalDateTime.of(2000, 1, 1, 14, 10), Duration.ofMinutes(1)));
        Task task = taskManager.searchByIdTask(1);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        assertEquals(200, response.statusCode());
        List<Task> tasksFromManager = taskManager.getAllTasks();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("TASK", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        taskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.now(), ofMinutes(10)));
        taskManager.addSubtask(new Subtask("SUBTASK", "S1", LocalDateTime.of(2025, 1, 1, 10, 0), Duration.ofDays(1), 1));
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        List<Epic> tasksFromManager = taskManager.getAllEpics();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("EPIC", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddSubtasks() throws IOException, InterruptedException {

        taskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.now(), ofMinutes(10)));
        taskManager.addSubtask(new Subtask("SUBTASK", "S1", LocalDateTime.of(2025, 1, 1, 10, 0), Duration.ofDays(1), 1));
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        List<Subtask> tasksFromManager = taskManager.getAllSubtasks();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("SUBTASK", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
    }
}


