package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubjectViewController {

    public TableView<Material> tableOfMaterials;
    public TableColumn<Material,String> tableLecture;
    public TableColumn<Material,String> tableLab;
    public TableColumn<Material, String> tableGroup;
    public  Database database;
    public Material currentMaterial;
    public Label statusMsg;
    @FXML
    public void initialize() {
        database=database.getInstance();
        tableOfMaterials.setItems(database.getInstance().getMaterials());
        tableOfMaterials.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial=newSelection;        }
        });
        currentMaterial=tableOfMaterials.getSelectionModel().getSelectedItem();
        ObservableList<Material> materials=database.getMaterials();
        System.out.println(materials.size());

        tableLecture.setText("Lectures");
        tableLab.setText("Labs");
        tableGroup.setText("Groups");
        final ObservableList<Material> data = FXCollections.observableArrayList(materials);
        tableOfMaterials.setItems(materials);
        tableLecture.setCellValueFactory(
                new PropertyValueFactory<Material,String>("nameMaterial")
        );

        if (materials.size() != 0) {
            currentMaterial=materials.get(0);
            tableOfMaterials.getSelectionModel().select(0);
        }
    }

}
