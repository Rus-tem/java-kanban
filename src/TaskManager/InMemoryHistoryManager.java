package TaskManager;

import Typeoftasks.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyTasks = new ArrayList<>();
    private Node<Task> tail;
    private Node<Task> head;
    Map<Integer, Node<Task>> historyNode = new HashMap<>();

    @Override
    public void add(Task task) {
        linkLast(task);
        historyNode.put(task.getId(), tail);
    }

    @Override
    public List<Task> getHistory() {
        getTasks();
        return historyTasks;
    }

    @Override
    public void remove(int id) {
        // Удаление из истории по номеру id
        Node<Task> node = historyNode.get(id);
        if (node == null) {
            return;
        } else {
            removeNode(node);
            historyNode.remove(id);
        }
    }

    public void removeNode(Node<Task> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else if (node.prev != null) {
            if (node.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }

    // метод добавляет задачу в конец этого списка
    public void linkLast(Task task) {
        if (historyNode.containsKey(task.getId())) {
            remove(task.getId());
        }
        if (tail == null) {
            head = new Node<>(null, task, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, task, null);
            tail = tail.next;
        }
    }

    // метод собирает все задачи из него в обычный ArrayList
    public void getTasks() {
        historyTasks.clear();
        Node<Task> node = head;
        while (node != null) {
            historyTasks.add(node.task);
            node = node.next;
        }
    }

    private static class Node<Task> {
        Task task;
        Node<Task> next;
        Node<Task> prev;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }
}