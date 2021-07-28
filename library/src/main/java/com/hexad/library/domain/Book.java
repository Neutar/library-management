package com.hexad.library.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.UUID;

@Entity
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String author;
    private String text;

}

