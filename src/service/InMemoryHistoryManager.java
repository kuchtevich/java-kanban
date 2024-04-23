package service;
import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
private final List<Task> history =new ArrayList<>(); //лист куда запишем
private static final int SIZE= 10; //максимальный размер истории

    @Override
    public void add(Task task) {
        if (task !=null) { // если история не 0 и список не равен 10, то добавляем
            if(history.size() == SIZE) {
                history.remove(0); //удаляем из истории первый фрагмент
            }
            history.add(task); // добавляеи новую единицу в историю
        } else {
            System.out.println("История пуста");
        }
    }
    @Override
    public List<Task> getHistory(){ //что делает не ясно
        return history;
    }

}
