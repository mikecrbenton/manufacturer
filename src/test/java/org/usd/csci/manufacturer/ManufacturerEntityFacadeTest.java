
package org.usd.csci.manufacturer;

import static com.ctc.wstx.util.DataUtil.Integer;
import java.io.File;
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
import org.junit.runner.RunWith;  //ADDED FOR @RunWith
import org.usd.csci.utility.InvalidEntityException;

/**
 *
 * @author Mike Benton CSC280
 */
@RunWith(Arquillian.class)//ADDED TO THIS CLASS FOR ASSGINMENT
public class ManufacturerEntityFacadeTest {
    
    //ADDED TO CLASS
    @Inject
    ManufacturerEntityFacade manFacade;
    
    //ADDED TO CLASS
    static public ManufacturerEntity manufacturer = new ManufacturerEntity();
    
    //SECOND MANUFACTURER FOR USE IN EDIT METHODS
    static public ManufacturerEntity existingManufacturer = new ManufacturerEntity();

    public ManufacturerEntityFacadeTest() {
    }
    
    //ADDED TO CLASS
    @Deployment
    public static JavaArchive deploy() {
        FileAsset persistenceXml = new FileAsset(
                new File("src/main/resources/META-INF/persistence.xml"));
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ManufacturerEntityFacade.class,
                        ManufacturerEntity.class)
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
        //Setup a manufacturer and initialize an Entity before each test to be used
        manufacturer = new ManufacturerEntity();
        manufacturer.setManufacturerId(19984800);
        manufacturer.setName("Vermillion Post Office");
        manufacturer.setAddressline1("5109 Union Street");
        manufacturer.setAddressline2("");
        manufacturer.setCity("Vermillion");
        manufacturer.setState("SD");
        manufacturer.setZip("57069");
        manufacturer.setPhone("605-670-0827");
        manufacturer.setFax("605-624-9191");
        manufacturer.setRep("LuuraChinn");
        manufacturer.setEmail("wwwsales@sony.com");
        
