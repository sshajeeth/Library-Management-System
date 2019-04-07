package services;

import tables.BookTable;
import tables.BorrowedItemTable;
import tables.DVDTable;

import java.sql.SQLException;
import java.text.ParseException;

public interface LibraryManager {

    void addBook(String isbn, String title, String publisher, String published_date, int no_of_pages,
                 String sector, String author);

    void addDVD(String isbn, String title, String producer, String actor, String published_date,
                String sector, String subtitles, String languages);

    void deleteItem(String isbn);

    void borrowItem(String isbn, String full_Name) throws SQLException;

    void returnItem(String isbn) throws SQLException, ParseException;

    void register(String name, String mobile_number, String email);

    BookTable[] displayBooks();

    DVDTable[] displayDVD();

    BorrowedItemTable[] generateReport() throws SQLException, ParseException;

}
