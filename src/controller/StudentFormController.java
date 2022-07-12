package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StudentFormController {
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public JFXTextField txtSearch;
    public TableView <Student> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;




    public void initialize() throws SQLException, ClassNotFoundException {



        colStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("studentName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNic.setCellValueFactory(new PropertyValueFactory("nic"));

        loadMembers();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadField(newValue);
            } else {
                clear();
            }
        });
    }

    private void clear() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtNic.clear();
        tblStudent.refresh();
    }

    private void loadField(Student s) {
        txtStudentId.setText(s.getStudentId());
        txtStudentName.setText(s.getStudentName());
        txtEmail.setText(s.getEmail());
        txtContact.setText(s.getContact());
        txtAddress.setText(s.getAddress());
        txtNic.setText(s.getNic());
    }


    private void loadMembers() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Student");
        while (resultSet.next()){
            observableList.add(
                    new Student(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        tblStudent.setItems(observableList);

        tblStudent.setItems(observableList);

        FilteredList<Student> filterData = new FilteredList<Student>(observableList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (Student.getStudentId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;


            });
        });

        SortedList<Student> sortedData = new SortedList<>(filterData);

        sortedData.comparatorProperty().bind(tblStudent.comparatorProperty());

        tblStudent.setItems(sortedData);
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student s = new Student(
                txtStudentId.getText(),txtStudentName.getText(),txtEmail.getText(),txtContact.getText(),
                txtAddress.getText(),txtNic.getText()
        );
        try {
            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",s.getStudentId(),s.getStudentName(),s.getEmail(),
                    s.getContact(),s.getAddress(),s.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadMembers();
        clear();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES,ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            CrudUtil.execute("DELETE FROM Student WHERE Student_id=?",txtStudentId.getText());

            loadMembers();
            clear();

        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student s= new Student(
                txtStudentId.getText(),txtStudentName.getText(),txtEmail.getText(),txtContact.getText(),
                txtAddress.getText(),txtNic.getText()
        );

        try {
            boolean isUpdated=CrudUtil.execute("UPDATE Student SET student_name=?, email=?, contact=?, address=?, nic=? WHERE student_id=? ", s.getStudentName(), s.getEmail(), s.getContact(),
                    s.getAddress(),s.getNic(),s.getStudentId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }


        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadMembers();
        clear();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clear();
    }

}
