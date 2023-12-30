package com.example.toy_trello.domain.card.dto;


import com.example.toy_trello.column.entity.Column;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardCreateRequestDto {
  private Long columnId;
  private String cardName;
  private String cardDescription;
  private String cardColor;
  private String dueDate;
  private Long cardOrder;

  // 문자열로 받은 날짜를 Date 객체로 변환
  public Date getDueDate() throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return dateFormat.parse(this.dueDate);
  }

}
