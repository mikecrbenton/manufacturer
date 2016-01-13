
package org.usd.csci.product;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.usd.csci.utility.InvalidEntityException;
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import static org.usd.csci.manufacturer.ManufacturerEntityFacadeTest.manufacturer;

/**
 *
 * @author Mike Benton CSC280
 */
@RunWith(Arquillian.class)
public class ProductEntityTest {
    
    //ADDED TO CLASS
    @Inject
    ManufacturerEntityFacade manFacade;
    
    @Inject
    ProductCodeEntityFacade productCodeFacade;
    
    static public ProductEntity product = new ProductEntity();
    //ADDED TO TEST setDiscountCode()
    static public ProductCodeEntity productCode = new ProductCodeEntity();
    
    private static Validator validator;
    
    public ProductEntityTest() {
        //Default Constructor
    }
    
    //ADDED TO CLASS
    @Deployment
    public static JavaArchive deploy() {
        FileAsset persistenceXml = new FileAsset(
                new File("src/main/resources/META-INF/persistence.xml"));
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ManufacturerEntityFacade.class,
                        ManufacturerEntity.class,
                        ProductCodeEntityFacade.class,
                        ProductCodeEntity.class,
                        ProductEntity.class)
                .addAsManifestResource(persistenceXml, "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
    }
    
    @BeforeClass
    public static void setUpClass() {
        //ADDED FOR ASSIGNMENT
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        product = new ProductEntity();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setPurchaseCost method, of class ProductEntity.
     * TEST CORRECT ENTRY
     */
    
    @org.junit.Test
    public void testSetPurchaseCost() {
        
        System.out.println("test setPurchaseCost correct entry");

        // create a BigDecimal object
        BigDecimal bd = new BigDecimal(12.45);
        
        product.setPurchaseCost(bd);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"purchaseCost");
        assertEquals(0,constraintViolations.size());   
    }
    
