
package by.bsu.cards.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Photo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Photo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="year-of-shot" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Photo", namespace = "http://www.example.com/cards", propOrder = {
    "name",
    "author",
    "yearOfShot"
})
public class Photo {

    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    private String name;
    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    private String author;
    @XmlElement(name = "year-of-shot", namespace = "http://www.example.com/cards", required = true)
    private String yearOfShot;

    public Photo() {
    }

    public Photo(String yearOfShot, String author, String name) {
        this.yearOfShot = yearOfShot;
        this.author = author;
        this.name = name;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the yearOfShot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearOfShot() {
        return yearOfShot;
    }

    /**
     * Sets the value of the yearOfShot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearOfShot(String value) {
        this.yearOfShot = value;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfShot='" + yearOfShot + '\'' +
                '}';
    }
}
