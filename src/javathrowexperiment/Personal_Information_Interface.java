/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathrowexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import static javathrowexperiment.Accounts.account_id;
import static javathrowexperiment.Accounts_Interface.JDBC_DRIVER;

/**
 *
 * @author morrejo_sd2023
 */
public class Personal_Information_Interface {

    ArrayList<PersonalInformation> pi;
    Scanner input1 = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/account";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public Personal_Information_Interface() {
        pi = new ArrayList();
    }
    
    public void retrieveDatabase() {
        java.sql.Connection conn;
        Statement stmt;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
//            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            String sql = "SELECT * FROM Information";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n\t\t    *** Personal Information ***");
            pi = new ArrayList();
     
            while (rs.next()) {
                int id = rs.getInt("id");
                int accID = rs.getInt("ACC_ID");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                int age = rs.getInt("age");
                System.out.println(id + "\t" + accID + "\t" + fname + "\t" + lname + "\t" + age + "\t");
                pi.add(new PersonalInformation(id, accID, fname, lname, Integer.toString(age)));
            }
//            STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
//        System.out.println("Retrieved!");
    }
    
    public void createToDB() {
        java.sql.Connection conn;
        Statement stmt;
        String fname, lname, age;
        System.out.print("Enter first name : ");
        fname = input1.nextLine();
        while (!Check.isString(fname)) {
            System.out.println("First name does not contain any number.");
            createToDB();
        }
        System.out.print("Enter last name : ");
        lname = input2.nextLine();
        while (!Check.isString(lname)) {
            System.out.println("Last name does not contain any number.");
            createToDB();
        }
        System.out.print("Enter age : ");
        age = input3.nextLine();
        while (Check.isString(age)) {
            System.out.println("Age is not a string.");
            createToDB();
        }
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
//            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "INSERT INTO Information (ACC_ID, firstname, lastname, age) VALUES ('"+account_id+"', '"+fname+"', '"+lname+"', '"+age+"')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        System.out.println("Saved!");
    }

    public void update(int acc_id) throws IOException {
        java.sql.Connection conn;
        Statement stmt;
        String fname = null, lname = null, age = null;
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
//            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            for (int i = 0; i < pi.size(); i++) {
                if (pi.get(i).getAccount_id() == acc_id) {
                    System.out.println("\t\t    *** Personal Information ***");
                    System.out.println(pi.get(i).getId() + "\t" + pi.get(i).getAccount_id() + "\t" + pi.get(i).getFname() + "\t" + pi.get(i).getLname() + "\t" + pi.get(i).getAge());
                    System.out.print("\nEnter new first name : ");
                    fname = input1.next();
                    while (!Check.isString(fname)) {
                        System.out.println("First name does not contain any number.");
                        update(acc_id);
                    }
                    System.out.print("Enter new last name : ");
                    lname = input2.nextLine();
                    while (!Check.isString(lname)) {
                        System.out.println("Last name does not contain any number.");
                        update(acc_id);
                    }
                    System.out.print("Enter new age : ");
                    age = input2.next();
                    while (Check.isString(age)) {
                        System.out.println("Age is not a string.");
                        update(acc_id);
                    }
                    String sql = "UPDATE Information SET `firstname` = '" + fname + "', `lastname` = '" + lname + "', `age` = '" + age + "' WHERE 'ID' = '" + pi.get(i).getId() +"'";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                } else if (pi.get(i).getAccount_id() != acc_id && i == pi.size() - 1 && fname == null && lname == null && age == null) {
                    System.out.print("Enter first name : ");
                    fname = input1.nextLine();
                    while (!Check.isString(fname)) {
                        System.out.println("First name does not contain any number.");
                        update(acc_id);
                    }
                    System.out.print("Enter last name : ");
                    lname = input2.nextLine();
                    while (!Check.isString(lname)) {
                        System.out.println("Last name does not contain any number.");
                        update(acc_id);
                    }
                    System.out.print("Enter age : ");
                    age = input3.nextLine();
                    while (Check.isString(age)) {
                        System.out.println("Age is not a string.");
                        update(acc_id);
                    }
                    String sql = "INSERT INTO Information (ACC_ID, firstname, lastname, age) VALUES ('"+account_id+"', '"+fname+"', '"+lname+"', '"+age+"')";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    break;
                }
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        System.out.println("Saved!");
//                PDATE `schedule` SET `ACC_ID` = '2', `title` = 'Web Dev 2', `schedule` = 'TTH' WHERE `schedule`.`ID` = 2;
            
       

    }

    public void delete(int acc_id) {
        java.sql.Connection conn;
        Statement stmt;
        for (int i = 0; i < pi.size(); i++) {
            if (pi.get(i).getAccount_id() == acc_id) {
                System.out.println(pi.get(i));
                try {
                    //STEP 2: Register JDBC driver
                    Class.forName(JDBC_DRIVER);

                    //STEP 3: Open a connection
//                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                    //STEP 4: Execute a query
//                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql = "DELETE FROM Information WHERE `ACC_ID` = '" + acc_id + "'";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (SQLException se) {
                    //Handle errors for JDBC
                    se.printStackTrace();
                } catch (Exception e) {
                    //Handle errors for Class.forName
                    e.printStackTrace();
                }
                System.out.println("Deleted!");
            }
        }
    }

    public void search(int acc_id) {
        pi.stream().filter((pi1) -> (pi1.getAccount_id() == acc_id)).forEach((pi1) -> {
            System.out.println(pi1.getId() + "\t" + pi1.getAccount_id() + "\t" + pi1.getFname() + "\t" + pi1.getLname() + "\t" + pi1.getAge());
        });
    }
}
