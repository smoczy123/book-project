/*
    The book project lets a user keep track of different books they've read, are currently reading or would like to read
    Copyright (C) 2020  Karan Kumar

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.karankumar.bookproject.backend.service;

import com.karankumar.bookproject.backend.model.Book;
import com.karankumar.bookproject.backend.repository.AuthorRepository;
import com.karankumar.bookproject.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Spring service that acts as the gateway to the {@code BookRepository} -- to use the {@code BookRepository},
 * a consumer should go via this {@code BookService}
 */
@Service
public class BookService extends BaseService<Book, Long> {

    private final AuthorRepository authorRepository;
    private BookRepository bookRepository;

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book save(Book book) {
        if (book != null) {
            if (book.getAuthor() != null) {
                authorRepository.save(book.getAuthor());
                LOGGER.log(Level.INFO, "Saving author: " + book.getAuthor());
            } else {
                LOGGER.log(Level.SEVERE, "Author is null");
            }

            if (book.getShelf() != null) {
                if (book.getShelf().getShelfName() == null) {
                    LOGGER.log(Level.SEVERE, "Shelf name should not be null");
                } else {
                    LOGGER.log(Level.INFO, "Shelf name (" + book.getShelf().getShelfName() + ") is not null");



                }
            } else {
                LOGGER.log(Level.SEVERE, "Shelf is null");
            }

            LOGGER.log(Level.INFO, "Book repository count before: " + bookRepository.count());
            bookRepository.save(book);
            LOGGER.log(Level.INFO, "Book repository count after: " + bookRepository.count());

            LOGGER.log(Level.INFO, book.getTitle() + " saved");
            return book;
        } else {
            LOGGER.log(Level.SEVERE, "Null book");
            return null;
        }
    }

    public Long count() {
        return bookRepository.count();
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.search(filterText);
    }

    @Override
    public void delete(Book book) {
        if (book == null) {
            LOGGER.log(Level.SEVERE, "Cannot delete an null book.");
            return;
        }

        LOGGER.log(Level.INFO, "Deleting book. Book repository size = " + bookRepository.count());
        bookRepository.delete(book);

        List<Book> books = bookRepository.findAll();
        if (books.contains(book)) {
            LOGGER.log(Level.SEVERE, book.getTitle() + " not deleted");
        } else {
            LOGGER.log(Level.INFO, book.getTitle() + " deleted. Book repository size = " + bookRepository.count());
        }
    }
}
