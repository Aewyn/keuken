package be.aewyn.keuken.repositories;

import be.aewyn.keuken.domain.Artikel;
import be.aewyn.keuken.domain.FoodArtikel;
import be.aewyn.keuken.domain.Korting;
import be.aewyn.keuken.domain.NonFoodArtikel;
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

    public ArtikelRepositoryTest(ArtikelRepository repository) {
        this.repository = repository;
    }

    private final Artikel testFoodB = new FoodArtikel("testFoodB", BigDecimal.ONE, BigDecimal.TEN, 7);
    private final Artikel testNonFoodB = new NonFoodArtikel("testNonFoodB", BigDecimal.ONE, BigDecimal.TEN, 7);

    public long idVanTestFoodArtikel(){
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testFoodA'",Long.class);
    }

    private long idVanTestNonFoodArtikel(){
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testNonFoodA'",Long.class);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestFoodArtikel())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testFoodA"));
        assertThat(repository.findById(idVanTestNonFoodArtikel())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testNonFoodA"));
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void create(){
        repository.create(testFoodB);
        assertThat(testFoodB.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS,"id = " + testFoodB.getId())).isOne();
        repository.create(testNonFoodB);
        assertThat(testNonFoodB.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id = " + testNonFoodB.getId())).isOne();
    }

    @Test
    void findByWoord(){
        repository.create(testFoodB);
        assertThat(repository.findByWoord("e")).hasSize(countRowsInTableWhere(ARTIKELS,"naam like '%e%'"));
        assertThat(countRowsInTableWhere(ARTIKELS, "naam like '%testFoodB%'")).isEqualTo(1);
    }

    @Test
    void algemenePrijsverhoging(){
        assertThat(repository.algemenePrijsverhoging(BigDecimal.TEN)).isEqualTo(countRowsInTable(ARTIKELS));
        assertThat(countRowsInTableWhere(ARTIKELS,"verkoopprijs = 4.4 and id = " + idVanTestFoodArtikel())).isOne();
    }
    @Test
    void kortingenLezen(){
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getKortingen())
                        .containsOnly(new Korting(1, BigDecimal.TEN)));
    }
}