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

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }

        String marker = getDictionaryTypeMarker();
        if (marker == null) {
            // Если файл пуст, устанавливаем метку
            setDictionaryTypeMarker(getDefaultMarker());
        }

        loadDictionary();
    }

    // Метод для получения стандартной метки словаря
    protected abstract String getDefaultMarker();

    ///загрузка в hashmap
    public void loadDictionary() {
        dictionary.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String marker = br.readLine(); // Читаем первую строку как метку

            if (marker == null || marker.trim().isEmpty()) {
                setDictionaryTypeMarker(getDefaultMarker()); // Устанавливаем метку, если её нет
                marker = getDictionaryTypeMarker();
            }

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



    /// Вывод всех записей
    public void printDictionary() {
        if (dictionary.isEmpty()) {
            System.out.println("Словарь пуст.");
        } else {
            dictionary.forEach((key, value) -> System.out.println(key + " - " + value));
        }
    }

    /// Поиск перевода по ключу
    public String search(String key) {
        return dictionary.getOrDefault(key, "Слово не найдено");
    }


    public abstract boolean addEntry(String key, String value)
    ;

    /// Удаление записи по ключу
    public boolean removeEntry(String key) {
        if (dictionary.containsKey(key)) {
            dictionary.remove(key);
            saveToFile();
            return true;
        }
        return false;
    }

    /// Метод для установки метки словаря в файле
    void setDictionaryTypeMarker(String marker) {
        try {
            // Читаем существующие данные
            StringBuilder content = new StringBuilder();
            boolean firstLine = true;

            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (firstLine) {
                            firstLine = false; // Пропускаем первую строку (старую метку)
                            continue;
                        }
                        content.append(line).append("\n");
                    }
                }
            }

            // Перезаписываем файл с новой меткой
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                bw.write(marker);
                bw.newLine();
                bw.write(content.toString());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при установке типа словаря: " + e.getMessage());
        }
    }

    /// Метод для получения метки словаря
    String getDictionaryTypeMarker() {
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String marker = br.readLine();
            return (marker != null && !marker.trim().isEmpty()) ? marker : null;
        } catch (IOException e) {
            System.out.println("Ошибка при чтении типа словаря: " + e.getMessage());
            return null;
        }
    }



    /// Сохранение всех изменений в файл
    protected void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            // 1️⃣ Сначала записываем метку словаря
            String marker = getDictionaryTypeMarker();
            if (marker == null) {
                marker = getDefaultMarker(); // Если маркера нет, устанавливаем стандартный
                setDictionaryTypeMarker(marker);
            }
            bw.write(marker);
            bw.newLine();

            // 2️⃣ Теперь записываем все ключи и значения
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    protected void updateFilePath(String newFilePath) {
        File newFile = new File(newFilePath);

        if (!newFile.exists()) {
            try {
                if (newFile.createNewFile()) {
                    System.out.println("Файл создан: " + newFilePath);
                    // Устанавливаем метку при создании файла
                    this.file = newFile;
                    setDictionaryTypeMarker(getDefaultMarker());
                } else {
                    System.out.println("Ошибка: не удалось создать файл!");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
                return;
            }
        } else {
            this.file = newFile;
        }

        this.filePath = newFilePath;
        loadDictionary(); // Перезагружаем словарь после изменения пути
    }


}
