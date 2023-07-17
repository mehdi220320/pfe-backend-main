package com.fsb.PFE.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO {
    private String userName;
    private String name;
    private String lastName;
    private String userPassword;
    private Set<Role> roles= new HashSet<>();
}
