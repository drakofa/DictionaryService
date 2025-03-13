import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private Map<String, String> dictionary = new HashMap<>();
    private static String filePath = "dictionary.txt";
    private static File file = new File(filePath);

    public static void fileReader() {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
        }
    }

    public void loadDictionary() {
        dictionary.clear(); // Очищаем перед загрузкой
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 2); // Разделяем строку по пробелу
                if (parts.length == 2) {
                    dictionary.put(parts[0], parts[1]); // Добавляем в HashMap
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    public boolean addEntry(String key, String value) {
        if ((filePath.equals("dictionary1.txt") && key.matches("[a-zA-Z]{4}")) ||
                (filePath.equals("dictionary2.txt") && key.matches("\\d{5}"))) {

            dictionary.put(key, value);
            saveToFile();
            return true;
        }
        return false;
    }
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }



}
