package httphandler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import typeoftasks.Epic;
import typeoftasks.Subtask;
import typeoftasks.TypeOfTasks;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Началась обработка /epics запроса от клиента.");
        String response = "";
        String path = exchange.getRequestURI().getPath();
        String[] id = path.split("/");
        int idTask = 0;
        System.out.println(id.length);
        switch (exchange.getRequestMethod()) {
            case "GET": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Epic epic = taskManager.searchByIdEpic(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    Epic epic = taskManager.searchByIdEpic(idTask);
                    response = gson.toJson(epic.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                } else if (id.length == 2) {
                    List<Epic> getAllEpics = taskManager.getAllEpics();
                    response = gson.toJson(getAllEpics.toString());
                    sendText(exchange, response, 200); // код 200
                    break;
                } else if (id.length == 4) {
                    idTask = Integer.parseInt(path.split("/")[2]);
                    String subtasks = path.split("/")[3];
                    if (subtasks.equals("subtasks")) {
                        try {
                            Epic epic = taskManager.searchByIdEpic(idTask);
                        } catch (Exception exception) {
                            response = gson.toJson("Такой задачи нет");
                            sendNotFound(exchange, response); // код ош 404
                        }
                        List<Subtask> getSubtasks = taskManager.printSubtasksByEpics(idTask);
                        response = gson.toJson(getSubtasks.toString());
                        sendText(exchange, response, 200); // код 200
                    } else {
                        response = gson.toJson("Ошибка запроса");
                        sendNotFound(exchange, response); // код 404
                    }
                }   //Создание Эпика: description, TimeStart (02.02.2001 14:10), Duration (PT10M)
            }
            case "POST": {
                String json = readText(exchange);
                StringBuilder stringBuilder = new StringBuilder(json);
                int first = stringBuilder.indexOf("");
                int last = stringBuilder.indexOf(",");
                String description = "";
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
                    taskManager.addEpic(new Epic(TypeOfTasks.EPIC.toString(), description, LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER), Duration.parse(duration)));
                    if (!taskManager.checkTasksToTime()) {
                        response = gson.toJson("Данная задача пересекается по времени с другой");
                        sendHasInteractions(exchange, response);
                        break;
                    } else {
                        response = gson.toJson("Задача успешно создана");
                        sendText(exchange, response, 201);
                        break;
                    } //Обновление задачи: IdEpic
                } else if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Epic epic = taskManager.searchByIdEpic(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    taskManager.updateEpic(idTask);
                    response = gson.toJson("Задача успешно обновлена");
                    sendText(exchange, response, 201);
                    break;
                }
            }
            case "DELETE": {
                if (id.length == 3) {
                    try {
                        idTask = Integer.parseInt(path.split("/")[2]);
                        Epic epic = taskManager.searchByIdEpic(idTask);
                    } catch (Exception exception) {
                        response = gson.toJson("Такой задачи нет");
                        sendNotFound(exchange, response); // код ош 404
                    }
                    taskManager.removeByIdEpic(idTask);
                    response = gson.toJson("Задача успешно удалена");
                    sendText(exchange, response, 200); // код 200
                    break;
                } else {
                    response = gson.toJson("Нужно выбрать задачу для удаления");
                    sendNotFound(exchange, response); // 404
                }
            }
            default:
                response = gson.toJson("Такой эндпоинт отсутствует");
                sendNotFound(exchange, response);
        }
    }
}


