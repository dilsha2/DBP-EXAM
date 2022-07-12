package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        autoStudentId();

        //tblAddMember.setItems();
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

    private void autoStudentId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT student_id FROM Student ORDER BY student_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("student_id");
            int i =id.length();

            String txt= id.substring(0,1);
            String num=id.substring(1,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtStudentId.setText(ftxt);
        }else{
            txtStudentId.setText("M1");
        }
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
    }

    public void addOnAction(ActionEvent actionEvent) {
        

    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
    }
}
