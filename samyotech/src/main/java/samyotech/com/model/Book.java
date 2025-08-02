package samyotech.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotBlank(message = "Title must not be blank")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Author must not be blank")
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull(message = "Publication year is required")
    @Min(value = 1500, message = "Publication year must be valid")
    @Max(value = 2100, message = "Publication year must be valid")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @NotBlank(message = "ISBN must not be blank")
    @Pattern(regexp = "^(97(8|9))?[-]?[\\d]{1,5}[-]?[\\d]{1,7}[-]?[\\d]{1,7}[-]?[\\dX]$", message = "ISBN format is invalid")
    private String isbn;




}
