package fr.sg.fmk.dto;

import fr.sg.fmk.constant.DTRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper mappant les propri�t�s d'une table DataTables
 *
 * @author jntakpe
 */
public final class DatatablesRequest {

    /**
     * Num�ro de la premi�re donn�e de la page � afficher
     */
    private Integer displayStart;

    /**
     * Nombre de donn�es par page
     */
    private Integer displaySize;

    /**
     * Propriet�s d'une colonne
     */
    private List<ColumnProp> columnProps;

    /**
     * Compteur d'appels
     */
    private Integer callCounter;

    /**
     * Champ de recherche global
     */
    private String search;

    /**
     * Constructeur (pour Spring)
     */
    public DatatablesRequest() {
    }

    /**
     * Constructeur appel� par le m�thode de cr�ation d'instance
     * {@link fr.sg.fmk.dto.DatatablesRequest#buildInstance(javax.servlet.http.HttpServletRequest)}
     *
     * @param displayStart num�ro de la premi�re donn�e de la page � afficher
     * @param displaySize  nombre de donn�es par page
     * @param columnProps  propriet�s d'une colonne
     * @param callCounter  compteur d'appels
     * @param search       champ de recherche global
     */
    private DatatablesRequest(Integer displayStart, Integer displaySize, List<ColumnProp> columnProps,
                              Integer callCounter, String search) {
        this.displayStart = displayStart;
        this.displaySize = displaySize;
        this.columnProps = columnProps;
        this.callCounter = callCounter;
        this.search = search;
    }

    /**
     * M�thode de cr�ation de l'instance
     *
     * @param request requ�te envoy� par DataTables
     * @return instance initialis�e
     */
    public static DatatablesRequest buildInstance(HttpServletRequest request) {
        if (request == null) return null;

        String search = request.getParameter(DTRequest.S_SEARCH.getParam());
        String sEcho = request.getParameter(DTRequest.S_ECHO.getParam());
        String sDisplayStart = request.getParameter(DTRequest.I_DISPLAY_START.getParam());
        String sDisplayLength = request.getParameter(DTRequest.I_DISPLAY_LENGTH.getParam());
        String sColNumber = request.getParameter(DTRequest.I_COLUMNS.getParam());
        String sSortingColNumber = request.getParameter(DTRequest.I_SORTING_COLS.getParam());

        Integer echo = StringUtils.isNotBlank(sEcho) ? Integer.parseInt(sEcho) : null;
        Integer displayStart = StringUtils.isNotBlank(sDisplayStart) ? Integer.parseInt(sDisplayStart) : null;
        Integer displayLength = StringUtils.isNotBlank(sDisplayLength) ? Integer.parseInt(sDisplayLength) : null;
        Integer colNb = StringUtils.isNotBlank(sColNumber) ? Integer.parseInt(sColNumber) : -1;
        Integer sortingColNb = StringUtils.isNotBlank(sSortingColNumber) ? Integer.parseInt(sSortingColNumber) : -1;

        List<ColumnProp> columnProps = new LinkedList<ColumnProp>();

        for (int i = 0; i < colNb; i++) columnProps.add(ColumnProp.newInstance(request, i));

        for (int i = 0; i < sortingColNb; i++) {
            String sSortingCol = request.getParameter(DTRequest.I_SORT_COL.getParam() + i);
            Integer sortingCol = StringUtils.isNotBlank(sSortingCol) ? Integer.parseInt(sSortingCol) : null;
            ColumnProp columnProp = columnProps.get(sortingCol);
            String sDir = request.getParameter(DTRequest.S_SORT_DIR.getParam() + i);
            columnProp.setSortDirection(StringUtils.isNotBlank(sDir) ? Sort.Direction.fromString(sDir) : null);
            columnProp.setFiltered(!columnProp.getSearch().isEmpty());
            columnProp.setSorted(true);
        }

        return new DatatablesRequest(displayStart, displayLength, columnProps, echo, search);
    }

    /**
     * Indique si une colonne est tri�e
     *
     * @return true si une colonne est tri�e
     */
    public final boolean hasSortedColumn() {
        for (ColumnProp columnProp : columnProps)
            if (columnProp.isSorted()) return true;
        return false;
    }

    /**
     * Indique si une colonne est filtr�e
     *
     * @return true si une colonne est filtr�e
     */
    public final boolean hasFilteredColumn() {
        for (ColumnProp columnProp : columnProps)
            if (StringUtils.isNotBlank(columnProp.getSearch())) return true;
        return false;
    }

    public Integer getDisplayStart() {
        return displayStart;
    }

    public void setDisplayStart(Integer displayStart) {
        this.displayStart = displayStart;
    }

    public Integer getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Integer displaySize) {
        this.displaySize = displaySize;
    }

    public List<ColumnProp> getColumnProps() {
        return columnProps;
    }

    public void setColumnProps(List<ColumnProp> columnProps) {
        this.columnProps = columnProps;
    }

    public Integer getCallCounter() {
        return callCounter;
    }

    public void setCallCounter(Integer callCounter) {
        this.callCounter = callCounter;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "DatatablesRequest{" +
                "displayStart=" + displayStart +
                ", displaySize=" + displaySize +
                ", columnProps=" + columnProps +
                ", callCounter=" + callCounter +
                '}';
    }
}
