import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Task> listeTache;

    public ToDoList(List<Task> liste){
        this.listeTache = liste;
    }
    public ToDoList(){
        this.listeTache = new ArrayList<Task>();
    }


    public void addTask(Task aTask){
        this.listeTache.add(aTask);
    }

    public void removeTask(Task aTask){
        this.listeTache.remove(aTask);
    }

    public String getList(){
        String result = "";
        for(Task t : listeTache){
            result = result + t.toString() + '\n';
        }
        return result;
    }
}
