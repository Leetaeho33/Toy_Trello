package com.example.toy_trello.domain.board.dto;

import java.util.List;

public record BoardPageDto (

  List<?> data, // boardResponseDto
  long totalElement,
  long totalPage,
  int currentPage,
  int size

){}