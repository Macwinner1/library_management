package com.library.frontend.model;

import com.google.gson.annotations.Expose;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Book {

    @Expose
    private Long id;
    @Expose
    private String title;
    @Expose
    private String author;
    @Expose
    private String isbn;
    @Expose
    private LocalDate publishedDate;

    private transient LongProperty idProperty;
    private transient StringProperty titleProperty;
    private transient StringProperty authorProperty;
    private transient StringProperty isbnProperty;
    private transient ObjectProperty<LocalDate> publishedDateProperty;

    public Book() {
        this(null, "", "", "", null);
    }

    public Book(Long id, String title, String author, String isbn, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    // ID Property
    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
        idProperty().set(value != null ? value : 0);
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, "id", id != null ? id : 0);
        }
        return idProperty;
    }

    // Title Property
    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
        titleProperty().set(value);
    }

    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this, "title", title);
        }
        return titleProperty;
    }

    // Author Property
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
        authorProperty().set(value);
    }

    public StringProperty authorProperty() {
        if (authorProperty == null) {
            authorProperty = new SimpleStringProperty(this, "author", author);
        }
        return authorProperty;
    }

    // ISBN Property
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String value) {
        this.isbn = value;
        isbnProperty().set(value);
    }

    public StringProperty isbnProperty() {
        if (isbnProperty == null) {
            isbnProperty = new SimpleStringProperty(this, "isbn", isbn);
        }
        return isbnProperty;
    }

    // Published Date Property
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate value) {
        this.publishedDate = value;
        publishedDateProperty().set(value);
    }

    public ObjectProperty<LocalDate> publishedDateProperty() {
        if (publishedDateProperty == null) {
            publishedDateProperty = new SimpleObjectProperty<>(this, "publishedDate", publishedDate);
        }
        return publishedDateProperty;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", publishedDate=" + getPublishedDate() +
                '}';
    }
}