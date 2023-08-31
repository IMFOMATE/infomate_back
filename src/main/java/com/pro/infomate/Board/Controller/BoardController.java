package com.pro.infomate.Board.Controller;

import com.pro.infomate.Board.dto.BoardDTO;
import com.pro.infomate.Board.dto.PostDTO;
import com.pro.infomate.Board.entity.Post;
import com.pro.infomate.Board.service.BoardService;
import com.pro.infomate.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")    // 게시판 (게시글 목록)
    public ResponseEntity<ResponseDTO> getPost() {
        log.info("[BoardController] getMainPage");

        List<PostDTO> postList = boardService.getAllBoards(); // 게시판의 모든 게시물을 조회

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 조회 성공", postList));
    }


    @PostMapping("/posting")    // 글작성페이지
    public ResponseEntity<ResponseDTO> postPost(@RequestBody PostDTO postDTO) {
        System.out.println("postDTO = " + postDTO);
        // String result = boardService.postPost(postDTO);
        boardService.postPost(postDTO);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("작성 완료")
                        .build());
    }



    @GetMapping("/notice")  // 공지사항
    public ResponseEntity<ResponseDTO> boardNotice() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 조회 성공", boardService.boardNotice()));
    }


    @GetMapping("/common")  // 일반게시판
    public ResponseEntity<ResponseDTO> boardCommon() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일반게시판 조회 성공", boardService.boardCommon()));
    }


    @GetMapping("/anony")   // 익명게시판
    public ResponseEntity<ResponseDTO> boardAnony() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "익명게시판 조회 성공", boardService.boardAnony()));
    }



    @GetMapping("/dept")   // 부서게시판
    public ResponseEntity<ResponseDTO> boardDept() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서게시판 조회 성공", boardService.boardDept()));
    }
    
}