package be.aewyn.keuken.repositories;

import be.aewyn.keuken.domain.Artikel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@Sql("/insertArtikel.sql")
@Import(ArtikelRepository.class)
class ArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ARTIKELS = "artikels";
    private final ArtikelRepository repository;
    private final Artikel artikel;

    public ArtikelRepositoryTest(ArtikelRepository repository) {
        this.repository = repository;
        this.artikel = new Artikel("testArt", BigDecimal.ONE, BigDecimal.TEN);
    }

    private long idVanTestArtikel(){
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testA'",Long.class);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestArtikel())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testA"));
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void create(){
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS,"id = " + artikel.getId())).isOne();
    }

    @Test
    void findByWoord(){
        repository.create(artikel);
        assertThat(repository.findByWoord("a")).hasSize(countRowsInTableWhere(ARTIKELS,"naam like '%a%'"));
        assertThat(countRowsInTableWhere(ARTIKELS, "naam like '%testA%'")).isEqualTo(2);
    }
}