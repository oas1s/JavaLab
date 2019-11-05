package users;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocketClient client = new SocketClient();
        client.startConnection("127.0.0.1", 7000);
        while (true) {
            String message = scanner.nextLine();
            client.sendMessage(message);
        }
    }
}
