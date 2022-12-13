package group15.ltm_moviesearcher.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Nhận kết nối từ client  
 * Tạo các thread
 * @author baoluan
 */
public class Server {
    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;
    private static int Port = 1312;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        serverThreadBus = new ServerThreadBus();
        int clientNumber = 0;

        // Mở một ServerSocket tại cổng 1312
        try {
            serverSocket = new ServerSocket(Port);
            System.out.println("Server binding at port " + Port);
            System.out.println("Waiting for client...");
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, // corePoolSize
                100, // maximumPoolSize
                10, // thread timeout
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );
        try {
            while (true) {
                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.
                socketOfServer = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Number of running thread: "+serverThreadBus.getLength());
                executor.execute(serverThread);
                
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
