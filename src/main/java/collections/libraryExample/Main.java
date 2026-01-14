package collections.libraryExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Book> b = new ArrayList<>(List.of(
                new Book("The Hobbit", " J.K. Rowling", "Fantasy", 123456789, 2003),
                new Book("The Lord of the Rings", "Tolkien", "Fantasy", 412342467, 2001),
                new Book("The Lord of the Rings", "Tolkien", "Fantasy", 612145267, 2001),
                new Book("1984", "Orwell", "Distopia", 451351221, 1949),
                new Book("Dune", "Frank Herbert", "SciFi", 847141221, 1965)
        ));

        LibraryManager manager = new LibraryManager();

        b.forEach(manager::registerBook);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("====== Welcome to the library System Manager ======");
            System.out.println("                Select an option:");
            System.out.println("1. Register a book");
            System.out.println("2. Delete a book");
            System.out.println("3. Search a book");
            System.out.println("4. List all catalogue");
            System.out.println("5. List by Author");
            System.out.println("6. List by Genre");
            System.out.println("7. Exit");


            System.out.print("Enter your choice: ");


            if (scanner.hasNextLong()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> registerBook(scanner, manager);
                    case 2 -> deleteBook(scanner, manager);
                    case 3 -> searchBookByISBN(scanner, manager);
                    case 4 -> manager.getAllBooks();
                    case 5 -> listBy(scanner, "author", manager);
                    case 6 -> listBy(scanner, "genre", manager);
                    case 7 -> running = false;
                }
            } else {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void registerBook(Scanner scanner, LibraryManager manager) {
        System.out.println("Enter book details");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        long isbn;
        while (true) {
            System.out.print("Enter ISBN: ");
            if (!scanner.hasNextLong()) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                continue;
            }
            isbn = scanner.nextLong();
            scanner.nextLine();
            if (manager.exists(isbn)) {
                System.out.println("Error: ISBN already exists.");
                continue;
            }
            break;
        }
        int year;
        while (true) {
            System.out.print("Enter Year: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                continue;
            }
            year = scanner.nextInt();
            break;
        }

        Book book = new Book(title, author, genre, isbn, year);
        System.out.println("Registering a book...");
        manager.registerBook(book);
        System.out.println("Book '" + title + "' registered successfully!");
    }

    private static void deleteBook(Scanner scanner, LibraryManager manager) {
        System.out.print("Enter ISBN of the book to delete: ");
        long isbn;
        while (true) {
            System.out.print("Enter ISBN: ");
            if (!scanner.hasNextLong()) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                continue;
            }
            isbn = scanner.nextLong();
            scanner.nextLine();
            break;
        }
        if (manager.deleteBook(isbn)) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Error: Book not found.");
        }
    }

    private static void searchBookByISBN(Scanner scanner, LibraryManager manager) {
        System.out.println("Enter ISBN of the book to search: ");
        long isbn;
        while (true) {
            System.out.print("Enter ISBN: ");
            if (!scanner.hasNextLong()) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                continue;
            }
            isbn = scanner.nextLong();
            scanner.nextLine();
            break;
        }
        Book book = manager.findBookByIsbn(isbn);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Error: Book not found.");
        }
    }

    private static void listBy(Scanner scanner, String type, LibraryManager manager) {
        if (type.equals("author")) {
            System.out.println("Enter Author: ");
            String authorInput = scanner.nextLine().trim().toLowerCase();

            System.out.println("Listing by Author...");
            List<Book> booksByAuthor = manager.listBy(authorInput);
            if (booksByAuthor.isEmpty()) {
                System.out.println("No books found for author: " + authorInput);
            } else {
                booksByAuthor.forEach(System.out::println);
            }

        } else if (type.equals("genre")) {
            System.out.println("Enter Genre: ");
            String genre = scanner.nextLine();
            System.out.println("Listing by Genre...");

            List<Book> booksByGenre = manager.listBy(genre);
            if (booksByGenre.isEmpty()) {
                System.out.println("No books found for genre: " + genre);
            } else {
                booksByGenre.forEach(System.out::println);
            }
        }
    }
}

