/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.sql.Connection;
import java.util.Queue;

/**
 *
 * @author ngohoa
 */
public class ServiceServer {
    
    boolean isPrimaryServer;
    Queue<String> queueMessage;
    Connection con;
}
