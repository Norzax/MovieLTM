package group15.ltm_moviesearcher.server;

import group15.ltm_moviesearcher.dto.MovieDetail;
import group15.ltm_moviesearcher.dto.MovieResponse;
import group15.ltm_moviesearcher.dto.MovieFuture;
import group15.ltm_moviesearcher.dto.MovieServices;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *  Dùng để gọi các function để xử lý dữ liệu và trả về client
 * @author baoluan
 */
class ServerThread implements Runnable{
    private Socket socketOfServer;
    private int clientNumber;
    private int clientPort;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;

    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started!");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream(),StandardCharsets.UTF_8));
            System.out.println("New Thread started successful, client ID : " + clientNumber);
            String message;
            while (!isClosed) {
                // Đọc dữ liệu từ client
                message = is.readLine();
                String[] str = message.split(" ");
                if(str[0].equals("Start")){
                    ArrayList<MovieResponse> result = MovieServices.getAllMovie();
                    String data = "";
                    for(int i = 0 ;i<result.size();i++){
                        data += result.get(i).toString();
                        data += "     ";
                    }
                    write(data);
                } if (str[0].equals("getDetail")){
                    ArrayList<MovieDetail> result = MovieServices.getMovieDetail(str[2]);
                    String data = "";
                    for(int i = 0 ;i<result.size();i++){
                        data += result.get(i).toString();
                        data += "     ";
                        System.out.println(result.get(i).toString());
                    }
                    write(data);
                } else if (str[0].equals("getFutureMovie")){
                    ArrayList<MovieFuture> result = MovieServices.getAllMovieFuture();
                    String data = "";
                    for(int i = 0 ;i<result.size();i++){
                        data += result.get(i).toString();
                        data += "     ";
                    }
                    write(data);
                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println("Client with ID " + this.clientNumber + " is out!");
            System.out.println(Server.serverThreadBus.getLength());
        }
    }
    
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
}
