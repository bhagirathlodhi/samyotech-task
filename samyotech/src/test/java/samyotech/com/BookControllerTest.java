package samyotech.com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import samyotech.com.controller.BookController;
import samyotech.com.model.Book;
import samyotech.com.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Book book1 = new Book(1L, "Java Basics", "bhagirath", 12345, "2020");


    // Positive test case to publish the book
    @Test
    void createBook_ValidDataCreated() throws Exception {
        Book validBook = new Book(null, "Java Basics", "Bhagirath", 2020, "9781234567890");

        when(bookService.createBook(any(Book.class))).thenReturn(validBook);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    //Nigative test cases to publish the book
    @Test
    void createBookBlankTitleBadRequest() throws Exception {
        Book invalidBook = new Book(null, "", "Author", 2020, "9781234567890");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title must not be blank"));
    }



    //Fetching all the Books
    @Test
    void getAllBooksList() throws Exception {
        List<Book> mockBooks = List.of(
                new Book(1L, "Java Basics", "Bhagirath", 2020, "9781234567890"),
                new Book(2L, "Spring Boot", "Yogesh", 2023, "9780987654321")
        );

        when(bookService.getAllBooks()).thenReturn(mockBooks);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Books fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Java Basics"));
    }



    //Fetching the Existing Book By Id ----Positive
    @Test
    void getBookByIdValidIdBook() throws Exception {
        Long bookId = 1L;
        Book book = new Book(bookId, "Effective Java", "Joshua Bloch", 2018, "9780134685991");

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/posts/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    //Nigative case to find the book by id
    @Test
    void getBookById_InvalidId_ReturnsNotFound() throws Exception {
        Long invalidId = 100L;

        when(bookService.getBookById(invalidId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/posts/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Book with ID 100 not found"));
    }

}
