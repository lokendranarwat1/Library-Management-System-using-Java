package com.example.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String department;
    private String campus;

    public Student() {}

    public Student(String id, String name, String department, String campus) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.campus = campus;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    @Override
    public String toString() {
        return "Student{" + "id='" + id + '\'' + ", name='" + name + '\'' +
                ", department='" + department + '\'' + ", campus='" + campus + '\'' + '}';
    }
}
