package com.pro.infomate.department.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TreeViewResponse {

  private int id;
  private int parent;
  private boolean droppable;
  private String text;
  private TreeDTO data;

  @Builder
  public TreeViewResponse(int id, int parent, boolean droppable, String text, TreeDTO data) {
    this.id = id;
    this.parent = parent;
    this.droppable = droppable;
    this.text = text;
    this.data = data;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @ToString
  public static class TreeDTO {

    private String fileType;
    private String rank;

    private String profile;

    @Builder
    public TreeDTO(String fileType, String rank,String profile) {
      this.fileType = fileType;
      this.rank = rank;
      this.profile = profile;
    }
  }
}
