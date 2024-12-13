package TaskManager;

import Typeoftasks.Epic;
import Typeoftasks.Subtask;
import Typeoftasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    // Добавление Задачи/Task
    void addTask(Task task);

    // Добавление Эпика/Epic
    void addEpic(Epic epic);

    // Добавление Позадачи/Subtask
    void addSubtask(Subtask subtask);

    // Обновление Задачи/Task
    void updateTask(Task task, int idTask, String newStatus);

    // Обновление Эпика/Epic
    void updateEpic(int idEpic);

    // Обновление подзадачи/Subtask
    void updateSubtask(Subtask subtask, int idSubtask, String newStatus);

    // Печать списка всех задач
    ArrayList<Task> getAllTasks();

    // Печать списка всех эпиков
    ArrayList<Epic> getAllEpics();

    // Печать списка всех подзадач
    ArrayList<Subtask> getAllSubtasks();

    // Удаление всех задач +
    void clearAllTasks();

    // Удаление всех подзадач/subtask +
    void clearAllSubtasks();

    // Удаление всех эпиков/epic +
    void clearAllEpics();

    // Поиск задачи/Task по ID
    Task searchByIdTask(int idTask);

    // Поиск подзадачи/Subtask по ID
    Subtask searchByIdSubtask(int idSubtask);

    // Поиск эпика по ID
    Epic searchByIdEpic(int idEpic);

    // Удаление задачи по ID +
    void removeByIdTask(int idTask);

    // Удаление подзадачи по ID +
    void removeByIdSubtask(int idSubtask);

    // Удаление эпика по ID +
    void removeByIdEpic(int idEpic);

    // Печать списка всех подзадач определённого эпика по ID эпика +
    void printSubtasksByEpics(int numberEpic);

    // Просмотр истории задач
    List<Task> getHistory();
}
