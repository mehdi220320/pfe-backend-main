package com.fsb.PFE.verificationCode;

public interface CodeServiceImp {
    public String GenerateCode();
    public void saveCode(CodeVerification code);
    public CodeVerification getCode (String idUser);
    public void delete(String idUser);
}
