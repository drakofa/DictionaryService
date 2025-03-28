public class LatinDictionaryManager extends DictionaryManager {
    public LatinDictionaryManager(String filePath) {
        super(filePath);
    }

    @Override
    protected String getDefaultMarker() {
        return "L";
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (!"L".equals(getDictionaryTypeMarker())) {
            System.out.println("Ошибка! Этот файл не предназначен для латинского словаря.");
            return false;
        }
        if (key.matches("[a-zA-Z]{4}")) {
            dictionary.put(key, value);
            saveToFile();
            return true;
        }
        System.out.println("Ошибка! Ключ должен содержать 4 латинские буквы.");
        return false;
    }
}
