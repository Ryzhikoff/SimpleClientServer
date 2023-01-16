import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleServer {

    //1 Массив для всех клиентов
    private static ArrayList<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // создаем серверный сокет на порту 1234
        ServerSocket server = new ServerSocket(1234);
        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();

            // создаем клиента на своей стороне
            Client client = new Client(socket);
            //2 додбавляем в массив
            clients.add(client);

            System.out.printf("%s connected!", client.getName());
            sendToAll(client, "К чату присоединился: " + client.getName());
            // запускаем поток
            Thread thread = new Thread(client);
            thread.start();
        }
    }

    //6 Проходимся по списку всех клиентов, игнорирую того - кто отправил и вызываем метод preintMessage.
    public static void sendToAll (Client client, String message) {
        for (Client cl : clients) {
            if (cl.equals(client)) {
                continue;
            }
            cl.printMessage(message);
        }
    }
}
