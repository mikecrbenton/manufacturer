
package org.usd.csci.product;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import org.usd.csci.utility.InvalidEntityException;
import org.usd.csci.utility.AbstractFacade;

/**
 * @author Mike Benton CSC280
 */
@Stateless
public class ProductEntityFacade extends AbstractFacade<ProductEntity> {
    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;
    
    @Inject
    ManufacturerEntityFacade manFacade;
    
    @Inject
    ProductCodeEntityFacade productCodeFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductEntityFacade() {
        super(ProductEntity.class);
    }
    
    /**
     * create                           Receives a ProductEntity Object and
     *                                  creates a record in the database
     * 
     *@param product                    ProductEntity object
     * 
     *@throws InvalidEntityException    If Object is null or business rules are not met
     */
    @Override
    public void create(ProductEntity product)throws InvalidEntityException {
        
        //CHECK THE ENTITY OBJECT FOR NULL
        if(product == null){
            throw new InvalidEntityException("A product cannot be null");
        }
        //IF QUANTITY ON HAND IS 0, SET AVAILABLE TO FALSE. ELSE SET TO TRUE
        if(product.getQuantityOnHand() == 0){
            product.setAvailable("FALSE");
        }
        else if(product.getQuantityOnHand() > 0){
            product.setAvailable("TRUE");
        }
        
        //VERIFY THAT THE PURCHASE COST HAS BEEN SET
        if(product.getPurchaseCost() == null){
            throw new InvalidEntityException("The purchase cost must be set");
        }
          
        ProductCodeEntity productCodeEntity;
        ManufacturerEntity manufacturerEntity;
        ProductEntity productEntity;
        
        //CHECK FOR ID, SEE IF IT IS UNIQUE
        productEntity = find(product.getProductId()); //find() calls the abstract for THIS class  
        if(productEntity != null){
            throw new InvalidEntityException("The product id must be unique");
        }
        
        //VERIFY THE MANUFACTURER CODE EXISTS IN THE DATABASE
        manufacturerEntity = manFacade.find(product.getManufacturerId().getManufacturerId());//Get Integer from Entity    
        if(manufacturerEntity == null){
            throw new InvalidEntityException("The manufacturer id does not represent a valid entry");
        }
        
        //VERIFY THE PRODUCT CODE EXISTS IN THE DATABASE
        productCodeEntity = productCodeFacade.find(product.getProductCode().getProdCode());//Get String from Entity 
        if(productCodeEntity == null){
            throw new InvalidEntityException("The product id does not represent a valid entry");
        }
        
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.create(product);
    }
    
    /**
     * edit                             receives a ProductEntity Object and edits
     *                                  a record in the database
     * 
     * @param product                   ProductEntity Object
     * 
     * @throws InvalidEntityException   If Entity or fields are null, or if business
     *                                  rules are not met
     */
    @Override
    public void edit(ProductEntity product)throws InvalidEntityException {
        
        //CHECK THE ENTITY OBJECT FOR NULL
        if(product == null){
            throw new InvalidEntityException("A product cannot be null");
        }
        
        //RETURN RECORD BY PRIMARY KEY MATCH 
        ProductEntity locEntity = find(product.getProductId());
                                                                                
        //CHECK THE ENTITY FOR NULL
        if(locEntity == null){
            throw new InvalidEntityException("You cannot edit: "
                    + "This product code is not in the database");
        }
        
        //IF QUANTITY ON HAND IS 0 AND AVAILABLE IS TRUE, OR IF THE QUANTITY ON HAND 
        //IS GREATER THAN ZERO AND THE AVAILABLE IS FALSE, THROW AN EXCEPTION
        if(product.getQuantityOnHand() == 0 && product.getAvailable() == "TRUE"){
            throw new InvalidEntityException("Quantity cannot be 0 and TRUE");
        }
        if(product.getQuantityOnHand() > 0 && product.getAvailable() == "FALSE"){
            throw new InvalidEntityException("Quantity cannot be greater than 0 and FALSE");
        }
        
        //VERIFY THAT THE PURCHASE COST HAS BEEN SET
        if(product.getPurchaseCost() == null){
            throw new InvalidEntityException("The purchase cost must be set");
        }
          
        ProductCodeEntity productCodeEntity;
        ManufacturerEntity manufacturerEntity;
        
        //VERIFY THE MANUFACTURER CODE EXISTS IN THE DATABASE
        manufacturerEntity = manFacade.find(product.getManufacturerId().getManufacturerId());//Get Integer from Entity    
        if(manufacturerEntity == null){
            throw new InvalidEntityException("The manufacturer id does not represent a valid entry");
        }
        
        //VERIFY THE PRODUCT CODE EXISTS IN THE DATABASE
        productCodeEntity = productCodeFacade.find(product.getProductCode().getProdCode());//Get String from Entity 
        if(productCodeEntity == null){
            throw new InvalidEntityException("The product id does not represent a valid entry");
        }
        
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.edit(product);
    }
    
