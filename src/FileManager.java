import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private String filePath;

    public String getFilePath()
    {
        return filePath;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public void startWorking() {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.out.println("Do you want to create a new file? (y/n)");

            Scanner scanner = new Scanner(System.in);
            String yesOrNo = scanner.nextLine();

            if (yesOrNo.equalsIgnoreCase("y")) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created successfully: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to create file.");
                    }
                } catch (IOException e) {
                    System.out.println("Error creating file: " + e.getMessage());
                }
            } else {
                System.out.println("Operation cancelled.");
            }
        } else {
            System.out.println("File exists: " + file.getAbsolutePath());
        }
    }
}
