import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    //3 PrintStream Для доступа в Поле класса
    private PrintStream out;
    //4 Имя для понимания кто пишет
    private String name;
    private static int count = 1;

    public String getName() { return name;}

    public Client(Socket socket){
        this.socket = socket;
        name = "Client " + count++;
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            Scanner in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to mountains!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                out.println("me> " + input);

                //5 Отправляем в статический метод SimpleServer`a наше сообщение
                SimpleServer.sendToAll(this, name + "> " + input);

                input = in.nextLine();
            }

            //9 Сообщяем что этот клиент покинул чат
            SimpleServer.sendToAll(this, "Чат покинул: " + name);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //7 отправляем в поток наше сообщение
    public void printMessage(String message) {
        out.println(message);
    }


}