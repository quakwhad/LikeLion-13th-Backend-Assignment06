package com.likelion.likelionjwt.comment.api.dto.response;

import com.likelion.likelionjwt.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentInfoResponseDto(
        String content,
        String writer
) {
    public static CommentInfoResponseDto from(Comment comment) {
        return CommentInfoResponseDto.builder()
                .content(comment.getContent())
                .writer(comment.getMember().getName())
                .build();
    }
}
