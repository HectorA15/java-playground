package collections.libraryExample;

public class Book {

    private String title;
    private String author;
    private String genre;
    private String publisher;
    private long ISBN;
    private int year;

    public Book() {
    }

    public Book(String title, String author, String genre, long ISBN, int year) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.year = year;
    }

    // =========== toString Method ==========
    @Override
    public String toString() {
        return "[" +
                "title: '" + title + '\'' +
                ", author: '" + author + '\'' +
                ", genre: '" + genre + '\'' +
                ", ISBN: " + ISBN +
                ", year: " + year +
                ']';
    }

    // =========== Getter Methods ==========
    public String getTitle() {
        return title;
    }

    // =========== Setter Methods ==========
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
