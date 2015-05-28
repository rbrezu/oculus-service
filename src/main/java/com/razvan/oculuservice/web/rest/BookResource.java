package com.razvan.oculuservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.razvan.oculuservice.domain.Book;
import com.razvan.oculuservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @Inject
    private BookRepository bookRepository;

    /**
     * POST  /books -> Create a new book.
     */
    @RequestMapping(value = "/books",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to save Book : {}", book);
        if (book.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new book cannot already have an ID").build();
        }
        bookRepository.save(book);
        return ResponseEntity.created(new URI("/api/books/" + book.getId())).build();
    }

    /**
     * PUT  /books -> Updates an existing book.
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to update Book : {}", book);
        if (book.getId() == null) {
            return create(book);
        }
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /books -> get all the books.
     */
    @RequestMapping(value = "/books",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Book> getAll() {
        log.debug("REST request to get all Books");
        return bookRepository.findAll();
    }

    /**
     * GET  /books/:id -> get the "id" book.
     */
    @RequestMapping(value = "/books/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    /**
     * DELETE  /books/:id -> delete the "id" book.
     */
    @RequestMapping(value = "/books/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.delete(id);
    }
}
