package model;

import java.util.List;

public class BookLists {
    private List<Book> allBooks;
    private List<Book> scientificBooks;
    private List<Book> politicalBooks;
    private List<Book> psychologyBooks;

    public BookLists(List<Book> allBooks, List<Book> scientificBooks, List<Book> politicalBooks, List<Book> psychologyBooks) {
        this.allBooks = allBooks;
        this.scientificBooks = scientificBooks;
        this.politicalBooks = politicalBooks;
        this.psychologyBooks = psychologyBooks;
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public List<Book> getScientificBooks() {
        return scientificBooks;
    }

    public List<Book> getPoliticalBooks() {
        return politicalBooks;
    }

    public List<Book> getPsychologyBooks() {
        return psychologyBooks;
    }
}
