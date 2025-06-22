package com.likelion.likelionjwt.comment.domain.repository;


import com.likelion.likelionjwt.member.domain.Member;
import com.likelion.likelionjwt.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMember(Member member);
}