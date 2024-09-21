import javafx.scene.control.ListView;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {
    private String name;
    private String description;
    private LocalDate dateAjout;
    private LocalDate dateFin;
    private Status status;

    public Task(String name, String description, LocalDate dateAjout, LocalDate dateFin, Status status){
        this.name = name;
        this.description = description;
        this.dateAjout = dateAjout;
        this.dateFin = dateFin;
        this.status = status;
    }

    public Task(String name, String description, LocalDate dateFin, Status status){
        this(name, description, LocalDate.now(), dateFin, status);
    }

    public Task(String name, String description){
        this(name, description, LocalDate.now(), LocalDate.now().plusDays(10), Status.TODO);
    }

    public Task(String name){
        this(name, "Pas de description pour cette tâche", LocalDate.now(), LocalDate.now().plusDays(10), Status.TODO);
    }


    public void setStatus(Status status){
        this.status = status;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDateFin(LocalDate date){
        this.dateFin = date;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDateAjout() {
        return this.dateAjout;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Status getStatus() {
        return this.status;
    }

    public String toString(){
        return "" + this.status + " : " + this.name + " --> " + this.description + " , expire le " + this.dateFin + ".";
    }

    public static void saveTasks(List<Task> tasks){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/ressources/Task.ser"))){
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }

    public static List<Task> loadTasks(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/ressources/Task.ser"))){
            return (List<Task>) ois.readObject();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Impossible de trouver le fichier à charger : " + cnfe.getMessage());
            return new ArrayList<>();
        } catch (IOException ioe) {
            System.out.println("Erreur lors du chargement des données : " + ioe.getMessage());
            return new ArrayList<>();
        }
    }
}
