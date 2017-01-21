/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Mikołaj
 */
public class Delete extends SimpleFileVisitor<Path>{
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        try {
            Files.deleteIfExists(file);
        } catch(IOException e) {
            System.err.println("You can not delete file: " + file.toString());
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        try {
            Files.deleteIfExists(dir);
        } catch(IOException e) {
            System.err.println("You can not delete direction: " + dir.toString());
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
