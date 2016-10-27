import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class FileWorker {

    static void write(String fileName, String text) {//Определяем файл
        File file = new File(fileName);
        try {//проверяем, что если файл не существует то создаем его
            if (!file.exists()) {
                file.createNewFile();
            }//PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {//Записываем текст у файл
                out.print(text);
            } finally {//закрываем
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String read(String fileName, String encoding)
            throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(new File(fileName), encoding);
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

}
