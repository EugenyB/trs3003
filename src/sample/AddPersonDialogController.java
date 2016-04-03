package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tables.Person;

/**
 * Created by eugeny on 27.03.2016.
 */
public class AddPersonDialogController {
    private Stage dialogStage;
    private Person person = new Person();
    private boolean okClicked = false;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField adressField;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setAge(Integer.parseInt(ageField.getText()));
            person.setAdress(adressField.getText());
            System.out.println("Ok");
            okClicked = true;
            dialogStage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка при вводе");
            alert.setHeaderText(null);
            alert.setContentText("Проверьте ввод");

            alert.showAndWait();
        }
    }

    private boolean isInputValid() {
        try {
            Integer.parseInt(ageField.getText());
            return ! (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || adressField.getText().isEmpty());
        } catch (NumberFormatException e) {
            return false;
        }

    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public Person getPerson() {
        return person;
    }
}
