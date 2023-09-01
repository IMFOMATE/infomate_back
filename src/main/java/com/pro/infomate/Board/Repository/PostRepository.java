package com.pro.infomate.Board.Repository;

import com.pro.infomate.Board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByBoardCode(int i);


    Page<Post> findAll(Pageable paging);

//    List<Post> findBy(int i);

//    List<Post> findByBoardId(int i);
}