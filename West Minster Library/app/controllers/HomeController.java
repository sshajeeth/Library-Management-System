package controllers;

import DatabaseConnection.DB_connect;
import Library_Classes.Messages;
import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.jdbc.PreparedStatement;
import io.ebean.Ebean;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LibraryManager;
import services.WestminsterLibraryManager;
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

public class HomeController extends Controller {
    private String isbn;
    private String title;
    private String published_date;
    private String sector;

    private LibraryManager libraryManager = new WestminsterLibraryManager();

    Connection connection = DB_connect.getConnection();


    public Result index() {
        return ok();
    }

    //Book
    public Result checkAvailablity(String isbn) {
        if (Ebean.find(BookTable.class, isbn) != null) {
            Messages bookMessage = new Messages("ISBN No: " + isbn + "\nBook is Already in Library!!!");
        } else {
            Messages bookMessage = new Messages("ok");
        }

        JsonNode bookMessage = Json.toJson(Messages.getMessage());
        return ok(bookMessage);
    }

    public Result addBook() {

        JsonNode addBook = request().body().asJson();

        isbn = addBook.findPath("isbn").textValue();
        title = addBook.findPath("title").textValue();
        String publisher = addBook.findPath("publisher").textValue();
        published_date = addBook.findPath("published_date").textValue();
        int noOfPages = addBook.findPath("no_of_pages").asInt();
        sector = addBook.findPath("sector").textValue();
        String author = addBook.findPath("author").textValue();

        libraryManager.addBook(isbn, title, publisher, published_date, noOfPages, sector, author);
        System.out.println(addBook);
        return ok("Success");
    }

    //Finding the Remaining Space for Books
    public Result bookRemain() {
        int bookSpaceRemain = 100 - Ebean.find(BookTable.class).findCount();
        JsonNode bookSpace = Json.toJson(bookSpaceRemain);
        return ok(bookSpace);
    }

    //DVD
    public Result checkDVDAvailablity(String isbn) {
        if (Ebean.find(DVDTable.class, isbn) != null) {
            Messages dvdMessage = new Messages("ISBN No: " + isbn + "\nDVD is Already in Library!!!");
        } else {
            Messages dvdMessage = new Messages("ok");
        }
        JsonNode dvdMessage = Json.toJson(Messages.getMessage());
        return ok(dvdMessage);
    }

    //Finding the Remaining Space for DVDs
    public Result dvdRemain() {
        int dvdSpaceRemain = 50 - Ebean.find(DVDTable.class).findCount();
        JsonNode dvdSpace = Json.toJson(dvdSpaceRemain);
        return ok(dvdSpace);
    }

    public Result addDVD() {

        JsonNode addDVD = request().body().asJson();

        isbn = addDVD.findPath("isbn").textValue();
        title = addDVD.findPath("title").textValue();
        String producer = addDVD.findPath("producer").textValue();
        String actor = addDVD.findPath("actor").textValue();
        published_date = addDVD.findPath("published_date").textValue();
        sector = addDVD.findPath("sector").textValue();
        String subtitles = addDVD.findPath("subtitles").textValue();
        String languages = addDVD.findPath("languages").textValue();

        libraryManager.addDVD(isbn, title, producer, actor, published_date, sector, subtitles, languages);

        return ok();
    }

    //Delete
    public Result delete(String isbn) {
        libraryManager.deleteItem(isbn);
        JsonNode delete = Json.toJson(Messages.getMessage());
        return ok(delete);
    }

    //Borrow
    public Result borrowNameCheck(String readerName) {
        if (Ebean.find(ReaderTable.class, readerName) != null) {
            if (Ebean.find(BorrowedItemTable.class, readerName) != null) {
                Messages borrowNameCheckMessage = new Messages("Already you borrowed a item...");
            } else {
                Messages borrowNameCheckMessage = new Messages("can borrow");
            }
        } else {
            Messages borrowNameCheckMessage = new Messages("please register");
        }
        JsonNode borrowNameCheck = Json.toJson(Messages.getMessage());
        return ok(borrowNameCheck);
    }

