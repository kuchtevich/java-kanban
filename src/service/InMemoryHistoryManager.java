package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class InMemoryHistoryManager implements HistoryManager {
    HashMap<Integer, Node> history = new HashMap<>();
    Node first; //нужно знать начало
    Node last; //нужно знать конец


    @Override
    public void add(Task task) {
        Node node = history.get(task.getId()); //проверяем есть ли такая задача
        removeNode(node); //удаляем ее
        linkLast(task);
    }


    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node current = first;
        while (current != null) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    private void removeNode(Node node) { //удаление из связного списка
        if (node == null) {
            return;
        }
        history.remove(node.item.getId());
        Node prev = node.prev;
        Node next = node.next;

        if (next == null) {
            if (prev != null) {
                prev.next = null;
                last = prev;
            }
        } else {
            if (prev != null) {
                prev.next = next;
            }
        }
        if (prev == null) {
            next.prev = null;
            first = next;
        } else {
            if (next != null) {
                next.prev = prev;
            }
        }
    }

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private void linkLast(Task task) {
        final Node savedLast = last;
        final Node node = new Node(savedLast, task, null);
        last = node;
        if (savedLast == null) {
            first = node;
        } else {
            savedLast.next = node;
        }
        // добавляем, чтобы не было дублирования задач
        history.put(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);
    }
}