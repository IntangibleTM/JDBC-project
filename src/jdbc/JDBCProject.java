package jdbc;

import java.sql.*;
import java.util.Scanner;

public class JDBCProject {

    static final String displayFormat = "%-17s%-17s%-17s%-17s\n";
    Scanner in = new Scanner(System.in);
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    public static String dispNull(String input) {
        if (input == null || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }

    public void run() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/JavaDataBase");

            userChoice();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {

        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se3) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void menu() {
        System.out.println("Menu \n1. List all writing groups\n"
                + "2. List all the data for a group specified by the user\n"
                + "3. List all publishers\n"
                + "4. List all the data for a publisher specified by the user\n"
                + "5. List all book titles\n"
                + "6. List all the data for a book specified by the user\n"
                + "7. Insert a new book\n"
                + "8. Insert a new publisher and update all book published by one publisher to be published by the new publisher\n"
                + "9. Remove a book specified by the user\n"
                + "10. Exit Program");
    }

    public void userChoice() {
        boolean done = false;
        while (!done) {
            menu();
            if (in.hasNextInt()) {
                int selection = in.nextInt();
                if (selection > 0 && selection <= 10) {
                    in.nextLine();
                    switch (selection) {
                        case 1:
                            listWritingGroups();
                            break;
                        case 2:
                            listWritingGroupsSpec();
                            break;
                        case 3:
                            listPublishers();
                            break;
                        case 4:
                            listPublishersSpec();
                            break;
                        case 5:
                            listBookTitles();
                            break;
                        case 6:
                            listBookTitlesSpec();
                            break;
                        case 7:
                            insertBook();
                            break;
                        case 8:
                            insertPublisher();
                            break;
                        case 9:
                            removeBook();
                            break;
                        case 10:
                            done = true;
                            break;
                    }
                    selection = 0;
                } else {
                    System.out.println("Not in correct range!");
                }
            } else {
                System.out.println("Not an integer!");
                in.next();
            }
            System.out.println("\n\n\nPress enter to continue...");
            in.nextLine();
        }
    }

    public void listWritingGroups() {
        try {
            String sql;
            sql = "SELECT * FROM WritingGroups";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf(displayFormat, "GroupName", "HeadWriter", "YearFormed", "Subject");
            while (rs.next()) {
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listWritingGroupsSpec() {
        try {
            boolean val = false;
            String UI = "";
            while (!val) {
                System.out.println("Enter the Group Name: ");

                UI = in.nextLine();
                if (validateUserIn("WritingGroups", "GroupName", UI) == true) {
                    val = true;
                } else {
                    System.out.println("Sorry! Not a correct group name! Try again!");
                }
            }
            String sql;
            sql = "SELECT * FROM WritingGroups WHERE groupName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UI);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf(displayFormat, "GroupName", "HeadWriter", "YearFormed", "Subject");
            while (rs.next()) {
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listPublishers() {
        try {
            String sql;
            sql = "SELECT * FROM Publishers";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf(displayFormat, "Publisher Name", "Address", "Phone", "Email");
            while (rs.next()) {
                String PublisherName = rs.getString("PublisherName");
                String PublisherAddress = rs.getString("PublisherAddress");
                String PublisherPhone = rs.getString("PublisherPhone");
                String PublisherEmail = rs.getString("PublisherEmail");
                System.out.printf(displayFormat,
                        dispNull(PublisherName), dispNull(PublisherAddress), dispNull(PublisherPhone), dispNull(PublisherEmail));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listPublishersSpec() {
        try {

            boolean val = false;
            String UI = "";
            while (!val) {
                System.out.println("Enter the Pulisher Name: ");
                UI = in.nextLine();

                if (validateUserIn("Publishers", "PublisherName", UI) == true) {
                    val = true;
                } else {
                    System.out.println("Sorry! Not a correct publisher name!");
                }
            }

            String sql;
            sql = "SELECT * FROM Publishers WHERE PublisherName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UI);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf(displayFormat, "Publisher Name", "Street", "PhoneNumber", "Email");
            while (rs.next()) {
                String PublisherName = rs.getString("PublisherName");
                String PublisherAddress = rs.getString("PublisherAddress");
                String PublisherPhone = rs.getString("PublisherPhone");
                String PublisherEmail = rs.getString("PublisherEmail");
                System.out.printf(displayFormat,
                        dispNull(PublisherName), dispNull(PublisherAddress), dispNull(PublisherPhone), dispNull(PublisherEmail));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listBookTitles() {
        try {
            String sql;
            sql = "SELECT * FROM Books";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String displayFormat2 = "%-15s%-15s%-15s%-15s%-15s\n";
            System.out.printf(displayFormat2, "BookTitle", "GroupName", "PublisherName", "YearPublished", "NumberPages");
            while (rs.next()) {
                String BookTitle = rs.getString("BookTitle");
                String GroupName = rs.getString("GroupName");
                String PublisherName = rs.getString("PublisherName");
                String YearPublished = rs.getString("YearPublished");
                String NumberPages = rs.getString("NumberPages");

                System.out.printf(displayFormat2,
                        dispNull(BookTitle), dispNull(GroupName), dispNull(PublisherName), dispNull(YearPublished), dispNull(NumberPages));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listBookTitlesSpec() {
        try {
            boolean val = false;
            String UI = "";
            while (!val) {
                System.out.println("Enter the name of the Book: ");
                UI = in.nextLine();

                if (validateUserIn("Books", "BookTitle", UI) == true) {
                    val = true;
                } else {
                    System.out.println("Sorry! Not a correct Book Title!");
                }
            }
            String sql;
            sql = "SELECT * FROM Books WHERE BookTitle = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UI);
            ResultSet rs = pstmt.executeQuery();
            String displayFormat2 = "%-15s%-15s%-15s%-15s%-15s\n";
            System.out.printf(displayFormat2, "BookTitle", "GroupName", "PublisherName", "YearPublished", "NumberPages");
            while (rs.next()) {
                String BookTitle = rs.getString("BookTitle");
                String GroupName = rs.getString("GroupName");
                String PublisherName = rs.getString("PublisherName");
                String YearPublished = rs.getString("YearPublished");
                String NumberPages = rs.getString("NumberPages");
                System.out.printf(displayFormat2,
                        dispNull(BookTitle), dispNull(GroupName), dispNull(PublisherName), dispNull(YearPublished), dispNull(NumberPages));
            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public boolean validateUserIn(String title, String titleSpec, String user) {
        boolean check = false;
        try {
            String sql;
            sql = "SELECT " + titleSpec + " FROM " + title;

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String sTitle = rs.getString(titleSpec);

                if (sTitle.equals(user)) {
                    check = true;
                }

            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return check;
    }

    public boolean validateUserInp(String title, String titleSpec, String user) {
        boolean check = false;
        try {

            String sql;
            sql = "SELECT " + titleSpec + " FROM " + title;

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String sTitle = rs.getString(titleSpec);

                if (sTitle.equals(user)) {
                    System.out.println("Duplicate Publisher Name!");

                } else {
                    check = true;

                }

            }
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return check;
    }

    public void insertBook() {
        try {
            boolean check = false;
            Scanner in = new Scanner(System.in);
            pstmt = conn.prepareStatement("INSERT INTO Books (bookTitle,groupName,publisherName,yearPublished,numberPages) VALUES (?,?,?,?,?)");
            System.out.println("Enter the name of the Book Title: ");
            String UI = in.nextLine();
            pstmt.setString(1, UI);
            while (!check) {
                System.out.println("Enter the Group Name: ");
                UI = in.nextLine();
                if (validateUserIn("WritingGroups", "GroupName", UI)) {
                    pstmt.setString(2, UI);
                    check = true;
                } else {
                    System.out.println("No group name by that name!");
                }
            }
            check = false;
            while (!check) {
                System.out.println("Enter the name of the Publisher Name: ");
                UI = in.nextLine();
                if (validateUserIn("Publishers", "PublisherName", UI)) {
                    pstmt.setString(3, UI);
                    check = true;
                } else {
                    System.out.println("Not a valid publisher!");
                }
            }
            check = false;
            while (!check) {
                System.out.println("Enter the name of the Year Published: ");
                if (in.hasNextInt()) {
                    int year = in.nextInt();
                    if (year < 10000 && year > 0) {
                        pstmt.setInt(4, year);
                        check = true;
                    } else {
                        System.out.println("Not a valid year!");
                    }
                } else {
                    System.out.println("Not a year number!");
                    in.next();
                }
            }
            check = false;
            while (!check) {
                System.out.println("Enter the name of the Page Number: ");
                if (in.hasNextInt()) {
                    int pNum = in.nextInt();
                    pstmt.setInt(5, pNum);
                    pstmt.executeUpdate();
                    check = true;
                } else {
                    System.out.println("Not a number!");
                    in.next();
                }
            }
            System.out.println("New Book has been created.");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertPublisher() {

        try {
            boolean check = false;
            pstmt = conn.prepareStatement("INSERT INTO Publishers (publisherName,publisherAddress,publisherPhone,publisherEmail) VALUES (?,?,?,?)");
            String pubName="";
            while (!check) {
                System.out.println("Enter the name of the Publisher Name: ");
                pubName = in.nextLine();
                if (!validateUserIn("Publishers", "PublisherName", pubName)) {
                    pstmt.setString(1, pubName);
                    check = true;
                }
                else
                {
                    System.out.println("Not a unique publisher name!");
                }
            }
            check=false;
            System.out.println("Enter the name of the Publisher Address: ");
            String UI = in.nextLine();
            pstmt.setString(2, UI);
            while (!check) {
                System.out.println("Enter the name of the Publisher Phone: ");
                if (in.hasNextInt()) {
                    int phone = in.nextInt();

                    if (phone <= 100000000 && phone > 999999) {

                        pstmt.setInt(3, phone);
                        check = true;
                    } else {
                        System.out.println("Not a valid phone number");
                    }
                } else {
                    System.out.println("Not a phone number!");
                    in.next();
                }
            }
            System.out.println("Enter the Publisher Email: ");
            in.nextLine();
            UI = in.nextLine();
            pstmt.setString(4, UI);

            pstmt.executeUpdate();

            System.out.println("New publisher has been created.");

            pstmt = conn.prepareStatement("UPDATE Books SET publisherName = ? WHERE publisherName = ?");
            check = false;
            while (!check) {
                System.out.println("Enter the name of the publisher you want to change in Books");
                UI = in.nextLine();

                if (validateUserIn("Publishers", "PublisherName", UI)) {

                    pstmt.setString(2, UI);
                    pstmt.setString(1, pubName);
                    check = true;
                } else {
                    System.out.println("Not a valid publisher!");

                }
            }
            pstmt.executeUpdate();
            System.out.println("The all the books by publisher: " + UI + "\n have been changed to: " + pubName);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void removeBook() {
        try {
            boolean check = false;
            while (!check) {
                System.out.println("Enter the name of the Book you want to delete: ");
                String UI = in.nextLine();
                if (validateUserIn("Books", "BookTitle", UI)) {
                    pstmt = conn.prepareStatement("DELETE FROM Books WHERE bookTitle = ?");
                    pstmt.setString(1, UI);
                    pstmt.executeUpdate();
                    System.out.println("The Book \"" + UI + "\" has been deleted.");
                    check = true;
                } else {
                    System.out.println("Not a valid book!");
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
