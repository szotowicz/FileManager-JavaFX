/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

/**
 *
 * @author Mikołaj
 */
public class ManagerController implements Initializable {
    
    @FXML private TreeView<Path> treeView;
    @FXML private TextField fileNameField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        treeView.setCellFactory(new PathCellFactory());
    }

    @FXML protected void changeRootButtonAction(ActionEvent event) {
        try {
            DirectoryChooser dir = new DirectoryChooser();
            File file = dir.showDialog(null);
            try {
                DisplayTree tree = new DisplayTree(treeView);
                Files.walkFileTree(file.toPath(), tree);

            } catch (IOException e) {
                System.err.println("You can not change. Error: " + e);
            }
        } catch (Exception e) {
            System.err.println("Not selected root. Error: " + e);
        }
    }

    @FXML protected void deleteFileButtonAction(ActionEvent event) {
        TreeItem<Path> selected = treeView.getSelectionModel().getSelectedItem();

        try {
            Delete del = new Delete();
            Files.walkFileTree(selected.getValue(), del);
            
        } catch(IOException e) {
            System.err.println("You can not delete. Error: " + e);
        }

        if (selected.getParent()!= null)
            selected.getParent().getChildren().removeAll(selected);
        else
            treeView.setRoot(null);
    }
    
    @FXML protected void createFileButtonAction(ActionEvent event) {
        try {
            TreeItem<Path> selected = treeView.getSelectionModel().getSelectedItem();
            String fileName = fileNameField.textProperty().get();

            if(!fileName.isEmpty()) {
                try {
                    Path p = Paths.get(selected.getValue().toString(), fileName);
                    Files.createFile(p);
                    TreeItem newFile = new TreeItem<Path>(p);
                    selected.getChildren().add(newFile);
                    fileNameField.textProperty().set(null);
                } catch (IOException e) {
                    System.err.println("You can not create file. Error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Not selected root. You can not create file. Error: " + e);
        }
    }
    
    @FXML protected void createDirButtonAction(ActionEvent event) {
        try {
            TreeItem<Path> selected = treeView.getSelectionModel().getSelectedItem();
            String fileName = fileNameField.textProperty().get();

            if(!fileName.isEmpty()) {
                try {
                    Path p = Paths.get(selected.getValue().toString(), fileName);
                    Files.createDirectory(p);
                    TreeItem newDirectory = new TreeItem<Path>(p);
                    selected.getChildren().add(newDirectory);
                    fileNameField.textProperty().set(null);
                } catch (IOException e) {
                    System.err.println("You can not create directory. Error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Not selected root. You can not create directory. Error: " + e);
        }
    }

    private class PathCell extends TreeCell<Path> {

        @Override
        protected void updateItem(Path file, boolean empty) {
            super.updateItem(file, empty);
            if (file != null)
                setText(file.getFileName().toString());
            else
                setText(null);
        }
        
    }

    private class PathCellFactory implements Callback<TreeView<Path>, TreeCell<Path>> {
        @Override
        public TreeCell<Path> call(TreeView<Path> p) {
            return new PathCell();
        }
    }
    
}
