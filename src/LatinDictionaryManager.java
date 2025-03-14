
/// Только 4 латинские буквы
public class LatinDictionaryManager extends DictionaryManager {
    public LatinDictionaryManager(String filePath) {
        super(filePath);
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (key.matches("[a-zA-Z]{4}")) {
            dictionary.put(key, value);
            saveToFile();
            return true;
        }
        System.out.println("Ошибка! Ключ должен содержать 4 латинские буквы.");
        return false;
    }
}
