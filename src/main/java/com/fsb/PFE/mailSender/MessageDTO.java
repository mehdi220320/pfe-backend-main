package com.fsb.PFE.mailSender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String email;
    private String message;
    private String name;
    private String selectedOption;
    private String tel;
}
