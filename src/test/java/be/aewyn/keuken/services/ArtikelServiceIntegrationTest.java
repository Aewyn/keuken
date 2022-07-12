package be.aewyn.keuken.services;

import be.aewyn.keuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@Import({ArtikelService.class, ArtikelRepository.class})
@Sql("/insertArtikel.sql")
class ArtikelServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ARTIKELS = "artikels";
    private final ArtikelService artikelService;
    private final EntityManager manager;

    public ArtikelServiceIntegrationTest(ArtikelService artikelService, EntityManager manager) {
        this.artikelService = artikelService;
        this.manager = manager;
    }

    private long idVanTestArtikel(){
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testA'", Long.class);
    }

    @Test
    void verhoogVerkoopprijs(){
        var id = idVanTestArtikel();
        artikelService.verhoogVerkoopprijs(id, BigDecimal.valueOf(6));
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS,"verkoopprijs = 10 and id = " + id)).isOne();
    }
}