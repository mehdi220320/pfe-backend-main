package com.fsb.PFE.verificationCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeService implements CodeServiceImp{
    @Autowired private CodeRepo codeRepository;

    @Override
    public String GenerateCode() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return String.valueOf(n);
    }

    @Override
    public void saveCode(CodeVerification code) {
        codeRepository.save(code);
    }


    @Override
    public CodeVerification getCode(String idUser) {
        return codeRepository.findById(idUser).get();
    }

    @Override
    public void delete(String idUser) {
        codeRepository.deleteById(idUser);
    }
}
