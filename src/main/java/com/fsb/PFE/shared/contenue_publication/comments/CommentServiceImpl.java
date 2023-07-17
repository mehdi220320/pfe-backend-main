package com.fsb.PFE.shared.contenue_publication.comments;

import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.shared.contenue_publication.ContenuePubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired CommentRepo commentRepo;
    @Autowired ContenuePubRepo contenuePubRepo;
    @Autowired UserDao userDao;


    @Override
    public void addComment(Comment comment, int idContenue) {
        contenuePubRepo.getOne(idContenue).getComments().add(comment);
        comment.setDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setUserId(auth.getName());
        String profileName = userDao.findById(auth.getName()).get().getName()+" "+userDao.findById(auth.getName()).get().getLastName();
        comment.setProfileName(profileName);
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(int idComment,int idContenue) {
        contenuePubRepo.findById(idContenue).get().getComments().remove(commentRepo.getOne(idComment));
        commentRepo.deleteById(idComment);
    }

    @Override
    public void updateComment(String descComment, int idComment) {
        commentRepo.getOne(idComment).setDescription(descComment);
        commentRepo.save(commentRepo.getOne(idComment));
    }

    @Override
    public List<CommentDTO> getCommentByPub(int idPub) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        if (contenuePubRepo.existsById(idPub)) {
            for (Comment c:contenuePubRepo.getOne(idPub).getComments()) {
                commentDTOS.add(mapCommentToDTO(c));
            }
            return commentDTOS;
        }
        return null;
    }


    CommentDTO mapCommentToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUserId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setProfileName(comment.getProfileName());
        return commentDTO;
    }

}
