package com.Raf.discoveryBooks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private String filePath;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER)
    @JsonIgnore
    private Writer writer;

}
