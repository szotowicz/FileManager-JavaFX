/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Mikołaj
 */
public class DisplayTree extends SimpleFileVisitor<Path>{
    
    private TreeView<Path> treeView;
    private TreeItem<Path> currentItem;
    
    public DisplayTree(TreeView<Path> tree) {
        treeView = tree;
        currentItem = null;
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        currentItem.getChildren().add(new TreeItem<Path>(file));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        TreeItem<Path> item = new TreeItem<Path>(dir);
        if (currentItem == null)
            treeView.setRoot(item);
        else
            currentItem.getChildren().add(item);
        
        currentItem = item;
        return FileVisitResult.CONTINUE;
    }
    
}
