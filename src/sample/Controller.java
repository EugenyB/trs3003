package sample;

import dao.PersonDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tables.Person;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AddPersonDialogController addPersonDialogController; // Диалог для ввода данных нового объекта Person

    @FXML TableColumn<Person,Integer> idColumn; // Столбец для отображения целого (Integer) поля из объекта Person
    @FXML TableColumn<Person,String> firstNameColumn; // Столбец для отображения строкового (String) поля из Person
    @FXML TableColumn<Person,String> lastNameColumn;
    @FXML TableColumn<Person,Integer> ageColumn;
    @FXML TableColumn<Person,String> adressColumn;
    @FXML
    TableView<Person> tablePersons;  // Таблица для отображения списка объектов класса Person

    private Connection connection;
    private PersonDAO personDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createDialogs(); // Создаем вспомогательные диалоговые окна (в этом примере - одно окно)
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/example","eugeny","123");
            personDAO = new PersonDAO(connection);
            fillTable();
        } catch (SQLException e) {
            System.err.println("Error");
        }

    }

    private void createDialogs() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("dialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Для диалога нужно создвть объект Stage и указать, что его владельцем будет Stage главного окна.
            Stage dialogStage = new Stage();

            // Настраиваем параметры диалога
            dialogStage.setTitle("Edit Person");
            // модальность диалога - свойство блокировать родительское окно, пока открыт диалог
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getMainStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            addPersonDialogController = loader.getController();
            addPersonDialogController.setDialogStage(dialogStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable() {

        ObservableList<Person> persons = FXCollections.observableArrayList(personDAO.findAll());
        // Связываем столбец idColumn с полем "id"
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Связываем столбец firstNameColumn с полем "firstNane" (имя поля из класса Person, а не из таблицы в БД)
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // предоставляем возможность изменения ячеек в этом столбце
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Связываем столбец lastNameColumn с полем "lastNane" (имя поля из класса Person, а не из таблицы в БД)
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        // предоставляем возможность изменения ячеек в этом столбце
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Связываем столбец ageColumn с полем "age"
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        // предоставляем возможность изменения ячеек в этом столбце с учетом необходимости преобразований в целое число
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // С адресом аналогично как с firstName или lastName
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("adress"));
        adressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //
        tablePersons.setItems(persons);
    }

    //
    public void editFirstName(TableColumn.CellEditEvent<Person, String> event) {
        int row = event.getTablePosition().getRow();
        String newFirstName = event.getNewValue();
        Person person = event.getTableView().getItems().get(row);
        person.setFirstName(newFirstName);
        personDAO.update(person);
        event.getTableView().getItems().set(row,person);
    }

    public void editLastName(TableColumn.CellEditEvent<Person, String> event) {
        int row = event.getTablePosition().getRow();
        String newLastName = event.getNewValue();
        Person person = event.getTableView().getItems().get(row);
        person.setLastName(newLastName);
        personDAO.update(person);
        event.getTableView().getItems().set(row,person);
    }

    public void editAdress(TableColumn.CellEditEvent<Person, String> event) {
        int row = event.getTablePosition().getRow();
        String newAdress = event.getNewValue();
        Person person = event.getTableView().getItems().get(row);
        person.setAdress(newAdress);
        personDAO.update(person);
        event.getTableView().getItems().set(row,person);
    }

    public void editAge(TableColumn.CellEditEvent<Person, Integer> event) {
        int row = event.getTablePosition().getRow();
        Integer newAge = event.getNewValue();
        Person person = event.getTableView().getItems().get(row);
        person.setAge(newAge);
        personDAO.update(person);
        event.getTableView().getItems().set(row,person);
    }

    public void deletePerson(ActionEvent actionEvent) {
        Person person = (Person) tablePersons.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление элемента");
        alert.setHeaderText("Подтвердите удаление элемента");
        alert.setContentText(
                "Имя: " + person.getFirstName()+"\n" +
                        "Фамилия: " + person.getLastName()+"\n" +
                        "Возраст: " + person.getAge()+"\n" +
                        "Адрес: " + person.getAdress()
        );
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK) {
            personDAO.delete(person);
            tablePersons.getItems().remove(person);
        }
    }

    public void addPerson(ActionEvent actionEvent) {
        addPersonDialogController.getDialogStage().showAndWait();
        if (addPersonDialogController.isOkClicked()) {
            personDAO.create(addPersonDialogController.getPerson());
            refreshTableItems();
        }
    }

    // Обновить записи в таблице с учетом обновления в БД
    private void refreshTableItems() {
        tablePersons.setItems(FXCollections.observableArrayList(personDAO.findAll()));
    }
}
