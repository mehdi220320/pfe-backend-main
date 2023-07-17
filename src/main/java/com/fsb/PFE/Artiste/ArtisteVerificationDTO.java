package com.fsb.PFE.Artiste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ArtisteVerificationDTO {
    private String code;
    private Artiste artiste;
}
