package com.shep.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonBackReference //when returning JSON to avoid recursion
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;
}