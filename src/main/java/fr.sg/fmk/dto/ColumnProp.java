package fr.sg.fmk.dto;

import fr.sg.fmk.constant.DTRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 * Propriété d'une colonne de table DataTables
 *
 * @author jntakpe
 */
public final class ColumnProp {

    /**
     * Nom de la colonne
     */
    private final String name;

    /**
     * Colonne triable
     */
    private final boolean sortable;

    /**
     * Colonne triée
     */
    private boolean sorted;

    /**
     * Colonne filtrable
     */
    private final boolean filterable;

    /**
     * Colonne filtrée
     */
    private boolean filtered;

    /**
     * Valeur du champ de recherche
     */
    private final String search;

    /**
     * Direction du tri de la colonne
     */
    private Sort.Direction sortDirection;

    /**
     * Constructeur privée appelé par la méthode statique de build
     *
     * @param name       nom de la colonne
     * @param sortable   colonne triable
     * @param filterable colonne filtrable
     * @param search     valeur du champ de recherche
     */
    private ColumnProp(String name, boolean sortable, boolean filterable, String search) {
        this.name = name;
        this.sortable = sortable;
        this.filterable = filterable;
        this.search = search;
    }

    /**
     * Méthode statique de création d'instance
     *
     * @param request requête envoyée par DataTables
     * @param idx     index de la colonne
     * @return l'instance initialisée
     */
    public static ColumnProp newInstance(HttpServletRequest request, int idx) {
        String name = request.getParameter(DTRequest.M_DATA_PROP.getParam() + idx);
        String search = request.getParameter(DTRequest.S_COLUMN_SEARCH.getParam() + idx);
        boolean sortable = Boolean.parseBoolean(request.getParameter(DTRequest.B_SORTABLE.getParam() + idx));
        boolean filterable = Boolean.parseBoolean(request.getParameter(DTRequest.B_SEARCHABLE.getParam() + idx));
        return new ColumnProp(name, sortable, filterable, search);
    }

    public String getName() {
        return name;
    }

    public boolean isSortable() {
        return sortable;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public String getSearch() {
        return search;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        return "ColumnProp{" +
                "name='" + name + '\'' +
                ", sortable=" + sortable +
                ", sorted=" + sorted +
                ", filterable=" + filterable +
                ", filtered=" + filtered +
                ", search='" + search + '\'' +
                ", sortDirection=" + sortDirection +
                '}';
    }
}
