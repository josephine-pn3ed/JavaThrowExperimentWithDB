/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathrowexperiment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author morrejo_sd2023
 */
public class JavaThrowExperiment {

    public static void main(String args[]) throws IOException, PasswordException, SQLException {
        Accounts_Interface ai = new Accounts_Interface();
        Personal_Information_Interface pi = new Personal_Information_Interface();
        Courses_Interface ci = new Courses_Interface();
        Scanner input1 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        Scanner input4 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input5 = new Scanner(System.in);
        boolean exit = false;

        while (exit == false) {

            System.out.println("\n***** CHOICES *****");
            System.out.println("c -- create\nr -- retrieve\nu -- update\nd -- delete\ns -- search\ne -- exit");
            System.out.print("Choice : ");
            String choice1 = input1.next();
            switch (choice1) {
                case "c":
                    ai.connect("c");
                    System.out.print("\nDo you want to add personal information (y/n) ? ");
                    String choice4 = input2.next();
                    switch (choice4) {
                        case "y":
                            pi.connect("c", 0);
                            while (true) {
                                System.out.print("\nDo you want to add courses (y/n) ? ");
                                String choice5 = input3.next();
                                if ("y".equals(choice5)) {
                                    ci.connect("c", 0);
                                } else {
                                    break;
                                }
                            }
                        case "n":
                            break;
                    }
                    break;
                case "r":
                    ai.connect("r");
                    pi.connect("r", 0);
                    ci.connect("r", 0);
                    break;
                case "u":
                    try {
                        System.out.println("\n--- UPDATE ---");
                        System.out.print("Account ID :");
                        int choice6 = input2.nextInt();
                        if (pi.connect("u", choice6) == false) {
                            System.out.println("Account ID did not exist.");
                        }
                        while (true) {
                            System.out.print("\nDo you want to update courses (y/n) ? ");
                            String choice8 = input5.next();
                            if ("y".equals(choice8)) {
                                if (ci.connect("u", choice6) == false) {
                                    System.out.println("Account ID did not exist.");
                                }
                            } else {
                                break;
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("\nMismatch input.");
                    }
                    break;
                case "d":
                    try {
                        System.out.println("\n--- DELETE ---");
                        System.out.print("Account ID :");
                        int choice2 = input4.nextInt();
                        pi.connect("d", choice2);
                        ci.connect("d", choice2);
                    } catch (InputMismatchException e) {
                        System.err.println("\nMismatch input.");
                    }
                    break;
                case "s":
                    try {
                        System.out.println("\n--- SEARCH ---");
                        System.out.print("Account ID :");
                        int choice3 = input3.nextInt();
                        ai.search(choice3);
                        pi.search(choice3);
                        ci.search(choice3);
                    } catch (InputMismatchException e) {
                        System.err.println("\nMismatch input.");
                    }
                    break;
                case "e":
                    exit = true;
                    break;
            }

        }
    }

}
