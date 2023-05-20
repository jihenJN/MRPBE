package com.medical.registry.service.mapper;

import com.medical.registry.domain.Fiche;
import com.medical.registry.service.dto.FicheDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fiche} and its DTO {@link FicheDTO}.
 */
@Mapper(componentModel = "spring")
public interface FicheMapper extends EntityMapper<FicheDTO, Fiche> {}
