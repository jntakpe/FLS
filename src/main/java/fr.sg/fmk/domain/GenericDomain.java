package fr.sg.fmk.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe � �tendre pour la cr�ation d'entit�s.
 * Toute classe fille doit absolument impl�menter son propre g�n�rateur de s�quence.
 *
 * @author jntakpe
 */
@MappedSuperclass
public abstract class GenericDomain implements Serializable {

    /**
     * Cl� primaire et technique de toutes les entit�s �tendant cette classe
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SG")
    private Long id;

    /**
     * Version permettant de d�tecter les modifications concurrentes
     */
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
