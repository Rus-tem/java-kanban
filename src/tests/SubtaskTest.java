package tests;

import taskmanager.*;
import typeoftasks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    TaskManager taskManager = Manager.getDefault();

    @Test
        // 2
    void testAddSubtask() {

        taskManager.addEpic(new Epic("Epic1", "Epic1"));
        taskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 1));
        Subtask subtask1 = taskManager.searchByIdSubtask(2);
        Subtask subtask2 = taskManager.searchByIdSubtask(2);
        assertEquals(subtask1, subtask2);
    }

    @Test
        //4
    void testAddSubtaskToSubtask() {
        taskManager.addEpic(new Epic("Epic1", "Epic1"));
        taskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 1));
        taskManager.addSubtask(new Subtask("Subtask2", "Subtask2", 1));
        Epic epic1 = taskManager.searchByIdEpic(1);
        Subtask subtask1 = taskManager.searchByIdSubtask(2);
        Subtask subtask2 = taskManager.searchByIdSubtask(3);
        assertEquals(subtask1.getEpicId(), subtask2.getEpicId());
        assertEquals(epic1.getSubtasksIds().get(1), subtask1.getEpicId());
        assertEquals(epic1.getSubtasksIds().get(1), subtask2.getEpicId());
        System.out.println("Подзадачи эпика с ID 1: ");
        taskManager.printSubtasksByEpics(1);
    }

}