package com.pro.infomate.Board.Repository;

import com.pro.infomate.Board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {


}
