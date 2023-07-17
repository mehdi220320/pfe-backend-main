package com.fsb.PFE.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTOLikes {
    private String name;
    private String lastName;
    private String profileId;
    private String profileImg;
}
