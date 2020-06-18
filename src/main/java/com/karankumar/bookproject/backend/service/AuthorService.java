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

import com.karankumar.bookproject.backend.model.Author;
import com.karankumar.bookproject.backend.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A Spring service that acts as the gateway to the {@code AuthorRepository} -- to use the {@code AuthorRepository},
 * a consumer should go via this {@code AuthorService}
 */
@Service
public class AuthorService extends BaseService<Author, Long> {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.getOne(id);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author save(Author author) {
        if (author != null) {
            authorRepository.save(author);
            return author;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public Long count() {
        return authorRepository.count();
    }
}
