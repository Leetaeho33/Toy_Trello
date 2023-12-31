package com.example.toy_trello.domain.comment.controller;

import com.example.toy_trello.domain.comment.service.CommentService;
import com.example.toy_trello.domain.comment.dto.CommentRequestDto;
import com.example.toy_trello.domain.comment.dto.CommentResponseDto;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

   @PostMapping("/card/{cardId}")
   public ResponseEntity<CommentResponseDto> post(@RequestBody CommentRequestDto commentRequestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long cardId){
       return ResponseEntity.status(HttpStatus.OK).body(commentService.post(commentRequestDto, cardId, userDetails.getUser()));
   }

   @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@RequestBody CommentRequestDto commentRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long commentId){
       return ResponseEntity.status(HttpStatus.OK).
               body(commentService.update(commentRequestDto,userDetails.getUser(),commentId));
   }
   @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long commentId){
       commentService.delete(userDetails.getUser(), commentId);
       return ResponseEntity.status(HttpStatus.OK).
               body(new CommonResponseDto("댓글이 삭제되었습니다.", HttpStatus.OK.value()));
   }
    @GetMapping("/card/{cardId}")
    public ResponseEntity<?> getComments(@PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(cardId));
    }

    @GetMapping("/card/{cardId}/page/{currentPage}")
    public ResponseEntity<?> getCommentsPage(@PathVariable Long cardId,
                                             @PathVariable int currentPage){
       return ResponseEntity.status(HttpStatus.OK).
               body(commentService.getCommentsPage(cardId, currentPage));
    }
}
