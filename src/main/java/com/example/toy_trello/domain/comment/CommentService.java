package com.example.toy_trello.domain.comment;

import com.example.toy_trello.domain.comment.dto.CommentRequestDto;
import com.example.toy_trello.domain.comment.dto.CommentResponseDto;
import com.example.toy_trello.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j(topic = "commentService")
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponseDto post(CommentRequestDto commentRequestDto) {
        log.info("댓글 작성");
        Comment comment = new Comment(commentRequestDto);
        commentRepository.save(comment);
        log.info("댓글 작성 완료");
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto update(CommentRequestDto commentRequestDto, Long commentId) {
        log.info("댓글 수정");
        Comment comment = findCommentById(commentId);
        comment.updateComment(commentRequestDto);
        log.info("댓글 수정 완료");
        return new CommentResponseDto(comment);
    }

    private Comment findCommentById(Long commentId){
        log.info("댓글 조회 시작");
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));
    }

//    private boolean checkAuthorization(User user, Comment comment){
//        log.info("작성자 인가 확인");
//        if(comment.getUser().getUsername().equals(user.getUsername())){
//            return true;
//        }else throw new IllegalArgumentException("작성자만 접근할 수 있습니다.");
//    }
}
