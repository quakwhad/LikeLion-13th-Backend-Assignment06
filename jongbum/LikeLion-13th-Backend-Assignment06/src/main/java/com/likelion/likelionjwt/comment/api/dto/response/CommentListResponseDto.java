package com.likelion.likelionjwt.comment.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponseDto(
        List<CommentInfoResponseDto> comments
) {
    public static CommentListResponseDto from(List<CommentInfoResponseDto> comments) {
        return CommentListResponseDto.builder()
                .comments(comments)
                .build();
    }
}
