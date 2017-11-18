/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connectdatabase.MySqlConnector;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ngohoa
 */
public class DBMangager {
    ArrayList<Object> databaseConectors ;
    public DBMangager(Scanner fileScan) throws ClassNotFoundException, SQLException, FileNotFoundException {
        //theo file config
        databaseConectors= new ArrayList<Object>();
        int numDB = fileScan.nextInt();
        fileScan.nextLine();
        for (int i = 0; i < numDB; i++) {
            String[] info = fileScan.nextLine().split(" ");
            if(info[0].equals("MysqlSever")){
                MySqlConnector co = new MySqlConnector(info[1]);
                databaseConectors.add(co);
            }else{
                System.out.println("Khong ho tro loai DB nay");
            }
        }
    }
    
}
