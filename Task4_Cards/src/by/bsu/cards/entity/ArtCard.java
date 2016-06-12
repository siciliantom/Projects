
package by.bsu.cards.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Art-card complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Art-card">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.com/cards}Card">
 *       &lt;sequence>
 *         &lt;element name="picture" type="{http://www.example.com/cards}Picture"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Art-card", namespace = "http://www.example.com/cards", propOrder = {
    "picture"
})
public class ArtCard extends Card

{

    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    private Picture picture;

    /**
     * Gets the value of the picture property.
     * 
     * @return
     *     possible object is
     *     {@link Picture }
     *     
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * Sets the value of the picture property.
     * 
     * @param value
     *     allowed object is
     *     {@link Picture }
     *     
     */
    public void setPicture(Picture value) {
        this.picture = value;
    }


    public String toStrinsg() {
        StringBuilder cardOutput = new StringBuilder(super.toString());
        cardOutput.append("ArtCard{" +
                "picture=" + picture.toString() +
                '}');
        return cardOutput.toString();
    }
}
