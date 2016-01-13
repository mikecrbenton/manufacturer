
package org.usd.csci.product;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.usd.csci.manufacturer.ManufacturerEntity;

/**
 *
 * @author Mike Benton CSC280
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
    @NamedQuery(name = "ProductEntity.findByProductId", query = "SELECT p FROM ProductEntity p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductEntity.findByPurchaseCost", query = "SELECT p FROM ProductEntity p WHERE p.purchaseCost = :purchaseCost"),
    @NamedQuery(name = "ProductEntity.findByQuantityOnHand", query = "SELECT p FROM ProductEntity p WHERE p.quantityOnHand = :quantityOnHand"),
    @NamedQuery(name = "ProductEntity.findByMarkup", query = "SELECT p FROM ProductEntity p WHERE p.markup = :markup"),
    @NamedQuery(name = "ProductEntity.findByAvailable", query = "SELECT p FROM ProductEntity p WHERE p.available = :available"),
    @NamedQuery(name = "ProductEntity.findByDescription", query = "SELECT p FROM ProductEntity p WHERE p.description = :description"),
    //QUERIES BELOW ADDED FOR ASSIGNMENT
    @NamedQuery(name = "ProductEntity.findAllByProductCode", query = "SELECT p FROM ProductEntity p WHERE p.productCode = :productCode"),
    @NamedQuery(name = "ProductEntity.findAllByManufacturer", query = "SELECT p FROM ProductEntity p WHERE p.manufacturerId = :manufacturerId"),
    @NamedQuery(name = "ProductEntity.findUnderQOHLimit", query = "SELECT p FROM ProductEntity p WHERE p.quantityOnHand < :quantityOnHand"),
    @NamedQuery(name = "ProductEntity.findAllAvailable", query = "SELECT p FROM ProductEntity p WHERE p.available = :available")
               })

public class ProductEntity implements Serializable, Comparable<ProductEntity> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private Integer productId;
       
    @DecimalMin("0.00")
    @Column(name = "PURCHASE_COST")
    private BigDecimal purchaseCost;
    
    @Min(value=0) 
    @Column(name = "QUANTITY_ON_HAND")
    private Integer quantityOnHand;
    
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @Column(name = "MARKUP")
    private BigDecimal markup;
    
    @Pattern(regexp="^([T][R][U][E]|[F][A][L][S][E])",
            message="Must be true or false - Uppercase Only")
    @Size(max = 5)
    @Column(name = "AVAILABLE")
    private String available;
    
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "MANUFACTURER_ID")
    @ManyToOne(optional = false)
    private ManufacturerEntity manufacturerId;
    
    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PROD_CODE")
    @ManyToOne(optional = false)
    private ProductCodeEntity productCode;

    public ProductEntity() {
    }

    public ProductEntity(Integer productId) {
        this.productId = productId;
    }

    /**
     * getProductId         returns an Entity's productId
     * 
     * @return              an Integer object representing the productId
     */
    public Integer getProductId() {
        return productId;
    }
    
    /**
     * setProductId        receives an Integer and sets an Entity's 
     *                     productId
     * 
     * @param productId    Integer representing the Entity productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * getPurchaseCost     returns an Entity's purchaseCost
     * 
     * @return              BigDecimal representing an Entity's purchaseCost
     */
    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    /**
     * setPurchaseCost        receives a BigDecimal and sets an Entity's 
     *                        purchaseCost
     * 
     * @param purchaseCost    BigDecimal representing the Entity purchaseCost
     */
    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    /**
     * getQuantityOnHand     returns an Entity's quantityOnHand
     * 
     * @return               Integer representing an Entity's quantityOnHand
     */
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * setQuantityOnHand        receives an Integer and sets an Entity's 
     *                          quantityOnHand
     * 
     * @param quantityOnHand    Integer representing the Entity quantityOnHand
     */
    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
    
    /**
     * getMarkup             returns an Entity's markup
     * 
     * @return               BigDecimal representing an Entity's markup
     */
    public BigDecimal getMarkup() {
        return markup;
    }

    /**
     * setMarkup                receives a BigDecimal and sets an Entity's 
     *                          markup
     * 
     * @param quantityOnHand    BigDecimal representing the Entity markup
     */
    public void setMarkup(BigDecimal markup) {
        this.markup = markup;
    }

    /**
     * getAvailable             returns an Entity's available field
     * 
     * @return                  String representing an Entity's available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * setAvailable             receives a String and sets an Entity's 
     *                          available
     * 
     * @param available         String representing the Entity available
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * getDescription           returns an Entity's description
     * 
     * @return                  String representing an Entity's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription           receives a String and sets an Entity's 
     *                          description
     * 
     * @param available         String representing the Entity description
     */
    public void setDescription(String description) {
        this.description = description;
    }

     /**
     * getManufacturerId        returns a ManufacturerEntity
     * 
     * @return                  Object representing a ManufacturerEntity
     */
    public ManufacturerEntity getManufacturerId() {
        return manufacturerId;
    }

    /**
     * setManufacturerId        receives a ManufacturerEntity and sets an Entity 
     *                          to that ManufacturerEntity
     * 
     * @param manufacturerId    ManufacturerEntity Object
     */
    public void setManufacturerId(ManufacturerEntity manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /**
     * getProductCode           returns a ProductCode Entity
     * 
     * @return                  Object representing a ProductCodeEntity
     */
    public ProductCodeEntity getProductCode() {
        return productCode;
    }

    /**
     * setProductCode           receives a ProductCodeEntity and sets an Entity 
     *                          to that ProductCodeEntity
     * 
     * @param productCode        ProductCodeEntity Object
     */
    public void setProductCode(ProductCodeEntity productCode) {
        this.productCode = productCode;
    }

    /**
     * hashCode    returns a unique integer for Object field
     * 
     * @return      integer 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }
    
    /**
     * equals           receives an Object returns a boolean true if it is equal
     *                  to this.object                  
     * 
     * @param object    Object object
     * 
     * @return          returns a boolean true if both objects are equal and false
     *                  if they are not equal
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductEntity)) {
            return false;
        }
        ProductEntity other = (ProductEntity) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    /**
     * toString         returns the description and product id
     * 
     * @return          String value of the specified variable 
     */
    @Override
    public String toString() {
        return description + " /Product ID: " + productId ;
    }

    /**
     * compareTo        receives a ProductEntity Object and returns a -1,0 
     *                  , or 1 if the the parameter is less than equal to or 
     *                  greater than the object parameter which is calling the method
     * 
     * @param obj       ProductEntity object
     * 
     * @return          returns an int showing the comparison of two Object fields
     */
    @Override
    public int compareTo(ProductEntity o) {
        return this.productId.compareTo(o.productId);
    }
    
}
