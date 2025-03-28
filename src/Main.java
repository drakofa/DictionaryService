import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String dictionaryFilePathLatin = "dictionary1.txt";
        String dictionaryFilePathNumber = "dictionary2.txt";

        //D:\lab_work\directoryTest\dictionary.txt
        //D:\lab_work\directoryTest\dictionary2.txt

        System.out.println("Выберите словарь:");
        System.out.println("1 - Латинские буквы (4 символа)");
        System.out.println("2 - Числа (5 символов)");
        System.out.print("> ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Чистим буфер ввода

        DictionaryManager manager;
        if (choice == 1) {
            manager = new LatinDictionaryManager(dictionaryFilePathLatin);
        } else {
            manager = new NumericDictionaryManager(dictionaryFilePathNumber); // исправлено
        }

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Показать все записи");
            System.out.println("2 - Найти слово");
            System.out.println("3 - Добавить слово");
            System.out.println("4 - Удалить слово");
            System.out.println("5 - Выход");
            System.out.println("6 - Изменить путь к файлу");
            System.out.print("> ");

            int action = scanner.nextInt();
            scanner.nextLine(); // Чистим буфер

            switch (action) {
                case 1:
                    manager.printDictionary();
                    break;
                case 2:
                    System.out.print("Введите ключ: ");
                    String searchKey = scanner.nextLine();
                    System.out.println("Перевод: " + manager.search(searchKey));
                    break;
                case 3:
                    System.out.print("Введите ключ: ");
                    String key = scanner.nextLine();
                    System.out.print("Введите перевод: ");
                    String value = scanner.nextLine();
                    if (manager.addEntry(key, value)) {
                        System.out.println("Запись добавлена!");
                    }
                    break;
                case 4:
                    System.out.print("Введите ключ для удаления: ");
                    String delKey = scanner.nextLine();
                    if (manager.removeEntry(delKey)) {
                        System.out.println("Запись удалена.");
                    } else {
                        System.out.println("Ключ не найден.");
                    }
                    break;
                case 5:
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                case 6:
                    System.out.println("Введите новый путь к файлу:");
                    String newPath = scanner.nextLine();
                    manager.updateFilePath(newPath);

                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }
}
