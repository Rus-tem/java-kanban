package httphandler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import typeoftasks.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Началась обработка /history запроса от клиента.");
        if (exchange.getRequestMethod().equals("GET")) {
            List<Task> getHistory = taskManager.getHistory();
            String response = gson.toJson(getHistory.toString());
            sendText(exchange, response, 200); // код 200
        } else {
            String response = "Такой эндпоинт отсутствует";
            sendNotFound(exchange, response); // код 200
        }
    }
}
