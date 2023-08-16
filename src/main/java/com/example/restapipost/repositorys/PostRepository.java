package com.example.restapipost.repositorys;

import com.example.restapipost.entity.PostOffices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostOffices,Long> {
}
