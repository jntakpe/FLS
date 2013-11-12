package fr.sg.fmk.dto;

/**
 * Wrapper permettant de contr�ler la contrainte d'unicit� d'un champ
 *
 * @author jntakpe
 */
public final class Unicity {

    /**
     * Nom du champ surlequel s'applique la contrainte
     */
    private String field;

    /**
     * Identifiant de l'entit� (null pour une nouvelle entit�)
     */
    private Long id;

    /**
     * Valeur du champ
     */
    private Object value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Unicity{" +
                "field='" + field + '\'' +
                ", id=" + id +
                ", value=" + value +
                '}';
    }
}
