package com.pro.infomate.Board.Controller;

import com.pro.infomate.Board.Repository.PostRepository;
import com.pro.infomate.Board.dto.BoardDTO;
import com.pro.infomate.Board.dto.PagingResponseDTO;
import com.pro.infomate.Board.dto.PostDTO;
import com.pro.infomate.Board.entity.Post;
import com.pro.infomate.Board.service.BoardService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/brd")
@Slf4j
class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")    // 전체글, 페이징
    public ResponseEntity<ResponseDTO> boardPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset){

        log.info("[ProductController] selectPostListWithPaging Start ============ ");
        log.info("[ProductController] selectPostListWithPaging offset : {} ", offset);

        int total = boardService.totalPost();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(boardService.postListPaging(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        log.info("[BoardController] boardPaging End =====================");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    @GetMapping("/board/post/{postCode}")  // 글 상세 페이지
    public ResponseEntity<ResponseDTO> postView(@PathVariable Integer postCode) {

        PostDTO postDTO = boardService.postView(postCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", postDTO));
        }

//    @GetMapping("/board/newpost")    // 게시판 (게시글 목록)
//    public ResponseEntity<ResponseDTO> getPost() {
//        log.info("[BoardController] getMainPage");
//
//        List<PostDTO> postList = boardService.getAllBoards(); // 게시판의 모든 게시물을 조회
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", postList));
//    }


    @PostMapping("/board/posting")    // 글작성페이지
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

    @PutMapping("board/{postCode}/update") // 게시글 수정
    public ResponseEntity<String> updatePost( @RequestBody PostDTO postDTO) {
        log.info("[PostController](updateByPost) postDTO: {} ", postDTO);
        boardService.updatePost(postDTO);

        String resultMessage = boardService.updatePost(postDTO);

        if (resultMessage != null && resultMessage.contains("성공")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
        }
//        return  ResponseEntity.ok("resultMessage");
    }

    @DeleteMapping("board/{postCode}/delete")   // 게시글 삭제
    public ResponseEntity<ResponseDTO> deletePost(@PathVariable Integer postCode) {
        try {
            boardService.deleteById(postCode);
            return ResponseEntity.ok()
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.OK)
                            .message("삭제 성공")
                            .build());
        } catch (Exception e) {
            // 삭제 실패 시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("삭제 실패: " + e.getMessage())
                            .build());
        }
    }








    @GetMapping("/board/notice")  // 공지사항
        public ResponseEntity<ResponseDTO> boardNotice () {

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 조회 성공", boardService.boardNotice()));
        }


        @GetMapping("/board/common")  // 일반게시판
        public ResponseEntity<ResponseDTO> boardCommon () {

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일반게시판 조회 성공", boardService.boardCommon()));
        }


        @GetMapping("/board/anony")   // 익명게시판
        public ResponseEntity<ResponseDTO> boardAnony () {

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "익명게시판 조회 성공", boardService.boardAnony()));
        }


        @GetMapping("/board/dept")   // 부서게시판
        public ResponseEntity<ResponseDTO> boardDept () {

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서게시판 조회 성공", boardService.boardDept()));
        }

    }