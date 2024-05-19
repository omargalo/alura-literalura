package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class BookEntity {

    @Id
    private int id;

    private String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthorEntity> authors;

    @ElementCollection
    private List<String> languages;

    private String htmlFormat;

    private int downloadCount;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getHtmlFormat() {
        return htmlFormat;
    }

    public void setHtmlFormat(String htmlFormat) {
        this.htmlFormat = htmlFormat;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
}
