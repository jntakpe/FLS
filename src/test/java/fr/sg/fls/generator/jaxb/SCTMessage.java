
package fr.sg.fls.generator.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="field" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="type">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="string"/>
 *                         &lt;enumeration value="numeric"/>
 *                         &lt;enumeration value="sequence"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="pad">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="right"/>
 *                         &lt;enumeration value="left"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="values" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "field"
})
@XmlRootElement(name = "SCTMessage")
public class SCTMessage {

    @XmlElement(required = true)
    protected List<SCTMessage.Field> field;

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SCTMessage.Field }
     * 
     * 
     */
    public List<SCTMessage.Field> getField() {
        if (field == null) {
            field = new ArrayList<SCTMessage.Field>();
        }
        return this.field;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="type">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="string"/>
     *               &lt;enumeration value="numeric"/>
     *               &lt;enumeration value="sequence"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="pad">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="right"/>
     *               &lt;enumeration value="left"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="values" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "label",
        "length",
        "mandatory",
        "type",
        "pad",
        "values"
    })
    public static class Field {

        @XmlElement(required = true)
        protected String label;
        @XmlElement(required = true)
        protected BigInteger length;
        protected boolean mandatory;
        @XmlElement(required = true)
        protected String type;
        @XmlElement(required = true, nillable = true)
        protected String pad;
        @XmlElement(required = true, nillable = true)
        protected String values;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        /**
         * Gets the value of the length property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLength() {
            return length;
        }

        /**
         * Sets the value of the length property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLength(BigInteger value) {
            this.length = value;
        }

        /**
         * Gets the value of the mandatory property.
         * 
         */
        public boolean isMandatory() {
            return mandatory;
        }

        /**
         * Sets the value of the mandatory property.
         * 
         */
        public void setMandatory(boolean value) {
            this.mandatory = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the pad property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPad() {
            return pad;
        }

        /**
         * Sets the value of the pad property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPad(String value) {
            this.pad = value;
        }

        /**
         * Gets the value of the values property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValues() {
            return values;
        }

        /**
         * Sets the value of the values property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValues(String value) {
            this.values = value;
        }

    }

}
