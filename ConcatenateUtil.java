import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ConcatenateUtil {

    public List<File> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
        List<File> fileList = new ArrayList<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!Files.isDirectory(file)) {
                    File theFile = file.toFile();
                    String absolutePath = theFile.getAbsolutePath();
                    if(absolutePath.endsWith(".java")) {
                        fileList.add(theFile);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }

    public static void main(String[] args) throws IOException {

        String dir = "L:/BaiduYunDownload/4.5_玩转算法面试-Leetcode真题分门别类讲解/coding-82/print/";

        String outputFile = "L:/BaiduYunDownload/4.5_玩转算法面试-Leetcode真题分门别类讲解/coding-82/coding-82.src.java";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputFile), "utf-8"))) {

            ConcatenateUtil util = new ConcatenateUtil();
            List<File> fileList = util.listFilesUsingFileWalkAndVisitor(dir);
            for(File oneFile: fileList) {
                System.out.println(oneFile.getAbsolutePath());
                byte[] encoded = Files.readAllBytes(oneFile.toPath());
                String content = new String(encoded, StandardCharsets.UTF_8);

                writer.write(oneFile.getAbsolutePath());
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
                writer.write(content);
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
                writer.flush();
            }
        }

    }
}
