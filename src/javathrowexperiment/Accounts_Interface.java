/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathrowexperiment;

import java.util.ArrayList;
import static java.util.Objects.hash;
import java.util.Scanner;
import static javathrowexperiment.Accounts.account_id;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author morrejo_sd2023
 */
public class Accounts_Interface {

    static ArrayList<Accounts> a = new ArrayList();
    Scanner input1 = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/account";

    static final String USER = "root";
    static final String PASS = "";
    Connection conn;
    Statement stmt;
    ResultSet rs;
    String username, password, confirm;

    public Accounts_Interface() {
    }

    void connect(String pro) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            if ("r".equals(pro)) {
                retrieveDatabase();
            } else if ("c".equals(pro)) {
                createToDB();
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
    }

    public void retrieveDatabase() throws SQLException {
        a = new ArrayList();
        String sql = "SELECT * FROM account";
        rs = stmt.executeQuery(sql);

        System.out.println("--- RETRIEVE ---");
        System.out.println("\n\t\t*** Accounts ***\n");

        while (rs.next()) {
            int id = rs.getInt("id");
            username = rs.getString("username");
            password = rs.getString("password");
            System.out.println(id + "\t\t" + username + "\t\t" + password + "\t\t");
            a.add(new Accounts(id, username, password));
        }
        if (a.isEmpty()) {
            account_id = 1;
        } else {
            account_id = a.get(a.size() - 1).getAcc_id();
        }
    }

    public void createToDB() throws SQLException {
        System.out.print("\n--- CREATE ---\nEnter username : ");
        username = input1.next();
        while (!Check.isString(username)) {
            System.out.println("Username containing all number.");
            createToDB();
        }
        while (true) {
            try {
                System.out.print("Enter password : ");
                password = input1.next();
                if (password.length() >= 8) {
                    break;
                } else {
                    throw new PasswordException("Password too short.");
                }
            } catch (PasswordException ex) {
                System.out.println(ex);
            }
        }
        while (true) {
            try {
                System.out.print("Re-enter password : ");
                confirm = input2.next();
                if (confirm == null ? password == null : confirm.equals(password)) {
                    break;
                } else {
                    throw new PasswordException("Password mismatch.");
                }
            } catch (PasswordException ex) {
                System.out.println(ex);
            }
        }
        String sql = "INSERT INTO Account (username, password) VALUES ";
        sql += "('" + username + "', '" + hash(password.toCharArray()) + "')";
        stmt.executeUpdate(sql);
        ++account_id;
        System.out.println("Saved!");
    }

    public void search(int acc_id) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getAcc_id() == acc_id) {
                System.out.println(a.get(i).getAcc_id() + "\t" + a.get(i).getUsername() + "\t" + a.get(i).getPassword());
            }
        }
    }

}
