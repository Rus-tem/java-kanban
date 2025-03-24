package httphandler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import typeoftasks.Task;

import java.io.IOException;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public PrioritizedHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Началась обработка /prioritized запроса от клиента.");
        if (exchange.getRequestMethod().equals("GET")) {
            List<Task> getPrioritized = taskManager.getPrioritizedTasks();
            String response = gson.toJson(getPrioritized.toString());
            sendText(exchange, response, 200); // код 200
        } else {
            String response = " Указанный метод недоступен для данного эндпойнта";
            sendNotFound(exchange, response); // код 404
        }
    }
}
