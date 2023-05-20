package com.medical.registry.domain;

import com.medical.registry.domain.enumeration.eactivite;
import com.medical.registry.domain.enumeration.escolarise;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Fiche.
 */
@Document(collection = "fiche")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fiche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("homocystinurie")
    private Boolean homocystinurie;

    @Field("leucinose")
    private Boolean leucinose;

    @Field("tyrosinemie")
    private Boolean tyrosinemie;

    @Field("date_maj")
    private LocalDate dateMaj;

    @Field("activite")
    private eactivite activite;

    @Field("travail")
    private Boolean travail;

    @Field("scolarise")
    private escolarise scolarise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Fiche id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHomocystinurie() {
        return this.homocystinurie;
    }

    public Fiche homocystinurie(Boolean homocystinurie) {
        this.setHomocystinurie(homocystinurie);
        return this;
    }

    public void setHomocystinurie(Boolean homocystinurie) {
        this.homocystinurie = homocystinurie;
    }

    public Boolean getLeucinose() {
        return this.leucinose;
    }

    public Fiche leucinose(Boolean leucinose) {
        this.setLeucinose(leucinose);
        return this;
    }

    public void setLeucinose(Boolean leucinose) {
        this.leucinose = leucinose;
    }

    public Boolean getTyrosinemie() {
        return this.tyrosinemie;
    }

    public Fiche tyrosinemie(Boolean tyrosinemie) {
        this.setTyrosinemie(tyrosinemie);
        return this;
    }

    public void setTyrosinemie(Boolean tyrosinemie) {
        this.tyrosinemie = tyrosinemie;
    }

    public LocalDate getDateMaj() {
        return this.dateMaj;
    }

    public Fiche dateMaj(LocalDate dateMaj) {
        this.setDateMaj(dateMaj);
        return this;
    }

    public void setDateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
    }

    public eactivite getActivite() {
        return this.activite;
    }

    public Fiche activite(eactivite activite) {
        this.setActivite(activite);
        return this;
    }

    public void setActivite(eactivite activite) {
        this.activite = activite;
    }

    public Boolean getTravail() {
        return this.travail;
    }

    public Fiche travail(Boolean travail) {
        this.setTravail(travail);
        return this;
    }

    public void setTravail(Boolean travail) {
        this.travail = travail;
    }

    public escolarise getScolarise() {
        return this.scolarise;
    }

    public Fiche scolarise(escolarise scolarise) {
        this.setScolarise(scolarise);
        return this;
    }

    public void setScolarise(escolarise scolarise) {
        this.scolarise = scolarise;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fiche)) {
            return false;
        }
        return id != null && id.equals(((Fiche) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fiche{" +
            "id=" + getId() +
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
