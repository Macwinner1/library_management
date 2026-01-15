package com.library.frontend;

import com.library.frontend.model.Book;
import com.library.frontend.service.BookApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryView extends BorderPane {

    private final BookApiService apiService;
    private final TableView<Book> tableView;
    private final ObservableList<Book> bookList;

    private final TextField titleField;
    private final TextField authorField;
    private final TextField isbnField;
    private final DatePicker publishedDatePicker;
    private final TextField searchField;

    private Book selectedBook = null;

    public LibraryView() {
        this.apiService = new BookApiService();
        this.bookList = FXCollections.observableArrayList();
        this.tableView = new TableView<>();

        // Initialize form fields
        this.titleField = new TextField();
        this.authorField = new TextField();
        this.isbnField = new TextField();
        this.publishedDatePicker = new DatePicker();
        this.searchField = new TextField();

        initializeUI();
        loadBooks();
    }

    private void initializeUI() {
        // Set up the table
        setupTableView();

        // Create form panel
        VBox formPanel = createFormPanel();

        // Create button panel
        HBox buttonPanel = createButtonPanel();

        // Create search panel
        HBox searchPanel = createSearchPanel();

        // Layout
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.getChildren().addAll(searchPanel, formPanel, buttonPanel);
        rightPanel.setMinWidth(350);

        this.setCenter(tableView);
        this.setRight(rightPanel);
        this.setPadding(new Insets(10));
    }

    private void setupTableView() {
        // ID Column
        TableColumn<Book, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        // Title Column
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(200);

        // Author Column
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(150);

        // ISBN Column
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnCol.setPrefWidth(120);

        // Published Date Column
        TableColumn<Book, LocalDate> dateCol = new TableColumn<>("Published Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        dateCol.setPrefWidth(120);

        tableView.getColumns().addAll(idCol, titleCol, authorCol, isbnCol, dateCol);
        tableView.setItems(bookList);

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        populateForm(newSelection);
                    }
                });
    }

    private VBox createFormPanel() {
        VBox formPanel = new VBox(10);
        formPanel.setPadding(new Insets(10));
        formPanel.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 5;");

        Label formTitle = new Label("Book Details");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        titleField.setPromptText("Title");
        authorField.setPromptText("Author");
        isbnField.setPromptText("ISBN");
        publishedDatePicker.setPromptText("Published Date");

        formPanel.getChildren().addAll(
                formTitle,
                new Label("Title:"), titleField,
                new Label("Author:"), authorField,
                new Label("ISBN:"), isbnField,
                new Label("Published Date:"), publishedDatePicker
        );

        return formPanel;
    }

    private HBox createButtonPanel() {
        HBox buttonPanel = new HBox(10);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

        addButton.setOnAction(e -> addBook());
        updateButton.setOnAction(e -> updateBook());
        deleteButton.setOnAction(e -> deleteBook());
        clearButton.setOnAction(e -> clearForm());

        buttonPanel.getChildren().addAll(addButton, updateButton, deleteButton, clearButton);

        return buttonPanel;
    }

    private HBox createSearchPanel() {
        HBox searchPanel = new HBox(10);
        searchPanel.setPadding(new Insets(10));
        searchPanel.setAlignment(Pos.CENTER_LEFT);

        Label searchLabel = new Label("Search:");
        searchField.setPromptText("Title or Author");
        searchField.setPrefWidth(180);

        Button searchButton = new Button("Search");
        Button refreshButton = new Button("Refresh All");

        searchButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        refreshButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white;");

        searchButton.setOnAction(e -> searchBooks());
        refreshButton.setOnAction(e -> loadBooks());

        searchPanel.getChildren().addAll(searchLabel, searchField, searchButton, refreshButton);

        return searchPanel;
    }

    private void populateForm(Book book) {
        selectedBook = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        publishedDatePicker.setValue(book.getPublishedDate());
    }

    private void clearForm() {
        selectedBook = null;
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedDatePicker.setValue(null);
        tableView.getSelectionModel().clearSelection();
    }

    private void loadBooks() {
        try {
            List<Book> books = apiService.getAllBooks();
            bookList.clear();
            bookList.addAll(books);
            searchField.clear();
        } catch (Exception e) {
            showError("Failed to load books", e.getMessage());
        }
    }

    private void addBook() {
        if (!validateForm()) {
            return;
        }

        try {
            Book newBook = new Book(
                    null,
                    titleField.getText(),
                    authorField.getText(),
                    isbnField.getText(),
                    publishedDatePicker.getValue()
            );

            Book createdBook = apiService.addBook(newBook);
            bookList.add(createdBook);
            clearForm();
            showSuccess("Book added successfully!");

        } catch (Exception e) {
            showError("Failed to add book", e.getMessage());
        }
    }

    private void updateBook() {
        if (selectedBook == null) {
            showWarning("No book selected", "Please select a book to update.");
            return;
        }

        if (!validateForm()) {
            return;
        }

        try {
            Book updatedBook = new Book(
                    selectedBook.getId(),
                    titleField.getText(),
                    authorField.getText(),
                    isbnField.getText(),
                    publishedDatePicker.getValue()
            );

            apiService.updateBook(selectedBook.getId(), updatedBook);

            // Update the book in the list
            int index = bookList.indexOf(selectedBook);
            if (index >= 0) {
                bookList.set(index, updatedBook);
            }

            clearForm();
            showSuccess("Book updated successfully!");

        } catch (Exception e) {
            showError("Failed to update book", e.getMessage());
        }
    }

    private void deleteBook() {
        if (selectedBook == null) {
            showWarning("No book selected", "Please select a book to delete.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Delete Book");
        confirmation.setContentText("Are you sure you want to delete this book?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                apiService.deleteBook(selectedBook.getId());
                bookList.remove(selectedBook);
                clearForm();
                showSuccess("Book deleted successfully!");

            } catch (Exception e) {
                showError("Failed to delete book", e.getMessage());
            }
        }
    }

    private void searchBooks() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            loadBooks();
            return;
        }

        try {
            List<Book> books = apiService.searchBooks(searchTerm);
            bookList.clear();
            bookList.addAll(books);

            if (books.isEmpty()) {
                showInfo("No results", "No books found matching: " + searchTerm);
            }

        } catch (Exception e) {
            showError("Failed to search books", e.getMessage());
        }
    }

    private boolean validateForm() {
        if (titleField.getText().trim().isEmpty()) {
            showWarning("Validation Error", "Title is required.");
            return false;
        }

        if (authorField.getText().trim().isEmpty()) {
            showWarning("Validation Error", "Author is required.");
            return false;
        }

        if (isbnField.getText().trim().isEmpty()) {
            showWarning("Validation Error", "ISBN is required.");
            return false;
        }

        if (publishedDatePicker.getValue() == null) {
            showWarning("Validation Error", "Published Date is required.");
            return false;
        }

        return true;
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}