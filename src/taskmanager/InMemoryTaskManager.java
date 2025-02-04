package taskmanager;

import typeoftasks.*;
import status.Status;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;
    private final HistoryManager historyManager = Manager.getDefaultHistory();


    // Добавление Задачи/Task
    @Override
    public void addTask(Task task) {
        task.setId(nextId++);
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
    }

    // Добавление Эпика/Epic
    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }

    // Добавление Позадачи/Subtask
    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        subtask.setStatus(Status.NEW);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().add(subtask.getId());
    }

    // Обновление Задачи/Task
    @Override
    public void updateTask(Task task, int idTask, String newStatus) {
        if (tasks.containsKey(idTask)) {
            task.setId(idTask);
            tasks.put(idTask, task);
            task.setStatus(Status.valueOf(newStatus));
        }
    }

    // Обновление Эпика/Epic
    @Override
    public void updateEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        if (epic.getSubtasksIds().isEmpty()) {
            epic.setId(idEpic);
            epic.setStatus(Status.NEW);
            epics.put(idEpic, epic);
            return;
        }
        changeStatusEpic(idEpic);
    }

    // Обновление подзадачи/Subtask
    @Override
    public void updateSubtask(Subtask subtask, int idSubtask, String newStatus) {
        if (subtasks.containsKey(idSubtask)) {
            subtask.setId(idSubtask);
            subtasks.put(idSubtask, subtask);
            subtask.setStatus(Status.valueOf(newStatus));
            changeStatusEpic(subtask.getEpicId());
        }
    }

    // Печать списка всех задач
    @Override
    public List<Task> getAllTasks() {
        List<Task> listTasks = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            listTasks.add(tasks.get(id));
        }
        return listTasks;
    }

    // Печать списка всех эпиков
    @Override
    public List<Epic> getAllEpics() {
        List<Epic> listEpics = new ArrayList<>();
        for (Integer id : epics.keySet()) {
            listEpics.add(epics.get(id));
        }
        return listEpics;
    }

    // Печать списка всех подзадач
    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> listSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            listSubtasks.add(subtasks.get(id));
        }
        return listSubtasks;
    }

    // Удаление всех задач
    @Override
    public void clearAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    // Удаление всех подзадач/subtask
    @Override
    public void clearAllSubtasks() {
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
        for (Integer id : epics.keySet()) {
            Epic epic = epics.get(id);
            epic.setStatus(Status.NEW);
        }
    }

    // Удаление всех эпиков/epic
    @Override
    public void clearAllEpics() {
        epics.clear();
        subtasks.clear();
        System.out.println("Все эпики и подзадачи удалены");
    }

    // Поиск задачи/Task по ID
    @Override
    public Task searchByIdTask(int idTask) {
        historyManager.add(tasks.get(idTask));
        return (tasks.get(idTask));
    }

    // Поиск подзадачи/Subtask по ID
    @Override
    public Subtask searchByIdSubtask(int idSubtask) {
        historyManager.add(subtasks.get(idSubtask));
        return (subtasks.get(idSubtask));
    }

    // Поиск эпика по ID
    @Override
    public Epic searchByIdEpic(int idEpic) {
        historyManager.add(epics.get(idEpic));
        return epics.get(idEpic);
    }

    // Удаление задачи по ID
    @Override
    public void removeByIdTask(int idTask) {
        if (tasks.containsKey((idTask))) {
            tasks.remove(idTask);
            historyManager.remove(idTask); // Удаление из истории
            System.out.println("Задача с номером " + idTask + " удалена");
        }
    }

    // Удаление подзадачи по ID
    @Override
    public void removeByIdSubtask(int idSubtask) {
        if (subtasks.containsKey(idSubtask)) {
            Subtask subtask = subtasks.get(idSubtask);
            Epic epic = epics.get(subtask.getEpicId());
            for (int i = 0; i < epic.getSubtasksIds().size(); i++) {
                if (epic.getSubtasksIds().get(i) == idSubtask)
                    epic.getSubtasksIds().remove(i);
            }
            subtasks.remove(idSubtask);
            historyManager.remove(idSubtask); //Удаление из истории
            System.out.println("Подзадача с номером " + idSubtask + " удалена");
            changeStatusEpic(subtask.getEpicId());
        }
    }

    // Удаление эпика по ID
    @Override
    public void removeByIdEpic(int idEpic) {
        if (epics.containsKey(idEpic)) {
            epics.remove(idEpic);
            historyManager.remove(idEpic); //Удаление из истории
            System.out.println("Эпик с номером " + idEpic + " удалена");
            Set<Integer> idsToRemove = new HashSet<>();
            for (Integer idSubtasks : subtasks.keySet()) {
                Subtask subtask = subtasks.get(idSubtasks);
                if (idEpic == subtask.getEpicId()) {
                    idsToRemove.add(subtask.getId());
                    historyManager.remove(subtask.getId());
                }
            }
            subtasks.keySet().removeAll(idsToRemove);
            System.out.println("Подзадачи эпика " + idEpic + " удалены");
        }
    }

    // Печать списка всех подзадач определённого эпика по ID эпика
    @Override
    public void printSubtasksByEpics(int numberEpic) {
        for (Integer idSubtasks : subtasks.keySet()) {
            Subtask subtask = subtasks.get(idSubtasks);
            if (subtask.getEpicId() == numberEpic) {
                System.out.println(subtask);
            }
        }
    }

    // Получение истории задач
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //Обновление статуса Эпика/Epic
    private void changeStatusEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        int sumDone = 0;
        int sumNew = 0;
        for (Integer id : subtasks.keySet()) {
            Subtask subtask = subtasks.get(id);
            if (idEpic == subtask.getEpicId()) {
                switch (subtask.getStatus()) {
                    case IN_PROGRESS -> {
                        epic.setStatus(Status.IN_PROGRESS);
                        epic.setId(idEpic);
                        epics.put(idEpic, epic);
                        return;
                    }
                    case NEW -> sumNew += 1;
                    case DONE -> sumDone += 1;
                }
            }
        }
        if (sumDone == 0) {
            epic.setId(idEpic);
            epic.setStatus(Status.NEW);
            epics.put(idEpic, epic);

        } else if (sumNew == 0) {
            epic.setId(idEpic);
            epic.setStatus(Status.DONE);
            epics.put(idEpic, epic);

        } else {
            epic.setId(idEpic);
            epic.setStatus(Status.IN_PROGRESS);
            epics.put(idEpic, epic);
        }
    }
}









