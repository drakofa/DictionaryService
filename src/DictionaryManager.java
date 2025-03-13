import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class DictionaryManager {
    protected Map<String, String> dictionary = new HashMap<>();
    protected String filePath;
    protected File file;

    public DictionaryManager(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
        loadDictionary();
    }

    // Загружаем данные в HashMap
    public void loadDictionary() {
        dictionary.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    dictionary.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    // Вывод всех записей
    public void printDictionary() {
        if (dictionary.isEmpty()) {
            System.out.println("Словарь пуст.");
        } else {
            dictionary.forEach((key, value) -> System.out.println(key + " - " + value));
        }
    }

    // Поиск перевода по ключу
    public String search(String key) {
        return dictionary.getOrDefault(key, "Слово не найдено");
    }

    // Метод addEntry() будет разным в потомках (делаем его абстрактным)
    public abstract boolean addEntry(String key, String value);

    // Удаление записи по ключу
    public boolean removeEntry(String key) {
        if (dictionary.containsKey(key)) {
            dictionary.remove(key);
            saveToFile();
            return true;
        }
        return false;
    }

    // Сохранение всех изменений в файл
    protected void saveToFile() {
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
