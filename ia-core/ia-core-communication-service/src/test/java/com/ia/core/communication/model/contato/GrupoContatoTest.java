package com.ia.core.communication.model.contato;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class GrupoContatoTest {

    @Test
    void testGrupoContatoConstruction() {
        // Given
        GrupoContato grupoContato = new GrupoContato();

        // When
        grupoContato.setId(1L);
        grupoContato.setNome("Grupo Teste");

        // Then
        assertThat(grupoContato.getId()).isEqualTo(1L);
        assertThat(grupoContato.getNome()).isEqualTo("Grupo Teste");
    }

    @Test
    void testGrupoContatoWithContatos() {
        // Given
        GrupoContato grupoContato = new GrupoContato();
        ContatoMensagem contato = new ContatoMensagem();
        contato.setId(1L);

        // When
        grupoContato.getContatos().add(contato);

        // Then
        assertThat(grupoContato.getContatos()).hasSize(1);
        assertThat(grupoContato.getContatos().get(0).getId()).isEqualTo(1L);
    }
}
