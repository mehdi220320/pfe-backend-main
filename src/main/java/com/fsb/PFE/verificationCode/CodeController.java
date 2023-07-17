package com.fsb.PFE.verificationCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CodeController {
    @Autowired private CodeService codeService;

    @PostMapping("/saveCode")
    public void saveCode(@RequestBody CodeVerification code){
        codeService.saveCode(code);
    }

    @GetMapping("/getCode")
    public CodeVerification getCode(@RequestBody String userId){
        return codeService.getCode(userId);
    }

    @DeleteMapping("/deleteCode")
    public void deleteCode(@RequestBody String userId){
        codeService.delete(userId);
    }



}