    /**
     * findAllByProductCode             receives a ProductCodeEntity and finds
     *                                  all records in the database by product code
     * 
     * @param productCode               ProductCodeEntity Object
     * 
     * @return                          List<ProductEntity>
     * 
     * @throws InvalidEntityException   if ProductCodeEntity is null
     */
    public List<ProductEntity> findAllByProductCode(ProductCodeEntity productCode)
                                                 throws InvalidEntityException{
        
        if(productCode == null){
            throw new InvalidEntityException("findAllByProductCode Entity cannot"
                    + "be be null");
        }
        //CREATE NAMED QUERY
        TypedQuery<ProductEntity> query =
                em.createNamedQuery("ProductEntity.findAllByProductCode",ProductEntity.class);
        //SET THE PARAMETER
        query.setParameter("productCode", productCode);
        
        //RETURN PRODUCT ENTITY LIST, WILL BE NULL IF NOT LOCATED
        return query.getResultList();
    }
    
    /**
     * findAllByManufacturer            receives a ManufacturerEntity and finds
     *                                  all records in the database by manufacturer Id
     * 
     * @param manufacturer               ManufacturerEntity Object
     * 
     * @return                          List<ProductEntity>
     * 
     * @throws InvalidEntityException   if ManufacturerEntity is null
     */
    public List<ProductEntity> findAllByManufacturer(ManufacturerEntity manufacturer)
                                                 throws InvalidEntityException{
        
        if(manufacturer == null){
            throw new InvalidEntityException("findAllByManufacturer Entity cannot"
                    + "be be null");
        }
        //CREATE NAMED QUERY
        TypedQuery<ProductEntity> query =
                em.createNamedQuery("ProductEntity.findAllByManufacturer",ProductEntity.class);
        //SET THE PARAMETER
        query.setParameter("manufacturerId",manufacturer);
        
        //RETURN PRODUCT ENTITY LIST, WILL BE NULL IF NOT LOCATED
        return query.getResultList();
    }
    
    /**
     * findUnderQOHLimit                receives an Integer representing a quantity
     *                                  on hand limit, and finds all records in the
     *                                  database under that limit
     * 
     * @param limit                     Integer
     * 
     * @return                          List<ProductEntity>
     * 
     * @throws InvalidEntityException   if limit is null
     */
    public List<ProductEntity> findUnderQOHLimit(Integer limit)throws InvalidEntityException{
        
        if(limit == null){
            throw new InvalidEntityException("The limit you entered cannot"
                    + "be be null");
        }
        //CREATE NAMED QUERY
        TypedQuery<ProductEntity> query =
                em.createNamedQuery("ProductEntity.findUnderQOHLimit",ProductEntity.class);
        //SET THE PARAMETER
        query.setParameter("quantityOnHand",limit);
        
        //RETURN PRODUCT ENTITY LIST, WILL BE NULL IF NOT LOCATED
        return query.getResultList();
        
    }
    
    /**
     * findAllAvailable                 receives a String representing whether a product
     *                                  is available, and finds all records in the 
     *                                  database that are available
     * 
     * @param available                 String 
     * 
     * @return                          List<ProductEntity>
     * 
     * @throws InvalidEntityException   if available is null 
     */
    public List<ProductEntity> findAllAvailable(String available)throws InvalidEntityException{
        
        if(available == null){
            throw new InvalidEntityException("The limit you entered cannot"
                    + "be be null");
        }
        //CREATE NAMED QUERY
        TypedQuery<ProductEntity> query =
                em.createNamedQuery("ProductEntity.findAllAvailable",ProductEntity.class);
        //SET THE PARAMETER
        query.setParameter("available",available);
        
        //RETURN PRODUCT ENTITY LIST, WILL BE NULL IF NOT LOCATED
        return query.getResultList();
        
    }
    
    
    
}//END METHOD
