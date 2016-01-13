
package org.usd.csci.product;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
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
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import static org.usd.csci.product.ProductEntityTest.productCode;
import org.usd.csci.utility.InvalidEntityException;

/**
 *
 * @author Mike Benton CSC280
 */
@RunWith(Arquillian.class)
public class ProductEntityFacadeTest {
    
    @Inject
    ManufacturerEntityFacade manFacade;
    
    @Inject
    ProductCodeEntityFacade productCodeFacade;
    
    @Inject
    ProductEntityFacade productFacade;
    
    static public ManufacturerEntity manufacturer;
    static public ProductCodeEntity productCode;
    
    static public ProductEntity product = new ProductEntity();
    static public ProductEntity productForEdit = new ProductEntity();
    
    public ProductEntityFacadeTest() {
    }
    
    @Deployment
    public static JavaArchive deploy() {
        FileAsset persistenceXml = new FileAsset(
                new File("src/main/resources/META-INF/persistence.xml"));
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ManufacturerEntityFacade.class,
                        ManufacturerEntity.class,
                        ProductCodeEntityFacade.class,
                        ProductCodeEntity.class,
                        ProductEntity.class,
                        ProductEntityFacade.class)
                .addAsManifestResource(persistenceXml, "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        List<ManufacturerEntity> manufacturers = manFacade.findAll();
        manufacturer = manufacturers.get(0);
        
        List<ProductCodeEntity> productCodes = productCodeFacade.findAll();
        productCode = productCodes.get(0);
        
        product.setProductId(980008);
        product.setAvailable("TRUE");
        product.setDescription("JUnit Test Product");
        product.setMarkup(new BigDecimal(50.00));
        product.setManufacturerId(manufacturer);
        product.setProductCode(productCode);
        product.setPurchaseCost(new BigDecimal(250.00));
        product.setQuantityOnHand(10);
        
        productForEdit.setProductId(988888);
        productForEdit.setAvailable("FALSE");
        productForEdit.setDescription("Product For Edit");
        productForEdit.setMarkup(new BigDecimal(50.00));
        productForEdit.setManufacturerId(manufacturer);
        productForEdit.setProductCode(productCode);
        productForEdit.setPurchaseCost(new BigDecimal(250.00));
        productForEdit.setQuantityOnHand(0);
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of create method, of class ProductEntityFacade.
     */
    @Test
    public void testCreate() throws Exception {
        
        System.out.println("testCreate");
        
        //make sure product does not exist in the database with the 
        //product code that was placed in the ProductEntity (in @Before)
        ProductEntity locProduct = productFacade.find(product.getProductId());
        
        if(locProduct != null){
            productFacade.remove(product);
        }
        
        //Create a ProductEntity in the database
        productFacade.create(product);
        //Look for it, assert it exists
        locProduct = productFacade.find(product.getProductId()); 

        assertFalse(locProduct == null);
        
        
    }
    /**
     * Test of create method, of class ProductEntityFacade with purchase cost not set
     */
    @Test
    public void testCreatePurchaseCostNotSet() {
        
        System.out.println("Test create with purchase cost not set");
       
        try{
            //make sure product does not exist in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());
        
            if(locProduct != null){
            productFacade.remove(product);
        }
            //Change Purchase cost to null
            product.setPurchaseCost(null);
            //Create a ProductEntity in the database
            productFacade.create(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   
    /**
     * Test of create method, of class ProductEntityFacade with purchase cost not set
     */
    @Test
    public void testCreateIdNotUnique() {
        
        System.out.println("Test create with invalid/non-unique id");
       
        try{
            //make sure product does not exist in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());
        
            if(locProduct != null){
            productFacade.remove(product);
        }
            //Create a ProductEntity in the database FIRST TIME
            productFacade.create(product);
            //Attempt to create the same ProductEntity again a SECOND TIME
            productFacade.create(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
    
    /**
     * Test of create method, of class ProductEntityFacade with invalid manufacturer
     */
    @Test
    public void testCreateInvalidManufacturer() {
        
        System.out.println("Test create with manufacturer id not in database");
       
        try{
            
            //make sure product does not exist in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);
            }
            //Create a local manufacturer and set its id to a number not in the
            //database (in this case 222222222)
            ManufacturerEntity locManufacturer = new ManufacturerEntity();
            locManufacturer.setManufacturerId(222222222);
            //Change manufacturer to a number not in the database
            product.setManufacturerId(locManufacturer);
            //Create a ProductEntity in the database
            productFacade.create(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   
    /**
     * Test of create method, of class ProductEntityFacade with invalid manufacturer
     */
    @Test
    public void testCreateInvalidProductCode() {
        
        System.out.println("Test create with product code not in database");
       
        try{
            
            //make sure product does not exist in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);
            }
            //Create a productcode and set its id to a number not in the
            //database (in this case ZZ)
            ProductCodeEntity locProductCode = new ProductCodeEntity();
            locProductCode.setProdCode("ZZ");
            //Change product code to a number not in the database
            product.setProductCode(locProductCode);
            //Create a ProductEntity in the database
            productFacade.create(product);
            
            fail("InvalidEntityException was not thrown");  

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   

    /**
     * Test of edit method, of class ProductEntityFacade.
     */
    @Test
    public void testEdit() throws Exception {
        
        System.out.println("test edit");
        
        //make sure at least one product exists in the database with the 
        //product code that was placed in the ProductEntity (in @Before)
        ProductEntity locProduct = productFacade.find(product.getProductId());
        
        if(locProduct != null){
            productFacade.remove(product);//REMOVE ANY PREVIOUSLY EDITED FIELDS
            productFacade.create(product);//START WITH AN UNEDITED PRODUCT
        }
        if(locProduct == null){
            productFacade.create(product);//CREATE A NEW PRODUCT TO WORK WITH
        }
        //Change a field in product
        product.setDescription("This is the edit");
        //Create a ProductEntity in the database
        productFacade.edit(product);
        //Look for it, assert it exists
        locProduct = productFacade.find(product.getProductId()); 

        assertFalse(locProduct == null);
        
        
    }
    /**
     * Test of edit method, of class ProductEntityFacade with incorrect quantity
     */
    @Test
    public void testEditQuantityOnHand() {
        
        System.out.println("Test edit with incorrect quantity on hand");
       
        try{
            
            //make sure at least one product exists in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);//REMOVE ANY PREVIOUSLY EDITED FIELDS
                productFacade.create(product);//START WITH AN UNEDITED PRODUCT
            }
            if(locProduct == null){
                productFacade.create(product);//CREATE A NEW PRODUCT TO WORK WITH
            }
            //Change Quantity on hand to 0, and available to TRUE in product
            product.setQuantityOnHand(0);
            product.setAvailable("TRUE");
            //Create a ProductEntity in the database
            productFacade.edit(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
    
    /**
     * Test of edit method, of class ProductEntityFacade with purchase cost not set
     */
    @Test
    public void testEditPurchaseCostNotSet() {
        
        System.out.println("Test edit with purchase cost not set");
       
        try{
            
            //make sure at least one product exists in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);//REMOVE ANY PREVIOUSLY EDITED FIELDS
                productFacade.create(product);//START WITH AN UNEDITED PRODUCT
            }
            if(locProduct == null){
                productFacade.create(product);//CREATE A NEW PRODUCT TO WORK WITH
            }
            //Change Purchase cost to null
            product.setPurchaseCost(null);
            //Create a ProductEntity in the database
            productFacade.edit(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   
    /**
     * Test of edit method, of class ProductEntityFacade with invalid manufacturer
     */
    @Test
    public void testEditInvalidManufacturer() {
        
        System.out.println("Test edit with manufacturer not in database");
       
        try{
            
            //make sure at least one product exists in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);//REMOVE ANY PREVIOUSLY EDITED FIELDS
                productFacade.create(product);//START WITH AN UNEDITED PRODUCT
            }
            if(locProduct == null){
                productFacade.create(product);//CREATE A NEW PRODUCT TO WORK WITH
            }
            //Create a local manufacturer and set its id to a number not in the
            //database (in this case 222222222)
            ManufacturerEntity locManufacturer = new ManufacturerEntity();
            locManufacturer.setManufacturerId(222222222);
            //Change manufacturer to a number not in the database
            product.setManufacturerId(locManufacturer);
            //Create a ProductEntity in the database
            productFacade.edit(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   
    /**
     * Test of edit method, of class ProductEntityFacade with invalid product code
     */
    @Test
    public void testEditInvalidProductCode() {
        
        System.out.println("Test edit with product code not in database");
       
        try{
            
            //make sure at least one product exists in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct != null){
                productFacade.remove(product);//REMOVE ANY PREVIOUSLY EDITED FIELDS
                productFacade.create(product);//START WITH AN UNEDITED PRODUCT
            }
            if(locProduct == null){
                productFacade.create(product);//CREATE A NEW PRODUCT TO WORK WITH
            }
            //Create a productcode and set its id to a number not in the
            //database (in this case ZZ)
            ProductCodeEntity locProductCode = new ProductCodeEntity();
            locProductCode.setProdCode("ZZ");
            //Change product code to a number not in the database
            product.setProductCode(locProductCode);
            //Create a ProductEntity in the database
            productFacade.edit(product);
            
            fail("InvalidEntityException was not thrown"); 

        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
              
    }
   
    /**
     * Test of findAllByProductCode method, of class ProductEntityFacade.
     */
    @Test
    public void testFindAllByProductCode() {
        
        System.out.println("findAllByProductCode");
        
        try{
            //make sure at least one product exists in the database with the 
            //product code that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId());

            if(locProduct == null){
                productFacade.create(product);
            }

            //A ProductEntity is now guaranteed to be in the database
            List<ProductEntity> productList =
                    productFacade.findAllByProductCode(product.getProductCode());

            System.out.println("Number of product codes" + productList.size());

            assertFalse(productList.isEmpty());
            
         }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
    }

    /**
     * Test of findAllByManufacturer method, of class ProductEntityFacade.
     */
    @Test
    public void testFindAllByManufacturer() {
        
        System.out.println("findAllByManufacturer");
        
        try{
            
            //make sure at least one product entity exists in the database with the 
            //manufacturerId that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId() );

            if(locProduct == null){
                productFacade.create(product);
            }

            //A ProductEntity is now guaranteed to be in the database
            List<ProductEntity> productList =
                    productFacade.findAllByManufacturer(product.getManufacturerId());

            System.out.println("Number of Manufacturer Id's " + productList.size());

            assertFalse(productList.isEmpty());
        
         }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
    }
    
    /**
     * Test of findUnderQOHLimit method, of class ProductEntityFacade.
     */
    @Test
    public void testFindUnderQOHLimit() {
        
        System.out.println("findUnderQOHLimit");
        
        try{
        
            //make sure at least one manufacturer exists in the database with the 
            //quantity that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId() );

            if(locProduct == null){
                productFacade.create(product);
            }

            //A ProductEntity is now guaranteed to be in the database
            List<ProductEntity> productList =
                    productFacade.findUnderQOHLimit(product.getQuantityOnHand());

            System.out.println("Number of items under quantity on hand " + productList.size());

            assertFalse(productList.isEmpty());
        
         }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
    }
    
    /**
     * Test of findAllAvailable method, of class ProductEntityFacade.
     */
    @Test
    public void testFindAllAvailable(){
        
        System.out.println("findAllAvailable");
        
        try{
        
            //make sure at least one manufacturer exists in the database with the 
            //quantity that was placed in the ProductEntity (in @Before)
            ProductEntity locProduct = productFacade.find(product.getProductId() );

            if(locProduct == null){
                productFacade.create(product);
            }

            //A ProductEntity is now guaranteed to be in the database
            List<ProductEntity> productList =
                    productFacade.findAllAvailable(product.getAvailable());

            System.out.println("Number of items available " + productList.size());

            assertFalse(productList.isEmpty());
        
         }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
    }
    
}
