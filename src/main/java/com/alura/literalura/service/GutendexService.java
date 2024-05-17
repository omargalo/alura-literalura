package com.alura.literalura.service;

import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    public GutendexService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Book> buscarLibros(String query) {
        String url = UriComponentsBuilder.fromHttpUrl("https://gutendex.com/books")
                .queryParam("search", query)
                .toUriString();

        BooksResponse response = restTemplate.getForObject(url, BooksResponse.class);
        return response != null ? response.getResults() : List.of();
    }

    public BookEntity guardarLibro(Book libro) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(libro.getTitle());

        List<AuthorEntity> authorEntities = libro.getAuthors().stream()
                .map(author -> {
                    AuthorEntity authorEntity = new AuthorEntity();
                    authorEntity.setName(author.getName());
                    authorEntity.setBirthYear(author.getBirth_year());
                    authorEntity.setDeathYear(author.getDeath_year());
                    authorEntity.setBook(bookEntity);
                    return authorEntity;
                }).collect(Collectors.toList());

        bookEntity.setAuthors(authorEntities);

        return bookRepository.save(bookEntity);
    }
}
