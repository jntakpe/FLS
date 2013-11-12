package fr.sg.fmk.constant;

/**
 * Enumération des paramètres envoyés par DataTables
 *
 * @author jntakpe
 */
public enum DTRequest {

    B_REGEX_COL("bRegex_"),
    B_REGEX("bRegex"),
    B_SEARCHABLE("bSearchable_"),
    B_SORTABLE("bSortable_"),
    I_COLUMNS("iColumns"),
    I_DISPLAY_START("iDisplayStart"),
    I_DISPLAY_LENGTH("iDisplayLength"),
    I_SORTING_COLS("iSortingCols"),
    I_SORT_COL("iSortCol_"),
    M_DATA_PROP("mDataProp_"),
    S_ECHO("sEcho"),
    S_COLUMNS("sColumns"),
    S_SEARCH("sSearch"),
    S_COLUMN_SEARCH("sSearch_"),
    S_SORT_DIR("sSortDir_");

    /**
     * Valeur du paramètre d'url
     */
    private final String param;

    /**
     * Constructeur
     *
     * @param param valeur du paramètre d'url
     */
    private DTRequest(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
