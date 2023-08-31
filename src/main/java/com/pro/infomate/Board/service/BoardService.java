package com.pro.infomate.Board.service;


import com.pro.infomate.Board.Repository.BoardRepository;
import com.pro.infomate.Board.Repository.PostRepository;
import com.pro.infomate.Board.dto.BoardDTO;
import com.pro.infomate.Board.dto.PostDTO;
import com.pro.infomate.Board.entity.Board;
import com.pro.infomate.Board.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {

//    private final BoardRepository boardRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BoardService(PostRepository postRepository, ModelMapper modelMapper) {
//        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;

    }

//    public Board getBoardById(int boardCode) {
//        return boardRepository.findById(boardCode)
//                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardCode));
//    }

    public List<PostDTO> getAllBoards() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public String postPost(PostDTO postDTO) {   // 새 글 작성

//        String imageName = UUID.randomUUID().toString().replace("-", "");
//        String replaceFileName = null;
        int result = 0;

        try {
            Post postPost = modelMapper.map(postDTO, Post.class);

            postRepository.save(postPost);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("[PostService] postPost End ====================");
        return (result > 0) ? "게시글 등록 성공" : "게시글 입력 실패";

    }
}