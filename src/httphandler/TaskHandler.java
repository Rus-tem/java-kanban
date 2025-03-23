package httphandler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import typeoftasks.Task;
import typeoftasks.TypeOfTasks;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента.");
        String response = "";
        String path = exchange.getRequestURI().getPath();
        String[] id = path.split("/");
        int idTask = 0;
        switch (exchange.getRequestMethod()) {
            case "GET": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Task task = taskManager.searchByIdTask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    Task task = taskManager.searchByIdTask(idTask);
                    response = gson.toJson(task.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                } else if (id.length == 2) {
                    List<Task> getAllTasks = taskManager.getAllTasks();
                    response = gson.toJson(getAllTasks.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                }
            }
            // Создание задачи: description, TimeStart (02.02.2001 14:10), Duration (PT10M)
            case "POST": {
                String json = readText(exchange);
                StringBuilder stringBuilder = new StringBuilder(json);
                int first = stringBuilder.indexOf("");
                int last = stringBuilder.indexOf(",");
                String description = "";
                String status = "";
                String localDateTime = "";
                String duration = "";
                int counter = 0;
                if (id.length == 2) {
                    while (counter < 3) {
                        if (counter == 0) {
                            description = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 1) {
                            localDateTime = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        } else if (counter == 2) {
                            last = stringBuilder.length();
                            duration = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        }
                        stringBuilder.delete(0, last + 2);
                        first = stringBuilder.indexOf("");
                        last = stringBuilder.indexOf(",");
                    }
                    taskManager.addTask(new Task(TypeOfTasks.TASK.toString(), description, LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER), Duration.parse(duration)));
                    if (!taskManager.checkTasksToTime()) {
                        response = gson.toJson("Данная задача пересекается по времени с другой");
                        sendHasInteractions(exchange, response);
                        break;
                    } else {
                        response = gson.toJson("Задача успешно создана");
                        sendText(exchange, response, 201);
                        break;
                    }
                    // Обновления задачи: description, STATUS(NEW, IN_PROGRESS, DONE), TimeStart (02.02.2001 14:10), Duration (PT10M)
                } else if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Task task = taskManager.searchByIdTask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    while (counter < 4) {
                        if (counter == 0) {
                            description = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 1) {
                            status = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 2) {
                            localDateTime = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        } else if (counter == 3) {
                            last = stringBuilder.length();
                            duration = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        }
                        stringBuilder.delete(0, last + 2);
                        first = stringBuilder.indexOf("");
                        last = stringBuilder.indexOf(",");
                    }
                    taskManager.updateTask(new Task(TypeOfTasks.TASK.toString(), description, LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER), Duration.parse(duration)), idTask, status);
                    response = gson.toJson("Задача успешно обновлена");
                    sendText(exchange, response, 201);
                    break;
                }
            }
            case "DELETE": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Task task = taskManager.searchByIdTask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    taskManager.removeByIdTask(idTask);
                    response = gson.toJson("Задача успешно удалена");
                    sendText(exchange, response, 200); // код 200
                    break;
                } else {
                    response = gson.toJson("Нужно выбрать задачу для удаления");
                    sendNotFound(exchange, response); // 404
                }
            }
            default:
                response = gson.toJson("Указанный метод недоступен для данного эндпойнта");
                sendNotFound(exchange, response);
        }
    }
}
