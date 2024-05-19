package com.alura.literalura.controller;

import com.alura.literalura.model.Book;
import com.alura.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private GutendexService gutendexService;

    @GetMapping("/books")
    public List<Book> buscarLibros(@RequestParam String query) {
        return gutendexService.buscarLibros(query);
    }

    @GetMapping("/books/author")
    public List<Book> buscarLibrosPorAutor(@RequestParam String autor) {
        return gutendexService.buscarLibrosPorAutor(autor);
    }
}
