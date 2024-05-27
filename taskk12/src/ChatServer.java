import java.io.*;
import java.net.*;
public class ChatServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client to connect...");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread readThread = new Thread(this::readFromClient);
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
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}
    private void readFromClient() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client: " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } }
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(12345);
    }
}