        //Setup a second manufacturer to be used for edit-nameAlreadyExists 
        // and emailAlreadyExists testing
        existingManufacturer = new ManufacturerEntity();
        existingManufacturer.setManufacturerId(19985800);
        existingManufacturer.setName("CSC Department"); //Name is the same, Id is different
        existingManufacturer.setAddressline1("506 Elk Street");
        existingManufacturer.setAddressline2("");
        existingManufacturer.setCity("Vermillion");
        existingManufacturer.setState("SD");
        existingManufacturer.setZip("57069");
        existingManufacturer.setPhone("605-670-0827");
        existingManufacturer.setFax("605-624-9191");
        existingManufacturer.setRep("LuuraChinn");
        existingManufacturer.setEmail("wwwsales@iw.net"); 
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * setManufacturerWithoutRep        Initializes the fields of a Manufacturer
     *                                  Entity and leaves the rep field uninitialized
     *                                  to be used in a unit test
     */
    public void setManufacturerWithoutRep() {
        //To be used without calling the Entity Class and therefore bypassing the
        //regular expression business rule for setRep
        manufacturer = new ManufacturerEntity();
        manufacturer.setManufacturerId(19984800);
        manufacturer.setName("Vermillion Post Office");
        manufacturer.setAddressline1("5109 Union Street");
        manufacturer.setAddressline2("");
        manufacturer.setCity("Vermillion");
        manufacturer.setState("SD");
        manufacturer.setZip("57069");
        manufacturer.setPhone("605-670-0827");
        manufacturer.setFax("605-624-9191");
        //manufacturer.setRep("Luura Chinn");
        manufacturer.setEmail("wwwsales@sony.com");
        
    }
     /**
     * testCreateManufacturer           Initializes a Manufacturer object and looks
     *                                  if its in the database.  If it is, the object
     *                                  is removed.  The manufacture object is then
     *                                  created, persisted in the database and asserted
     *                                  to be in the database.
     * 
     */ 
    @Test
    public void testCreateManufacturer() {
        
        System.out.println("testCreateManufacturer");
        //@Before setUp initializes "manufacturer" object and its fields
        //Assume data is in the database for testing
        //make sure this entity doesn't already exist
      
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
            manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //Create a manufacturerEntity in the database
            manFacade.create(manufacturer);
            //Look for it, assert it exists
            locManufacturer = manFacade.find(manufacturer.getManufacturerId()); 
            
            assertFalse(locManufacturer == null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    }//END METHOD
    
    /**
     * testCreateManufacturerAlreadyExists    Initializes a Manufacturer object and
     *                                        checks if it is in the database. If it
     *                                        is not, one is created.  A duplicate
     *                                        Manufacturer object is attempted to
     *                                        create in the database.  An Invalid
     *                                        EntityException is expected to be thrown.
     */
    @Test//EXCEPTION EXPECTED 
    public void testCreateManufacturerAlreadyExists() {
        
        System.out.println("testCreateManufacturerAlreadyExists");
        
        //Create a local manufacturer from the database
        ManufacturerEntity locManufacturer = 
            manFacade.find(manufacturer.getManufacturerId()); 
            
        try{
            //If the Id doesn't exist in the database create it
            if(locManufacturer == null){
                manFacade.create(locManufacturer);
            }
            
            //Create a duplicate manufacturerEntity in database--SHOULD THROW AN EXCEPTION
            manFacade.create(locManufacturer);
            
        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
         
    }//END METHOD
    
    /**
     * testCreateManufacturerNoRep      A ManufacturerEntity is created without
     *                                  setting the rep field.  The entity is looked
     *                                  for in the database.  If it exists, it is 
     *                                  deleted.  It is then passed into create
     *                                  and expected to throw an exception
     */
    @Test //EXCEPTION EXPECTED
    public void testCreateManufacturerNoRep(){
        
        System.out.println("testCreateManufacturerNoRep");
        
        //Call method to initialize manufacturer without "rep" being set
        //The reason this is needed is to bypass the Entity rules(which would catch it)
        setManufacturerWithoutRep();
        
        //Look for by the Id in database, if it exists, delete it
            ManufacturerEntity locManufacturer
                    = manFacade.find(manufacturer.getManufacturerId());                 
               
            if(locManufacturer != null){                                          
                manFacade.remove(locManufacturer);
            }
        try{
           //Create a manufacturerEntity without the "rep" being set
            manFacade.create(manufacturer);
            fail("InvalidEntityException was not thrown");
        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        } 
    }//END METHOD
    
    
    /**
     * testEditManufacturer     A ManufacturerEntity is created and checked if it
     *                          is in the database.  If not found, it is persisted
     *                          in the database.  The address field is changed, and
     *                          the Entity is passed into the edit method to be
     *                          persisted in the database with the new information
     */
    @Test
    public void testEditManufacturer() {
        
        System.out.println("testEditManufacturer");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Check if the manufacturerEntity is in the database
            ManufacturerEntity locManufacturer =  manFacade.find(manufacturer.getManufacturerId());
            if(locManufacturer == null){
                //If not found create a manufacturerEntity in database
                manFacade.create(manufacturer);
                
                //If was null, create again a local entity from the database entity
                locManufacturer = manFacade.find(manufacturer.getManufacturerId()); 
            }
            
            //Edit fields of locManufacturer to a different address (from separate object) and pass into edit
            locManufacturer.setAddressline1(existingManufacturer.getAddressline1());
            manFacade.edit(locManufacturer);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
    }//END METHOD
    
    /**
     * testEditManufacturerNameAlreadyExists     A ManufacturerEntity is created and checked if it
     *                                           is in the database.  If not found, it is persisted
     *                                           in the database.  A second Entity has the name
     *                                           set identically to the Entity that already exists
     *                                           in the database.  An InvalidEntityException is
     *                                           expected to be thrown
     */
    @Test //EXCEPTION EXPECTED
    public void testEditManufacturerNameAlreadyExists() {
        
        System.out.println("testEditManufacturerNameAlreadyExists");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Check if 1st Manufacturer Entity is in the database
            ManufacturerEntity locManufacturer =  manFacade.find(manufacturer.getManufacturerId());
                                                                                
            if(locManufacturer == null){
                //If not found  manufacturerEntity in database
                manFacade.create(manufacturer);
                
                //If was null, create again a local entity from the database entity
                //locManufacturer = manFacade.find(manufacturer.getManufacturerId()); 
            }
            //Check if 2nd ManufacturerEntity is in the database
            //To be used to try and change name field to the same as the
            //1st ManufacturerEntity
            ManufacturerEntity editManufacturer =  manFacade.find(existingManufacturer.getManufacturerId());
                                                                                
            if(editManufacturer == null){
                //If not found  manufacturerEntity in database
                manFacade.create(existingManufacturer);
                
            }
            
            //Apply a name field to the 2nd manufacturerEntity with a name that already
            //exists in the database(1st Entity) and pass it into edit
            existingManufacturer.setName(manufacturer.getName());
            //This will be Vermillion Post Office, ID 19985800 
            //(Verm. Post Office 19984800 already exists)
            manFacade.edit(existingManufacturer);
            
            fail("InvalidEntityException was not thrown");
            
        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
    }//END METHOD
   
   /**
     * testEditManufacturerEmailAlreadyExists     A ManufacturerEntity is created and checked if it
     *                                           is in the database.  If not found, it is persisted
     *                                           in the database.  A second Entity has the email
     *                                           set identically to the Entity that already exists
     *                                           in the database.  An InvalidEntityException is
     *                                           expected to be thrown
     */
   @Test //EXCEPTION EXPECTED
    public void testEditManufacturerEmailAlreadyExists() {
        
        System.out.println("testEditManufacturerNameAlreadyExists");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Check if 1st ManufacturerEntity is in the database
            ManufacturerEntity locManufacturer 
                    =  manFacade.find(manufacturer.getManufacturerId());
                                                                                
            if(locManufacturer == null){
                //If not found  manufacturerEntity in database
                manFacade.create(manufacturer);
                
            }
            //Check if 2nd ManufacturerEntity is in the database
            //To be used to try and change email field to the same as the
            //1st ManufacturerEntity
            ManufacturerEntity editManufacturer 
                    =  manFacade.find(existingManufacturer.getManufacturerId());
                                                                                
            if(editManufacturer == null){
                //If not found create 2nd manufacturerEntity in database
                manFacade.create(existingManufacturer);
                
            }
            
            //Apply email field to a ManufacturerEntity with an email that already
            //exists in the database and pass it into edit
            existingManufacturer.setEmail(manufacturer.getEmail());
            System.out.println(existingManufacturer.getEmail() + existingManufacturer.getManufacturerId());
            manFacade.edit(existingManufacturer);
            
            fail("InvalidEntityException was not thrown");
            
        }catch(InvalidEntityException e){
            System.out.println("TEST SUCCEEDED: an exception was thrown " 
                    + e.getMessage()+ "/  "+ e.toString());
        }
    }//END METHOD
    
    /**
     * testFindByName       A ManufacturerEntity is persisted in the database and
     *                      looked for by the findByName() method.  An exception 
     *                      is thrown if the record is not found
     */
    @Test
    public void testFindByName() {
        
        System.out.println("testFindName method()");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //Create manufacturerEntity
            manFacade.create(manufacturer);
            //Look for entity in database by findByName()
            locManufacturer = manFacade.findByName(manufacturer.getName());
            //Assert that findByName retrieved correctly
            assertFalse(locManufacturer == null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    }//END METHOD
    /**
     *testFindByNameNotInDatabase   A ManufacturerEntity is searched for in the
     *                              database and if found is deleted.  The Entity
     *                              is then searched for by the findByName() method
     *                              and an exception is expected to be thrown
     */
    @Test
    public void testFindByNameNotInDatabase() {
        
        System.out.println("testFindNameNotInDatabase method()");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //***************Do NOT create a manufacturerEntity
            //manFacade.create(manufacturer);
            
            //Look for entity in database by findByName()
            locManufacturer = manFacade.findByName(manufacturer.getName());
            //Assert that findByName retrieved correctly
            assertFalse(locManufacturer != null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    }
        
    /**
     * testFindByEmail       A ManufacturerEntity is persisted in the database and
     *                      looked for by the findByEmail() method.  An exception 
     *                      is thrown if the record is not found
     */
    @Test
    public void testFindByEmail() {
        
        System.out.println("testFindEmail method()");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
            manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //Create manufacturerEntity
            manFacade.create(manufacturer);
            //Look for entity in database by findByName()
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            //Assert that findByName retrieved correctly
            assertFalse(locManufacturer == null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    } 
    /**
     *testFindByEmailNotInDatabase  A ManufacturerEntity is searched for in the
     *                              database and if found is deleted.  The Entity
     *                              is then searched for by the findByEmail() method
     *                              and an exception is expected to be thrown
     */
    @Test
    public void testFindByEmailNotInDatabase() {
        
        System.out.println("testFindEmailNotInDatabase method()");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
            manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //**********Do NOT create a manufacturerEntity after removing
            //manFacade.create(manufacturer);
            
            //Look for entity in database by findByName()
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            //Assert that findByEmail did not return any records
            assertFalse(locManufacturer != null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    }//END METHOD 
    
    /**
     * testFindByCityAndState       A ManufacturerEntity is persisted in the database and
     *                              looked for by the findByCityAndState() method.  
     *                              An exception is thrown if the record is not found
     */
    @Test
    public void testFindByCityAndState() {
        
        System.out.println("testFindByCityAndState method()");
        //@Before setUp initializes "manufacturer" object and its fields
        //Assume data is in the database for testing
        //make sure this entity already exists
        
        try{
            //Look for the ID, if it exists delete it
            ManufacturerEntity locManufacturer
                = manFacade.find(manufacturer.getManufacturerId()); 
            if(locManufacturer != null){
            manFacade.remove(locManufacturer);
            }
            
            //Look for the name, if it exists delete it
            locManufacturer = manFacade.findByName(manufacturer.getName());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            }
            //Look for the email, if it exists delete it
            locManufacturer = manFacade.findByEmail(manufacturer.getEmail());
            if(locManufacturer != null){
                manFacade.remove(locManufacturer);
            } 
            //Create manufacturerEntity
            manFacade.create(manufacturer);
            //Look for entity in database by findByCityAndState()- returns a list of city/state combinations
            List<ManufacturerEntity> list = manFacade.findByCityAndState(manufacturer.getCity(),manufacturer.getState());
            //Assert that findByCityAndState retrieved correctly
            assertFalse(list == null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    }//END METHOD
    
    /**
     *testFindByCityAndStateNotInDatabase   Initializes a list of a specific city/state 
     *                                      combination from a ManufacturerEntity 
     *                                      and if the list is not null, each Entity in
     *                                      the list is removed from the database. The
     *                                      findByCityAndState() method is then called
     *                                      and an InvalidEntityException is expected
     */
    @Test
    public void testFindByCityAndStateNotInDatabase() {
        
        System.out.println("testFindByCityAndStateNotInDatabase method()");
        //@Before setUp initializes "manufacturer" object and its fields
        
        try{
           
            //Look for city/state combinations, if they exist delete them
            List<ManufacturerEntity> checkCityState = manFacade.findByCityAndState(manufacturer.getCity(),manufacturer.getState());
           
            if(checkCityState != null){
                for (int i = 0; i < checkCityState.size(); i++) {
                    if(checkCityState.get(i) != null){
                        manFacade.remove(checkCityState.get(i));
                    }
                }
            }
            //*********Do NOT create a new manufacturerEntity
            //manFacade.create(manufacturer);
            
            //Look for entity in database by findByCityAndState()- returns a list of city/state combinations
            List<ManufacturerEntity> list = manFacade.findByCityAndState(manufacturer.getCity(),manufacturer.getState());
            //Assert that findByCityAndState did not return any records
            assertFalse(list != null);
            
        }catch(InvalidEntityException e){
            fail(e.getMessage());
        }
        
    } 
    
}
