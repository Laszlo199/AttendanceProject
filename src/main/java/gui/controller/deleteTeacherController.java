package gui.controller;

import be.Subject;
import be.Teacher;
import gui.model.AdminModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class deleteTeacherController implements Initializable {


    @FXML private TableView<Subject> tableOfViewSubjects;

    @FXML private TableColumn<Subject, String> columnName;

    @FXML private TableColumn<Subject, Integer> columnID;
    @FXML private TableColumn<Subject, Integer> columnSubjectID;
    @FXML private TableColumn<Subject, Integer> SubjectColumnCourseID;
    @FXML private TextField teacherIDField;
    @FXML private TextField lblTeacherName;
    @FXML private TextField lblTeacherID;

    private Teacher selectedTeacher;
    private AdminModel adminModel2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminModel2 = new AdminModel();
        initSubjectTableView();


    }

    public void initTeacher(Teacher teacher){
       selectedTeacher = teacher;
        lblTeacherID.setText(String.valueOf(selectedTeacher.getId()));
        lblTeacherName.setText(selectedTeacher.getName());
    }


    private void initSubjectTableView(){
        columnSubjectID.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<Subject,String>("Name"));
        columnID.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("teacherId"));
        //SubjectColumnCourseID.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("Course ID"));

        adminModel2.loadSubjects();
        tableOfViewSubjects.setItems(adminModel2.getAllSubjects());



    }

    public void btnSubjectEdit(ActionEvent event) {
        Subject newSubject = tableOfViewSubjects.getSelectionModel().getSelectedItem();
        newSubject.setTeacherId(Integer.parseInt(teacherIDField.getText()));

        adminModel2.updateSubject(tableOfViewSubjects.getSelectionModel().getSelectedItem(),newSubject);
    }

    public void readSubject(MouseEvent mouseEvent) {
        teacherIDField.setText(String.valueOf(tableOfViewSubjects.getSelectionModel().getSelectedItem().getTeacherId()));

    }

    public void deleteTeacher(ActionEvent actionEvent) {
        int i = 0;
        int teacherToDelete = Integer.parseInt(lblTeacherID.getText());
        ObservableList<Teacher> allTeachers = adminModel2.getAllTeachers();

        while (i<allTeachers.size() && allTeachers.get(i).getId() != teacherToDelete)
            i++;


        if (i<allTeachers.size()){
            Teacher temp = new Teacher(allTeachers.get(i).getId(),
                                        allTeachers.get(i).getName(),
                                        allTeachers.get(i).getEmail(),
                                        allTeachers.get(i).getPhotoPath(),
                                        allTeachers.get(i).getDepartment()
                    );
            adminModel2.delete(temp);
        }




    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent tableVewParent = FXMLLoader.load(getClass().getResource("/AdminView.fxml"));
        Scene scene = new Scene(tableVewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
