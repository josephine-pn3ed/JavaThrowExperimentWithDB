/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathrowexperiment;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import static javathrowexperiment.Accounts.account_id;
import static javathrowexperiment.Accounts_Interface.a;

/**
 *
 * @author morrejo_sd2023
 */
public class Personal_Information_Interface {

    ArrayList<PersonalInformation> pi;
    Scanner input1 = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);
    Scanner input4 = new Scanner(System.in);
    Scanner input5 = new Scanner(System.in);
    Scanner input6 = new Scanner(System.in);

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/account";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    java.sql.Connection conn;
    Statement stmt;
    ResultSet rs;

    public Personal_Information_Interface() {
        pi = new ArrayList();
    }

    boolean connect(String pro, int id) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            if (null != pro) {
                switch (pro) {
                    case "r":
                        retrieveDatabase();
                        break;
                    case "u":
                        return update(id);
                    case "c":
                        createToDB();
                        break;
                    case "d":
                        delete(id);
                        break;
                    default:
                        break;
                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void retrieveDatabase() throws SQLException {
        String sql = "SELECT * FROM Information";
        rs = stmt.executeQuery(sql);

        System.out.println("\n\t   *** Personal Information ***\n");
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
    }

    public boolean createToDB() throws SQLException {
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
        String sql = "INSERT INTO Information (ACC_ID, firstname, lastname, age) VALUES ('" + account_id + "', '" + fname + "', '" + lname + "', '" + age + "')";
        stmt.executeUpdate(sql);
        return true;
    }

    public boolean update(int acc_id) throws IOException, SQLException {
        String fname = null, lname = null, age = null;
        for (int i = 0; i < pi.size(); i++) {
            if (pi.get(i).getAccount_id() == acc_id) {
                System.out.println("\t    *** Personal Information ***\n");
                System.out.println(pi.get(i).getId() + "\t\t" + pi.get(i).getAccount_id() + "\t\t" + pi.get(i).getFname() + "\t\t" + pi.get(i).getLname() + "\t\t" + pi.get(i).getAge());
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
                age = input3.next();
                while (Check.isString(age)) {
                    System.out.println("Age is not a string.");
                    update(acc_id);
                }
                String sql = "UPDATE Information SET `ACC_ID` = '" + acc_id + "', `firstname` = '" + fname + "', `lastname` = '" + lname + "', `age` = '" + age + "' WHERE `ACC_ID` = '" + acc_id + "'";
                stmt.executeUpdate(sql);
                System.out.println("Updated!");
                return true;
            } else if (pi.get(pi.size() - 1).getAccount_id() != acc_id) {
                for (int j = 0; j < a.size(); j++) {
                    if (a.get(j).getAcc_id() == acc_id) {
                        System.out.print("Enter first name : ");
                        fname = input4.nextLine();
                        while (!Check.isString(fname)) {
                            System.out.println("First name does not contain any number.");
                            update(acc_id);
                        }
                        System.out.print("Enter last name : ");
                        lname = input5.nextLine();
                        while (!Check.isString(lname)) {
                            System.out.println("Last name does not contain any number.");
                            update(acc_id);
                        }
                        System.out.print("Enter age : ");
                        age = input6.nextLine();
                        while (Check.isString(age)) {
                            System.out.println("Age is not a string.");
                            update(acc_id);
                        }
                        String sql = "INSERT INTO Information (ACC_ID, firstname, lastname, age) VALUES ('" + acc_id + "', '" + fname + "', '" + lname + "', '" + age + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Saved!");
                        return true;
                    }
                }
            }
        }
        if (acc_id == 1) {
            System.out.println("\t\t    *** Personal Information ***");
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
            String sql = "INSERT INTO Information (ACC_ID, firstname, lastname, age) VALUES ('" + account_id + "', '" + fname + "', '" + lname + "', '" + age + "')";
            stmt.executeUpdate(sql);
            System.out.println("Saved!");
            return true;
        }
        return false;
    }

    public void delete(int acc_id) throws SQLException {
        for (int i = 0; i < pi.size(); i++) {
            if (pi.get(i).getAccount_id() == acc_id) {
                System.out.println(pi.get(i));

                String sql = "DELETE FROM Information WHERE `ACC_ID` = '" + acc_id + "'";
                System.out.println(sql);
                stmt.executeUpdate(sql);
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
