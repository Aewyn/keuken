package be.aewyn.keuken.services;

import be.aewyn.keuken.domain.Artikel;
import be.aewyn.keuken.domain.FoodArtikel;
import be.aewyn.keuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtikelServiceTest {
    private ArtikelService service;
    @Mock
    private ArtikelRepository repository;
    private Artikel artikel;

    @BeforeEach
    void beforeEach(){
        service = new ArtikelService(repository);
        artikel = new FoodArtikel("testA", BigDecimal.valueOf(2), BigDecimal.valueOf(3), 7);
    }

    @Test
    void verhoog(){
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopprijs(1, BigDecimal.valueOf(5));
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("8");
        verify(repository).findById(1);
    }

}