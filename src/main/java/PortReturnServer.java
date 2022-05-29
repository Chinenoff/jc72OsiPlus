import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PortReturnServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        log("Server start");

        try {
            while (true) {
                //  Ждем подключения клиента и получаем потоки для дальнейшей работы
                Socket clientSocket = serverSocket.accept();
                handle(clientSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            serverSocket.close();
        }
    }

    private static void handle(Socket socket) {
        log("client connected: " + socket.getRemoteSocketAddress());
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            String nameUser;
            if (socket.isConnected()) out.println(String.format("Write your name"));
            nameUser = in.readLine();
            System.out.println(nameUser);
            out.println(String.format("Are you child? (yes/no)"));
            msg = in.readLine();
            switch (msg) {
                case ("yes"):
                    out.println("Welcome to the kids area, " + nameUser + " Let's play!");
                    break;
                case ("no"):
                    out.println("Welcome to the adult zone, " + nameUser + "! Have a good rest, or a good working day!");
                    break;
                default:
                    out.println("Access is denied");
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
