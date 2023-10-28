import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Phonebook {
    public static void main(String[] args) {
        Map<String, String> phoneBook = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("exit")) {

            try {
                BufferedReader reader = new BufferedReader(new FileReader("phonebook.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    String lastName = parts[0];
                    String phoneNumber = parts[1];
                    phoneBook.put(lastName, phoneNumber);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Ошибка при чтении данных из файла.");
            }

            System.out.println("Телефонная книга:");
            printPhoneBookDescending(phoneBook);

            while (!command.equals("exit")) {
                System.out.println(
                        "Выберите команду: (add)добавить данные, (del)удалить данные, (num)найти номера по фамилии, (sur)найти фамилию, (save)сохранить, (exit)выход");
                command = scanner.nextLine();

                switch (command) {
                    case "add":
                        System.out.println("Введите фамилию:");
                        String lastName = scanner.nextLine();
                        System.out.println("Введите номер телефона:");
                        String phoneNumber = scanner.nextLine();
                        if (phoneBook.containsKey(lastName)) {
                            String existingPhoneNumber = phoneBook.get(lastName);
                            phoneNumber = existingPhoneNumber + ", " + phoneNumber;
                        }
                        phoneBook.put(lastName, phoneNumber);
                        System.out.println("Данные добавлены в телефонную книгу.");
                        break;
                    case "del":
                        System.out.println("Введите фамилию для удаления:");
                        lastName = scanner.nextLine();
                        if (phoneBook.containsKey(lastName)) {
                            phoneBook.remove(lastName);
                            System.out.println("Данные удалены из телефонной книги.");
                        } else {
                            System.out.println("Данные с такой фамилией не найдены.");
                        }
                        break;
                    case "num":
                        System.out.println("Введите фамилию для поиска номера:");
                        lastName = scanner.nextLine();
                        if (phoneBook.containsKey(lastName)) {
                            phoneNumber = phoneBook.get(lastName);
                            System.out.println("Номер телефона: " + phoneNumber);
                        } else {
                            System.out.println("Данные с такой фамилией не найдены.");
                        }
                        break;
                    case "sur":
                        System.out.println("Введите номер телефона для поиска фамилии:");
                        phoneNumber = scanner.nextLine();
                        boolean found = false;
                        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                            if (entry.getValue().contains(phoneNumber)) {
                                lastName = entry.getKey();
                                System.out.println("Фамилия: " + lastName);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Данные с таким номером телефона не найдены.");
                        }
                        break;

                    case "save":
                        try {
                            FileWriter writer = new FileWriter("phonebook.txt");
                            for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                            }
                            writer.close();
                            System.out.println("Данные сохранены в Телефонной книге.");
                        } catch (IOException e) {
                            System.out.println("Ошибка при сохранении данных.");
                        }
                        break;
                    case "exit":
                        System.out.println("Выход из программы.");
                        break;
                    default:
                        System.out.println("Неверная команда, попробуйте еще раз.");
                        break;
                }
            }

            try {
                BufferedReader reader = new BufferedReader(new FileReader("phonebook.txt"));
                String line;
                System.out.println("Телефонная книга:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Ошибка при чтении данных из файла.");
            }
        }
    }

    private static void printPhoneBookDescending(Map<String, String> phoneBook) {
        List<Map.Entry<String, String>> entries = new ArrayList<>(phoneBook.entrySet());

        entries.sort((e1, e2) -> {
            String[] phoneNumbers1 = e1.getValue().split(",");
            String[] phoneNumbers2 = e2.getValue().split(",");

            if (phoneNumbers1.length > phoneNumbers2.length) {
                return -1;
            } else if (phoneNumbers1.length < phoneNumbers2.length) {
                return 1;
            } else {

                int compare = phoneNumbers2[0].compareTo(phoneNumbers1[0]);
                if (compare != 0) {
                    return compare;
                }

                for (int i = 1; i < phoneNumbers1.length; i++) {
                    compare = phoneNumbers2[i].compareTo(phoneNumbers1[i]);
                    if (compare != 0) {
                        return compare;
                    }
                }
            }
            return 0;
        });

        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}