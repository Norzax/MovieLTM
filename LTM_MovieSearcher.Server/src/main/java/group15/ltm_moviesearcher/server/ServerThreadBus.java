package group15.ltm_moviesearcher.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dùng để tạo các hàm xử lý dữ liệu và trả về client
 * @author baoluan
 */
class ServerThreadBus {
    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }
    
    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }
    
    public int getLength(){
        return listServerThreads.size();
    }
    
    public void remove(int id){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getClientNumber()==id){
                Server.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }
}
