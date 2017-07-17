package services;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileVisitorA {
    static class myVisitor extends SimpleFileVisitor {
        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isDirectory()) System.out.println(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

    }
    public static ArrayList<String> listDirectory(String path) throws IOException {
        Files.walkFileTree(Paths.get(path).toAbsolutePath(), new myVisitor());
        ArrayList<String> list = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(Paths.get(path).toAbsolutePath())) {
            for (Path entry : entries)
                if (Files.isRegularFile(entry)){
                        list.add(entry.getFileName().toString());
                    }
                }
                    // System.out.printf("File \"%s\", size : %d\n", entry.getFileName(), Files.size(entry));
        return list;
    }
}
