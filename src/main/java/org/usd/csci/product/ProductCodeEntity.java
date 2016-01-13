
package org.usd.csci.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.usd.csci.utility.InvalidEntityException;

/**
 *
 * @author Mike Benton CSC280
 */
@Entity
@Table(name = "PRODUCT_CODE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCodeEntity.findAll", query = "SELECT p FROM ProductCodeEntity p"),
    @NamedQuery(name = "ProductCodeEntity.findByProdCode", query = "SELECT p FROM ProductCodeEntity p WHERE p.prodCode = :prodCode"),
    @NamedQuery(name = "ProductCodeEntity.findByDiscountCode", query = "SELECT p FROM ProductCodeEntity p WHERE p.discountCode = :discountCode"),
    @NamedQuery(name = "ProductCodeEntity.findByDescription", query = "SELECT p FROM ProductCodeEntity p WHERE p.description = :description")})
public class ProductCodeEntity implements Serializable, Comparable<ProductCodeEntity> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[A-Z]{2}", 
            message="Product Code must be 2 uppercase letters")
    @Column(name = "PROD_CODE")
    private String prodCode;
    
    @Basic(optional = false)
    @NotNull
    //BUSINESS RULE IS IN SET() METHOD
    @Column(name = "DISCOUNT_CODE")
    private Character discountCode;
    
    @Column(name = "DESCRIPTION")
    private String description;
  
    public ProductCodeEntity() {
    }
    
    public ProductCodeEntity(String prodCode) {
        this.prodCode = prodCode;
    }

    public ProductCodeEntity(String prodCode, Character discountCode) {
        this.prodCode = prodCode;
        this.discountCode = discountCode;
    }
    
    /**
     * getProductCode   returns a ProductCodeEntity's prodCode
     * 
     * @return          String representing a prodCode
     */
    public String getProdCode() {
        return prodCode;
    }
    
    /**
     * setProductCode   receives a String and sets a ProductCodeEntity's prodCode
     * 
     * @param prodCode  String representing a prodCode
     */
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    /**
     * getDiscountCode   returns a ProductCodeEntity's discountCode
     * 
     * @return          String representing a discountCode
     */
    public Character getDiscountCode() {
        return discountCode;
    }
    
    /**
     * setDiscountCode          receives a Character and sets a ProductCodeEntity's discountCode
     * 
     * @param discountCode      Character representing a discountCode
     * 
     * throws                   InvalidEntityException if letter is not uppercase A-Z
     */
    public void setDiscountCode(Character discountCode) throws InvalidEntityException {
            
        if(discountCode =='A'|discountCode =='B'|discountCode =='D'|discountCode =='E'|
           discountCode =='F'|discountCode =='G'|discountCode =='H'|discountCode =='I'|
           discountCode =='J'|discountCode =='K'|discountCode =='L'|discountCode =='M'|
           discountCode =='N'|discountCode =='O'|discountCode =='P'|discountCode =='Q'| 
           discountCode =='R'|discountCode =='S'|discountCode =='T'|discountCode =='U'|
           discountCode =='V'|discountCode =='W'|discountCode =='X'|discountCode =='Y'|
           discountCode =='Z'){
            
            this.discountCode = discountCode;
        }
        else{
            throw new InvalidEntityException("Must be an Uppercase Letter");
        }
        
    }
    /**
     * getDescription   returns a ProductCodeEntity's description
     * 
     * @return          String representing a description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription       receives a String and sets a ProductCodeEntity's description
     * 
     * @param description   String representing a description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodCode != null ? prodCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCodeEntity)) {
            return false;
        }
        ProductCodeEntity other = (ProductCodeEntity) object;
        if ((this.prodCode == null && other.prodCode != null) || (this.prodCode != null && !this.prodCode.equals(other.prodCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return prodCode;
    }

    @Override
    public int compareTo(ProductCodeEntity o) {
        return this.prodCode.compareTo(o.prodCode);
    }
    
    
}
