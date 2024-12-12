package Tests;

import TaskManager.InMemoryTaskManager;
import Typeoftasks.Epic;
import Typeoftasks.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
        // 2
    void testAddSubtask() {

        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 1));
        Subtask subtask1 = inMemoryTaskManager.searchByIdSubtask(2);
        Subtask subtask2 = inMemoryTaskManager.searchByIdSubtask(2);
        assertEquals(subtask1, subtask2);
    }

    @Test
        //4
    void testAddSubtaskToSubtask() {
        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 1));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask2", "Subtask2", 1));
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(1);
        Subtask subtask1 = inMemoryTaskManager.searchByIdSubtask(2);
        Subtask subtask2 = inMemoryTaskManager.searchByIdSubtask(3);
        assertEquals(subtask1.getEpicId(), subtask2.getEpicId());
        assertEquals(epic1.getSubtasksIds().get(1), subtask1.getEpicId());
        assertEquals(epic1.getSubtasksIds().get(1), subtask2.getEpicId());
        System.out.println("Подзадачи эпика с ID 1: ");
        inMemoryTaskManager.printSubtasksByEpics(1);


    }
}