package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.excel.ObjectRepo;

public class UIElements {

    @FXML
    public Button fileSearch;

    @FXML
    public Button fileClear;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public TextField fileTextField;

    @FXML
    public Label fileLabel;

    @FXML
    public ComboBox<String> comboBox;

    ObservableList<String> observableList;
    ObservableList<ObjectRepo> objectRepoObservableList;

    @FXML
    public TextField objSearch;

    @FXML
    public Button objSearchBtn;

    @FXML
    public Button objClearBtn;

    @FXML
    public TableView<ObjectRepo> tableView;
    @FXML
    public TableColumn<ObjectRepo, String> pageName;
    @FXML
    public TableColumn<ObjectRepo, String> variableName;
    @FXML
    public TableColumn<ObjectRepo, String> identifierName;
    @FXML
    public TableColumn<ObjectRepo, String> valueName;

    //FXCollections.observableArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun");


}
