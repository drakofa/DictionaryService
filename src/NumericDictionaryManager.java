public class NumericDictionaryManager extends DictionaryManager {
    public NumericDictionaryManager(String filePath) {
        super(filePath);
    }

    @Override
    protected String getDefaultMarker() {
        return "N";
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (!"N".equals(getDictionaryTypeMarker())) {
            System.out.println("Ошибка! Этот файл не предназначен для числового словаря.");
            return false;
        }
        if (key.matches("\\d{5}")) {
            dictionary.put(key, value);
            saveToFile();
            return true;
        }
        System.out.println("Ошибка! Ключ должен содержать 5 цифр.");
        return false;
    }
}
