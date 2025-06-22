package com.likelion.likelionjwt.comment.domain;

import com.likelion.likelionjwt.member.domain.Member;
import com.likelion.likelionjwt.comment.api.dto.request.CommentUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Comment(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public void update(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.content = commentUpdateRequestDto.content();
    }
}