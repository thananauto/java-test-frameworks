package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.excel.ObjectRepo;
import sample.excel.XcelAccess;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller  extends UIElements implements Initializable{

    private XcelAccess xcelAccess;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //
        fileClear.setDisable(true);
        comboBox.setDisable(true);
        objSearch.setDisable(true);
         objSearchBtn.setDisable(true);
         objClearBtn.setDisable(true);
    }

    public void onSelect(ActionEvent actionEvent){
        fileTextField.setDisable(true);
        objSearch.setDisable(false);
        objSearchBtn.setDisable(false);
        objClearBtn.setDisable(false);
    }


    /**
     * Method to search the object
     * @param actionEvent
     */
    public void searchObjects(ActionEvent actionEvent){
                objClearBtn.setDisable(false);
             xcelAccess.setDatasheetName(comboBox.getSelectionModel().getSelectedItem());
            String searchText = objSearch.getText();
        if(searchText.trim().equals("")){
            KeywordAlert("");
            return;
        }
            List<ObjectRepo> output =xcelAccess.lstSearchResults(searchText);
            if(output!=null && output.size()>0){

                objectRepoObservableList = FXCollections.observableArrayList(output);
                pageName.setCellValueFactory(new PropertyValueFactory<ObjectRepo, String>("PageName"));
                variableName.setCellValueFactory(new PropertyValueFactory<ObjectRepo, String>("VariableName"));
                identifierName.setCellValueFactory(new PropertyValueFactory<ObjectRepo, String>("IdentifierName"));
                valueName.setCellValueFactory(new PropertyValueFactory<ObjectRepo, String>("ValueName"));
                tableView.setItems(objectRepoObservableList);
            }else{
                MsgAlert(searchText);
            }

    }


    public void clearObject(ActionEvent actionEvent){
        objSearch.setText("");
        tableView.getItems().clear();


    }


    /**
     * Select the file from modal dialog
     * @param mouseEvent
     */
    public void selectFileFromDirectory(MouseEvent mouseEvent){

        FileChooser fileChooser = new FileChooser();
       //fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialDirectory(new File("C:\\projects\\OUTPUT\\pvh\\PVH\\src\\test\\resources\\businessflows"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));
        File file = fileChooser.showOpenDialog(new Stage());

        //read the file and set the path
        if(file!=null) {
            String fileName = file.getName();
            String filePath = file.getParent();
            xcelAccess = new XcelAccess(filePath, fileName);
            observableList = FXCollections.observableArrayList(xcelAccess.getAllSheets());
            comboBox.setItems(observableList);
            fileTextField.setText(file.getAbsolutePath());
            fileClear.setDisable(false);
            comboBox.setDisable(false);


        }else
            errorAlert();
     }

    /**
     * Message for Error Alert
     * */
    public void errorAlert( ){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Select the workbook contains Object Repo!");
        alert.showAndWait();
    }

    public void MsgAlert(String keyword ){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Keywords search");
        alert.setContentText("Keyword: \""+keyword+"\" is not matched in repository");
        alert.showAndWait();
    }

    public void KeywordAlert(String keyword ){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Enter any keywords....");
        alert.showAndWait();
    }


    public void clearSearch(ActionEvent actionEvent){
        comboBox.getItems().clear();
        comboBox.setDisable(true);
        fileTextField.setDisable(false);
        fileTextField.setText("");
        fileClear.setDisable(true);
        fileClear.setDisable(true);
        objSearch.setDisable(true);
        objSearchBtn.setDisable(true);
        objClearBtn.setDisable(true);

    }
}