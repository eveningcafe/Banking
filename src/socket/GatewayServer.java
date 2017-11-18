/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngohoa
 */
public class GatewayServer {
    //co nhiem vu can bang tai. va thong bao dau la primary sever de nguoi dung lam viec voi no. 
    ArrayList<ServiceServer> serviceSever;
    public GatewayServer(File configServer) {
        try {
            Scanner in = new Scanner(configServer);
            String[] infoServer = in.nextLine().split(" ");
            ServerSocket listener = new ServerSocket(Integer.parseInt(infoServer[2]));
            System.out.println("da mo gateway");
            ArrayList<InfoSever> infoServiceServers = new ArrayList();
            while (in.hasNextLine()) {
                infoServer = in.nextLine().split(" ");
                InfoSever s = new InfoSever(infoServer[1], Integer.parseInt(infoServer[2]));
                infoServiceServers.add(s);
            }
            int clientNumber=0;
             try {
            while (true) {
                System.out.println("dang doi client ket noi");
                Socket socketForGateway = listener.accept();
                new GatewayServiceThread(socketForGateway, clientNumber++,infoServiceServers).start();
                
            }
             }finally{
                 listener.close();
             }
        } catch (FileNotFoundException ex) {
            System.out.println("có lỗi với file configGatewaySever.txt");
            Logger.getLogger(GatewayServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("xung đột cổng");
            Logger.getLogger(GatewayServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void main(String[] args) {
//        if(args ==null )System.out.println("missing args");
//        else{
//            File f = new File(args[0]);
//        }
        File f = new File("src/configGatewaySever.txt");
        GatewayServer g = new GatewayServer(f);
    }
    
}
