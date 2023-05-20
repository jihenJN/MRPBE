package com.medical.registry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical.registry.IntegrationTest;
import com.medical.registry.domain.Fiche;
import com.medical.registry.domain.enumeration.eactivite;
import com.medical.registry.domain.enumeration.escolarise;
import com.medical.registry.repository.FicheRepository;
import com.medical.registry.service.dto.FicheDTO;
import com.medical.registry.service.mapper.FicheMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link FicheResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FicheResourceIT {

    private static final Boolean DEFAULT_HOMOCYSTINURIE = false;
    private static final Boolean UPDATED_HOMOCYSTINURIE = true;

    private static final Boolean DEFAULT_LEUCINOSE = false;
    private static final Boolean UPDATED_LEUCINOSE = true;

    private static final Boolean DEFAULT_TYROSINEMIE = false;
    private static final Boolean UPDATED_TYROSINEMIE = true;

    private static final LocalDate DEFAULT_DATE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAJ = LocalDate.now(ZoneId.systemDefault());

    private static final eactivite DEFAULT_ACTIVITE = eactivite.NP;
    private static final eactivite UPDATED_ACTIVITE = eactivite.NON;

    private static final Boolean DEFAULT_TRAVAIL = false;
    private static final Boolean UPDATED_TRAVAIL = true;

    private static final escolarise DEFAULT_SCOLARISE = escolarise.NON;
    private static final escolarise UPDATED_SCOLARISE = escolarise.ECOLE_NORMAL;

    private static final String ENTITY_API_URL = "/api/fiches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FicheRepository ficheRepository;

    @Autowired
    private FicheMapper ficheMapper;

    @Autowired
    private MockMvc restFicheMockMvc;

    private Fiche fiche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fiche createEntity() {
        Fiche fiche = new Fiche()
            .homocystinurie(DEFAULT_HOMOCYSTINURIE)
            .leucinose(DEFAULT_LEUCINOSE)
            .tyrosinemie(DEFAULT_TYROSINEMIE)
            .dateMaj(DEFAULT_DATE_MAJ)
            .activite(DEFAULT_ACTIVITE)
            .travail(DEFAULT_TRAVAIL)
            .scolarise(DEFAULT_SCOLARISE);
        return fiche;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fiche createUpdatedEntity() {
        Fiche fiche = new Fiche()
            .homocystinurie(UPDATED_HOMOCYSTINURIE)
            .leucinose(UPDATED_LEUCINOSE)
            .tyrosinemie(UPDATED_TYROSINEMIE)
            .dateMaj(UPDATED_DATE_MAJ)
            .activite(UPDATED_ACTIVITE)
            .travail(UPDATED_TRAVAIL)
            .scolarise(UPDATED_SCOLARISE);
        return fiche;
    }

    @BeforeEach
    public void initTest() {
        ficheRepository.deleteAll();
        fiche = createEntity();
    }

    @Test
    void createFiche() throws Exception {
        int databaseSizeBeforeCreate = ficheRepository.findAll().size();
        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);
        restFicheMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheDTO)))
            .andExpect(status().isCreated());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeCreate + 1);
        Fiche testFiche = ficheList.get(ficheList.size() - 1);
        assertThat(testFiche.getHomocystinurie()).isEqualTo(DEFAULT_HOMOCYSTINURIE);
        assertThat(testFiche.getLeucinose()).isEqualTo(DEFAULT_LEUCINOSE);
        assertThat(testFiche.getTyrosinemie()).isEqualTo(DEFAULT_TYROSINEMIE);
        assertThat(testFiche.getDateMaj()).isEqualTo(DEFAULT_DATE_MAJ);
        assertThat(testFiche.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
        assertThat(testFiche.getTravail()).isEqualTo(DEFAULT_TRAVAIL);
        assertThat(testFiche.getScolarise()).isEqualTo(DEFAULT_SCOLARISE);
    }

    @Test
    void createFicheWithExistingId() throws Exception {
        // Create the Fiche with an existing ID
        fiche.setId("existing_id");
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        int databaseSizeBeforeCreate = ficheRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFiches() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        // Get all the ficheList
        restFicheMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fiche.getId())))
            .andExpect(jsonPath("$.[*].homocystinurie").value(hasItem(DEFAULT_HOMOCYSTINURIE.booleanValue())))
            .andExpect(jsonPath("$.[*].leucinose").value(hasItem(DEFAULT_LEUCINOSE.booleanValue())))
            .andExpect(jsonPath("$.[*].tyrosinemie").value(hasItem(DEFAULT_TYROSINEMIE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].travail").value(hasItem(DEFAULT_TRAVAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].scolarise").value(hasItem(DEFAULT_SCOLARISE.toString())));
    }

    @Test
    void getFiche() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        // Get the fiche
        restFicheMockMvc
            .perform(get(ENTITY_API_URL_ID, fiche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fiche.getId()))
            .andExpect(jsonPath("$.homocystinurie").value(DEFAULT_HOMOCYSTINURIE.booleanValue()))
            .andExpect(jsonPath("$.leucinose").value(DEFAULT_LEUCINOSE.booleanValue()))
            .andExpect(jsonPath("$.tyrosinemie").value(DEFAULT_TYROSINEMIE.booleanValue()))
            .andExpect(jsonPath("$.dateMaj").value(DEFAULT_DATE_MAJ.toString()))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE.toString()))
            .andExpect(jsonPath("$.travail").value(DEFAULT_TRAVAIL.booleanValue()))
            .andExpect(jsonPath("$.scolarise").value(DEFAULT_SCOLARISE.toString()));
    }

    @Test
    void getNonExistingFiche() throws Exception {
        // Get the fiche
        restFicheMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFiche() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();

        // Update the fiche
        Fiche updatedFiche = ficheRepository.findById(fiche.getId()).get();
        updatedFiche
            .homocystinurie(UPDATED_HOMOCYSTINURIE)
            .leucinose(UPDATED_LEUCINOSE)
            .tyrosinemie(UPDATED_TYROSINEMIE)
            .dateMaj(UPDATED_DATE_MAJ)
            .activite(UPDATED_ACTIVITE)
            .travail(UPDATED_TRAVAIL)
            .scolarise(UPDATED_SCOLARISE);
        FicheDTO ficheDTO = ficheMapper.toDto(updatedFiche);

        restFicheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ficheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
        Fiche testFiche = ficheList.get(ficheList.size() - 1);
        assertThat(testFiche.getHomocystinurie()).isEqualTo(UPDATED_HOMOCYSTINURIE);
        assertThat(testFiche.getLeucinose()).isEqualTo(UPDATED_LEUCINOSE);
        assertThat(testFiche.getTyrosinemie()).isEqualTo(UPDATED_TYROSINEMIE);
        assertThat(testFiche.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
        assertThat(testFiche.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testFiche.getTravail()).isEqualTo(UPDATED_TRAVAIL);
        assertThat(testFiche.getScolarise()).isEqualTo(UPDATED_SCOLARISE);
    }

    @Test
    void putNonExistingFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ficheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFicheWithPatch() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();

        // Update the fiche using partial update
        Fiche partialUpdatedFiche = new Fiche();
        partialUpdatedFiche.setId(fiche.getId());

        partialUpdatedFiche
            .tyrosinemie(UPDATED_TYROSINEMIE)
            .dateMaj(UPDATED_DATE_MAJ)
            .activite(UPDATED_ACTIVITE)
            .travail(UPDATED_TRAVAIL)
            .scolarise(UPDATED_SCOLARISE);

        restFicheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiche.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiche))
            )
            .andExpect(status().isOk());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
        Fiche testFiche = ficheList.get(ficheList.size() - 1);
        assertThat(testFiche.getHomocystinurie()).isEqualTo(DEFAULT_HOMOCYSTINURIE);
        assertThat(testFiche.getLeucinose()).isEqualTo(DEFAULT_LEUCINOSE);
        assertThat(testFiche.getTyrosinemie()).isEqualTo(UPDATED_TYROSINEMIE);
        assertThat(testFiche.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
        assertThat(testFiche.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testFiche.getTravail()).isEqualTo(UPDATED_TRAVAIL);
        assertThat(testFiche.getScolarise()).isEqualTo(UPDATED_SCOLARISE);
    }

    @Test
    void fullUpdateFicheWithPatch() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();

        // Update the fiche using partial update
        Fiche partialUpdatedFiche = new Fiche();
        partialUpdatedFiche.setId(fiche.getId());

        partialUpdatedFiche
            .homocystinurie(UPDATED_HOMOCYSTINURIE)
            .leucinose(UPDATED_LEUCINOSE)
            .tyrosinemie(UPDATED_TYROSINEMIE)
            .dateMaj(UPDATED_DATE_MAJ)
            .activite(UPDATED_ACTIVITE)
            .travail(UPDATED_TRAVAIL)
            .scolarise(UPDATED_SCOLARISE);

        restFicheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiche.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiche))
            )
            .andExpect(status().isOk());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
        Fiche testFiche = ficheList.get(ficheList.size() - 1);
        assertThat(testFiche.getHomocystinurie()).isEqualTo(UPDATED_HOMOCYSTINURIE);
        assertThat(testFiche.getLeucinose()).isEqualTo(UPDATED_LEUCINOSE);
        assertThat(testFiche.getTyrosinemie()).isEqualTo(UPDATED_TYROSINEMIE);
        assertThat(testFiche.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
        assertThat(testFiche.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testFiche.getTravail()).isEqualTo(UPDATED_TRAVAIL);
        assertThat(testFiche.getScolarise()).isEqualTo(UPDATED_SCOLARISE);
    }

    @Test
    void patchNonExistingFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ficheDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ficheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ficheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFiche() throws Exception {
        int databaseSizeBeforeUpdate = ficheRepository.findAll().size();
        fiche.setId(UUID.randomUUID().toString());

        // Create the Fiche
        FicheDTO ficheDTO = ficheMapper.toDto(fiche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ficheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fiche in the database
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFiche() throws Exception {
        // Initialize the database
        ficheRepository.save(fiche);

        int databaseSizeBeforeDelete = ficheRepository.findAll().size();

        // Delete the fiche
        restFicheMockMvc
            .perform(delete(ENTITY_API_URL_ID, fiche.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fiche> ficheList = ficheRepository.findAll();
        assertThat(ficheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
