package com.library.frontend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.library.frontend.model.Book;
import com.library.frontend.util.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookApiService {

    private static final String BASE_URL = "http://localhost:8080/api/books";
    private final HttpClient httpClient;
    private final Gson gson;

    public BookApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    // Fetch all books
    public List<Book> getAllBooks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), new TypeToken<List<Book>>(){}.getType());
        } else if (response.statusCode() == 204) {
            return new ArrayList<>();
        } else {
            throw new IOException("Failed to fetch books. Status code: " + response.statusCode());
        }
    }

    // Add a new book
    public Book addBook(Book book) throws IOException, InterruptedException {
        String jsonBody = gson.toJson(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return gson.fromJson(response.body(), Book.class);
        } else {
            throw new IOException("Failed to add book. Status code: " + response.statusCode());
        }
    }

    // Update an existing book
    public Book updateBook(Long id, Book book) throws IOException, InterruptedException {
        String jsonBody = gson.toJson(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), Book.class);
        } else if (response.statusCode() == 404) {
            throw new IOException("Book not found with id: " + id);
        } else {
            throw new IOException("Failed to update book. Status code: " + response.statusCode());
        }
    }

    // Delete a book
    public void deleteBook(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            if (response.statusCode() == 404) {
                throw new IOException("Book not found with id: " + id);
            } else {
                throw new IOException("Failed to delete book. Status code: " + response.statusCode());
            }
        }
    }

    // Search books - Bonus feature
    public List<Book> searchBooks(String searchTerm) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/search?q=" + searchTerm))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), new TypeToken<List<Book>>(){}.getType());
        } else if (response.statusCode() == 204) {
            return new ArrayList<>();
        } else {
            throw new IOException("Failed to search books. Status code: " + response.statusCode());
        }
    }
}