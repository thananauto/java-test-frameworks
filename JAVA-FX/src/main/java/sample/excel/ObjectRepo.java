package sample.excel;

import javafx.beans.property.SimpleStringProperty;

public class ObjectRepo {

    public String getPageName() {
        return pageName.get();
    }

    public String getVariableName() {
        return variableName.get();
    }

    public String getIdentifierName() {
        return identifier.get();
    }

    public String getValueName() {
        return value.get();
    }

    private final SimpleStringProperty variableName;
    private final SimpleStringProperty identifier;
    private final SimpleStringProperty value;
    private final SimpleStringProperty pageName;

    public ObjectRepo(String pageName, String varName, String identifier, String value) {
        super();
        this.pageName = new SimpleStringProperty(pageName);
        this.variableName = new SimpleStringProperty(varName);
        this.identifier = new SimpleStringProperty(identifier);
        this.value = new SimpleStringProperty(value);
    }
}
