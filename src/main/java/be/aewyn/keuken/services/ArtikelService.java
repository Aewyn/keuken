package be.aewyn.keuken.services;

import be.aewyn.keuken.exceptions.ArtikelNotFoundException;
import be.aewyn.keuken.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ArtikelService {
    private final ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    public void verhoogVerkoopprijs(long id, BigDecimal bedrag){
        artikelRepository.findById(id).orElseThrow(ArtikelNotFoundException::new).verhoogVerkoopprijs(bedrag);
    }
}
