package com.pro.infomate.Board.Repository;

import com.pro.infomate.Board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByBoardCode(int i);


    Page<Post> findAll(Pageable paging);

    Optional<Post> findByPostCode(Integer postCode);

    Optional<Post> findById(int postCode);

    List<Post> findByBoardCategory(String 일반게시판);

//    void deleteAllByPost(int item);


//    List<Post> findBy(int i);

//    List<Post> findByBoardId(int i);
}