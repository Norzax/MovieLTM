package group15.ltm_moviesearcher.client;

import chrriis.dj.nativeswing.swtimpl.*;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author baoluan
 */
public class Client extends JFrame{
    private BufferedWriter sendToServer;
    private BufferedReader inFromServer;
    private Socket socketOfClient;
    
    public Client() throws IOException {
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setUpSocket();
        showAllMovie();
    }
    
    private void initComponents(){
        this.setTitle("Tra cứu lịch chiếu phim");
        ImageIcon image = new ImageIcon(".../LTM_Project/src/main/java/icon/sgu.png");
        this.setIconImage(image.getImage());
        this.setSize(1366,768);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(266,768));
        leftPanel.setBackground(new Color(233,233,233));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0,4,0,10));
        rightPanel.setBackground(Color.BLACK);

        this.add(leftPanel,BorderLayout.WEST);
        this.add(BorderLayout.CENTER, new JScrollPane(rightPanel));
        
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void setUpSocket(){
        try {
            //Gửi yêu cầu kết nối tới Server đang lắng nghe
            // trên máy 'localhost' cổng 1312.
            socketOfClient = new Socket("localhost", 1312);
            System.out.println("Connected successful!");
            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
            sendToServer = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
            inFromServer = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream(),StandardCharsets.UTF_8));
            sendStartRequest();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showAllMovie() throws IOException{
        String result = inFromServer.readLine();
        //movieName
        //imgUrl
        //idMovie
        //dateRelease
        //urlDetail
        
        JLabel lbshowing = new JLabel("Phim đang chiếu");
        lbshowing.setSize(new Dimension(266,100));
        lbshowing.setForeground(Color.WHITE);
        lbshowing.setBackground(Color.BLACK);
        lbshowing.setOpaque(true);
        lbshowing.setBorder(new EmptyBorder(20,34,20,34));
        lbshowing.setFont(new Font("",Font.BOLD,25));
        
        JLabel lbfuture = new JLabel("Phim sắp chiếu");
        lbfuture.setSize(new Dimension(266,100));
        lbfuture.setForeground(Color.BLACK);
        lbfuture.setBackground(Color.WHITE);
        lbfuture.setOpaque(true);
        lbfuture.setBorder(new EmptyBorder(20,42,20,42));
        lbfuture.setFont(new Font("",Font.BOLD,25));
        
        JLabel lbshowdate = new JLabel("Lịch chiếu");
        lbshowdate.setSize(new Dimension(266,100));
        lbshowdate.setForeground(Color.BLACK);
        lbshowdate.setBackground(Color.WHITE);
        lbshowdate.setOpaque(true);
        lbshowdate.setBorder(new EmptyBorder(20,71,20,71));
        lbshowdate.setFont(new Font("",Font.BOLD,25));
        
        JLabel lbcinema = new JLabel("Rạp");
        lbcinema.setSize(new Dimension(266,100));
        lbcinema.setForeground(Color.BLACK);
        lbcinema.setBackground(Color.WHITE);
        lbcinema.setOpaque(true);
        lbcinema.setBorder(new EmptyBorder(20,109,20,109));
        lbcinema.setFont(new Font("",Font.BOLD,25));
        
        leftPanel.add(lbshowing);
        leftPanel.add(lbfuture);
        leftPanel.add(lbshowdate);
        leftPanel.add(lbcinema);
        leftPanel.revalidate();
        
        String[] data = result.split("     ");
        for(int i= 0 ;i<data.length;i++){
            String[] perData = data[i].split("movieResponse");
            JPanel panel = new JPanel();
            JLabel lbimg = new JLabel();
            JLabel lbname = new JLabel();
            JLabel lbdate = new JLabel();
            
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            URL url = new URL(perData[1]);
            Image image = ImageIO.read(url);
            Image imageResize = image.getScaledInstance(230, 300,  java.awt.Image.SCALE_SMOOTH);

            panel.setPreferredSize(new Dimension(260, 360));
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.setBackground(Color.black);
            panel.setBorder(new EmptyBorder(10, 10, 10 , 10));

            lbimg = new JLabel();
            lbimg.setSize(new Dimension(260, 30));
            lbimg.setIcon(new ImageIcon(imageResize));

            String urlDetail = perData[4];
            String idMovie = perData[2];
            lbimg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("OK " + idMovie);
                    String request = "getDetail "+urlDetail+" "+idMovie;
                    try {
                        write(request);
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        showDetailPerMovie();
                    } catch (IOException | NoPlayerException | CannotRealizeException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            lbshowing.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    lbshowing.setForeground(Color.WHITE);
                    lbshowing.setBackground(Color.BLACK);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    lbshowing.setForeground(Color.WHITE);
                    lbshowing.setBackground(Color.BLACK);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                }


                @Override
                public void mouseReleased(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.WHITE);
                    lbfuture.setBackground(Color.BLACK);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    lbshowing.setForeground(Color.WHITE);
                    lbshowing.setBackground(Color.BLACK);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(rightPanel.isVisible()){
                        lbshowing.setForeground(Color.WHITE);
                        lbshowing.setBackground(Color.BLACK);
                        lbfuture.setForeground(Color.BLACK);
                        lbfuture.setBackground(Color.WHITE);
                        lbcinema.setForeground(Color.BLACK);
                        lbcinema.setBackground(Color.WHITE);
                        lbshowdate.setForeground(Color.BLACK);
                        lbshowdate.setBackground(Color.WHITE);
                    }
                }
            });

            lbfuture.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.WHITE);
                    lbfuture.setBackground(Color.BLACK);
                    try {
                        write("getFutureMovie");
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.WHITE);
                    lbfuture.setBackground(Color.BLACK);
                }


                @Override
                public void mouseReleased(MouseEvent e) {
                    lbfuture.setForeground(Color.WHITE);
                    lbfuture.setBackground(Color.BLACK);
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.WHITE);
                    lbfuture.setBackground(Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(rightPanel.isVisible()){
                        lbshowing.setForeground(Color.WHITE);
                        lbshowing.setBackground(Color.BLACK);
                        lbfuture.setForeground(Color.BLACK);
                        lbfuture.setBackground(Color.WHITE);
                    }
                }
            });
            
            lbshowdate.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbshowdate.setForeground(Color.WHITE);
                    lbshowdate.setBackground(Color.BLACK);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbshowdate.setForeground(Color.WHITE);
                    lbshowdate.setBackground(Color.BLACK);
                }


                @Override
                public void mouseReleased(MouseEvent e) {
                    lbshowdate.setForeground(Color.WHITE);
                    lbshowdate.setBackground(Color.BLACK);
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbshowdate.setForeground(Color.WHITE);
                    lbshowdate.setBackground(Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(rightPanel.isVisible()){
                        lbshowing.setForeground(Color.WHITE);
                        lbshowing.setBackground(Color.BLACK);
                        lbshowdate.setForeground(Color.BLACK);
                        lbshowdate.setBackground(Color.WHITE);
                    }
                }
            });
            
            lbcinema.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbcinema.setForeground(Color.WHITE);
                    lbcinema.setBackground(Color.BLACK);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbcinema.setForeground(Color.WHITE);
                    lbcinema.setBackground(Color.BLACK);
                }


                @Override
                public void mouseReleased(MouseEvent e) {
                    lbcinema.setForeground(Color.WHITE);
                    lbcinema.setBackground(Color.BLACK);
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    lbshowing.setForeground(Color.BLACK);
                    lbshowing.setBackground(Color.WHITE);
                    lbfuture.setForeground(Color.BLACK);
                    lbfuture.setBackground(Color.WHITE);
                    lbcinema.setForeground(Color.WHITE);
                    lbcinema.setBackground(Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(rightPanel.isVisible()){
                        lbshowing.setForeground(Color.WHITE);
                        lbshowing.setBackground(Color.BLACK);
                        lbcinema.setForeground(Color.BLACK);
                        lbcinema.setBackground(Color.WHITE);
                    }
                }
            });
            
            lbname.setText(perData[0]);
            lbname.setPreferredSize(new Dimension(260,10));
            lbdate.setFont(new Font("",Font.BOLD,15));
            lbname.setForeground(Color.WHITE);
            
            lbdate.setText(perData[3]);
            lbdate.setPreferredSize(new Dimension(260,15));
            lbdate.setFont(new Font("",Font.ITALIC,15));
            lbdate.setForeground(Color.WHITE);

            panel.add(lbimg,BorderLayout.NORTH);
            panel.add(lbname,BorderLayout.CENTER);
            panel.add(lbdate,BorderLayout.SOUTH);
            rightPanel.add(panel);
        }
        rightPanel.revalidate();
    }
    
    private void showDetailPerMovie() throws IOException, NoPlayerException, CannotRealizeException{
        String result = inFromServer.readLine();
        String[] data = result.split("movieDetail");
        
        JFrame frame = new JFrame();
        frame.setTitle("Chi tiết phim " + data[7]);
        frame.setSize(1336,1000);
        frame.setBackground(Color.black);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        
        JPanel panelNorth = new JPanel();
        panelNorth.setSize(new Dimension(1366,300));
        panelNorth.setBackground(Color.black);
        panelNorth.setLayout(new BorderLayout());
        String[] imgUrl = data[6].split(",");
        String[] imgUrlX1 = imgUrl[0].trim().split(" ");
        String[] imgUrlX2 = imgUrl[1].trim().split(" ");
        
        URL url = new URL(imgUrlX2[0]);
        Image image = ImageIO.read(url);
        Image imageResize = image.getScaledInstance(250, 380,  java.awt.Image.SCALE_SMOOTH);
        
        JLabel lbimg = new JLabel();
        lbimg.setSize(new Dimension(250, 380));
        lbimg.setIcon(new ImageIcon(imageResize));
        lbimg.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        JPanel panelNorthChild = new JPanel();
        panelNorthChild.setLayout(new BorderLayout());
        panelNorthChild.setSize(new Dimension(1066,380));
        panelNorthChild.setBorder(new EmptyBorder(20, 10, 0, 0));
        panelNorthChild.setBackground(Color.black);
        
        JLabel lbname = new JLabel("<html><font color='white'>"+data[7]+"</font></html>");
        lbname.setSize(new Dimension(1066,30));
        lbname.setFont(new Font("", Font.BOLD, 30));
        
        JLabel lbmovietype = new JLabel("<html><font color='white'>"+data[8]+"</font></html>");
        lbmovietype.setSize(new Dimension(1066,20));
        lbmovietype.setFont(new Font("", Font.ITALIC, 18));
        
        JPanel movieTitle= new JPanel();
        movieTitle.setSize(new Dimension(1066,50));
        movieTitle.setBackground(Color.black);
        movieTitle.setLayout(new BorderLayout());
        
        JPanel movieInfoBot = new JPanel();
        movieInfoBot.setLayout(new BorderLayout());
        movieInfoBot.setSize(new Dimension(1046,310));
        movieInfoBot.setLayout(new BorderLayout());
        
        JPanel movieReview = new JPanel();
        movieReview.setSize(new Dimension(1066,50));
        movieReview.setBackground(Color.black);
        movieReview.setLayout(new BoxLayout(movieReview, BoxLayout.X_AXIS));
        
        JLabel lbtrailer = new JLabel("Trailer");
        lbtrailer.setPreferredSize(new Dimension(100,50));
        lbtrailer.setBackground(Color.white);
        lbtrailer.setForeground(Color.BLACK);
        lbtrailer.setOpaque(true);
        lbtrailer.setLocation(0,20);
        lbtrailer.setFont(new Font("",Font.BOLD,20));
        lbtrailer.setBorder(new LineBorder(Color.WHITE));
        lbtrailer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //showTrailer(data[9],data[7]);
                System.out.println("OK");
            }
            
            @Override
            public void mouseEntered(MouseEvent e){
                lbtrailer.setBackground(Color.BLACK);
                lbtrailer.setForeground(Color.white);
            }
            
            @Override
            public void mouseExited(MouseEvent e){
                lbtrailer.setBackground(Color.WHITE);
                lbtrailer.setForeground(Color.BLACK);
            }
        });
        
        movieReview.add(lbtrailer);
        
        JPanel movieInfo= new JPanel();
        movieInfo.setPreferredSize(new Dimension(600,210));
        movieInfo.setBackground(Color.black);
        movieInfo.setLayout(new BorderLayout());
        
        JPanel moviePerson= new JPanel();
        moviePerson.setPreferredSize(new Dimension(446,210));
        moviePerson.setBackground(Color.black);
        moviePerson.setBorder(new EmptyBorder(0,20,0,0));
        moviePerson.setLayout(new BoxLayout(moviePerson,BoxLayout.Y_AXIS));
        
        JLabel lbdes = new JLabel("<html><font color='white'>"+data[1]+"</font></html>");
        lbdes.setFont(new Font("", Font.PLAIN, 17));
        lbdes.setSize(new Dimension(580,260));
        lbdes.setVerticalAlignment(JLabel.TOP);
        
        JScrollPane scroller = new JScrollPane(lbdes);
        scroller.getViewport().setBackground(Color.black);
        scroller.setBorder(new EmptyBorder(0,0,0,1));
        scroller.getViewport().getView().setPreferredSize(new Dimension(600,210));
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel movieInfo2= new JPanel();
        movieInfo2.setPreferredSize(new Dimension(600,50));
        movieInfo2.setBackground(Color.black);
        movieInfo2.setLayout(new BoxLayout(movieInfo2,BoxLayout.X_AXIS));
        
        if(!"".equals(data[10])){
            JPanel pndate = new JPanel();
            pndate.setPreferredSize(new Dimension(100,50));
            pndate.setBackground(Color.black);
            pndate.setLayout(new BorderLayout());
            JLabel lbdate = new JLabel("<html><meta charset = utf-8><font color='white'> Khởi chiếu </font></html>");
            lbdate.setFont(new Font("",Font.BOLD,15));
            lbdate.setPreferredSize(new Dimension(100,25));
            JLabel lbinfodate = new JLabel("<html><font color='white'>"+data[10]+"</font></html>");
            lbinfodate.setPreferredSize(new Dimension(190,25));
            pndate.add(lbinfodate,BorderLayout.CENTER);
            pndate.add(lbdate,BorderLayout.NORTH);
            movieInfo2.add(pndate);
        }
        
        JPanel pnduration = new JPanel();
        pnduration.setPreferredSize(new Dimension(100,50));
        pnduration.setLayout(new BorderLayout());
        pnduration.setBackground(Color.black);
        JLabel lbduration = new JLabel("<html><meta charset = utf-8><font color='white'> Thời lượng </font></html>");
        lbduration.setFont(new Font("",Font.BOLD,15));
        lbduration.setPreferredSize(new Dimension(100,25));
        JLabel lbinfoduration = new JLabel("<html><font color='white'>"+data[11]+"</font></html>");
        lbinfoduration.setPreferredSize(new Dimension(100,25));
        pnduration.add(lbinfoduration,BorderLayout.CENTER);
        pnduration.add(lbduration,BorderLayout.NORTH);
        movieInfo2.add(pnduration);
        
        JPanel pnuserage = new JPanel();
        pnuserage.setPreferredSize(new Dimension(100,50));
        pnuserage.setLayout(new BorderLayout());
        pnuserage.setBackground(Color.black);
        JLabel lbuserage = new JLabel("<html><meta charset = utf-8><font color='white'> Giới hạn tuổi </font></html>");
        lbuserage.setFont(new Font("",Font.BOLD,15));
        lbuserage.setPreferredSize(new Dimension(100,25));
        JLabel lbinfouserage = new JLabel("<html><font color='white'>"+data[12]+"</font></html>");
        lbinfouserage.setPreferredSize(new Dimension(100,25));
        pnuserage.add(lbinfouserage,BorderLayout.CENTER);
        pnuserage.add(lbuserage,BorderLayout.NORTH);
        movieInfo2.add(pnuserage);
        
        JLabel lbactor = new JLabel();
        JLabel lbinfoactor = new JLabel();
        if(!"".equals(data[5])){
            lbactor = new JLabel("<html><meta charset = utf-8><font color='red'> Diễn viên </font></html>");
            lbactor.setPreferredSize(new Dimension(446,50));
            lbactor.setFont(new Font("", Font.PLAIN,22));
            lbinfoactor = new JLabel("<html><font color='white'>"+data[3]+"</font></html>");
            lbinfoactor.setPreferredSize(new Dimension(446,50));
            lbinfoactor.setBorder(new EmptyBorder(0,0,15,0));
            lbinfoactor.setFont(new Font("", Font.PLAIN,18));
        } else {
            lbactor = new JLabel("<html><meta charset = utf-8><font color='red'> Diễn viên </font></html>");
            lbactor.setPreferredSize(new Dimension(446,50));
            lbactor.setFont(new Font("", Font.PLAIN,22));
            lbinfoactor = new JLabel("<html><font color='white'> Đang cập nhật </font></html>");
            lbinfoactor.setPreferredSize(new Dimension(446,50));
            lbinfoactor.setBorder(new EmptyBorder(0,0,15,0));
            lbinfoactor.setFont(new Font("", Font.PLAIN,18));
        }
        
        JLabel lbdirector = new JLabel();
        JLabel lbinfodirector = new JLabel();
        if(!"".equals(data[5])){
            lbdirector = new JLabel("<html><meta charset = utf-8><font color='red'> Đạo diễn </font></html>");
            lbdirector.setPreferredSize(new Dimension(446,50));
            lbdirector.setFont(new Font("", Font.PLAIN,22));
            lbinfodirector = new JLabel("<html><font color='white'>"+data[4]+"</font></html>");
            lbinfodirector.setPreferredSize(new Dimension(446,50));
            lbinfodirector.setBorder(new EmptyBorder(0,0,15,0));
            lbinfodirector.setFont(new Font("", Font.PLAIN,18));
        } else {
            lbdirector = new JLabel("<html><meta charset = utf-8><font color='red'> Đạo diễn </font></html>");
            lbdirector.setPreferredSize(new Dimension(446,50));
            lbdirector.setFont(new Font("", Font.PLAIN,22));
            lbinfodirector = new JLabel("<html><font color='white'> Đang cập nhật </font></html>");
            lbinfodirector.setPreferredSize(new Dimension(446,50));
            lbinfodirector.setBorder(new EmptyBorder(0,0,15,0));
            lbinfodirector.setFont(new Font("", Font.PLAIN,18));
        }
        
        JLabel lbproductor = new JLabel();
        JLabel lbinfoproductor = new JLabel();
        if(!"".equals(data[5])){
            lbproductor = new JLabel("<html><meta charset = utf-8><font color='red'> Nhà xuất bản </font></html>");
            lbproductor.setPreferredSize(new Dimension(446,50));
            lbproductor.setFont(new Font("", Font.PLAIN,22));
            lbinfoproductor = new JLabel("<html><font color='white'>"+data[5]+"</font></html>");
            lbinfoproductor.setPreferredSize(new Dimension(446,50));
            lbinfoproductor.setFont(new Font("", Font.PLAIN,18));
        } else {
            lbproductor = new JLabel("<html><meta charset = utf-8><font color='red'> Nhà xuất bản </font></html>");
            lbproductor.setPreferredSize(new Dimension(446,50));
            lbproductor.setFont(new Font("", Font.PLAIN,22));
            lbinfoproductor = new JLabel("<html><font color='white'> Đang cập nhật</font></html>");
            lbinfoproductor.setPreferredSize(new Dimension(446,50));
            lbinfoproductor.setFont(new Font("", Font.PLAIN,18));
        }
        
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BorderLayout());
        panelSouth.setPreferredSize(new Dimension(0,0));
        panelSouth.setBackground(new Color(80,74,74));
        
        JPanel panelnav = new JPanel();
        panelnav.setLayout(new BoxLayout(panelnav, BoxLayout.X_AXIS));
        panelnav.setPreferredSize(new Dimension(0,30));
        panelnav.setBackground(Color.white);
        panelnav.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbtrailer2 = new JLabel("Trailer");
        lbtrailer2.setPreferredSize(new Dimension(100,30));
        lbtrailer2.setBackground(Color.WHITE);
        lbtrailer2.setForeground(Color.BLACK);
        lbtrailer2.setOpaque(true);
        lbtrailer2.setFont(new Font("",Font.BOLD,20));
        
        JLabel lbshowtime = new JLabel("Lịch chiếu");
        lbshowtime.setPreferredSize(new Dimension(100,30));
        lbshowtime.setBackground(Color.WHITE);
        lbshowtime.setForeground(Color.BLACK);
        lbshowtime.setOpaque(true);
        lbshowtime.setFont(new Font("",Font.BOLD,20));
        
        JLabel lbreview = new JLabel("Đánh giá");
        lbreview.setPreferredSize(new Dimension(100,30));
        lbreview.setBackground(Color.WHITE);
        lbreview.setForeground(Color.BLACK);
        lbreview.setOpaque(true);
        lbreview.setFont(new Font("",Font.BOLD,20));
        
        JLabel lbrottentomatoes = new JLabel("Rotten Tomatoes");
        lbrottentomatoes.setPreferredSize(new Dimension(100,30));
        lbrottentomatoes.setBackground(Color.WHITE);
        lbrottentomatoes.setForeground(Color.BLACK);
        lbrottentomatoes.setOpaque(true);
        lbrottentomatoes.setFont(new Font("",Font.BOLD,20));
        
        panelnav.add(lbtrailer2);
        panelnav.add(lbshowtime);
        panelnav.add(lbreview);
        panelnav.add(lbrottentomatoes);
        panelSouth.add(panelnav,BorderLayout.NORTH);
        
        moviePerson.add(lbactor);
        moviePerson.add(lbinfoactor);
        moviePerson.add(lbdirector);
        moviePerson.add(lbinfodirector);
        moviePerson.add(lbproductor);
        moviePerson.add(lbinfoproductor);
        
        movieTitle.add(lbname,BorderLayout.NORTH);
        movieTitle.add(lbmovietype,BorderLayout.CENTER);
        
        movieInfoBot.add(movieReview,BorderLayout.NORTH);
        movieInfo.add(movieInfo2,BorderLayout.SOUTH);
        movieInfoBot.add(movieInfo,BorderLayout.CENTER);
        
        movieInfo.add(scroller,BorderLayout.CENTER);
        
        movieInfoBot.add(movieInfo,BorderLayout.WEST);
        movieInfoBot.add(moviePerson,BorderLayout.CENTER);
        
        panelNorthChild.add(movieTitle,BorderLayout.NORTH);
        panelNorthChild.add(movieInfoBot, BorderLayout.CENTER);
        
        panelNorth.add(lbimg,BorderLayout.WEST);
        panelNorth.add(panelNorthChild,BorderLayout.CENTER);
        
        frame.add(panelNorth,BorderLayout.NORTH);
        frame.add(BorderLayout.CENTER, new JScrollPane(panelSouth));
        frame.setVisible(true);
    
    }
    
    private void showTrailer(String data9, String data7) {
        SwingUtilities.invokeLater(() -> {
            JFrame frameTrailer = new JFrame("Trailer " + data7);
            frameTrailer.setSize(1280,800);
            frameTrailer.setBackground(Color.black);
            frameTrailer.setLocationRelativeTo(null);
            frameTrailer.setLayout(new BorderLayout());
            frameTrailer.getContentPane().add(getBrowser(data9),BorderLayout.CENTER);
            frameTrailer.setResizable(false);
            frameTrailer.setVisible(true);
        });
    }
    
    public static JPanel getBrowser(String url){
        JPanel wbPanel = new JPanel(new BorderLayout());
        wbPanel.setBackground(Color.red);
        JWebBrowser wb = new JWebBrowser();
        wbPanel.add(wb,BorderLayout.CENTER);
        wb.setBarsVisible(false);
        wb.navigate(url);
        return wbPanel;
    }
    
    private void sendStartRequest() throws IOException{
        write("Start");
    }
    
    private void write(String message) throws IOException{
        sendToServer.write(message);
        sendToServer.newLine();
        sendToServer.flush();
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
    
    private JPanel leftPanel;
    private JPanel rightPanel;
}
