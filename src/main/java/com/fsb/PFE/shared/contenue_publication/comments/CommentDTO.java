package com.fsb.PFE.shared.contenue_publication.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CommentDTO {
    private int id;
    private String userId;
    private Date date;
    private String description;
    private String profileName;
}
