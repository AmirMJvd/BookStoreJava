package model;
import model.Book;

import java.util.List;

//package model;
//
//import java.util.List;
//
//public class BookLists {
//    private List<Book> allBooks;
//    private List<Book> scientificBooks;
//    private List<Book> politicalBooks;
//    private List<Book> psychologyBooks;
//
//    public BookLists(List<Book> allBooks, List<Book> scientificBooks, List<Book> politicalBooks, List<Book> psychologyBooks) {
//        this.allBooks = allBooks;
//        this.scientificBooks = scientificBooks;
//        this.politicalBooks = politicalBooks;
//        this.psychologyBooks = psychologyBooks;
//    }
//
//    public List<Book> getAllBooks() {
//        return allBooks;
//    }
//
//    public List<Book> getScientificBooks() {
//        return scientificBooks;
//    }
//
//    public List<Book> getPoliticalBooks() {
//        return politicalBooks;
//    }
//
//    public List<Book> getPsychologyBooks() {
//        return psychologyBooks;
//    }
//}
public class BookLists {
    private List<Book> allBooks;
    private List<Book> scientificBooks;
    private List<Book> historicalBooks;
    private List<Book> psychologyBooks;
    private List<Book> politicalBooks;  // اضافه کردن politicalBooks

    // سازنده با پنج لیست
    public BookLists(List<Book> allBooks, List<Book> scientificBooks,
                     List<Book> historicalBooks, List<Book> psychologyBooks,
                     List<Book> politicalBooks) {
        this.allBooks = allBooks;
        this.scientificBooks = scientificBooks;
        this.historicalBooks = historicalBooks;
        this.psychologyBooks = psychologyBooks;
        this.politicalBooks = politicalBooks;  // مقداردهی politicalBooks
    }

    // متدهای گتر برای دسترسی به لیست‌ها
    public List<Book> getAllBooks() {
        return allBooks;
    }

    public List<Book> getScientificBooks() {
        return scientificBooks;
    }

    public List<Book> getHistoricalBooks() {
        return historicalBooks;
    }

    public List<Book> getPsychologyBooks() {
        return psychologyBooks;
    }

    public List<Book> getPoliticalBooks() {
        return politicalBooks;  // اضافه کردن متد گتر برای politicalBooks
    }
}

