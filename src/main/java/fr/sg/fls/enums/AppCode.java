package fr.sg.fls.enums;

/**
 * Enumération correspondante aux valeurs possibles du champ AppCode d'un SCT filtré
 *
 * @author jntakpe
 */
public enum AppCode {

    /**
     * Code correspondant à NICE
     */
    NICE("8XXSCT"),

    /**
     * Code correspondant à LCL
     */
    LCL("LCLSCT"),

    /**
     * Code correspondant à CACIB
     */
    CACIB("CACIBSCT"),

    /**
     * Code correspondant à 3PG
     */
    THREEPG("3PGSCT");

    private final String value;

    private AppCode(String value) {
        this.value = value;
    }

    /**
     * Récupère l'instance à partir de la valeur. Utile pour JPA car sinon l'enum ne ferait pas parsable ('un champ ne
     * peut pas commencer par un chiffre').
     *
     * @param value valeur du champ
     * @return instance
     */
    public static AppCode parse(String value) {
        AppCode appCode = null;
        for (AppCode item : AppCode.values()) {
            if (item.getValue().equals(value)) {
                appCode = item;
                break;
            }
        }
        return appCode;
    }

    public String getValue() {
        return value;
    }
}
