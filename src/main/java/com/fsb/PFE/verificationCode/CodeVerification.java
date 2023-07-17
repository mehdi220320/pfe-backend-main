package com.fsb.PFE.verificationCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CodeVerification {
    @Id
    @Column(length = 50)
    private String userId;
    private String code;
}
