/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngohoa
 */
public class GatewayServiceThread extends Thread {

    private final int clientNumber;
    private final Socket socketOfServer;
    private final ArrayList<InfoSever> infoServiceServer;

    public GatewayServiceThread(Socket socketForGateway, int clientNumber, ArrayList<InfoSever> infoServiceServers) {
        this.clientNumber = clientNumber;
        this.socketOfServer = socketForGateway;
        System.out.println("New connection with client# " + this.clientNumber + " at " + socketForGateway);
        this.infoServiceServer = infoServiceServers;
    }

    @Override
    public void run() {
        try {
            this.sendInfoPrimaryServer(infoServiceServer);
        } catch (IOException ex) {
            System.out.println("ket noi client bi mat");
            Logger.getLogger(GatewayServiceThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendInfoPrimaryServer(ArrayList<InfoSever> s) throws IOException {
    
        ArrayList<Socket> sev = new ArrayList<Socket>();
        for (InfoSever i : s) {
            try {
                
                Socket temp = new Socket(i.host, i.port);
                sev.add(temp);
            } catch (IOException ex) {
                //hoi lan luot xem co server nao hoat dong.
            }
        }
        if (sev.isEmpty()) {
            System.out.println("chua co service sever nao hoat dong");
            sendMessage("err: chua co service sever nao hoat dong");
        } else {
            for (Socket socket : sev) {
                BufferedWriter oSev = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader iSev = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oSev.write("is primary?");
                oSev.newLine();
                oSev.flush();
                String[] recive = iSev.readLine().split(" ");
                if (recive[0].equals("true")) {
                    sendMessage(recive[1] + recive[2]);
                    break;
                }
            }
            //neu chua co ai la primary:
            callVote(sev);
            sendInfoPrimaryServer(s);
        }
    }

    private void sendMessage(String thing) throws IOException {
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
        os.write(thing);
        os.newLine();
        os.flush();
    }

    private String getMessage() throws IOException {
        BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
        return is.readLine();
    }

    private void callVote(ArrayList<Socket> sev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
