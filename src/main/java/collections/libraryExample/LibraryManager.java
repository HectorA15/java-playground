package collections.libraryExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManager {

    private final Map<Long, Book> catalogue = new HashMap<>();


    public void registerBook(Book book) {
        catalogue.put(book.getISBN(), book);
    }


    public boolean deleteBook(long isbn) {
        return catalogue.remove(isbn) != null;
    }

    public Book findBookByIsbn(long isbn) {
        return catalogue.get(isbn);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(catalogue.values());
    }

    public List<Book> findBooksByGenre(String genre) {
        return catalogue.values().stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    public boolean exists(long isbn) {
        return catalogue.containsKey(isbn);
    }

    public void printCatalogue(String type, Map<Long, Book> catalogue) {
        catalogue.forEach((isbn, book) -> System.out.println(book));
    }

    public List<Book> listBy(String type) {
        List<Book> booksFound = catalogue.values().stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(type))
                .toList();

        return booksFound;
    }
}
