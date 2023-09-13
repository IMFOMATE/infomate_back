package com.pro.infomate.Board.service;


import com.pro.infomate.Board.Repository.PostRepository;
import com.pro.infomate.Board.dto.PostDTO;
import com.pro.infomate.Board.entity.BoardFile;
import com.pro.infomate.Board.entity.Post;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.pro.infomate.Board.entity.QPost.post;

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

    public int totalPost() {    // 페이지

        log.info("[BoardService] totalPost Start ==========================");

        List<Post> postList = postRepository.findAll();
        log.info("[PostService] PostList.Size : {}", postList.size());
        log.info("[PostService] totalPost End =========================");

        return postList.size();
    }

    public List<PostDTO> postListPaging(Criteria cri) { // 전체글, 페이징

        log.info("[BoardService] postListPaging Start ================");
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("postCode").descending());

        Page<Post> result = postRepository.findAll(paging);

        List<PostDTO> postList = result.stream()
                .map(post -> modelMapper
                        .map(post, PostDTO.class)).collect(Collectors.toList());

//        for(int i = 0; i < postList.size(); i++){
//            postList.get(i).setPostImageUrl(IMAGE_URL + postList.get(i).getPostImageUrl());
//        }

        log.info("[BoardService] postListPaging End ==================");
        return postList;
    }


    public PostDTO postView(Integer postCode) {     // 게시글 상세보기
        Optional<Post> postOptional = postRepository.findByPostCode(postCode);

            Post post = postOptional.get();

            PostDTO postDTO = modelMapper.map(post, PostDTO.class);

            Member member = post.getMember();
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMemberId(member.getMemberId());
            memberDTO.setMemberName(member.getMemberName());
            memberDTO.setMemberNo(member.getMemberNo());

            postDTO.setMember(memberDTO);

            return postDTO; // 게시글을 찾지 못한 경우 null 반환 또는 예외 처리
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



    @Transactional // 게시글 수정
    public String updatePost(PostDTO postDTO) {
    log.info("[updatePost] postDTO :  {}", postDTO);
        try {
            Post post = postRepository.findById(postDTO.getPostCode()).get();

            if (post != null) {
                post.setPostCode(postDTO.getPostCode());
                post.setPostTitle(postDTO.getPostTitle());
                post.setPostDate(postDTO.getPostDate());
                post.setPostContents(postDTO.getPostContents());
                post.setBoardCategory(postDTO.getBoardCategory());
                post.setBoardCode(postDTO.getBoard().getBoardCode());

                postRepository.save(post);
                return "게시글 수정 완료";
            } else {
                return "게시글을 찾을 수 없습니다.";
            }
        } catch (Exception e) {
            return "게시글 수정 실패: " + e.getMessage();
        }
    }


    @Transactional
    public String deletePost(Integer postCode) {   // 게시글 삭제
        int result = 0;

        try {
            // 게시글 코드를 사용하여 해당 게시글을 조회
            Post post = postRepository.findById(postCode).orElse(null);

            if (post != null) {
                // 게시글 삭제
                log.info("[PostService] deleteById start ====================");
                postRepository.deleteById(postCode);

                result = 1; // 성공 시 결과값을 1로 설정
            } else {
                log.error("게시글을 찾을 수 없습니다. postCode: {}", postCode);
            }
        } catch (Exception e) {
            log.error("게시글 삭제 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        log.info("[PostService] deletePost End ====================");
        return (result > 0) ? "게시글 삭제 성공" : "게시글 삭제 실패";
    }





    public List<PostDTO> getCommon() {        // 게시판 카테고리 나누기
        List<Post> normalPosts = postRepository.findByBoardCategory("일반게시판");
        return normalPosts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }
}


//
//        log.info("[ProductService] updatePost Start ===================================");
//        log.info("[ProductService] postDTO : " + postDTO);
//
////        String replaceFileName = null;
////        int result = 0;
//
//        try {
//            Post post = postRepository.findById(postDTO.getPostCode()).get();
////            String BoardFile = post.getBoardFile();
////            log.info("[updatePost] postImage : " + postImage);
//
//
//                post.setPostCode(postDTO.getPostCode());
//                post.setPostTitle(postDTO.getPostTitle());
//                post.setPostDate(postDTO.getPostDate());
//                post.setPostContents(postDTO.getPostContents());
//                post.setBoardCategory(postDTO.getBoardCategory());
//                post.setBoardCode(postDTO.getPostCode());
//
//                if (postImage != null && !postImage.isEmpty()) {
//                    String imageName = UUID.randomUUID().toString().replace("-", "");
//                    replaceFileName = FileUploadUtils.saveFile(imageName, BoardFile);
//                    log.info("updatePost] InsertFileName : " + replaceFileName);
//
//                    post.setBoardFile(replaceFileName);
//                    log.info("[updatePost] deleteFileName : " + BoardFile);
//
//                    boolean isDelete = FileUploadUtils.deleteFile(BoardFile);
//                    log.info("[update] isDelete : " + isDelete);
//                } else {
//
//                    post.setBoardFile(postImage);
//                }
//
//                result = 1;
//        } catch (IOException e) {
//            log.info("[updatePost] Exception!!");
////            FileUploadUtils.deleteFile(replaceFileName);
//            throw new RuntimeException(e);
//        }
//        log.info("[PostService] updatePost End ==================================");
//        return (result > 0) ? "게시글 수정 완료" : "게시글 수정 실패";
//
//    }