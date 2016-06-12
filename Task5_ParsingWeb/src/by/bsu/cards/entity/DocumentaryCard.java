
package by.bsu.cards.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Documentary-card complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Documentary-card">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.com/cards}Card">
 *       &lt;sequence>
 *         &lt;element name="photo" type="{http://www.example.com/cards}Photo"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Documentary-card", namespace = "http://www.example.com/cards", propOrder = {
    "photo"
})
public class DocumentaryCard
    extends Card
{

    @XmlElement(namespace = "http://www.example.com/cards", required = true)
    private Photo photo;

    /**
     * Gets the value of the photo property.
     * 
     * @return
     *     possible object is
     *     {@link Photo }
     *     
     */
    public Photo getPhoto() {
        return photo;
    }

    /**
     * Sets the value of the photo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Photo }
     *     
     */
    public void setPhoto(Photo value) {
        this.photo = value;
    }

    @Override
    public String toString() {
        StringBuilder cardOutput = new StringBuilder(super.toString());
        cardOutput.append(" photo: " + photo.toString());
        return cardOutput.toString();
    }
}
