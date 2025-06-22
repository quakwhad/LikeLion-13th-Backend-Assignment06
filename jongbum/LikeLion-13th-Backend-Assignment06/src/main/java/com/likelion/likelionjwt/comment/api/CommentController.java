package com.likelion.likelionjwt.comment.api;

import com.likelion.likelionjwt.common.error.SuccessCode;
import com.likelion.likelionjwt.common.template.ApiResTemplate;
import com.likelion.likelionjwt.comment.api.dto.request.CommentSaveRequestDto;
import com.likelion.likelionjwt.comment.api.dto.request.CommentUpdateRequestDto;
import com.likelion.likelionjwt.comment.api.dto.response.CommentListResponseDto;
import com.likelion.likelionjwt.comment.application.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/save")
    public ApiResTemplate<String> commentSave(@RequestBody @Valid CommentSaveRequestDto commentSaveRequestDto, Principal principal) {
        commentService.commentSave(commentSaveRequestDto, principal);
        return ApiResTemplate.successWithNoContent(SuccessCode.COMMENT_SAVE_SUCCESS);
    }

    // 전체 댓글 조회
    @GetMapping("/all")
    public ApiResTemplate<CommentListResponseDto> commentFindAll() {
        CommentListResponseDto commentListResponseDto = commentService.commentFindAll();
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, commentListResponseDto);
    }

    // 사용자 id를 기준으로 해당 사용자가 작성한 댓글 목록 조회
    @GetMapping("/{members}")
    public ApiResTemplate<CommentListResponseDto> myCommentFindAll(Principal principal) {
        CommentListResponseDto commentListResponseDto = commentService.commentFindMember(principal);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, commentListResponseDto);
    }

    // 댓글 id를 기준으로 사용자가 작성한 댓글 수정
    @PatchMapping("/{commentId}")
    public ApiResTemplate<String> commentUpdate(@PathVariable("commentId") Long commentId,
                                             @RequestBody @Valid CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.commentUpdate(commentId, commentUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.COMMENT_UPDATE_SUCCESS);
    }


    // 댓글 id를 기준으로 사용자가 작성한 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResTemplate<String> commentDelete(@PathVariable("commentId") Long commentId) {
        commentService.commentDelete(commentId);
        return ApiResTemplate.successWithNoContent(SuccessCode.COMMENT_DELETE_SUCCESS);
    }
}