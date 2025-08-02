package samyotech.com.service;

import jakarta.validation.Valid;
import samyotech.com.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(@Valid Book post);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book updateBook(Long id, @Valid Book updatedBook);

    Book deleteBook(Long id);

    List<Book> searchBooksByTitle(String title);
}
