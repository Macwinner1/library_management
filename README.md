# Library Management System

A full-stack library management application with a Spring Boot backend and JavaFX desktop frontend.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete books
- **Search Functionality**: Search books by title or author
- **RESTful API**: Backend exposes REST endpoints for book management
- **Desktop UI**: JavaFX-based graphical user interface
- **In-Memory Database**: H2 database for quick setup and testing

## Technology Stack

### Backend
- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Maven**

### Frontend
- **Java 17+**
- **JavaFX 21**
- **Gson** (for JSON serialization)
- **Maven**

## Prerequisites

Before running the application, ensure you have the following installed:

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **Set JAVA_HOME environment variable**
   - Windows: `C:\Program Files\Java\jdk-21` (or your JDK path)
   - Add to System Environment Variables

**Note**: Maven is NOT required as the project includes Maven Wrapper scripts (`mvnw.cmd` for Windows).

## Setup Instructions

### 1. Clone or Download the Project

```bash
cd library_management
```

### 2. Verify Java Installation

```bash
java -version
```

You should see Java 17 or higher.

### 3. Set JAVA_HOME (if not already set)

**Windows (PowerShell):**
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
```

**Windows (Command Prompt):**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-21
```

## Running the Application

### Option 1: Run Both Applications (Recommended)

**Step 1: Start the Backend**

Open a terminal/command prompt and run:

```bash
cd library-backend
mvnw.cmd spring-boot:run
```

Wait for the message: `Library Management System Backend is running on http://localhost:8080`

**Step 2: Start the Frontend**

Open a **NEW** terminal/command prompt and run:

```bash
cd library-frontend
mvnw.cmd javafx:run
```

The JavaFX window will open automatically.

### Option 2: Run with PowerShell (Single Command)

**Terminal 1 (Backend):**
```powershell
cd library-backend
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; .\mvnw.cmd spring-boot:run
```

**Terminal 2 (Frontend):**
```powershell
cd library-frontend
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; .\mvnw.cmd javafx:run
```

## Using the Application

### Frontend Interface

Once the JavaFX window opens, you can:

1. **View All Books**
   - Books are displayed in the table automatically
   - Click "Refresh All" to reload the list

2. **Add a New Book**
   - Fill in the form fields (Title, Author, ISBN, Published Date)
   - Click "Add" button
   - The book will appear in the table

3. **Update a Book**
   - Select a book from the table
   - Modify the form fields
   - Click "Update" button

4. **Delete a Book**
   - Select a book from the table
   - Click "Delete" button
   - Confirm the deletion

5. **Search Books**
   - Enter a search term in the search field
   - Click "Search" button
   - Results will be filtered by title or author

6. **Clear Form**
   - Click "Clear" to reset the form fields

### Backend API Endpoints

The backend exposes the following REST endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Get all books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Create a new book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |
| GET | `/api/books/search?q={term}` | Search books |

### Testing the API with curl

```bash
# Get all books
curl http://localhost:8080/api/books

# Add a new book
curl -X POST http://localhost:8080/api/books ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"1984\",\"author\":\"George Orwell\",\"isbn\":\"978-0-452-28423-4\",\"publishedDate\":\"1949-06-08\"}"

# Search books
curl "http://localhost:8080/api/books/search?q=Orwell"
```

### H2 Database Console

Access the H2 database console at: http://localhost:8080/h2-console

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:librarydb`
- Username: `sa`
- Password: (leave empty)

## Configuration

### Backend Configuration

Edit `library-backend/src/main/resources/application.properties`:

```properties
# Change server port
server.port=8080

# Database configuration
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.username=sa
spring.datasource.password=

# Enable H2 console
spring.h2.console.enabled=true
```

### Frontend Configuration

Edit `library-frontend/src/main/java/com/library/frontend/service/BookApiService.java`:

```java
// Change backend URL if needed
private static final String BASE_URL = "http://localhost:8080/api/books";
```

## Troubleshooting

### Issue: "JAVA_HOME not found"

**Solution:** Set the JAVA_HOME environment variable:
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
```

### Issue: "Port 8080 already in use"

**Solution:** Either:
1. Stop the process using port 8080
2. Change the port in `application.properties`

### Issue: "Failed to load books"

**Solution:** 
1. Ensure the backend is running on port 8080
2. Check the backend console for errors
3. Verify the backend URL in `BookApiService.java`

### Issue: Maven wrapper not executing

**Solution:** Make sure you're in the correct directory:
```bash
cd library-backend  # or library-frontend
.\mvnw.cmd spring-boot:run  # or javafx:run
```

### Issue: Frontend window doesn't open

**Solution:**
1. Check if JavaFX dependencies downloaded successfully
2. Ensure Java 17+ is installed
3. Check the terminal for error messages

## Stopping the Application

1. **Frontend**: Close the JavaFX window or press `Ctrl+C` in the terminal
2. **Backend**: Press `Ctrl+C` in the terminal

## License

This project is for educational purposes.

## Support

For issues or questions, please check:
1. Java version compatibility
2. JAVA_HOME environment variable
3. Backend is running before starting frontend
4. Port 8080 is available

---
