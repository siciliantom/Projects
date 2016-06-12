
package by.bsu.cards.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Card complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Card">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="theme" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valuable">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="historical"/>
 *               &lt;enumeration value="collection"/>
 *               &lt;enumeration value="thematic"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="type" default="ordinary">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="congratulatory"/>
 *             &lt;enumeration value="advertising"/>
 *             &lt;enumeration value="ordinary"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="posted" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="registry" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}ID">
 *             &lt;pattern value="[a-zA-Z]{1}[0-9]{6}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Card", namespace = "http://www.example.com/cards", propOrder = {
    "theme",
    "country",
    "year",
    "valuable"
})
@XmlSeeAlso({
    ArtCard.class,
    DocumentaryCard.class
})
public abstract class Card {

    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    protected String theme;
    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    protected String country;
    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    protected String year;
    @XmlElement(namespace = "http://www.example.com/cards", required = true, defaultValue = "thematic")
    protected String valuable;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "posted")
    protected Boolean posted;
    @XmlAttribute(name = "registry", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String registry;

    /**
     * Gets the value of the theme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the value of the theme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTheme(String value) {
        this.theme = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYear(String value) {
        this.year = value;
    }

    /**
     * Gets the value of the valuable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuable() {
        return valuable;
    }

    /**
     * Sets the value of the valuable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuable(String value) {
        this.valuable = value;
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
        if (type == null) {
            return "ordinary";
        } else {
            return type;
        }
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
     * Gets the value of the posted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPosted() {
        if (posted == null) {
            return true;
        } else {
            return posted;
        }
    }

    /**
     * Sets the value of the posted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPosted(Boolean value) {
        this.posted = value;
    }

    /**
     * Gets the value of the registry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistry() {
        return registry;
    }

    /**
     * Sets the value of the registry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistry(String value) {
        this.registry = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "theme='" + theme + '\'' +
                ", country='" + country + '\'' +
                ", year='" + year + '\'' +
                ", valuable='" + valuable + '\'' +
                ", type='" + type + '\'' +
                ", posted=" + posted +
                ", registry='" + registry + '\'' +
                '}';
    }
}
