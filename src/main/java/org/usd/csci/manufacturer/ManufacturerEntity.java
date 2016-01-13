
package org.usd.csci.manufacturer;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.usd.csci.product.ProductEntity;

/**
 *
 * @author Mike Benton CSC280
 */
@Entity
@Table(name = "MANUFACTURER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManufacturerEntity.findAll", query = "SELECT m FROM ManufacturerEntity m"),
    @NamedQuery(name = "ManufacturerEntity.findByManufacturerId", query = "SELECT m FROM ManufacturerEntity m WHERE m.manufacturerId = :manufacturerId"),
    @NamedQuery(name = "ManufacturerEntity.findByName", query = "SELECT m FROM ManufacturerEntity m WHERE m.name = :name"),
    @NamedQuery(name = "ManufacturerEntity.findByAddressline1", query = "SELECT m FROM ManufacturerEntity m WHERE m.addressline1 = :addressline1"),
    @NamedQuery(name = "ManufacturerEntity.findByAddressline2", query = "SELECT m FROM ManufacturerEntity m WHERE m.addressline2 = :addressline2"),
    @NamedQuery(name = "ManufacturerEntity.findByCity", query = "SELECT m FROM ManufacturerEntity m WHERE m.city = :city"),
    @NamedQuery(name = "ManufacturerEntity.findByState", query = "SELECT m FROM ManufacturerEntity m WHERE m.state = :state"),
    @NamedQuery(name = "ManufacturerEntity.findByZip", query = "SELECT m FROM ManufacturerEntity m WHERE m.zip = :zip"),
    @NamedQuery(name = "ManufacturerEntity.findByPhone", query = "SELECT m FROM ManufacturerEntity m WHERE m.phone = :phone"),
    @NamedQuery(name = "ManufacturerEntity.findByFax", query = "SELECT m FROM ManufacturerEntity m WHERE m.fax = :fax"),
    @NamedQuery(name = "ManufacturerEntity.findByEmail", query = "SELECT m FROM ManufacturerEntity m WHERE m.email = :email"),
    //@NamedQuery added for assignment 
    @NamedQuery(name = "ManufacturerEntity.findByCityAndState", query = "SELECT m FROM ManufacturerEntity m WHERE m.city = :city AND m.state = :state"),
    @NamedQuery(name = "ManufacturerEntity.findByRep", query = "SELECT m FROM ManufacturerEntity m WHERE m.rep = :rep")})

