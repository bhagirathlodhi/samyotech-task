package samyotech.com.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samyotech.com.exception.ResourceNotFoundException;
import samyotech.com.model.ApiResponse;
import samyotech.com.model.Book;
import samyotech.com.service.BookService;

import java.util.List;

@RestController  
@RequestMapping("/api/posts")
public class BookController {

    @Autowired
    private BookService bookService;

    //Publishing the book
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book post) {
        Book created = bookService.createBook(post);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Fetching All the books
    @GetMapping
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        ApiResponse response = new ApiResponse(true, "Books fetched successfully", books);
        return ResponseEntity.ok(response);
    }

    //Fetching the Book By Id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
        return ResponseEntity.ok(book);
    }

    //Updating the Book by id
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    //Deleting the Targeted Book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book deletedBook  = bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    //Serching the trageted book by title name
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

}
