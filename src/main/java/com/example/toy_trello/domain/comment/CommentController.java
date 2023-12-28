package com.example.toy_trello.domain.comment;

import com.example.toy_trello.domain.comment.dto.CommentRequestDto;
import com.example.toy_trello.domain.comment.dto.CommentResponseDto;
import com.example.toy_trello.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

   @PostMapping
   public ResponseEntity<CommentResponseDto> post(@RequestBody CommentRequestDto commentRequestDto){
       return ResponseEntity.status(HttpStatus.OK).body(commentService.post(commentRequestDto));
   }

   @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@RequestBody CommentRequestDto commentRequestDto,
                                                     @PathVariable Long commentId){
       return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentRequestDto, commentId));
   }
   @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommonResponseDto> delete(@PathVariable Long commentId){
       commentService.delete(commentId);
       return ResponseEntity.status(HttpStatus.OK).
               body(new CommonResponseDto("댓글이 삭제되었습니다.", HttpStatus.OK.value()));
   }
}