    /**
     * Test of setPurchaseCost method, of class ProductEntity.
     * TEST NEGATIVE ENTRY
     */
    @org.junit.Test
    public void testNegativeSetPurchaseCost() {
        
        System.out.println("test setPurchaseCost negative entry");
        
        // create a BigDecimal object
        BigDecimal bd = new BigDecimal(-1.00);
        
        product.setPurchaseCost(bd);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"purchaseCost");
        assertTrue(constraintViolations.size()>0);   
    }
    
    /**
     * Test of setQuantityOnHand method, of class ProductEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetQuantityOnHand() {
        
        System.out.println("test correct setQuantityOnHand");
        
        //NOTE ON PROJECT - HAD TO TAKE OUT REGULAR EXPRESSION TO MAKE WORK
        Integer quantity = 10;
        product.setQuantityOnHand(quantity);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"quantityOnHand");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setNegativeQuantityOnHand method, of class ProductEntity.
     * TEST NEGATIVE CORRECT ENTRY
     */
    @org.junit.Test
    public void testNegativeSetQuantityOnHand() {
        
        System.out.println("test negative setQuantityOnHand");
        
        //NOTE ON PROJECT - HAD TO TAKE OUT REGULAR EXPRESSION TO MAKE WORK
        Integer quantity = -10;
        product.setQuantityOnHand(quantity);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"quantityOnHand");
        assertTrue(constraintViolations.size()>0);
    }
    
    /**
     * Test of setMarkup method, of class ProductEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetMarkup() {
        
        System.out.println("test correct setMarkup");
        
        // create a BigDecimal object
        BigDecimal bd = new BigDecimal(12.00);
        
        product.setMarkup(bd);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"markup");
        assertEquals(0,constraintViolations.size()); 
        
    }
    
    /**
     * Test of setMarkup method, of class ProductEntity.
     * TEST NEGATIVE ENTRY
     */
    @org.junit.Test
    public void testNegativeSetMarkup() {
        
        System.out.println("test negative setMarkup");
       // create a BigDecimal object
        BigDecimal bd = new BigDecimal(-12.00);
        
        product.setMarkup(bd);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"markup");
        assertTrue(constraintViolations.size()>0); 
        
    }
    
    /**
     * Test of setMarkup method, of class ProductEntity.
     * TEST GREATER THAN $100 ENTRY
     */
    @org.junit.Test
    public void testGreaterSetMarkup() {
        
        System.out.println("test greater than $100 setMarkup");
        // create a BigDecimal object
        BigDecimal bd = new BigDecimal(200.00);
        
        product.setMarkup(bd);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"markup");
        assertTrue(constraintViolations.size()>0); 
        
    } 
    
     /**
     * Test of setAvailable method, of class ProductEntity.
     * TEST CORRECT ENTRY "true"
     */
    @org.junit.Test
    public void testSetAvailableTrue() {
        System.out.println("test setAvailable with \"True\"");
        String name = "TRUE"; 
        product.setAvailable(name);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"available");
        assertEquals(0,constraintViolations.size());   
    }
    
    /**
     * Test of setAvailable method, of class ProductEntity.
     * TEST CORRECT ENTRY "false"
     */
    @org.junit.Test
    public void testSetAvailableFalse() {
        System.out.println("test setAvailable with \"false\"");
        String name = "FALSE"; 
        product.setAvailable(name);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"available");
        assertEquals(0,constraintViolations.size());   
    }
    
    /**
     * Test of setAvailable method, of class ProductEntity.
     * TEST CORRECT ENTRY "false"
     */
    @org.junit.Test
    public void testSetAvailableIncorrect() {
        System.out.println("test setAvailable with an incorrect entry");
        String name = "foo bar"; 
        product.setAvailable(name);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"available");
        assertTrue(constraintViolations.size()>0);   
    }
    
     /**
     * Test of setDescription method, of class ProductEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetDescription() {
        System.out.println("test setDescription correct");
        String name = "Printer Cable"; 
        product.setDescription(name);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"description");
        assertEquals(0,constraintViolations.size()); 
    }
    
     /**
     * Test of setDescription method, of class ProductEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testSetDescriptionInvalid() {
        System.out.println("test setDescription with too many characters");
        String name = "123456789123456789123456789123456789123456789123456789"; 
        product.setDescription(name);
        Set<ConstraintViolation<ProductEntity>> constraintViolations =
                validator.validateProperty(product,"description");
        assertTrue(constraintViolations.size()>0);  
    }
    
    

    /**
     * Test of setManufacturerId method, of class ProductEntity.
     */
    @org.junit.Test
    public void testSetManufacturerId(){
        
        System.out.println("setManufacturerId");
                
        //Get a List<> of Manufacturer entities from the database
        List<ManufacturerEntity> manufacturerList = manFacade.findAll();
           
        //Get the 1st Manufacturer from the list
        ManufacturerEntity aManufacturer = manufacturerList.get(0);

        //Set the ManufacturerId in the Product Entity with the first 
        //ManufacturerEntity in the List<>
        product.setManufacturerId(aManufacturer); 

        //Call the getManufacturerId method in product to verify
        //Assert that the name of the first manufacturer in the List<> from the
        //databse equals the manufacturer id set in product
        assertEquals(aManufacturer,product.getManufacturerId());
       
    }

    
    
    /**
     * Test of setProductCode method, of class ProductEntity.
     */
    @Test
    public void testSetProductCode() {
         
        System.out.println("setProductCode");
                
        //Get a List<> of ProductCode entities from the database
        List<ProductCodeEntity> productCodeList = productCodeFacade.findAll();
           
        //Get the 1st Manufacturer from the list
        ProductCodeEntity aProductCodeEntity = productCodeList.get(0);

        //Set the ManufacturerId in the Product Entity with the first 
        //ManufacturerEntity in the List<>
        product.setProductCode(aProductCodeEntity); 
        
        //Call the getManufacturerId method in product to verify
        //Assert that the name of the first manufacturer in the List<> from the
        //databse equals the manufacturer id set in product
        assertEquals(aProductCodeEntity,product.getProductCode());
    }
    /**
     * Test for setDiscountCode in ProductCodeEntity
     * CORRECT ENTRY
     */
    @Test
    public void testSetDiscountCode() {
        
        System.out.println("Test setDiscountCode with a correct entry");
        try{
            Character cr = 'A';
            productCode.setDiscountCode(cr);
            assertEquals(cr,productCode.getDiscountCode());
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
           
    }

    /**
     * Test for setDiscountCode in ProductCodeEntity
     * INVALID ENTRY
     */
    @Test(expected=InvalidEntityException.class)
    public void testSetInvalidDiscountCode()throws Exception {
        
        System.out.println("Test setDiscountCode with an invalid entry");
       
        Character cr = '9';
        productCode.setDiscountCode(cr);
        assertEquals(cr,productCode.getDiscountCode());
              
    }
   

    
    
}//END CLASS
