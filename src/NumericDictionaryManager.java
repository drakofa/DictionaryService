///  Только 5 цифр
public class NumericDictionaryManager extends DictionaryManager {
    public NumericDictionaryManager(String filePath) {
        super(filePath);
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (key.matches("\\d{5}")) {
            dictionary.put(key, value);
            saveToFile();
            return true;
        }
        System.out.println("Ошибка! Ключ должен содержать 5 цифр.");
        return false;
    }
}
