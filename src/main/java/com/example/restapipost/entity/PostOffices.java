package com.example.restapipost.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class PostOffices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;


    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String branchAddress;

    @OneToMany(mappedBy = "postOffice")
    private List<Movements> movements;
}
