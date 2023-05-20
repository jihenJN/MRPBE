package com.medical.registry.service.dto;

import com.medical.registry.domain.enumeration.eactivite;
import com.medical.registry.domain.enumeration.escolarise;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.medical.registry.domain.Fiche} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FicheDTO implements Serializable {

    private String id;

    private Boolean homocystinurie;

    private Boolean leucinose;

    private Boolean tyrosinemie;

    private LocalDate dateMaj;

    private eactivite activite;

    private Boolean travail;

    private escolarise scolarise;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHomocystinurie() {
        return homocystinurie;
    }

    public void setHomocystinurie(Boolean homocystinurie) {
        this.homocystinurie = homocystinurie;
    }

    public Boolean getLeucinose() {
        return leucinose;
    }

    public void setLeucinose(Boolean leucinose) {
        this.leucinose = leucinose;
    }

    public Boolean getTyrosinemie() {
        return tyrosinemie;
    }

    public void setTyrosinemie(Boolean tyrosinemie) {
        this.tyrosinemie = tyrosinemie;
    }

    public LocalDate getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
    }

    public eactivite getActivite() {
        return activite;
    }

    public void setActivite(eactivite activite) {
        this.activite = activite;
    }

    public Boolean getTravail() {
        return travail;
    }

    public void setTravail(Boolean travail) {
        this.travail = travail;
    }

    public escolarise getScolarise() {
        return scolarise;
    }

    public void setScolarise(escolarise scolarise) {
        this.scolarise = scolarise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheDTO)) {
            return false;
        }

        FicheDTO ficheDTO = (FicheDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ficheDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FicheDTO{" +
            "id='" + getId() + "'" +
            ", homocystinurie='" + getHomocystinurie() + "'" +
            ", leucinose='" + getLeucinose() + "'" +
            ", tyrosinemie='" + getTyrosinemie() + "'" +
            ", dateMaj='" + getDateMaj() + "'" +
            ", activite='" + getActivite() + "'" +
            ", travail='" + getTravail() + "'" +
            ", scolarise='" + getScolarise() + "'" +
            "}";
    }
}
