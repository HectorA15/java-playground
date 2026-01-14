package collections.libraryExample;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    LibraryManager services = new LibraryManager();

    @Test
    public void testDelete() {
        services.deleteBook(1241412);
        assertEquals(0, services.getAllBooks().size());
        services.registerBook(new Book("1984", "Orwell", "Distopia", 451L, 1949));
        assertEquals(1, services.getAllBooks().size());
        services.deleteBook(451L);
        assertEquals(0, services.getAllBooks().size());
        services.registerBook(new Book("1984", "Orwell", "Distopia", 451L, 1949));
        services.registerBook(new Book("The Lord of the Rings", "Tolkien", "Fantasy", 612145267, 2001));
        services.registerBook(new Book("1984", "Orwell", "Distopia", 451351221, 1949));
        services.registerBook(new Book("Dune", "Frank Herbert", "SciFi", 847141221, 1965));
        assertEquals(4, services.getAllBooks().size());
    }

    @Test
    public void testRegister() {
        assertEquals(0, services.getAllBooks().size());
        Book book = new Book("1984", "Orwell", "Distopia", 451L, 1949);
        services.registerBook(book);
        assertEquals(1, services.getAllBooks().size());
        services.registerBook(new Book("Dune", "Frank Herbert", "SciFi", 847141221, 1965));
        assertEquals(2, services.getAllBooks().size());
    }

    @Test
    public void testSearch() {
        services.registerBook(new Book("1984", "Orwell", "Distopia", 451L, 1949));
        services.registerBook(new Book("The Lord of the Rings", "Tolkien", "Fantasy", 612145267, 2001));
        services.registerBook(new Book("Dune", "Frank Herbert", "SciFi", 847141221, 1965));
        services.registerBook(new Book("1984", "Orwell", "Distopia", 451L, 1949));
        List<Book> books = services.findBooksByGenre("Fantasy");
        assertEquals(1, books.size());
        books = services.findBooksByGenre("Distopia");
        assertEquals(1, books.size());
        books = services.listBy("Distopia");
        assertEquals(1, books.size());
    }
}
