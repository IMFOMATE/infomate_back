package com.pro.infomate.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum; // 현재 페이지

    private int amount; // 한페이지당 보여질 게시글 수

    private String searchValue; // 검색

    public Criteria(){
        this(1, 10);  // 1페이지, 10개 게시글
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }


}
