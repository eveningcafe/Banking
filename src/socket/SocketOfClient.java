/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ngohoa
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class SocketOfClient {
    Socket socketToGateway;
    Socket socketToService;
    JFrame rootpane;
    public SocketOfClient(File config, JFrame rootpane) {
        this.rootpane =rootpane;
        try {
            Scanner in = new Scanner(config);
            String infoGateway[] = in.nextLine().split(" ");
            try{
                
            socketToGateway = new Socket(infoGateway[1], Integer.parseInt(infoGateway[2]));
            }catch(IOException ex){
                JOptionPane.showMessageDialog(rootpane,"sever chưa mở, hãy thử lại lúc khác");
                System.exit(0);
            }catch(java.lang.NumberFormatException e){
                JOptionPane.showMessageDialog(rootpane, "File configClient.txt bị sai");
                System.exit(0);
            };
            
            try {
                String[] infoService = this.getInfoService();
                if(infoService!=null){
                try {
                    socketToService = new Socket(infoService[1], Integer.parseInt(infoService[2]));
                } catch (IOException ex) {
                    Logger.getLogger(SocketOfClient.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(rootpane,"sever bị lỗi, hãy thử lại lúc khác");
                    System.exit(0);
                }
                }else {
                JOptionPane.showMessageDialog(rootpane,"Dữ liệu chưa sẵng sàng, hãy thử lại lúc khác");
                System.exit(0);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootpane,"gateway bị đóng bất thường, hãy thử lại lúc khác");
                Logger.getLogger(SocketOfClient.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(rootpane, "File configClient.txt không tìm thấy hoặc bị sai");
            Logger.getLogger(SocketOfClient.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }   
    }
        
    private String getNextGatewayMessage() {
        BufferedReader is;
        try {
            is = new BufferedReader(new InputStreamReader(socketToGateway.getInputStream()));
            return is.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootpane,"gateway bị đóng bất thường, hãy thử lại lúc khác");
            Logger.getLogger(SocketOfClient.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(SocketOfClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;//se khong xay ra        
    }


    private String[] getInfoService() throws IOException {
        if(this.getNextGatewayMessage().equals("Service socket in:")){
            //sever phai tra ve  Service socket in:\n localHost, port cua primary sever
            return this.getNextGatewayMessage().split(" ");
        }
        return null;
    }
    
}
