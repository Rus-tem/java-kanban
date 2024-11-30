package TaskManager;

import Typeoftasks.*;
import Status.Status;

import java.util.HashMap;
import java.util.HashSet;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;

    // Добавление Задачи/Task
    public void addTask(Task task) {
        task.setId(nextId++);
        task.setStatus(String.valueOf(Status.NEW));
        tasks.put(task.getId(), task);
    }

    // Добавление Эпика/Epic
    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(String.valueOf(Status.NEW));
    }

    // Добавление Позадачи/Subtask
    public void addSubtask(Subtask subtask) {
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        subtask.setStatus(String.valueOf(Status.NEW));
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().add(subtask.getEpicId());
    }

    // Обновление Задачи/Task
    public void updateTask(Task task, int idTask, String newStatus) {
        if (tasks.containsKey(idTask)) {
            task.setId(idTask);
            tasks.put(idTask, task);
            task.setStatus(String.valueOf(newStatus));
        }
    }

    // Обновление Эпика/Epic
    public void updateEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        if (epic.getSubtasksIds().isEmpty()) {
            epic.setId(idEpic);
            epic.setStatus(String.valueOf(Status.NEW));
            epics.put(idEpic, epic);
            return;
        }
        changeStatusEpic(idEpic);
    }

    // Обновление подзадачи/Subtask
    public void updateSubtask(Subtask subtask, int idSubtask, String newStatus) {
        if (subtasks.containsKey(idSubtask)) {
            subtask.setId(idSubtask);
            subtasks.put(idSubtask, subtask);
            subtask.setStatus(String.valueOf(newStatus));
            changeStatusEpic(subtask.getEpicId());
        }
    }

    // Печать списка всех задач --
    public HashMap<Integer, Task> printAllTasks() {
        return tasks;
    }

    // Печать списка всех эпиков --
    public HashMap<Integer, Epic> printAllEpics() {
        return epics;
    }

    // Печать списка всех подзадач --
    public HashMap<Integer, Subtask> printAllSubtasks() {
        return subtasks;
    }

    // Удаление всех задач
    public void clearAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    // Удаление всех подзадач/subtask
    public void clearAllSubtasks() {
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
    }

    // Удаление всех эпиков/epic
    public void clearAllEpics() {
        epics.clear();
        System.out.println("Все эпики удалены");
    }

    // Поиск задачи/Task по ID
    public Task searchByIdTask(int searchNumber) {
        if (tasks.containsKey(searchNumber)) {
            return (tasks.get(searchNumber));
        }
        return null;
    }

    // Поиск подзадачи/Subtask по ID
    public Subtask searchByIdSubtask(int searchNumber) {
        if (subtasks.containsKey(searchNumber)) {
            return (subtasks.get(searchNumber));
        }
        return null;
    }

    // Поиск эпика по ID
    public Epic searchByIdEpic(int searchNumber) {
        if (epics.containsKey(searchNumber)) {
            return (epics.get(searchNumber));
        }
        return null;
    }

    // Удаление задачи по ID
    public void removeByIdTask(int removeNumber) {
        if (tasks.containsKey(removeNumber)) {
            tasks.remove(removeNumber);
            System.out.println("Задача с номером " + removeNumber + " удалена");
        }
    }

    // Удаление подзадачи по ID
    public void removeByIdSubtask(int removeNumber) {
        if (subtasks.containsKey(removeNumber)) {
            Subtask subtask = subtasks.get(removeNumber);
            subtasks.remove(removeNumber);
            System.out.println("Подзадача с номером " + removeNumber + " удалена");
            changeStatusEpic(subtask.getEpicId());
        }
    }

    // Удаление эпика по ID
    public void removeByIdEpic(int removeNumber) {
        if (epics.containsKey(removeNumber)) {
            epics.remove(removeNumber);
            System.out.println("Эпик с номером " + removeNumber + " удалена");
            HashSet<Integer> idsToRemove = new HashSet<Integer>();
            for (Integer idSubtasks : subtasks.keySet()) {
                Subtask subtask = subtasks.get(idSubtasks);
                if (removeNumber == subtask.getEpicId()) {
                    idsToRemove.add(subtask.getId());
                }
            }
            subtasks.keySet().removeAll(idsToRemove);
            System.out.println("Подзадачи эпика " + removeNumber + " удалены");
        }
    }

    // Печать списка всех подзадач определённого эпика по ID эпика
    public void printSubtasksByEpics(int numberEpic) {
        for (Integer idSubtasks : subtasks.keySet()) {
            Subtask subtask = subtasks.get(idSubtasks);
            if (subtask.getEpicId() == numberEpic) {
                System.out.println(subtask);
            }
        }
    }

    //Обновление статуса Эпика/Epic
    public void changeStatusEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        int sumDone = 0;
        int sumNew = 0;
        for (Integer id : subtasks.keySet()) {
            Subtask subtask = subtasks.get(id);
            switch (subtask.getStatus()) {
                case "IN_PROGRESS" -> {
                    epic.setStatus(String.valueOf(Status.IN_PROGRESS));
                    epic.setId(idEpic);
                    epics.put(idEpic, epic);
                    return;
                }
                case "NEW" -> sumNew += 1;
                case "DONE" -> sumDone += 1;
            }
        }
        if (sumDone == 0) {
            epic.setId(idEpic);
            epic.setStatus(String.valueOf(Status.NEW));
            epics.put(idEpic, epic);

        } else if (sumNew == 0) {
            epic.setId(idEpic);
            epic.setStatus(String.valueOf(Status.DONE));
            epics.put(idEpic, epic);

        } else {
            epic.setId(idEpic);
            epic.setStatus(String.valueOf(Status.IN_PROGRESS));
            epics.put(idEpic, epic);

        }
    }
}