    public Result borrowItemCheck(String isbn) throws SQLException {
        Statement statement = connection.createStatement();
        String bookReader = "";
        String dvdReader = "";
        String bookISBN = "";
        String dvdISBN = "";


        ResultSet book = statement.executeQuery("select * from addbooks where isbn='" + isbn + "'");
        while (book.next()) {
            bookReader = book.getString("reader");
            bookISBN = book.getString("isbn");

        }

        ResultSet dvd = statement.executeQuery("select * from adddvd where isbn='" + isbn + "'");
        while (dvd.next()) {
            dvdReader = dvd.getString("reader");
            dvdISBN = dvd.getString("isbn");

        }

        if (bookISBN.equals(isbn) || dvdISBN.equals(isbn)) {
            if (bookReader == null || bookReader.equals("")) {
                Messages borrowItemCheckMessage = new Messages("Book");
            } else if (dvdReader == null || dvdReader.equals("")) {
                Messages borrowItemCheckMessage = new Messages("DVD");
            } else {
                Messages borrowItemCheckMessage = new Messages("Item Already Borrowed!!!");
            }
        } else {
            Messages borrowItemCheckMessage = new Messages("Invalid ISBN Number!!!");
        }
        JsonNode borrowItemCheck = Json.toJson(Messages.getMessage());
        return ok(borrowItemCheck);
    }

    public Result borrow() throws SQLException {
        JsonNode borrow = request().body().asJson();

        String fullName = borrow.findPath("fullName").textValue();
        isbn = borrow.findPath("isbn").textValue();
        libraryManager.borrowItem(isbn, fullName);
        return ok();
    }

    //Return
    public Result returnItem(String isbn) throws SQLException, ParseException {
        libraryManager.returnItem(isbn);
        JsonNode returnMessage = Json.toJson(Messages.getMessage());
        return ok(returnMessage);
    }

    //Register
    public Result registerNameCheck(String fullName) {
        if (Ebean.find(ReaderTable.class, fullName) != null) {
            Messages registerMessage = new Messages("Already registered");
        } else {
            Messages registerMessage = new Messages("can register");
        }
        JsonNode registerNameMessage = Json.toJson(Messages.getMessage());
        return ok(registerNameMessage);
    }

    public Result register() {
        JsonNode register = request().body().asJson();

        String fullName = register.findPath("fullName").textValue();
        String mobileNumber = register.findPath("phoneNumber").textValue();
        String email = register.findPath("email").textValue();

        libraryManager.register(fullName, mobileNumber, email);
        return ok();
    }

    //Display Items
    public Result displayBook() {

        return ok(Json.toJson(libraryManager.displayBooks()));
    }

    public Result displayDVD() {
        return ok(Json.toJson(libraryManager.displayDVD()));
    }

    public Result report() throws ParseException, SQLException {

        return ok(Json.toJson(libraryManager.generateReport()));
    }

    //Reservation
    public Result reserve(String isbn) throws SQLException {
        Statement statement = connection.createStatement();
        String iSBN = "";
        int reserve = 0;
        String date = "";
        ResultSet book = statement.executeQuery("select * from borroweditems where isbn='" + isbn + "'");
        while (book.next()) {
            iSBN = book.getString("isbn");
            reserve = book.getInt("reservation");
            date = book.getString("date");

        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Date time = new Date();


        String currentTime = timeFormat.format(time);

        double fine = 0;
        int hour = 0;


        LocalDate date1 = LocalDate.parse(date, formatter);
        long daysBetween = ChronoUnit.DAYS.between(date1, currentDate);

        PreparedStatement pst = (PreparedStatement) connection.prepareStatement("update  borroweditems " +
                "set reservation=? where isbn='" + iSBN + "'");
        pst.setInt(1, reserve + 1);
        pst.execute();

        if (Ebean.find(BookTable.class, isbn) != null) {
            int diff = 7 - (int) daysBetween;
            if (daysBetween > 7) {
                Messages msg = new Messages("Book is Overdue. If the book is available.\nWe will inform you!");

            } else {
                Messages msg = new Messages("You will get book within " + diff + " days!");
            }
        } else if (Ebean.find(DVDTable.class, isbn) != null) {
            int diff = 7 - (int) daysBetween;
            if (daysBetween > 3) {
                Messages msg = new Messages("DVD is Overdue. If the DVD is available.\nWe will inform you!");

            } else {
                Messages msg = new Messages("You will get DVD within " + diff + " days!");
            }

        }
        return ok(Messages.getMessage());
    }
}
