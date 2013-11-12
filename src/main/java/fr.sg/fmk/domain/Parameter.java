package fr.sg.fmk.domain;

import fr.sg.fmk.constant.Format;

import javax.persistence.*;

/**
 * Entité représentant un paramètre
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_PARAMETER")
public class Parameter extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String code;

    private String label;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Format format;

    @Column(nullable = false)
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (code != null ? !code.equals(parameter.code) : parameter.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Parameter{");
        sb.append("label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
