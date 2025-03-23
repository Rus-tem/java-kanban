package httphandler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import typeoftasks.Subtask;
import typeoftasks.TypeOfTasks;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public SubtaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Началась обработка /subtask запроса от клиента.");
        String response = "";
        String path = exchange.getRequestURI().getPath();
        String[] id = path.split("/");
        int idTask = 0;
        switch (exchange.getRequestMethod()) {
            case "GET": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Subtask subtask = taskManager.searchByIdSubtask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    Subtask subtask = taskManager.searchByIdSubtask(idTask);
                    response = gson.toJson(subtask.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                } else {
                    List<Subtask> getAllSubtasks = taskManager.getAllSubtasks();
                    response = gson.toJson(getAllSubtasks.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                }
            }
            case "POST": { // Cоздание подзадачи: description, IdEpic, TimeStart (02.02.2001 14:10), Duration (PT10M)
                String json = readText(exchange);
                StringBuilder stringBuilder = new StringBuilder(json);
                int first = stringBuilder.indexOf("");
                int last = stringBuilder.indexOf(",");
                String description = "";
                String status = "";
                String localDateTime = "";
                String duration = "";
                int idEpic = 0;
                int counter = 0;
                if (id.length == 2) {
                    while (counter < 4) {
                        if (counter == 0) {
                            description = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 1) {
                            idEpic = Integer.parseInt(stringBuilder.substring(first, last));
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
                    taskManager.addSubtask(new Subtask(TypeOfTasks.SUBTASK.toString(), description, LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER), Duration.parse(duration), idEpic));
                    if (!taskManager.checkTasksToTime()) {
                        response = gson.toJson("Данная задача пересекается по времени с другой");
                        sendHasInteractions(exchange, response);
                        break;
                    } else {
                        response = gson.toJson("Задача успешно создана");
                        sendText(exchange, response, 201);
                        break;
                    }
                } else if (id.length == 3) { // Обновление подзадачи: desk, STATUS(NEW, IN_PROGRESS, DONE), IdEpic ,TimeStart (02.02.2001 14:10), Duration (PT10M)
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Subtask subtask = taskManager.searchByIdSubtask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    while (counter < 5) {
                        if (counter == 0) {
                            description = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 1) {
                            status = stringBuilder.substring(first, last);
                            counter += 1;
                        } else if (counter == 2) {
                            idEpic = Integer.parseInt(stringBuilder.substring(first, last));
                            counter += 1;
                        } else if (counter == 3) {
                            localDateTime = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        } else if (counter == 4) {
                            last = stringBuilder.length();
                            duration = stringBuilder.substring(first, last);
                            counter = counter + 1;
                        }
                        stringBuilder.delete(0, last + 2);
                        first = stringBuilder.indexOf("");
                        last = stringBuilder.indexOf(",");
                    }
                    taskManager.updateSubtask(new Subtask("SUBTASK", description, LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER), Duration.parse(duration), idEpic), idTask, status);
                    response = gson.toJson("Задача успешно обновлена");
                    sendText(exchange, response, 201);
                    break;
                }
            }
            case "DELETE": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Subtask subtask = taskManager.searchByIdSubtask(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    taskManager.removeByIdSubtask(idTask);
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


