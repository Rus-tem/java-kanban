package taskmanager;

import status.Status;
import typeoftasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File fileToSave = new File("E:\\test.txt");

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task, int idTask, String newStatus) {
        super.updateTask(task, idTask, newStatus);
    }

    @Override
    public void updateEpic(int idEpic) {
        super.updateEpic(idEpic);
    }

    @Override
    public void updateSubtask(Subtask subtask, int idSubtask, String newStatus) {
        super.updateSubtask(subtask, idSubtask, newStatus);
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
    }

    @Override
    public void clearAllSubtasks() {
        super.clearAllSubtasks();
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
    }

    @Override
    public Task searchByIdTask(int idTask) {
        return super.searchByIdTask(idTask);
    }

    @Override
    public Subtask searchByIdSubtask(int idSubtask) {
        return super.searchByIdSubtask(idSubtask);
    }

    @Override
    public Epic searchByIdEpic(int idEpic) {
        return super.searchByIdEpic(idEpic);
    }

    @Override
    public void removeByIdTask(int idTask) {
        super.removeByIdTask(idTask);
    }

    @Override
    public void removeByIdSubtask(int idSubtask) {
        super.removeByIdSubtask(idSubtask);
    }

    @Override
    public void removeByIdEpic(int idEpic) {
        super.removeByIdEpic(idEpic);
    }

    @Override
    public void printSubtasksByEpics(int numberEpic) {
        super.printSubtasksByEpics(numberEpic);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    // метод сохраняет текущее состояние задач в файл
    public void save() {
        Writer writerFile;
        try {
            writerFile = new FileWriter(fileToSave);
            for (Integer i : getTasks().keySet()) {
                writerFile.write(getTasks().get(i) + "\n");
            }
            for (Integer i : getEpics().keySet()) {
                writerFile.write(getEpics().get(i) + "\n");
            }
            for (Integer i : getSubtasks().keySet()) {
                writerFile.write(getSubtasks().get(i) + ", " + getSubtasks().get(i).getEpicId() + "\n");
            }
            writerFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // метод загружает задачи из файла
    public void loadFromFile(File loadFromFile) {
        String file;
        try {
            file = Files.readString(loadFromFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] splitFile = file.split("\n");
        for (int i = 0; i < splitFile.length; i++) {
            StringBuilder stringBuilder = new StringBuilder(splitFile[i]);
            int first = stringBuilder.indexOf(":");
            int last = stringBuilder.indexOf(",");
            int counter = 0;
            int id = 0;
            String nameOfTask = "";
            String description = "";
            String status = "";
            int epicId = 0;

            while (first != -1) {
                if (last == -1) last = stringBuilder.length();
                if (counter == 0) {
                    id = Integer.parseInt(stringBuilder.substring(first + 2, last));
                    counter = counter + 1;
                } else if (counter == 1) {
                    nameOfTask = stringBuilder.substring(first + 2, last);
                    counter = counter + 1;
                } else if (counter == 2) {
                    description = stringBuilder.substring(first + 2, last);
                    counter = counter + 1;
                } else if (counter == 3) {
                    status = stringBuilder.substring(first + 2, last);
                    stringBuilder.delete(0, last + 2);
                    if (!stringBuilder.isEmpty()) {
                        epicId = Integer.parseInt(String.valueOf(stringBuilder));
                    }
                    if (nameOfTask.equals("TASK")) {
                        Task task = new Task(typeOfTasks.TASK, description);
                        task.setId(id);
                        if (status.equals("NEW")) task.setStatus(Status.NEW);
                        else if (status.equals("DONE")) task.setStatus(Status.DONE);
                        else task.setStatus(Status.IN_PROGRESS);
                        getTasks().put(task.getId(), task);
                    } else if (nameOfTask.equals("EPIC")) {
                        Epic epic = new Epic(typeOfTasks.EPIC, description);
                        epic.setId(id);
                        if (status.equals("NEW")) epic.setStatus(Status.NEW);
                        else if (status.equals("DONE")) epic.setStatus(Status.DONE);
                        else epic.setStatus(Status.IN_PROGRESS);
                        getEpics().put(epic.getId(), epic);
                    } else if (nameOfTask.equals("SUBTASK")) {
                        Subtask subtask = new Subtask(typeOfTasks.SUBTASK, description, epicId);
                        subtask.setId(id);
                        if (status.equals("NEW")) subtask.setStatus(Status.NEW);
                        else if (status.equals("DONE")) subtask.setStatus(Status.DONE);
                        else subtask.setStatus(Status.IN_PROGRESS);
                        getSubtasks().put(subtask.getId(), subtask);
                    } else {
                        System.out.println("Такой задачи не найдено");
                    }
                }
                stringBuilder.delete(0, last + 2);
                first = stringBuilder.indexOf(":");
                last = stringBuilder.indexOf(",");
            }
        }
    }
}
