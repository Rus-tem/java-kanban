public class Main {

    static TaskManager ts;

    public static void main(String[] args) {
        ts = new TaskManager();
        ts.addTask(new Task("Task1", "Task1")); // -- Задача с ID 1
        ts.addTask(new Task("Task2", "Task2")); // -- Задача с ID 2

        ts.addEpic(new Epic("Epic1", "Epic1")); // --  Эпик c ID 3
        ts.addSubtask(new Subtask("Subtask1", "Subtask1", 3)); // -- Подзадача с ID 4
        ts.addSubtask(new Subtask("Subtask2", "Subtask2", 3)); // -- Подзадача с ID 5

        ts.addEpic(new Epic("Epic2", "Epic2")); // -- Эпик с ID 6
        ts.addSubtask(new Subtask("Subtask3", "Subtask3", 6)); // -- Подзадача с ID 7
        ts.printAllTasksAndEpics();

        ts.updateTask(new Task("NewTask1", "NewTask1"), 1, "DONE");
        ts.updateTask(new Task("NewTask2", "NewTask2"), 2, "IN_PROGRESS");

        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 3), 4, "DONE");
        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 3), 5, "DONE");
        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 6), 6, "IN_PROGRESS");
        ts.printAllTasksAndEpics();

        ts.removeById(1); // Удаление по ID
        ts.removeById(3); // Удаление по ID
        ts.printAllTasksAndEpics();

    }
}
/*
Печать списка всех подзадач определённого эпика по ID эпика -- printSubtasksByEpics
Удаление задачи, подзадачи и эпика по ID -- removeById
Поиск задачи, подзадачи и эпика по ID -- searchById
Удаление всех задач, подзадач и эпиков -- clearAllTasksAndEpics
Печать списка всех задач, подзадач и эпиков -- printAllTasksAndEpics
Обновление подзадачи/Subtask -- updateSubtask
Обновление Эпика/Epic -- updateEpic
Обновление Задачи/Task -- updateTask
Добавление Позадачи/Subtask -- addSubtask
Добавление Эпика/Epic -- addEpic
Добавление Задачи/Task -- addTask
*/