import java.io.*;
import java.net.*;
public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public void start(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread readThread = new Thread(this::readFromServer);
            readThread.start();
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String inputLine;
            while ((inputLine = consoleInput.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void readFromServer() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start("localhost", 12345);
    }
}
