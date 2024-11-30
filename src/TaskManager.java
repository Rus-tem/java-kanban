import java.util.HashMap;
import java.util.HashSet;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int nextId = 1;

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
        Epic epic = epics.get(subtask.epicId);
        epic.subtasksIds.add(subtask.epicId);
    }

    // Обновление Задачи/Task
    public void updateTask(Task task, int idTask, String newStatus) {
        for (Integer id : tasks.keySet()) {
            if (idTask == id) {
                task.setId(idTask);
                tasks.put(idTask, task);
                task.setStatus(String.valueOf(newStatus));
                break;
            }
        }
    }

    // Обновление Эпика/Epic
    public void updateEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        if (epic.subtasksIds.isEmpty()) {
            epic.setId(idEpic);
            epic.setStatus(String.valueOf(Status.NEW));
            epics.put(idEpic, epic);
            return;
        }
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

    // Обновление подзадачи/Subtask
    public void updateSubtask(Subtask subtask, int idSubtask, String newStatus) {
        for (Integer id : subtasks.keySet()) {
            if (idSubtask == id) {
                subtask.setId(idSubtask);
                subtasks.put(idSubtask, subtask);
                subtask.setStatus(String.valueOf(newStatus));
                break;
            }
        }
    }

    // Печать списка всех задач, подзадач и эпиков
    public void printAllTasksAndEpics() {
        System.out.println("Список всех задач/подзадач/эпиков:");
        for (Integer idTasks : tasks.keySet()) {
            System.out.println(tasks.get(idTasks));
        }
        for (Integer idEpics : epics.keySet()) {
            System.out.println(epics.get(idEpics));
        }
        for (Integer idSubtasks : subtasks.keySet()) {
            System.out.println(subtasks.get(idSubtasks));
        }
    }

    // Удаление всех задач, подзадач и эпиков
    public void clearAllTasksAndEpics() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        System.out.println("Все задачи, подзадачи и эпики удалены");
    }

    // Поиск задачи, подзадачи и эпика по ID
    public void searchById(int searchNumber) {
        for (Integer idTasks : tasks.keySet()) {
            if (searchNumber == idTasks) {
                System.out.println(tasks.get(searchNumber));
                break;
            }
        }
        for (Integer idSubtasks : subtasks.keySet()) {
            if (searchNumber == idSubtasks) {
                System.out.println(subtasks.get(searchNumber));
                break;
            }
        }
        for (Integer idEpics : epics.keySet()) {
            if (searchNumber == idEpics) {
                System.out.println(epics.get(searchNumber));
                break;
            }
        }
    }

    // Удаление задачи, подзадачи и эпика по ID
    public void removeById(int removeNumber) {
        for (Integer idTasks : tasks.keySet()) {
            if (removeNumber == idTasks) {
                tasks.remove(removeNumber);
                System.out.println("Задача с номером " + removeNumber + " удалена");
                return;
            }
        }
        for (Integer idSubtasks : subtasks.keySet()) {
            if (removeNumber == idSubtasks) {
                subtasks.remove(removeNumber);
                System.out.println("Подзадача с номером " + removeNumber + " удалена");
                return;
            }
        }
        for (Integer idEpics : epics.keySet()) {
            if (removeNumber == idEpics) {
                epics.remove(removeNumber);
                System.out.println("Эпик с номером " + removeNumber + " удалена");
                break;
            }
        }
        HashSet<Integer> idsToRemove = new HashSet<Integer>();
        for (Integer idSubtasks : subtasks.keySet()) {
            Subtask subtask = subtasks.get(idSubtasks);
            if (removeNumber == subtask.epicId) {
                idsToRemove.add(subtask.getId());
            }
        }
        subtasks.keySet().removeAll(idsToRemove);
        System.out.println("Подзадачи эпика " + removeNumber + " удалены");
    }

    // Печать списка всех подзадач определённого эпика по ID эпика
    public void printSubtasksByEpics(int numberEpic) {
        for (Integer idSubtasks : subtasks.keySet()) {
            Subtask subtask = subtasks.get(idSubtasks);
            if (subtask.epicId == numberEpic) {
                System.out.println(subtask);
            }
        }
    }
}





