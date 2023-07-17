package com.fsb.PFE.shared.contenue_publication.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired CommentService commentService;

    @PostMapping("/addComment/{idContenue}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void addComment(@RequestBody Comment comment,@PathVariable int idContenue){
        commentService.addComment(comment, idContenue);
    }

    @DeleteMapping("/deleteComment/{idComment}/idContenue/{idContenue}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void deleteComment(@PathVariable int idContenue,@PathVariable int idComment){
        commentService.deleteComment(idComment,idContenue);
    }

    @PutMapping("/updateComment/{id}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void updateComment(@RequestBody String descComment,@PathVariable int id){
        commentService.updateComment(descComment,id);
    }

    @GetMapping("/getCommentByPub/{idPub}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<CommentDTO> getCommentByPub(@PathVariable int idPub){
        return commentService.getCommentByPub(idPub);
    }




}
