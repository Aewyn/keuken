package be.aewyn.keuken.repositories;

import be.aewyn.keuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class ArtikelRepository {
    private final EntityManager manager;

    public ArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Artikel> findById(long id){
        return Optional.ofNullable(manager.find(Artikel.class,id));
    }
}
