package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;

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

    private void loadField(Student newValue) {
    }

    private void autoStudentId() {
        ResultSet result = CrudUtil.execute("SELECT member_id FROM member ORDER BY member_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("member_id");
            int i =id.length();

            String txt= id.substring(0,1);
            String num=id.substring(1,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtMemberId.setText(ftxt);
        }else{
            txtMemberId.setText("M1");
        }
    }
    }

    private void loadMembers() {
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
