package service;
import model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedHashMap map;

    LinkedList list;

//private final List<Task> history =new ArrayList<>(); //лист куда запишем
//private static final int SIZE= 10; //максимальный размер истории


    HashMap<Integer, Node> history = new HashMap<>();
    Node first; //нужно знать начало
    Node last; //нужно знать конец

    @Override
    public void add(Task task) {
        Node node = history.get(task.getId()); //проверяем есть ли такая задача
        removeNode(node); //удаляем ее
        linkLast(task); // добавляем, чтобы не было дублирования задач
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);
    }

    @Override
    public List<Task> getAll() {
        ArrayList<Task> list = new ArrayList<>();
        Node current = first; //идем от начала
        while (current != null) {
            //TODO - деляем какое то действие
            list.add(current.item); //обходим связный список
            current = current.next; //перемещаемся к следующей записи
        }
        return list;
    }

    @Override
    public List<Task> getHistory(){
        return history;
    }

    private void removeNode(Node node) { //удаление из связного списка
        //TODO
        node.prev.next = node.next;
        node.next.prev = node.prev;
        history.remove(node.item.getId());
    }

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Node elements, Node next) {
            this.item = elements;
            this.prev = prev;
            this.next = next;
        }
    }
}
