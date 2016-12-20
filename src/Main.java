import java.io.*;
import java.util.Scanner;

import static java.nio.file.Files.exists;

public class Main {

    public static void main(String[] args) {
        String fileKey = "D:\\dev\\CMAC\\K.txt";
        String fileVector = "D:\\dev\\CMAC\\V.txt";
        String fileMessage = "D:\\dev\\CMAC\\M.txt";
        String fileEncrypted = "D:\\dev\\CMAC\\E.txt";
        String fileDecrypted = "D:\\dev\\CMAC\\DE.txt";
        String encoding = "UTF8";
        String message = "";
        String key = "";
        String vector = "";

        try {//считываем с файлов ключ, вектор, сообщение
            message = FileWorker.read(fileMessage, encoding);
            key = FileWorker.read(fileKey, encoding);
            vector = FileWorker.read(fileVector, encoding);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        EncryptionModes enm = new EncryptionModes(message, key, vector, vector.length());//инициализация обьекта
        System.out.println(message);
        enm.EncodCBC();//шифрование
        System.out.println(new String(enm.encryptedMessageByte));
        FileWorker.write(fileEncrypted, new String(enm.encryptedMessageByte));
        String decod = new String(enm.decoderCBC(new String(enm.encryptedMessageByte), vector));
        decod = decod.trim();//расшифрование
        FileWorker.write(fileDecrypted, decod);//запись зашифрованого строкой
        System.out.println(decod);
        System.out.println(" ");
        System.out.println(message.equals(decod));

    }

}
