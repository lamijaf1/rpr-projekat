package ba.unsa.etf.rpr.projekat;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class SubjectViewController {

    public TableView<Material> tableOfLectures, tableOfLabs, tableOfGroups;
    public TableColumn<Material, String> columnName, columnNameLab, columnNameGroup;
    public static CoursewareDAO coursewareDAO;
    public Material currentMaterial;
    public Label statusMsg;
    private static String subjectName;
    private static String subjectType;
    public Button tbAdd, tbHide, tbUnhide, tbDelete, tbOpen, tbClear, tbBack, tbHome;
    private boolean youAreProfessorOnSubject = CourseListController.isEditOnSelectSubject();
    ObservableList<Material> groupsForGuest = FXCollections.observableArrayList();
    ObservableList<Material> labsForGuest = FXCollections.observableArrayList();
    ObservableList<Material> lecturesForGuest = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        coursewareDAO = coursewareDAO.getInstance();
        subjectName = CourseListController.getSubjectTitle();
        String notificationString = "";
        if (coursewareDAO.getNotification(subjectName) != null) {
            ObservableList<Notification> notifications = coursewareDAO.getNotification(subjectName);
            for (Notification notification : notifications) {
                notificationString += (notification.getDate() + "-> " + notification.getText() + "\n");
            }
            statusMsg.setText(notificationString);
        } else {
            statusMsg.setText("Welcome to " + subjectName);
        }
        if (!CourseListController.isEditOnSelectSubject()) {
            tbHide.setVisible(false);
            tbUnhide.setVisible(false);
            tbDelete.setVisible(false);
            tbClear.setVisible(false);
            tbAdd.setVisible(false);
            refillTables();
        } else {
            fillGroupTable();
            fillLectureTable();
            fillLabTable();
        }


    }

    private void fillGroupTable() {

        tableOfGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;

            }
        });
        currentMaterial = tableOfGroups.getSelectionModel().getSelectedItem();
        ObservableList<Material> groups = coursewareDAO.getGroups(subjectName);


        columnNameGroup.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfGroups.setItems(groups);


    }

    private void fillLabTable() {
        tableOfLabs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLabs.getSelectionModel().getSelectedItem();
        ObservableList<Material> labs = coursewareDAO.getLabs(subjectName);

        columnNameLab.setCellValueFactory(new PropertyValueFactory<Material, String>("nameMaterial"));

        tableOfLabs.setItems(labs);
    }

    private void fillLectureTable() {
        tableOfLectures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLectures.getSelectionModel().getSelectedItem();
        ObservableList<Material> lectures = coursewareDAO.getLectures(subjectName);

        columnName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfLectures.setItems(lectures);
    }

    public void clearNotification(ActionEvent actionEvent) throws SQLException {
        if (!youAreProfessorOnSubject) error();
        else {
            statusMsg.setText("Welcome to " + subjectName);
            coursewareDAO.deleteNotification(subjectName);
        }

    }

    public void back(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseList.fxml"));
        CourseListController controller = new CourseListController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        primaryStage.setTitle("Course List");
        primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void signOut(ActionEvent actionEvent) throws Exception {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Main main = new Main();

        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        main.start(primaryStage);

    }

    public void openMaterial(ActionEvent actionEvent) throws IOException {
        File file = new File("./resources/pdfs/" + currentMaterial.getNameMaterial() + ".pdf");
        Desktop.getDesktop().open(file);

    }

    public void hideMaterial(ActionEvent actionEvent) throws IOException, SQLException {
        if (currentMaterial != null) {
            currentMaterial.setVisible(0);
            coursewareDAO.changeMaterial(currentMaterial);
        }

        //refillTables();
        if (currentMaterial != null) System.out.println(currentMaterial.isVisible());
    }

    public void unhideMaterial(ActionEvent actionEvent) throws IOException, SQLException {
        if (currentMaterial != null) currentMaterial.setVisible(1);
        coursewareDAO.changeMaterial(currentMaterial);
        //refillTables();
        if (currentMaterial != null) System.out.println(currentMaterial.isVisible());
    }


    public void saveMaterials(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/typeOfMaterial.fxml"));
        TypeOfMaterialController controller = new TypeOfMaterialController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        primaryStage.setTitle("Type of material");
        primaryStage.setScene(new Scene(root, 400, 140));
        primaryStage.setResizable(false);

    }

    public static void setSubjectType(String subject) {
        subjectType = subject;
    }

    public static void openChooser() throws SQLException, IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("", "pdf"));
        if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getName();
            String currFIle = filename;
            if (!filename.endsWith(".pdf"))
                filename += ".pdf";

            Path from = Paths.get(chooser.getSelectedFile().toURI());
            Path to = Paths.get("./resources/pdfs/" + filename);
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
            int id = coursewareDAO.getMaxIdOfMaterials();
            Material material = new Material(id, currFIle, subjectName, subjectType, 1);
            coursewareDAO.addNewMaterial(material);

        }
    }

    public void deleteMaterial(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete material");
        alert.setHeaderText("Are you sure you want delete " + currentMaterial.getNameMaterial());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            //coursewareDAO delete material

            System.out.println(currentMaterial.getType());
            if (currentMaterial.getType().equals("lab")) {
                coursewareDAO.deleteMaterial(currentMaterial);
                coursewareDAO = coursewareDAO.getInstance();
                fillLabTable();
                System.out.println("Da li si ovdje bio?");
                columnNameLab.setCellValueFactory(
                        new PropertyValueFactory<Material, String>("nameMaterial")
                );
            }
            if (currentMaterial.getType().equals("lecture")) {
                //System.out.println("Da li si ovdje bio?");
                coursewareDAO.deleteMaterial(currentMaterial);
                coursewareDAO = coursewareDAO.getInstance();

                fillLectureTable();
                // tableOfLectures.setItems(coursewareDAO.getLectures(subjectName));
                columnName.setCellValueFactory(
                        new PropertyValueFactory<Material, String>("nameMaterial")
                );
            }

            if (currentMaterial.getType() == "group") {
                coursewareDAO.deleteMaterial(currentMaterial);
                //System.out.println("Da li si ovdje bio?");
                coursewareDAO = coursewareDAO.getInstance();
                fillGroupTable();
                tableOfGroups.setItems(coursewareDAO.getGroups(subjectName));
                columnNameGroup.setCellValueFactory(
                        new PropertyValueFactory<Material, String>("nameMaterial")
                );
            }


        } else {
            alert.setHeaderText("Okay");
        }
    }

    public void refillTables() {
        ObservableList<Material> groups = FXCollections.observableArrayList();
        ObservableList<Material> labs = FXCollections.observableArrayList();
        ObservableList<Material> lectures = FXCollections.observableArrayList();
        tableOfGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfGroups.getSelectionModel().getSelectedItem();

        for (Material m : coursewareDAO.getGroups(subjectName)) {
            if (m.isVisible() == 1) {
                groupsForGuest.add(m);
            }
        }


        columnNameGroup.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfGroups.setItems(groupsForGuest);

        tableOfLabs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLabs.getSelectionModel().getSelectedItem();
        for (Material m : coursewareDAO.getLabs(subjectName)) {
            if (m.isVisible() == 1) {
                labsForGuest.add(m);
            }
        }

        columnNameLab.setCellValueFactory(new PropertyValueFactory<Material, String>("nameMaterial"));

        tableOfLabs.setItems(labsForGuest);

        tableOfLectures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLectures.getSelectionModel().getSelectedItem();

        for (Material m : coursewareDAO.getLectures(subjectName)) {
            if (m.isVisible() == 1) {
                lecturesForGuest.add(m);
            }
        }
        columnName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfLectures.setItems(lecturesForGuest);

    }

    public void error() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("ops, you are not a professor on this predmet!");
        alert.showAndWait();
    }

    public void browser(ActionEvent actionEvent) throws IOException, URISyntaxException {
        int courseId = coursewareDAO.getSubjectByName(subjectName).getId(); //main page of c2 courseware
        //if (coursewareDAO.getSubjectByName(subjectName).getId()!=1) courseId = LoginFormController.getCurrentUser().getId();
        //   if(LoginFormController.getCurrentUser().getId())
        //  if(CourseListController.getSubjectTitle().toLowerCase().equals("rpr"))courseId=49;

        URL url = new URL("https://c2.etf.unsa.ba/course/view.php?id=" + courseId);
        Desktop d = Desktop.getDesktop();

        // Browse a URL, C2 courseware, id of subject is id of subject on c2
        d.browse(new URI("https://c2.etf.unsa.ba/course/view.php?id=" + courseId));


    }

    public void saveXml(File file) {
        // Make a transformer factory to create the Transformer
        //creating xml file using dom parser
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = ((org.w3c.dom.Document) document).createElement(CourseListController.getSubjectTitle());
            ((org.w3c.dom.Document) document).appendChild(root);
            Element izdvojeni = null;
            Element elementi = null;
            for (Material m : coursewareDAO.getMaterialsBySubject(CourseListController.getSubjectTitle())) {
                elementi = document.createElement("id");
                root.appendChild(elementi);
                Attr attr = document.createAttribute("nameMaterial");
                elementi.setAttributeNode(attr);
                Element firstColumn = document.createElement("subject");
                elementi.appendChild(firstColumn);
                Element secondColumn = document.createElement("type");
                elementi.appendChild(secondColumn);
                Element thirdColumn = document.createElement("visible");
                elementi.appendChild(thirdColumn);
                attr.setValue(m.getId() + "");
                firstColumn.setTextContent(m.getSubject());
                secondColumn.setTextContent(m.getType());
                thirdColumn.setTextContent(m.isVisible() + "");
            }
            // create the xml file
            //transform the DOM Object to an XML File

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //disable 'INDENT' and set the indent amount for the transformer
            // transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "3");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            // disable 'INDENT' for testSave2
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Error!");
            alert.setContentText("File not found!");
            alert.showAndWait();
        }
    }

    public void openXml(File file) {
        Subject subject = new Subject();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodelist = doc.getDocumentElement().getChildNodes();
            if (!doc.getDocumentElement().getTagName().equals(CourseListController.getSubjectTitle()))
                throw new WrongChoiceException("ERROR!");
            for (int i = 0; i < nodelist.getLength(); i++) {
                Material material = new Material();
                Node d = nodelist.item(i);
                if (d instanceof Element) {
                    Element e = (Element) d;
                    material.setId(Integer.parseInt(e.getAttribute("id")));
                    NodeList podaci = e.getChildNodes();
                    for (int j = 0; j < podaci.getLength(); j++) {
                        Node podatak = podaci.item(j);
                        if (podatak instanceof Element) {
                            Element noviPodatak = (Element) podatak;
                            switch (noviPodatak.getTagName()) {
                                case "nameMaterial":
                                    material.setNameMaterial(noviPodatak.getTextContent());
                                    break;
                                case "subject":
                                    material.setSubject(noviPodatak.getTextContent());
                                    break;
                                case "type":
                                    material.setType(noviPodatak.getTextContent());
                                    break;
                                case "visible":
                                    material.setVisible(Integer.parseInt(noviPodatak.getTextContent()));
                                    break;
                                default:
                            }
                        }
                    }

                }
                coursewareDAO.addNewMaterial(material);

            }
        } catch (Exception | WrongChoiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Wrong format!");
            alert.setContentText("File not found!");
            alert.showAndWait();
        }


    }

    public void doOpen(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath() + "/xml");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setSelectedFile(new File(CourseListController.getSubjectTitle() + ".xml"));
        chooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
        if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().toString();
            if (!filename.endsWith(".xml"))
                filename += ".xml";
            openXml(chooser.getSelectedFile());
        }

    }

    public void doSave(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            // JFileChooser chooser= new JFileChooser();
            JFileChooser chooser = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath() + "/xml");
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setSelectedFile(new File(CourseListController.getSubjectTitle() + ".xml"));
            chooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
            //chooser.setCurrentDirectory(new File(String.valueOf(getClass().getResource("xml"))));
            if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().toString();
                if (!filename.endsWith(".xml"))
                    filename += ".xml";
                saveXml(chooser.getSelectedFile());
            }

        });
    }

    public void about(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            Parent root = loader.load();
            newStage.setTitle("Something about aplication");
            newStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
