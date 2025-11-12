# 📚 Library Management System (Java + MongoDB + React)

A full-stack Library Management System with a **Spring Boot (Java)** backend and a **React + Tailwind** frontend.  
This app supports adding books, students, issuing books, and syncing data across campuses (North, South, Central).

---

## ⚙️ Requirements
Before running the project, install:
- Java 17+ 
- Maven 3.9+ 
- Node.js 18+ and npm
- MongoDB

---

## 🖥️ Folder Structure
Library_Management_System_JAVA/
├─ BackEnd/ ← Backend (Spring Boot)

│ ├─ src/

│ └─ pom.xml

├─ FrontEnd/ ← Frontend (React + Tailwind)

  ├─ src/

  └─ package.json


---

## 🚀 How to Run

### 1️⃣ Backend (Spring Boot)
> cd BackEnd
> mvn spring-boot:run

### 2️⃣ Frontend (React)
> cd FrontEnd
> npm install
> npm run dev
Note: Runs on http://localhost:5173

---

## 🔗 Backend–Frontend Connection
The frontend is preconfigured to call the backend at: http://localhost:8080/api
‼️If you see a CORS error in the browser console‼️
    open `LibraryController.java` and add this line above the class: @CrossOrigin(origins = "*")

---

## 🧩 Features
### Add Book
- Fill in Book ID, Title, Author, Publisher, and select Campus.
- Click Add Book to store it in MongoDB.

### Add Student
- Enter Student ID, Name, Department, and Campus.
- Click Add Student to add a student record.

### Issue Book
- Fill Issue ID, Book ID, Student ID, Issue Date, and Campus.
- Click Issue Book to log the issue transaction.

### View Data
- North Campus Books / South Campus Books show respective data.
- Central (Aggregated) fetches combined data from all campuses.
- Unreturned Books lists books that haven’t been returned.

---

## 🧠 Common Problems
### ❌ Backend fails to start
Ensure MongoDB is running locally.
Make sure Java 17+ is installed and on PATH.
### ❌ CORS Error (frontend can’t reach backend)
Add this above your controller: @CrossOrigin(origins = "*")
### ❌ Ports Busy
- Backend: 8080
- Frontend: 5173
Change server.port in application.yml if needed.

---

## 🧱 Dependencies
### Backend
- Spring Boot 3+
- Spring Data MongoDB
- MongoDB Driver
- Maven
### Frontend
- React (Vite)
- TailwindCSS
- Axios

---

## ✅ Quick Demo Steps
1. Start MongoDB (if not already running)
2. Run backend:
    > cd BackEnd
    > mvn spring-boot:run
3. Run frontend:
    > cd FrontEnd
    > npm run dev

4. Visit http://localhost:5173
