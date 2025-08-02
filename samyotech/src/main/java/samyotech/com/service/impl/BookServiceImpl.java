package samyotech.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samyotech.com.exception.ResourceNotFoundException;
import samyotech.com.model.Book;
import samyotech.com.repository.BookRepository;
import samyotech.com.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book post) {
        return bookRepository.save(post);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {

        return bookRepository.findById(id).map(existingBook -> {
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        return bookRepository.save(existingBook);
    }).orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    @Override
    public Book deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.deleteById(id);
                    return book;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);

        return bookList;
    }


}
