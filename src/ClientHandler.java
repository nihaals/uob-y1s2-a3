import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    Socket socket;
    int clientId;
    Database db;

    public ClientHandler(Socket socket, int clientId, Database db) {
        this.socket = socket;
        this.clientId = clientId;
        this.db = db;
    }

    public void run() {
        System.out.println("ClientHandler started...");
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            String clientMessage;
            while (true) {
                clientMessage = inFromClient.readLine();
                if (clientMessage == null || clientMessage.equals("stop")) {
                    break;
                }
                System.out.println("Client sent the artist name " + clientMessage);
                int titlesNum = db.getTitles(clientMessage);
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            System.out.println("Client " + clientId + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");
            inFromClient.close();
            outToClient.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
