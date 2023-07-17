package com.fsb.PFE.shared.contenue_publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContenuePubServiceImpl implements ContenuePubService{
    @Autowired ContenuePubRepo contenuePubRepo;


    @Override
    public ContenuePub addContenuePub(ContenuePub contenuePub) {
        return contenuePubRepo.save(contenuePub);
    }

    @Override
    public ContenuePub updateContenuePub(ContenuePub contenuePub) {
        return contenuePubRepo.save(contenuePub);
    }


    @Override
    public ContenuePub getContenuePubById(int id) {
        return contenuePubRepo.findById(id).get();
    }
}
