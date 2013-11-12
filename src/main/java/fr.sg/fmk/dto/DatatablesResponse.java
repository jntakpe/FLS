package fr.sg.fmk.dto;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Wrapper encapsulant la réponse envoyée à une DataTable
 *
 * @author jntakpe
 */
public final class DatatablesResponse<T extends GenericDomain> {

    /**
     * Contenu de la table à afficher
     */
    private final List<T> aaData;

    /**
     * Nombre total d'enregistrements sur une page (enregistrements filtrés)
     */
    private final Integer iTotalRecords;

    /**
     * Nombre total d'enregistrements (total de toutes les pages)
     */
    private final Long iTotalDisplayRecords;

    /**
     * Compteur d'appel au serveur
     */
    private final Integer sEcho;

    /**
     * Constructeur
     *
     * @param page  objet contenant les informations sur la page à afficher
     * @param sEcho compteur d'appel au serveur
     */
    public DatatablesResponse(Page<T> page, Integer sEcho) {
        this.aaData = page.getContent();
        this.iTotalRecords = page.getNumberOfElements();
        this.iTotalDisplayRecords = page.getTotalElements();
        this.sEcho = sEcho;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public Long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public Integer getsEcho() {
        return sEcho;
    }

    @Override
    public String toString() {
        return "DatatablesResponse{" +
                "aaData=" + aaData +
                ", iTotalRecords=" + iTotalRecords +
                ", iTotalDisplayRecords=" + iTotalDisplayRecords +
                ", sEcho=" + sEcho +
                '}';
    }
}
