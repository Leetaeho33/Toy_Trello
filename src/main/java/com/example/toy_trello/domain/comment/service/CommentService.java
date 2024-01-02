package com.example.toy_trello.domain.comment.service;

import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.card.repository.CardRepository;
import com.example.toy_trello.domain.comment.dto.CommentRequestDto;
import com.example.toy_trello.domain.comment.dto.CommentResponseDto;
import com.example.toy_trello.domain.comment.dto.PageDto;
import com.example.toy_trello.domain.comment.entity.Comment;
import com.example.toy_trello.domain.comment.exception.CommentErrorCode;
import com.example.toy_trello.domain.comment.exception.CommentExistsException;
import com.example.toy_trello.domain.comment.exception.OutOfRangeException;
import com.example.toy_trello.domain.comment.repository.CommentRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.toy_trello.domain.comment.exception.CommentErrorCode.COMMENT_NOT_FOUND;
import static com.example.toy_trello.domain.comment.exception.CommentErrorCode.OUT_OF_RANGE;


@RequiredArgsConstructor
@Service
@Slf4j(topic = "commentService")
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CommentResponseDto post(CommentRequestDto commentRequestDto, Long cardId, User user) {
        log.info("댓글 작성");
        Card card = findCardById(cardId);
        Comment comment = new Comment(commentRequestDto, card, user);
        commentRepository.save(comment);
        log.info("댓글 작성 완료");
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto update(CommentRequestDto requestDTO, User user, Long commentId) {
        log.info("댓글 수정 시작");
        log.info("로그인 된 유저 " + user.getUsername());
        Comment comment = findCommentById(commentId);
        log.info("댓글 작성자 " + comment.getUser().getUsername());
        if(checkAuthorization(user, comment)){
            comment.updateComment(requestDTO);
        }
        log.info("댓글 수정 완료");
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void delete(User user, Long commentId) {
        log.info("댓글 삭제");
        log.info("로그인 된 유저 " + user.getUsername());
        Comment comment = findCommentById(commentId);
        log.info("댓글 작성자 " + comment.getUser().getUsername());
        if(checkAuthorization(user, comment)){
            commentRepository.delete(comment);
        }
        commentRepository.delete(comment);
        log.info("댓글 삭제 완료");
    }

    private Comment findCommentById(Long commentId){
        log.info("댓글 조회 시작");
        return commentRepository.findById(commentId).orElseThrow(()->
                new CommentExistsException(COMMENT_NOT_FOUND));
    }

    // Card 도메인 예외 사용
    private Card findCardById(Long cardId){
        log.info("카드 조회 시작");
        return cardRepository.findById(cardId).orElseThrow(()->
                new IllegalArgumentException("해당 카드는 존재하지 않습니다."));
    }

    private boolean checkAuthorization(User user, Comment comment){
        log.info("작성자 인가 확인");
        if(comment.getUser().getUsername().equals(user.getUsername())){
            return true;
        }else throw new CommentExistsException(CommentErrorCode.UNAUTHORIZED_USER);
    }

    public PageDto getComments(Long cardId) {
        Pageable pageable = PageRequest.of(0,5);

        Page<Comment> result = commentRepository.findByCard_CardId(cardId, pageable);
        if(result.isEmpty()){
            log.error("댓글이 존재하지 않습니다..)");
            throw new CommentExistsException(COMMENT_NOT_FOUND);
        }
        var data = result.getContent().stream()
                    .map(CommentResponseDto::new)
                    .toList();

        return new PageDto(data,
                result.getTotalElements(),
                result.getTotalPages(),
                pageable.getPageNumber(),
                data.size()
        );
    }

    public PageDto getCommentsPage(Long cardId, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage,5);
        int pageSize = pageable.getPageSize();
        if(currentPage<pageSize-1){
            Page<Comment> result = commentRepository.findByCard_CardId(cardId, pageable);
            if(result.isEmpty()){
                log.error("댓글이 존재하지 않습니다..)");
                throw new CommentExistsException(COMMENT_NOT_FOUND);
            }
            var data = result.getContent().stream()
                    .map(CommentResponseDto::new)
                    .toList();

            return new PageDto(data,
                    result.getTotalElements(),
                    result.getTotalPages(),
                    pageable.getPageNumber(),
                    data.size()
            );
        }else{
            throw new OutOfRangeException(OUT_OF_RANGE);
        }
    }
}
