package services;

import DatabaseConnection.DB_connect;
import Library_Classes.Messages;
import com.mysql.jdbc.PreparedStatement;
import io.ebean.Ebean;
import tables.BookTable;
import tables.BorrowedItemTable;
import tables.DVDTable;
import tables.ReaderTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class WestminsterLibraryManager implements LibraryManager {
    private Connection connection = DB_connect.getConnection();

    @Override
    public void addBook(String isbn, String title, String publisher, String published_date,
                        int no_of_pages, String sector, String author) {

        BookTable addBook = new BookTable();
        addBook.setIsbn(isbn);
        addBook.setTitle(title);
        addBook.setPublisher(publisher);
        addBook.setPublished_date(published_date);
        addBook.setNo_of_pages(no_of_pages);
        addBook.setSector(sector);
        addBook.setAuthor(author);
        addBook.setReader("");
        Ebean.save(addBook);
    }

    @Override
    public void addDVD(String isbn, String title, String producer, String actor,
                       String published_date, String sector, String subtitles, String languages) {
        DVDTable addDVD = new DVDTable();
        addDVD.setIsbn(isbn);
        addDVD.setTitle(title);
        addDVD.setProducer(producer);
        addDVD.setActor(actor);
        addDVD.setPublished_date(published_date);
        addDVD.setSector(sector);
        addDVD.setSubtitles(subtitles);
        addDVD.setLanguages(languages);
        addDVD.setReader("");
        Ebean.save(addDVD);

    }

    @Override
    public void deleteItem(String isbn) {

        if (Ebean.find(BookTable.class, isbn) != null) {
            Ebean.delete(BookTable.class, String.valueOf(isbn));
            int space = 100 - Ebean.find(BookTable.class).findCount();
            Messages deleteMessage = new Messages("ISBN No: " + isbn +
                    "\nBook was deleted from library\nYou have " + space +
                    " Spaces to Store Books!!!");
        } else if (Ebean.find(DVDTable.class, isbn) != null) {
            Ebean.delete(DVDTable.class, String.valueOf(isbn));
            int space = 50 - Ebean.find(DVDTable.class).findCount();
            Messages deleteMessage = new Messages("ISBN No: " + isbn +
                    "\nDVD was deleted from library\nYou have " + space +
                    " Spaces to Store DVDs!");
        } else {
            Messages deleteMessage = new Messages("invalid");
        }
    }


    @Override
    public void borrowItem(String isbn, String full_Name) throws SQLException {
        Date DateAndTime = new Date();
        SimpleDateFormat borrowedDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat borrowedTimeFormat = new SimpleDateFormat("HH:mm:ss");

        BorrowedItemTable borrowedItemTable = new BorrowedItemTable();
        borrowedItemTable.setReaderName(full_Name);
        borrowedItemTable.setIsbn(String.valueOf(isbn));
        borrowedItemTable.setDate(borrowedDateFormat.format(DateAndTime));
        borrowedItemTable.setTime(borrowedTimeFormat.format(DateAndTime));
        Ebean.save(borrowedItemTable);

        if (Messages.getMessage().equals("Book")) {
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement("update  addbooks " +
                    "set reader=? where isbn='" + isbn + "'");
            pst.setString(1, full_Name);
            pst.execute();
            System.out.println(full_Name);
        } else if (Messages.getMessage().equals("DVD")) {
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement("update  adddvd " +
                    "set reader=? where isbn='" + isbn + "'");
            pst.setString(1, full_Name);
            pst.execute();
        }

    }

    @Override
    public void returnItem(String isbn) throws SQLException, ParseException {
        String borrowedDate = "";
        String borrowedTime = "";
        String borrowed_isbn = "";
        Statement statement = connection.createStatement();
        ResultSet borrowedItems = statement.executeQuery("select * from borroweditems " +
                "where isbn='" + isbn + "'");
        while (borrowedItems.next()) {
            borrowedDate = borrowedItems.getString("date");
            borrowedTime = borrowedItems.getString("time");
            borrowed_isbn = borrowedItems.getString("isbn");
        }
        if (isbn.equals(borrowed_isbn)) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date time = new Date();


            String currentTime = timeFormat.format(time);

            double fine = 0;
            int hour = 0;


            LocalDate date1 = LocalDate.parse(borrowedDate, formatter);
            long daysBetween = ChronoUnit.DAYS.between(currentDate, date1);

            Date time1 = timeFormat.parse(borrowedTime);
            Date time2 = timeFormat.parse(currentTime);

            long timeBetween = time1.getTime() - time2.getTime();
            hour = (int) ((int) timeBetween * 2.7778e-7);
            if (Ebean.find(BookTable.class, isbn) != null) {
                if (daysBetween > 7 && daysBetween < 11) {
                    fine = hour * 0.2* (daysBetween - 7);
                    Messages returnMessage = new Messages("Your Book is Overdue!!" +
                            "\nYour Fine is " + String.valueOf(fine));
                } else if (daysBetween > 10) {
                    fine = hour * 0.5* (daysBetween - 10);
                    Messages returnMessage = new Messages("Your Book is Overdue!!" +
                            "\nYour Fine is " + String.valueOf(fine));
                } else {
                    Messages returnMessage = new Messages("Book successfully Returned!!!");
                }
            } else if (Ebean.find(DVDTable.class, isbn) != null) {
                if (daysBetween > 3 && daysBetween < 7) {
                    fine = hour * 0.2* (daysBetween - 3);
                    Messages returnMessage = new Messages("Your DVD is Overdue!!" +
                            "\nYour Fine is " + String.valueOf(fine));
                } else if (daysBetween > 6) {
                    fine = hour * 0.5* (daysBetween - 6);
                    Messages returnMessage = new Messages("Your DVD is Overdue!!" +
                            "\nYour Fine is " + String.valueOf(fine));
                } else {
                    Messages returnMessage = new Messages("DVD successfully Returned!!!");
                }
            }
        } else {
            Messages returnMessage = new Messages("Item is not borrowed!!!");
        }

    }

    @Override
    public void register(String name, String mobile_number, String email) {
        ReaderTable readerTable = new ReaderTable();
        readerTable.setReaderName(name);
        readerTable.setMobile_number(String.valueOf(mobile_number));
        readerTable.setEmail(email);
        Ebean.save(readerTable);


    }

    @Override
    public BookTable[] displayBooks() {
        BookTable[] displayBooks = new BookTable[Ebean.find(BookTable.class).findCount()];
        displayBooks = Ebean.find(BookTable.class).findList().toArray(displayBooks);
        return displayBooks;
    }

    @Override
    public DVDTable[] displayDVD() {
        DVDTable[] displayDVD = new DVDTable[Ebean.find(DVDTable.class).findCount()];
        displayDVD = Ebean.find(DVDTable.class).findList().toArray(displayDVD);
        return displayDVD;

    }

    @Override
    public BorrowedItemTable[] generateReport() throws SQLException, ParseException {
        BorrowedItemTable borrowedItemTable = new BorrowedItemTable();
        String borrowedDate = "";
        String borrowedTime = "";
        String borrowed_isbn = null;
        String due = null;
        Statement statement = connection.createStatement();

//        System.out.println(borrowed_isbn);
        ResultSet borrowedItems = statement.executeQuery("select * from borroweditems ");
        while (borrowedItems.next()) {
            borrowedDate = borrowedItems.getString("date");
            borrowedTime = borrowedItems.getString("time");
            borrowed_isbn = borrowedItems.getString("isbn");


            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date time = new Date();


            String currentTime = timeFormat.format(time);

            double fine = 0;
            int hour = 0;


            LocalDate date1 = LocalDate.parse(borrowedDate, formatter);
            long daysBetween = ChronoUnit.DAYS.between(date1, currentDate);

            Date time1 = timeFormat.parse(borrowedTime);
            Date time2 = timeFormat.parse(currentTime);

            long timeBetween = time1.getTime() - time2.getTime();
            hour = (int) ((int) timeBetween * 2.7778e-7);
            if (Ebean.find(BookTable.class, borrowed_isbn) != null) {

                if (daysBetween > 7 && daysBetween < 11) {
                    fine = hour * 0.2 * (daysBetween - 7);
                    System.out.println(fine);
//                        Math.round(fine);
                } else if (daysBetween > 10) {
                    fine = hour * 0.5 * (daysBetween - 10);
                    System.out.println(fine);
//                        Math.round(fine);
                }
            } else if (Ebean.find(DVDTable.class, borrowed_isbn) != null) {
                if (daysBetween > 3 && daysBetween < 7) {
                    fine = hour * 0.3 * (daysBetween - 3);
                    System.out.println(fine);
//                        Math.round(fine);
                } else if (daysBetween > 6) {
                    fine = hour * 0.5 * (daysBetween - 6);
                    System.out.println(fine);
//                        Math.round(fine);
                }
            }
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement("update  borroweditems " +
                    "set due=? where isbn='" + borrowed_isbn + "'");
            pst.setString(1, String.valueOf(fine));
            pst.execute();
// System.out.println(borrowedItemTable.getDue());
        }


        BorrowedItemTable[] report = new BorrowedItemTable[Ebean.find(BorrowedItemTable.class).findCount()];
        report = Ebean.find(BorrowedItemTable.class).findList().toArray(report);
        return report;

    }
}
