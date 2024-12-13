import java.io.*;

public class InOut {
    public static void processFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String upperCaseLine = line.toUpperCase();
                writer.write(upperCaseLine);
                writer.newLine();
            }
            System.out.println("Файл успешно обработан!");

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Входной файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка во время выполнеия файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\makar\\OneDrive\\Рабочий стол\\input.txt";
        String outputFile = "C:\\Users\\makar\\OneDrive\\Рабочий стол\\output.txt";
        processFile(inputFile, outputFile);
    }
}