package com.medical.registry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical.registry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FicheTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fiche.class);
        Fiche fiche1 = new Fiche();
        fiche1.setId("id1");
        Fiche fiche2 = new Fiche();
        fiche2.setId(fiche1.getId());
        assertThat(fiche1).isEqualTo(fiche2);
        fiche2.setId("id2");
        assertThat(fiche1).isNotEqualTo(fiche2);
        fiche1.setId(null);
        assertThat(fiche1).isNotEqualTo(fiche2);
    }
}
