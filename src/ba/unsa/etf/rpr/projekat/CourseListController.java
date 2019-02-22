package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class CourseListController {
    @FXML private TreeView<String> undergraduateTree;
    @FXML private TreeView<String> masterTree;
    @FXML private TreeView<String> phdTree;

    public void initialize() {
        loadUndergraduateItems("initial 1", "initial 2", "initial 3");
        loadMasterItems("initial 1", "initial 2", "initial 3");
        loadPhdItems("initial 1", "initial 2", "initial 3");

    }

    public void loadUndergraduateItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Undergraduate");
        root.setExpanded(true);
        for (String itemString: rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        undergraduateTree.setRoot(root);
    }

    public void loadMasterItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Master");
        root.setExpanded(true);
        for (String itemString: rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        masterTree.setRoot(root);
    }

    public void loadPhdItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Phd");
        root.setExpanded(true);
        for (String itemString: rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        phdTree.setRoot(root);
    }

    }