public class ManufacturerEntity implements Serializable,Comparable<ManufacturerEntity> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull(message = "Cannot be null")
    @Column(name = "MANUFACTURER_ID")
    private Integer manufacturerId;
    
    @Pattern(regexp="^([A-Z]{1}[A-Za-z]+)([ -]*[A-Za-z]*[ -]*)*",
            message = "Invalid format, should be xxxxx or xxxx-xxxx" )
    @Size(max = 30, message = "Maximum size is 30 characters")
    @NotNull(message = "Cannot be null")
    @Column(name = "NAME")
    private String name;
    
    @Size(max = 30, message = "Maximum size is 30 characters")
    @Column(name = "ADDRESSLINE1")
    @NotNull(message = "Cannot be null")
    private String addressline1;
    
    @Size(max = 30, message = "Maximum size is 30 characters")
    @Column(name = "ADDRESSLINE2")
    private String addressline2;
    
    @Pattern(regexp="^([A-Z]{1}[A-Za-z]+)([ -]*[A-Za-z]*[ -]*)*", 
            message="Invalid format")
    @Size(max = 25, message = "Maximum size is 25")
    @Column(name = "CITY")
    private String city;
    
    @Pattern(regexp="(A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]"
            + "|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])"
            , message="Invalid state format, should be XX(no lowercase)")
    @Size(max = 2,message = "Size is 2 maximum")
    @NotNull(message = "Cannot be null")
    @Column(name = "STATE")
    private String state;
    
    @Pattern(regexp="[0-9]{5}[\\-]?[0-9]{4}|[0-9]{5}", message="Invalid zip format, should be xxxxx or xxxxx-xxxx")
    @Size(max = 10, message = "Limit is 10 characters")
    @Column(name = "ZIP")
    private String zip;
    
    @Pattern(regexp="^[0-9]{3}-[0-9]{3}-[0-9]{4}?", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 12, message = "Limit is 12 characters(including special characters)")
    @Column(name = "PHONE")
    private String phone;
    
    @Pattern(regexp="^[0-9]{3}-[0-9]{3}-[0-9]{4}?", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 12, message = "Limit is 12 characters(including special characters)")
    @Column(name = "FAX")
    private String fax;
    
    @Pattern(regexp="[A-Za-z0-9!#\\.$%&'*+/=?^_`{|}~-]+[@.]+[a-zA-Z0-9]+\\.[a-zA-Z]+", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 40, message = "Maximum size exceeded(40 characters)")
    @Column(name = "EMAIL")
    private String email;
    
    @Pattern(regexp="^([a-zA-Z0-9]+(?:\\.)?(?:(?:'| )[a-zA-Z0-9]+(?:\\.)?)*)$"
    ,message= "Invalid format")
    @Size(max =30 , message = "Size is limited to 30")
    @NotNull(message = "Cannot be null")
    @Column(name = "REP")
    private String rep;

    public ManufacturerEntity() {
    }

    public ManufacturerEntity(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    
    /**
     * getManufacturerId    returns an Entity's manufacturerId
     * 
     * @return              Integer object representing the manufacturerId
     */
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    
    /**
     * setManufacturerId        receives an Integer and sets an Entities 
     *                          manufufacturerId
     * 
     * @param manufacturerId    Integer representing the Entity manufacturerId
     */
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    
    /**
     * getName              returns an Entity's name
     * 
     * @return              String representing the Entity name 
     */
    public String getName() {
        return name;
    }
    
    /**
     * setName              receives a String representing the Entity name and
     *                      sets the Entity's name
     * 
     * @param name          String representing the Entity name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getAddressline1      returns the Line 1 Address as a String from an Entity
     * 
     * @return              String representing the Line 2 Address
     */
    public String getAddressline1() {
        return addressline1;
    }
    
    /**
     * setAddressline1          receives a String representing the Entity address
     *                          line 1 and sets the Entity's address line 1
     * 
     * @param addressline1      String representing the address line 1 
     */
    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    /**
     * getAddressline2      returns the Line 2 Address as a String from an Entity
     * 
     * @return              String representing the Line 2 Address
     */
    public String getAddressline2() {
        return addressline2;
    }
    
    /**
     * setAddressline2          receives a String representing the Entity address
     *                          line 2 and sets the Entity's address line 2
     * 
     * @param addressline2      String representing the address line 2 
     */
    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }
    
    /**
     * getCity          returns the city from an Entity as a String
     * 
     * @return          String representing the city 
     */
    public String getCity() {
        return city;
    }
    
    /**
     * setCity          receives a String representing an Entity city and
     *                  sets the Entity city
     * 
     * @param city      String representing a city 
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * getState         returns the state from an Entity as a String
     * 
     * @return          String representing the state 
     */
    public String getState() {
        return state;
    }

    /**
     * setState         receives a String representing an Entity state and
     *                  sets the Entity state
     * 
     * @param state     String representing a state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * getZip           returns the zip code from an Entity as a String
     * 
     * @return          String representing the zip code 
     */
    public String getZip() {
        return zip;
    }

    /**
     * setZip           receives a String representing an Entity zip code and
     *                  sets the Entity zip code
     * 
     * @param zip       String representing a zip code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    /**
     * getPhone         returns the phone number from an Entity as a String
     * 
     * @return          String representing the phone number 
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setPhone         receives a String representing an Entity phone number and
     *                  sets the Entity phone number
     * 
     * @param phone     String representing a phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getFax           returns the fax number from an Entity as a String
     * 
     * @return          String representing the fax number 
     */
    public String getFax() {
        return fax;
    }

    /**
     * setFax          receives a String representing an Entity fax number and
     *                 sets the Entity fax number
     * 
     * @param fax      String representing a fax number
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * getEmail         returns the email address from an Entity as a String
     * 
     * @return          String representing the email address 
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail         receives a String representing an Entity email address and
     *                  sets the Entity email address
     * 
     * @param email     String representing an email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getRep           returns the rep name from an Entity as a String
     * 
     * @return          String representing the rep name 
     */
    public String getRep() {
        return rep;
    }

    /**
     * setRep          receives a String representing an Entity rep name and
     *                 sets the Entity rep name
     * 
     * @param rep      String representing the rep name
     */
    public void setRep(String rep) {
        this.rep = rep;
    }
    
    /**
     * hashCode    returns a unique integer for Object field
     * 
     * @return      integer 
     */
    @Override
    public int hashCode() {
        
        int hash = 0;
        //CHANGED FROM MANUFACTURERID 
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    /**
     * equals           receives an Object returns a boolean true if it is equal
     *                  to the test object                  
     * 
     * @param object    Object object
     * 
     * @return          returns a boolean true if both objects are equal and false
     *                  if they are not equal
     */
    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof ManufacturerEntity)) {
            return false;
        }
        ManufacturerEntity other = (ManufacturerEntity) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }
    
    /**
     * compareTo        receives a ManufacturerEntity Object and returns a -1,0 
     *                  , or 1 if the the parameter is less than equal to or 
     *                  greater than the object parameter which is calling the method
     * 
     * @param obj       ManufacturerEntity object
     * 
     * @return          returns an int showing the comparison of two Object fields
     */
    @Override
    public int compareTo(ManufacturerEntity obj) {
        return this.name.compareTo(obj.name);
    }
   
    /**
     * toString         returns the name of the calling objects specified parameter 
     * 
     * @return          returns the value of the specified variable 
     */
    @Override
    public String toString() {
        return name;
    }
    
    public static void main(String[] args) {
        ManufacturerEntity me = new ManufacturerEntity();
        
        System.out.println(me.toString());
    }

    
}
