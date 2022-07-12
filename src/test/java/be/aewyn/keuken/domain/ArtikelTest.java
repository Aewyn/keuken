package be.aewyn.keuken.domain;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ArtikelTest {
    private static final BigDecimal BEDRAG = BigDecimal.valueOf(5);
    private Artikel artikel;
    @BeforeEach
    void beforeEach(){
        artikel = new Artikel("testA", BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void verhoog(){
        artikel.verhoogVerkoopprijs(BEDRAG);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("8");
    }

    @Test
    void verhoogMetNegatiefGetalGaatNiet(){
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopprijs(BigDecimal.valueOf(-2)));
    }

    @Test
    void verhoogMetNullGaatNiet(){
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopprijs(null));
    }

    @Test
    void verhoogMet0GaatNiet(){
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopprijs(BigDecimal.ZERO));
    }

}