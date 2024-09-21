import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoListApp extends Application {

    private ObservableList<Task> taskObservableList; //liste observables des tâches

    public void start(Stage primaryStage) {
        List<Task> loadedTask = Task.loadTasks();
        taskObservableList = FXCollections.observableArrayList(loadedTask);

        TextField nameTaskInput = new TextField();
        nameTaskInput.setPromptText("Nom de la tâche");
        TextField descriptionTaskInput = new TextField();
        descriptionTaskInput.setPromptText("Description de la tâche");
        TextField statusTaskInput = new TextField();
        statusTaskInput.setPromptText("Status de la tâche : TODO ; DOING ; DONE");

        Button addButton = new Button("Ajouter");
        ListView<Task> listeTask = new ListView<>(taskObservableList);

        addButton.setOnAction(e ->{
            String taskName = nameTaskInput.getText();
            String taskDescription = descriptionTaskInput.getText();
            String taskStatus = statusTaskInput.getText();

            if(!taskName.isEmpty()){
                Task newTask = new Task(taskName, taskDescription, LocalDate.now().plusDays(7), Status.valueOf(taskStatus));

                //ajouter la tâche à la liste
                taskObservableList.add(newTask);
                Task.saveTasks(new ArrayList<>(taskObservableList)); //sauvegarder après l'ajout d'une tâche

                //vider les champs de texte
                nameTaskInput.clear();
                descriptionTaskInput.clear();
                statusTaskInput.clear();
            }
        });


        listeTask.setOnMouseClicked(event ->{
            if(event.getClickCount() == 2){
                Task selectedtask = listeTask.getSelectionModel().getSelectedItem();
                if(selectedtask != null){
                    openEditTaskWindow(selectedtask, listeTask); // appel à la méthode openEditTaskWindow
                }
            }
        });

        //mise en place de la VBox et l'àjout des différents éléments
        VBox layout = new VBox(10); // espacement de 10px entre les éléments
        layout.getChildren().addAll(nameTaskInput, descriptionTaskInput, statusTaskInput, addButton, listeTask);

        //creation de la scène
        Scene scene = new Scene(layout, 400, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ToDolist");
        primaryStage.show();
    }

    private void openEditTaskWindow(Task task, ListView<Task> taskListView){
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL); //mettre la fenêtre modale, ce qui signifie qu'on ne pourra pas cliquer sur celle de derrière
        editStage.setTitle("Modifier la tâche");

        //champ de texte qui sera rempli par défaut par les informations de la tâche
        TextField nameTaskInput = new TextField(task.getName());
        TextField descriptionTaskInput = new TextField(task.getDescription());
        DatePicker dateTaskInput = new DatePicker(task.getDateFin());
        TextField statusTaskInput = new TextField(task.getStatus().name());
        CheckBox favoriteCheckbox = new CheckBox("Favori");
        favoriteCheckbox.setSelected(task.getIsFavorite());

        Button saveButton = new Button("Sauvegarder");
        Button deleteButton = new Button("Supprimer");


        saveButton.setOnAction(e ->{
            task.setName(nameTaskInput.getText());
            task.setDescription(descriptionTaskInput.getText());
            task.setDateFin(dateTaskInput.getValue());
            task.setStatus(Status.valueOf(statusTaskInput.getText()));
            task.setIsFavorite(favoriteCheckbox.isSelected());

            taskListView.refresh(); //mettre à jour la liste
            Task.saveTasks(new ArrayList<>(taskObservableList));
            editStage.close(); //fermer la fenêtre de modification
        });

        deleteButton.setOnAction(e ->{
            taskListView.getItems().remove(task); //supprimer la tâche de la liste
            Task.saveTasks(new ArrayList<>(taskObservableList));
            editStage.close(); //fermer la fenêtre de modification
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("Nom"), nameTaskInput, new Label("Description"), descriptionTaskInput, new Label("Date de fin"), dateTaskInput, new Label("Status"), statusTaskInput, favoriteCheckbox, saveButton, deleteButton);

        Scene scene = new Scene(layout, 300, 400);
        editStage.setScene(scene);
        editStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
