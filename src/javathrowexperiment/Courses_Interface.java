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
public class Courses_Interface {

    ArrayList<Courses> c;
    Scanner input1 = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/account";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    java.sql.Connection conn;
    Statement stmt;
    ResultSet rs;
    String sql;

    public Courses_Interface() {
        c = new ArrayList();
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
        sql = "SELECT * FROM Schedule";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n\t    *** Schedules ***\n");
        c = new ArrayList();
        while (rs.next()) {
            int id = rs.getInt("id");
            int accid = rs.getInt("ACC_ID");
            String title = rs.getString("title");
            int unit = rs.getInt("unit");
            String schedule = rs.getString("schedule");
            System.out.println(id + "\t" + accid + "\t" + title + "\t" + unit + "\t" + schedule + "\t");
            c.add(new Courses(id, accid, title, Integer.toString(unit), schedule));
        }
    }

    public boolean createToDB() throws SQLException {
        String title, unit, schedule;
        System.out.print("Enter title/subject : ");
        title = input1.nextLine();
        System.out.print("Enter units : ");
        unit = input2.nextLine();
        while (Check.isString(unit)) {
            System.out.println("Units is not a string.");
            createToDB();
        }
        System.out.print("Enter schedule : ");
        schedule = input3.nextLine();
        sql = "INSERT INTO Schedule (ACC_ID, title, unit, schedule) VALUES ('" + account_id + "', '" + title + "', '" + unit + "', '" + schedule + "')";;
        stmt.executeUpdate(sql);
        return true;
    }

    public boolean update(int acc_id) throws IOException, SQLException {
        String title = null, unit = null, schedule = null;
        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getAccount_id() == acc_id) {
                System.out.println("\t\t *** Schedules ***\n");
                System.out.println(c.get(i).getId() + "\t\t" + c.get(i).getAccount_id() + "\t\t" + c.get(i).getTitle() + "\t\t" + c.get(i).getUnit() + "\t\t" + c.get(i).getSchedule());
                System.out.print("\nEnter new title/subject : ");
                title = input1.nextLine();
                System.out.print("Enter new units : ");
                unit = input2.nextLine();
                while (Check.isString(unit)) {
                    System.out.println("Units is not s string.");
                    update(acc_id);
                }
                System.out.print("Enter new schedule : ");
                schedule = input3.nextLine();
                sql = "UPDATE Schedule SET `ACC_ID` = '" + acc_id + "', `title` = '" + title + "', `unit` = '" + unit + "', `schedule` = '" + schedule + "' WHERE `ACC_ID` = '" + acc_id + "'";
                stmt.executeUpdate(sql);
                System.out.println("Saved!");
                return true;
            } else if (c.get(c.size() - 1).getAccount_id() != acc_id) {
                for (int j = 0; j < a.size(); j++) {
                    if (a.get(j).getAcc_id() == acc_id) {
                        System.out.print("Enter title/subject : ");
                        title = input1.nextLine();
                        System.out.print("Enter units : ");
                        unit = input2.nextLine();
                        while (Check.isString(unit)) {
                            System.out.println("Units is not a string.");
                            update(acc_id);
                        }
                        System.out.print("Enter schedule : ");
                        schedule = input3.nextLine();
                        sql = "INSERT INTO Schedule (ACC_ID, title, unit, schedule) VALUES ('" + account_id + "', '" + title + "', '" + unit + "', '" + schedule + "')";;
                        stmt.executeUpdate(sql);
                        System.out.println("Saved!");
                        return true;
                    }
                }
            }
        }
        if (acc_id == 1) {
            System.out.println("\t\t    *** Schedules ***");
            System.out.print("Enter title/subject : ");
            title = input1.nextLine();
            System.out.print("Enter units : ");
            unit = input2.nextLine();
            while (Check.isString(unit)) {
                System.out.println("Units is not a string.");
                update(acc_id);
            }
            System.out.print("Enter schedule : ");
            schedule = input3.nextLine();
            sql = "INSERT INTO Schedule (ACC_ID, title, unit, schedule) VALUES ('" + account_id + "', '" + title + "', '" + unit + "', '" + schedule + "')";;
            stmt.executeUpdate(sql);
            System.out.println("Saved!");
            return true;
        }
        return false;
    }

    public void delete(int acc_id) throws SQLException {
        sql = "DELETE FROM Schedule WHERE `ACC_ID` = '" + acc_id + "'";
        stmt.executeUpdate(sql);
        System.out.println("Deleted!");
    }

    public void search(int acc_id) {
        c.stream().filter((c1) -> (c1.getAccount_id() == acc_id)).forEach((c1) -> {
            System.out.println(c1.getId() + "\t" + c1.getAccount_id() + "\t" + c1.getTitle() + "\t" + c1.getUnit() + "\t" + c1.getSchedule());
        });
    }
}
