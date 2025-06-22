package com.likelion.likelionjwt.comment.application;

import com.likelion.likelionjwt.common.error.ErrorCode;
import com.likelion.likelionjwt.common.exception.BusinessException;
import com.likelion.likelionjwt.member.domain.Member;
import com.likelion.likelionjwt.member.domain.repository.MemberRepository;
import com.likelion.likelionjwt.comment.api.dto.request.CommentSaveRequestDto;
import com.likelion.likelionjwt.comment.api.dto.request.CommentUpdateRequestDto;
import com.likelion.likelionjwt.comment.api.dto.response.CommentInfoResponseDto;
import com.likelion.likelionjwt.comment.api.dto.response.CommentListResponseDto;
import com.likelion.likelionjwt.comment.domain.Comment;
import com.likelion.likelionjwt.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    // 댓글 저장
    @Transactional
    public void commentSave(CommentSaveRequestDto commentSaveRequestDto, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION
                        , ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        Comment comment = Comment.builder()
                .content(commentSaveRequestDto.content())
                .member(member)
                .build();

        commentRepository.save(comment);
    }

    // 전체 댓글 조회
    public CommentListResponseDto commentFindAll() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentInfoResponseDto> commentInfoResponseDtos = comments.stream()
                .map(CommentInfoResponseDto::from)
                .toList();

        return CommentListResponseDto.from(commentInfoResponseDtos);
    }

    // 특정 작성자가 작성한 댓글 목록을 조회
    public CommentListResponseDto commentFindMember(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION
                        , ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage() + memberId));

        List<Comment> comments = commentRepository.findByMember(member);
        List<CommentInfoResponseDto> commentInfoResponseDtos = comments.stream()
                .map(CommentInfoResponseDto::from)
                .toList();

        return CommentListResponseDto.from(commentInfoResponseDtos);
    }

    // 댓글 수정
    @Transactional
    public void commentUpdate(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto)
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION
                        , ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage() + commentId)
        );
        comment.update(commentUpdateRequestDto);
    }

    // 댓글 삭제
    @Transactional
    public void commentDelete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION
                        , ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage() + commentId)
        );
        commentRepository.delete(comment);
    }
}