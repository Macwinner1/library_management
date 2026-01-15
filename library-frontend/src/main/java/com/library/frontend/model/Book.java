package com.library.frontend.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Book {

    private final LongProperty id;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty isbn;
    private final ObjectProperty<LocalDate> publishedDate;

    public Book() {
        this(null, "", "", "", null);
    }

    public Book(Long id, String title, String author, String isbn, LocalDate publishedDate) {
        this.id = new SimpleLongProperty(id != null ? id : 0);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.publishedDate = new SimpleObjectProperty<>(publishedDate);
    }

    // ID Property
    public Long getId() {
        return id.get();
    }

    public void setId(Long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }

    // Title Property
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String value) {
        title.set(value);
    }

    public StringProperty titleProperty() {
        return title;
    }

    // Author Property
    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String value) {
        author.set(value);
    }

    public StringProperty authorProperty() {
        return author;
    }

    // ISBN Property
    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String value) {
        isbn.set(value);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    // Published Date Property
    public LocalDate getPublishedDate() {
        return publishedDate.get();
    }

    public void setPublishedDate(LocalDate value) {
        publishedDate.set(value);
    }

    public ObjectProperty<LocalDate> publishedDateProperty() {
        return publishedDate;
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