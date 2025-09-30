package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.RegisterBookDTO;
import io.github.eglecia.sblibrary.controller.dto.ResultBookDTO;
import io.github.eglecia.sblibrary.controller.mappers.BookMapper;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.model.EBookGenre;
import io.github.eglecia.sblibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController implements GenericController{
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Void> createBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        // Mapear DTO para entidade
        Book book = bookMapper.toEntity(bookDTO);
        // Salvar o objeto criado
        bookService.save(book);
        // Criar o URI do recurso recém-criado
        var url = createHeaderLocation(book.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<ResultBookDTO> detailBook(@PathVariable("id") String id) {
        UUID idBook = UUID.fromString(id);

        return bookService
                .findById(idBook)
                .map(book -> {
                    ResultBookDTO bookDTO = bookMapper.toDTO(book);
                    return ResponseEntity.ok(bookDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") String id){
        UUID idBook = UUID.fromString(id);

        return bookService.findById(idBook)
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Page<ResultBookDTO>> findBooks(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) EBookGenre genre,
            @RequestParam(value = "year-published", required = false) Integer yearPublished,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        Page<Book> pageResult = bookService.find(isbn, title, authorName, genre, yearPublished, page, pageSize);

        Page<ResultBookDTO> listDTO = pageResult.map(bookMapper::toDTO);

        return ResponseEntity.ok(listDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Object> updateBook(
            @PathVariable("id") String id,
            @RequestBody @Valid RegisterBookDTO bookDTO
    ) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    Book updatedBook = bookMapper.toEntity(bookDTO);

                    book.setIsbn(updatedBook.getIsbn());
                    book.setTitle(updatedBook.getTitle());
                    book.setDtPublished(updatedBook.getDtPublished());
                    book.setGenre(updatedBook.getGenre());
                    book.setPrice(updatedBook.getPrice());
                    book.setAuthor(updatedBook.getAuthor());

                    bookService.update(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
