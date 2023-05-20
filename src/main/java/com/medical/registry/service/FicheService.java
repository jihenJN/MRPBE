package com.medical.registry.service;

import com.medical.registry.domain.Fiche;
import com.medical.registry.repository.FicheRepository;
import com.medical.registry.service.dto.FicheDTO;
import com.medical.registry.service.mapper.FicheMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Fiche}.
 */
@Service
public class FicheService {

    private final Logger log = LoggerFactory.getLogger(FicheService.class);

    private final FicheRepository ficheRepository;

    private final FicheMapper ficheMapper;

    public FicheService(FicheRepository ficheRepository, FicheMapper ficheMapper) {
        this.ficheRepository = ficheRepository;
        this.ficheMapper = ficheMapper;
    }

    /**
     * Save a fiche.
     *
     * @param ficheDTO the entity to save.
     * @return the persisted entity.
     */
    public FicheDTO save(FicheDTO ficheDTO) {
        log.debug("Request to save Fiche : {}", ficheDTO);
        Fiche fiche = ficheMapper.toEntity(ficheDTO);
        fiche = ficheRepository.save(fiche);
        return ficheMapper.toDto(fiche);
    }

    /**
     * Update a fiche.
     *
     * @param ficheDTO the entity to save.
     * @return the persisted entity.
     */
    public FicheDTO update(FicheDTO ficheDTO) {
        log.debug("Request to update Fiche : {}", ficheDTO);
        Fiche fiche = ficheMapper.toEntity(ficheDTO);
        fiche = ficheRepository.save(fiche);
        return ficheMapper.toDto(fiche);
    }

    /**
     * Partially update a fiche.
     *
     * @param ficheDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FicheDTO> partialUpdate(FicheDTO ficheDTO) {
        log.debug("Request to partially update Fiche : {}", ficheDTO);

        return ficheRepository
            .findById(ficheDTO.getId())
            .map(existingFiche -> {
                ficheMapper.partialUpdate(existingFiche, ficheDTO);

                return existingFiche;
            })
            .map(ficheRepository::save)
            .map(ficheMapper::toDto);
    }

    /**
     * Get all the fiches.
     *
     * @return the list of entities.
     */
    public List<FicheDTO> findAll() {
        log.debug("Request to get all Fiches");
        return ficheRepository.findAll().stream().map(ficheMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fiche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FicheDTO> findOne(String id) {
        log.debug("Request to get Fiche : {}", id);
        return ficheRepository.findById(id).map(ficheMapper::toDto);
    }

    /**
     * Delete the fiche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Fiche : {}", id);
        ficheRepository.deleteById(id);
    }
}
