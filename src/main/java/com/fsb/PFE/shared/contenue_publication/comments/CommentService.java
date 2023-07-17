package com.fsb.PFE.shared.contenue_publication.comments;


import java.util.List;

public interface CommentService {
    void addComment(Comment comment,int idContenue);
    void deleteComment(int idComment,int idContenue);
    void updateComment(String descComment,int idComment);
    List<CommentDTO> getCommentByPub(int idPub);
}
