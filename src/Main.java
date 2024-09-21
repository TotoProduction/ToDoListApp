import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Task tache1 = new Task("Faire projet", "Pour me permettre de m'améliorer en Java");
        Task tache2 = new Task("Cube", "M'entraîner au cube", LocalDate.now().plusDays(31), Status.DOING);
        ToDoList todo = new ToDoList();
        todo.addTask(tache1);
        todo.addTask(tache2);
        System.out.println(todo.getList());
    }
}