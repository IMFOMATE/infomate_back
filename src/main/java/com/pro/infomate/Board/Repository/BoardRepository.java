package com.pro.infomate.Board.Repository;

import com.pro.infomate.Board.entity.Board;
import com.pro.infomate.Board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {



}
