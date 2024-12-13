import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class FileCopier {

    public static void copyFileNIO(String sourcePath, String destPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destPath);

        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл успешно скопирован с помощью NIO.2");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла с помощью NIO.2: " + e.getMessage());
        }
    }

    public static void copyFileChannel(String sourcePath, String destPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destPath);

        try (FileChannel srcChannel = FileChannel.open(source);
             FileChannel destChannel = FileChannel.open(destination, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            System.out.println("File copied successfully using FileChannel");
        } catch (IOException e) {
            System.err.println("Error copying file using FileChannel: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String sourceFile = "large_file.txt"; // Replace with your large file
        String destFile = "large_file_copy.txt";

        //Create a dummy large file for testing.  Comment out if a large file already exists
        try{
            byte[] bytes = new byte[1024 * 1024 * 100]; // 100MB dummy file
            Files.write(Paths.get(sourceFile), bytes);
        }catch (IOException e){
            System.err.println("Error creating dummy file: "+ e.getMessage());
        }


        //Using Files.copy (Recommended for large files)
        long startTime = System.currentTimeMillis();
        copyFileNIO(sourceFile, destFile);
        long endTime = System.currentTimeMillis();
        System.out.println("NIO.2 copy time: "+ (endTime-startTime) +" ms");

        //Using FileChannel (for comparison)
        startTime = System.currentTimeMillis();
        copyFileChannel(sourceFile, destFile + "_channel");
        endTime = System.currentTimeMillis();
        System.out.println("FileChannel copy time: "+ (endTime-startTime) +" ms");


    }
}