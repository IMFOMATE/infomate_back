package com.pro.infomate.Board.Repository;

import com.pro.infomate.Board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
