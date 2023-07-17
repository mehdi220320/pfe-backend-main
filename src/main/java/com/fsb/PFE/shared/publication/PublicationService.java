package com.fsb.PFE.shared.publication;

import com.fsb.PFE.shared.contenue_publication.ContenuePub;

import java.util.List;

public interface PublicationService {
    void addPublication(Publication publication);
    void deletePublication(int idPublication);
    List<PublicationDTO> getAllPublications();
    PublicationDTO getPublicationById(int id);
    List<PublicationDTO> getPublicationsByOwner(String idOwner);
    List<PublicationDTO> getPublicationsByProfileId(String profileId);
}
