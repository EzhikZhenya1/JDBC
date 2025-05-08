package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static boolean flag = true;

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService usersSer = new UserServiceImpl();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want create table? (y/n)");

        try {
            while (flag) {
                String line = sc.next();
                if (line.equalsIgnoreCase("y") || line.equalsIgnoreCase("yes")) {

                    usersSer.createUsersTable();
                    System.out.println("Table created");

                    while (flag) {
                        System.out.println("\nAdd user (press 1)");
                        System.out.println("Drop user (press 2)");
                        System.out.println("Get all users (press 3)");
                        System.out.println("Clear table (press 4)");
                        System.out.println("Delete table (press 5)");
                        System.out.println("Exit (press 6)");

                        switch (sc.next()) {
                            case "1":
                                System.out.println("Press name: ");
                                String name = sc.next();
                                System.out.println("Press last name: ");
                                String lastName = sc.next();

                                byte age = 0;
                                do {
                                    System.out.println("Press age: ");
                                    age = sc.nextByte();
                                    if (age < 0 || age > 100) System.out.println("Invalid age");
                                } while (age < 0 || age > 100);

                                usersSer.saveUser(name, lastName, age);
                                System.out.println("User: " + name + " " + lastName + " " + age + " created.");
                                break;
                            case "2":
                                System.out.println("Press id: ");
                                int id = sc.nextInt();
                                usersSer.removeUserById(id);
                                System.out.println("User: " + id + " removed.");
                                break;
                            case "3":
                                usersSer.getAllUsers().forEach(System.out::println);
                                break;
                            case "4":
                                usersSer.cleanUsersTable();
                                System.out.println("Table cleared.");
                                break;
                            case "5":
                                usersSer.dropUsersTable();
                                System.out.println("Table dropped.");
                                break;
                            case "6":
                                flag = false;
                                break;
                            default:
                                System.out.println("Руки не из того места? Нужно ввести 1-6");
                        }
                    }

                } else if (line.equalsIgnoreCase("n") || line.equalsIgnoreCase("no")) {

                    System.out.println("Bye. Come again.");
                    flag = false;

                } else {
                    System.out.println("Руки не из того места? Нужно ввести y/n");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
