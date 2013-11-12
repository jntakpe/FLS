package fr.sg.fmk.dto;

/**
 * Wrapper permettant de contrôler la contrainte d'unicité d'un champ
 *
 * @author jntakpe
 */
public final class Unicity {

    /**
     * Nom du champ surlequel s'applique la contrainte
     */
    private String field;

    /**
     * Identifiant de l'entité (null pour une nouvelle entité)
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
