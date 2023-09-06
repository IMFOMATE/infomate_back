package com.pro.infomate.Board.dto;

import com.pro.infomate.Board.entity.BoardFile;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {

    private int postCode;

    private String postTitle;

    private String postDate;

    private String postContents;

    private String boardCategory;

    private BoardDTO board;

    private MemberDTO member;

    private BoardFileDTO boardFile;
}
