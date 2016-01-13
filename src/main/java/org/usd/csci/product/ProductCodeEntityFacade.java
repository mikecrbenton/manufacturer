
package org.usd.csci.product;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.usd.csci.utility.InvalidEntityException;
import org.usd.csci.utility.AbstractFacade;

/**
 *
 * @author Mike Benton CSC280
 */
@Stateless
public class ProductCodeEntityFacade extends AbstractFacade<ProductCodeEntity> {
    
    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductCodeEntityFacade() {
        super(ProductCodeEntity.class);
    }
    
    
    /**
     * create                           Receives a ProductCodeEntity Object and
     *                                  creates a record in the database
     * 
     *@param productCode                ProducCodetEntity object
     * 
     *@throws InvalidEntityException    If Object is null or business rules are not met
     */
    @Override
    public void create(ProductCodeEntity productCode)throws InvalidEntityException {
        
        //CHECK THE ENTITY OBJECT FOR NULL
        if(productCode == null){
            throw new InvalidEntityException("A new product cannot be null");
        }
        //CHECK FOR NULL FIELDS 
        if(productCode.getProdCode() == null){
            throw new InvalidEntityException("The product code cannot be null");
        }
        if(productCode.getDiscountCode() == null){
            throw new InvalidEntityException("The Discount Code cannot be null");
        }
        
        ProductCodeEntity locEntity;
        
        //SEE IF THE PRODUCT CODE EXISTS 
        locEntity = find(productCode.getProdCode());     
        if(locEntity != null){
            throw new InvalidEntityException("The product code must be unique");
        }
        
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.create(productCode);
    }
    
    /**
     * edit                             Receives a ProductCodeEntity Object and
     *                                  creates a record in the database
     * 
     *@param productCode                ProductCodeEntity object
     * 
     *@throws InvalidEntityException    If Object is null 
     */
    @Override
    public void edit(ProductCodeEntity productCode) throws InvalidEntityException {
        
        //RETURN RECORD BY PRIMARY KEY MATCH 
        ProductCodeEntity locEntity = find(productCode.getProdCode());
                                                                                
        //CHECK THE ENTITY FOR NULL
        if(locEntity == null){
            throw new InvalidEntityException("You cannot edit: This product code is not in the database");
        }
           
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.edit(productCode);
        
    }
    
    //ADDED FOR ASSIGNMENT- NOT NEEDED????
//    public ProductCodeEntity findByProductCode(String code) throws InvalidEntityException {
//        
//        if(code == null){
//            throw new InvalidEntityException("Product code cannot be null");
//        }
//        //CREATE THE NAMED QUERY
//        //createnamesQuery( name of query, name of class)
//        TypedQuery<ProductCodeEntity> query = 
//        em.createNamedQuery("ProductCodeEntity.findByProdCode",ProductCodeEntity.class);
//        
//        //set the parameter- (string following the colon in JPA)
//        query.setParameter("prodCode", code);
//        
//        //RETURN A ProductCodeEntity-  WILL BE EMPTY OR HAVE ONE RESULT
//        List<ProductCodeEntity> list = query.getResultList();//all records with that product code
//        
//        if(list.isEmpty()){
//            return null;
//        }
//        return list.get(0); //first element in list
//    }
//    
    
}
