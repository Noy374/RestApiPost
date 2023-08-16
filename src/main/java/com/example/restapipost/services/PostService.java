package com.example.restapipost.services;


import com.example.restapipost.entity.PostOffices;
import com.example.restapipost.repositorys.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public PostOffices getPostById(Long id) {
      return  postRepository.getReferenceById(id);
    }
}
