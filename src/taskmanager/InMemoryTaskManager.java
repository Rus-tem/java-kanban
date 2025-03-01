package taskmanager;

import typeoftasks.*;
import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    private Set<Task> sortByTime = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparing(Task::getId));

    // Добавление Задачи/Task
    @Override
    public void addTask(Task task) {
        task.setId(nextId++);
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
        if (task.getStartTime() != null) {
            checkTasksCross(task);
        }
    }

    // Добавление Эпика/Epic
    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
        if (epic.getStartTime() != null) {
            checkTasksCross(epic);
        }
    }

    // Добавление Позадачи/Subtask
    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        subtask.setStatus(Status.NEW);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().add(subtask.getId());
        calculationDateTimeAndDurationEpic();
        if (subtask.getStartTime() != null) {
            checkTasksCross(subtask);
        }
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
        calculationDateTimeAndDurationEpic();
    }

    // Обновление подзадачи/Subtask
    @Override
    public void updateSubtask(Subtask subtask, int idSubtask, String newStatus) {
        if (subtasks.containsKey(idSubtask)) {
            subtask.setId(idSubtask);
            subtasks.put(idSubtask, subtask);
            subtask.setStatus(Status.valueOf(newStatus));
            changeStatusEpic(subtask.getEpicId());
            calculationDateTimeAndDurationEpic();
        }
    }

    // Печать списка всех задач
    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().toList();
    }

    // Печать списка всех эпиков
    @Override
    public List<Epic> getAllEpics() {
        return epics.values().stream().toList();
    }

    // Печать списка всех подзадач
    @Override
    public List<Subtask> getAllSubtasks() {
        return subtasks.values().stream().toList();
    }

    // Удаление всех задач +
    @Override
    public void clearAllTasks() {
        List<Task> removeTask = sortByTime.stream()
                .filter(task -> task.getName().equals("Task") || task.getName().equals("TASK") || task.getName().equals(TypeOfTasks.TASK.toString()))
                .toList();
        removeTask.forEach(sortByTime::remove);
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    // Удаление всех подзадач/subtask +
    @Override
    public void clearAllSubtasks() {
        List<Task> removeSubtask = sortByTime.stream()
                .filter(subtask -> subtask.getName().equals("Subtask") || subtask.getName().equals("SUBTAKS") || subtask.getName().equals(TypeOfTasks.SUBTASK.toString()))
                .toList();
        removeSubtask.forEach(sortByTime::remove);
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
        epics.values().forEach(epic -> epic.setStatus(Status.NEW));
    }

    // Удаление всех эпиков/epic +
    @Override
    public void clearAllEpics() {
        sortByTime.removeAll(epics.values());
        sortByTime.removeAll(subtasks.values());
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

    // Удаление задачи по ID +
    @Override
    public void removeByIdTask(int idTask) {
        if (tasks.containsKey((idTask))) {
            historyManager.remove(idTask); // Удаление из истории
            sortByTime.remove(tasks.get(idTask));
            tasks.remove(idTask);
            System.out.println("Задача с номером " + idTask + " удалена");
        } else {
            System.out.println("Task с таким Id отсутствует");
        }
    }

    // Удаление подзадачи по ID +
    @Override
    public void removeByIdSubtask(int idSubtask) {
        if (subtasks.containsKey(idSubtask)) {
            Subtask subtask = subtasks.get(idSubtask);
            Set<Integer> idsToRemove = new HashSet<>();
            epics.values().stream()
                    .map(epic -> epics.get(subtask.getEpicId()))
                    .forEach(epic -> idsToRemove.add(subtask.getId()));
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksIds().removeAll(idsToRemove);
            historyManager.remove(idSubtask);
            sortByTime.remove(subtask);
            subtasks.remove(idSubtask);
            System.out.println("Подзадача с номером " + idSubtask + " удалена");
            changeStatusEpic(subtask.getEpicId());
            calculationDateTimeAndDurationEpic();
        } else {
            System.out.println("Subtask с таким Id отсутствует");
        }
    }

    // Удаление эпика по ID +
    @Override
    public void removeByIdEpic(int idEpic) {
        if (epics.containsKey(idEpic)) {
            historyManager.remove(idEpic);
            sortByTime.remove(epics.get(idEpic));
            Set<Integer> idsToRemove = new HashSet<>();
            subtasks.values().stream()
                    .filter(subtask -> subtask.getEpicId() == idEpic)
                    .forEach(subtask -> idsToRemove.add(subtask.getId()));
            subtasks.values().stream()
                    .filter(subtask -> subtask.getEpicId() == idEpic)
                    .forEach(sortByTime::remove);
            subtasks.keySet().removeAll(idsToRemove);
            epics.remove(idEpic);
            System.out.println("Эпик с номером " + idEpic + " удален");
            System.out.println("Подзадачи эпика " + idEpic + " удалены");
        } else {
            System.out.println("Эпик с таким Id отсутствует");
        }
    }

    // Печать списка всех подзадач определённого эпика по ID эпика +
    @Override
    public void printSubtasksByEpics(int numberEpic) {
        subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == numberEpic)
                .forEach(System.out::println);
    }

    // Получение истории задач +
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Метод возвращающий список задач и подзадач в заданном порядке (по StartTime) +
    @Override
    public List<Task> getPrioritizedTasks() {
        return sortByTime.stream().toList();
    }

    //метод проверки пересечения времени задач и сохранения их в sortByTime
    protected void checkTasksCross(Task task) {
        Set<Task> taskToAdd = new HashSet<>();
        Set<Task> crossTasks = new HashSet<>();
        if (sortByTime.isEmpty()) {
            sortByTime.add(task);
        } else if (tasks.containsKey(task.getId())) {
            crossTasks = sortByTime.stream()
                    .dropWhile(task1 -> {
                        if ((task1.getStartTime().isBefore(task.getEndTime()) && task1.getEndTime().isBefore(task.getStartTime()))) {
                            taskToAdd.add(task);
                            return true;
                        } else if (task1.getStartTime().isAfter(task.getEndTime()) && task1.getEndTime().isAfter(task.getStartTime())) {
                            taskToAdd.add(task);
                            return true;
                        } else if (sortByTime.equals(task)) {
                            return false;
                        } else {
                            System.out.println("Задача: " + "№" + task.getId() + " " + task.getName() + " пересекается по времени с другой задачей: " + "№" + task1.getId() + " " + task1.getName());
                            return false;
                        }
                    })
                    .collect(Collectors.toSet());
            if (crossTasks.isEmpty() && !taskToAdd.isEmpty()) sortByTime.addAll(taskToAdd);

        } else if (epics.containsKey(task.getId())) {
            crossTasks = sortByTime.stream()
                    .dropWhile(epic1 -> {
                        if ((epic1.getStartTime().isBefore(task.getEndTime()) && epic1.getEndTime().isBefore(task.getStartTime()))) {
                            taskToAdd.add(task);
                            return true;
                        } else if (epic1.getStartTime().isAfter(task.getEndTime()) && epic1.getEndTime().isAfter(task.getStartTime())) {
                            taskToAdd.add(task);
                            return true;
                        } else if (sortByTime.equals(task)) {
                            return false;
                        } else {
                            System.out.println("Задача: " + "№" + task.getId() + " " + task.getName() + " пересекается по времени с другой задачей: " + "№" + epic1.getId() + " " + epic1.getName());
                            return false;
                        }
                    })
                    .collect(Collectors.toSet());
            if (crossTasks.isEmpty() && !taskToAdd.isEmpty()) sortByTime.addAll(taskToAdd);

        } else if (subtasks.containsKey(task.getId())) {
            crossTasks = sortByTime.stream()
                    .dropWhile(subtask1 -> {
                        if ((subtask1.getStartTime().isBefore(task.getEndTime()) && subtask1.getEndTime().isBefore(task.getStartTime()))) {
                            taskToAdd.add(task);
                            return true;
                        } else if (subtask1.getStartTime().isAfter(task.getEndTime()) && subtask1.getEndTime().isAfter(task.getStartTime())) {
                            taskToAdd.add(task);
                            return true;
                        } else if (sortByTime.equals(task)) {
                            return false;
                        } else {
                            System.out.println("Задача: " + "№" + task.getId() + " " + task.getName() + " пересекается по времени с другой задачей: " + "№" + subtask1.getId() + " " + subtask1.getName());
                            return false;
                        }
                    })
                    .collect(Collectors.toSet());
            if (crossTasks.isEmpty() && !taskToAdd.isEmpty()) sortByTime.addAll(taskToAdd);
        }
    }

    //Обновление статуса Эпика/Epic +
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

    // метод для расчета даты, времени и длительности Эпика/Epic +
    protected void calculationDateTimeAndDurationEpic() {
        LocalDateTime startTimeSubtask = null;
        Duration durationSubtask = null;
        Duration sum = null;
        for (Integer idEpic : epics.keySet()) {
            for (Integer idSubtask : subtasks.keySet()) {
                Subtask subtask = subtasks.get(idSubtask);
                if (subtask.getEpicId() == idEpic) {
                    if (durationSubtask == null) {
                        durationSubtask = subtask.getDuration();
                        sum = durationSubtask;
                    } else {
                        sum = subtask.getDuration().plus(sum);
                    }
                    if (startTimeSubtask == null) {
                        startTimeSubtask = subtask.getStartTime();
                    } else if (subtask.getStartTime().isBefore(startTimeSubtask)) {
                        startTimeSubtask = subtask.getStartTime();
                    }
                }
            }
            Epic epic = epics.get(idEpic);
            if (epic.getSubtasksIds().isEmpty()) {
                break;
            } else {
                epic.setStartTime(startTimeSubtask);
                epic.setDuration(sum);
            }
        }
    }
}







