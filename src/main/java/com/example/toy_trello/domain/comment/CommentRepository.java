package com.example.toy_trello.domain.comment;

import com.example.toy_trello.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByCard_CardId(Long postId, Pageable pageable);
}
