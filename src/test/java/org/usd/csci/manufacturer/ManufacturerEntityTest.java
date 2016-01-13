
package org.usd.csci.manufacturer;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike Benton CSC280
 */
public class ManufacturerEntityTest {
        //ADDED FOR ASSIGNMENT
        private static Validator validator;
        private static ManufacturerEntity manufacturer;
    
    public ManufacturerEntityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //ADDED FOR ASSIGNMENT
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        manufacturer = new ManufacturerEntity();
        
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
     * Test of setManufacturerId method, of class ManufacturerEntity.
     * TEST FOR NULL
     */
    @org.junit.Test
    public void testSetManufacturerId() {
        System.out.println("setManufacturerId");
        Integer manufacturerId = null;
        ManufacturerEntity instance = new ManufacturerEntity();
        instance.setManufacturerId(manufacturerId);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"name");
        assertTrue(constraintViolations.size()>0); 
         
    }

    /**
     * Test of setName method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetName() {
        System.out.println("test setName");
        String name = "McDonald"; 
        manufacturer.setName(name);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"name");
        assertEquals(0,constraintViolations.size());   
    }
    
    /**
     * Test of setName method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetName() {
        System.out.println("test invalid setName wrong format");
        String name = "a b c d e f"; 
        manufacturer.setName(name);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"name");
        assertTrue(constraintViolations.size()>0);  
    }
    
    /**
     * Test of setName method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetName2() {
        System.out.println("test invalid setName size exceeded");
        String name = "123456789012345678901234567890---too many"; 
        manufacturer.setName(name);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"name");
        assertTrue(constraintViolations.size()>0);  
    }
    /**
     * Test of setAddressline1 method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetAddressline1() {
        System.out.println("test setAddressline1");
        String addressline1 = "123456789012345678901234567890"; 
        manufacturer.setAddressline1(addressline1);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"addressline1");
        assertEquals(0,constraintViolations.size()); 
    }
    
    /**
     * Test of setAddressline1 method, of class ManufacturerEntity.
     * TEST INVALID ENTRY
     */
    @org.junit.Test
    public void testSetInvalidAddressline1() {
        System.out.println("test setInvalidAddressline1");
        String addressline1 = "123456789012345678901234567890---and30"; 
        manufacturer.setAddressline1(addressline1);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"addressline1");
        assertTrue(constraintViolations.size()>0);
    }
    
    /**
     * Test of setAddressline2 method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetAddressline2() {
        System.out.println("test setAddressline2");
        String addressline2 = "123456789012345678901234567890"; 
        manufacturer.setAddressline1(addressline2);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"addressline2");
        assertEquals(0,constraintViolations.size()); 
    }
    
    /**
     * Test of setAddressline2 method, of class ManufacturerEntity.
     * TEST INVALID ENTRY
     */
    @org.junit.Test
    public void testSetInvalidAddressline2() {
        System.out.println("test setInvalidAddressline2");
        String addressline2 = "123456789012345678901234567890----more"; 
        manufacturer.setAddressline2(addressline2);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"addressline2");
        assertTrue(constraintViolations.size()>0);
    }

    /**
     * Test of setCity method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetCity() {
        System.out.println("test setCity");
        String City = "Denver"; 
        manufacturer.setCity(City);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"city");
        assertEquals(0,constraintViolations.size()); 
    }
    
    /**
     * Test of setCity method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: Invalid Entry
     */
    @org.junit.Test
    public void testInvalidSetCity() {
        System.out.println("test invalid setCity wrong format");
        String City = "vermillion"; 
        manufacturer.setCity(City);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"city");
        assertTrue(constraintViolations.size()>0);
    }
    
    /**
     * Test of setCity method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetCity2() {
        System.out.println("test invalid setCity size exceeded");
        String City = "123456789012345678901234567890----more"; 
        manufacturer.setCity(City);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"city");
        assertTrue(constraintViolations.size()>0);
    }

    /**
     * Test of setState method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
    */
    @org.junit.Test
    public void testSetState() {
        System.out.println("setState");
        String state = "SD";
        manufacturer.setState(state);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"state");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setState method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
    */
    @Test
    public void testSetInvalidState() {
        System.out.println("Test invalid setState maximum size exceeded");
        String state = "South Dakota";
        manufacturer.setState(state);
        Set<ConstraintViolation<ManufacturerEntity>> constraints =
                validator.validateProperty(manufacturer,"state");
        
        Iterator<ConstraintViolation<ManufacturerEntity>> it = constraints.iterator();
        while(it.hasNext()){
            ConstraintViolation<ManufacturerEntity> constraint = it.next();
            System.out.println("Error: " + constraint.getMessage());
        }
        assertTrue(constraints.size()>0);
    }
    
    /**
     * Test of setState method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
    */
    @Test
    public void testSetInvalidState2() {
        System.out.println("Test invalid setState wrong format");
        String state = "AA";
        manufacturer.setState(state);
        Set<ConstraintViolation<ManufacturerEntity>> constraints =
                validator.validateProperty(manufacturer,"state");
        assertTrue(constraints.size()>0);
    }
    /**
     * Test of setZip method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetZip() {
        System.out.println("setZip");
        String zip = "57069-0000";
        manufacturer.setZip(zip);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"zip");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setZip method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetZip() {
        System.out.println("Test invalid setZip wrong format");
        String zip = "000";
        manufacturer.setZip(zip);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"zip");
        assertTrue(constraintViolations.size()>0);
    }
    
    /**
     * Test of setZip method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetZip2() {
        System.out.println("Test invalid setZip size exceeded");
        String zip = "123456789012345";
        manufacturer.setZip(zip);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"zip");
        assertTrue(constraintViolations.size()>0);
    }
    /**
     * Test of setPhone method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetPhone() {
        System.out.println("Test setPhone");
        String phone = "605-670-0827";
        manufacturer.setPhone(phone);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"phone");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setPhone method, of class ManufacturerEntity.
     * TEST INVALID ENTRY:WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetPhone() {
        System.out.println("Test invalid setPhone wrong format");
        String phone = "16056700827";
        manufacturer.setPhone(phone);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"phone");
        assertTrue(constraintViolations.size()>0);
    }

    /**
     * Test of setPhone method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetPhone2() {
        System.out.println("Test invalid setPhone size exceeded");
        String phone = "1-(605)-670-0827";
        manufacturer.setPhone(phone);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"phone");
        assertTrue(constraintViolations.size()>0);
    }
    /**
     * Test of setFax method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetFax() {
        System.out.println("Test setFax");
        String fax = "605-670-0827";
        manufacturer.setFax(fax);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"fax");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setFax method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetFax() {
        System.out.println("Test invalid setFax wrong format");
        String fax = "(605)6700827";
        manufacturer.setFax(fax);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"fax");
        assertTrue(constraintViolations.size()>0);
    }

    /**
     * Test of setFax method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetFax2() {
        System.out.println("Test invalid setFax size exceeded");
        String fax = "1-(605)-670-0827";
        manufacturer.setFax(fax);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"fax");
        assertTrue(constraintViolations.size()>0);
    }
    /**
     * Test of setEmail method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetEmail() {
        System.out.println("Test setEmail");
        String email = "mikecrbenton@hotmail.com";
        manufacturer.setEmail(email);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"email");
        assertEquals(0,constraintViolations.size());
    }
    
    /**
     * Test of setEmail method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetEmail() {
        System.out.println("Test invalid setEmail wrong format");
        String email = "mikecrbenton@hotmail.com.com";
        manufacturer.setEmail(email);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"email");
        assertTrue(constraintViolations.size()>0);
    }
    
    /**
     * Test of setEmail method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetEmail2() {
        System.out.println("Test invalid setEmail size exceeded");
        String email = "123456789012345678901234567890@hotmail.com";
        manufacturer.setEmail(email);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"email");
        assertTrue(constraintViolations.size()>0);
    }
    /**
     * Test of setRep method, of class ManufacturerEntity.
     * TEST CORRECT ENTRY
     */
    @org.junit.Test
    public void testSetRep() {
        System.out.println("test setRep");
        String rep = "Joe Smith"; 
        manufacturer.setRep(rep);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"rep");
        assertEquals(0,constraintViolations.size()); 
    }
    
     /**
     * Test of setRep method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: WRONG FORMAT
     */
    @org.junit.Test
    public void testInvalidSetRep() {
        System.out.println("test invalid setRep wrong format");
        String rep = "Joe/Smith/"; 
        manufacturer.setRep(rep);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"rep");
        assertTrue(constraintViolations.size()>0); 
    }
    
     /**
     * Test of setRep method, of class ManufacturerEntity.
     * TEST INVALID ENTRY: MAXIMUM SIZE EXCEEDED
     */
    @org.junit.Test
    public void testInvalidSetRep2() {
        System.out.println("test invalid setRep size exceeded");
        String rep = "There is more than 30 characters in this string"; 
        manufacturer.setRep(rep);
        Set<ConstraintViolation<ManufacturerEntity>> constraintViolations =
                validator.validateProperty(manufacturer,"rep");
        assertTrue(constraintViolations.size()>0); 
    }
    /**
     * Test of hashCode method, of class ManufacturerEntity.
     */
    /*
    @org.junit.Test
    public void testHashCode() {
        System.out.println("hashCode");
        ManufacturerEntity instance = new ManufacturerEntity();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
    /**
     * Test of equals method, of class ManufacturerEntity.
     * TEST OF EQUAL OBJECTS
     */
    @org.junit.Test
    public void testEquals() {
        System.out.println("Test equals");
        
        ManufacturerEntity obj = new ManufacturerEntity();
        ManufacturerEntity instance = new ManufacturerEntity();
        
        obj.setName("Verizon");
        instance.setName("Verizon");
        
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of equals method, of class ManufacturerEntity.
     * TEST OF UNEQUAL OBJECTS
     */
    @org.junit.Test
    public void testNotEquals() {
        System.out.println("Test for not equals");
        
        ManufacturerEntity obj = new ManufacturerEntity();
        ManufacturerEntity instance = new ManufacturerEntity();
        
        obj.setName("Verizon");
        instance.setName("Polaris");
        
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of compareTo method, of class ManufacturerEntity.
     * TEST FOR EQUAL NAMES
     */
    @org.junit.Test
    public void testCompareTo() {
        System.out.println("Test compareTo");
        
        ManufacturerEntity obj = new ManufacturerEntity();
        ManufacturerEntity instance = new ManufacturerEntity();
        
        obj.setName("Polaris");
        instance.setName("Polaris");
        
        int expResult = 0;
        int result = instance.compareTo(obj);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of compareTo method, of class ManufacturerEntity.
     * TEST FOR UNEQUAL NAMES
     */
    @org.junit.Test
    public void testUnequalCompareTo() {
        System.out.println("Test unequal compareTo");
        
        ManufacturerEntity obj = new ManufacturerEntity();
        ManufacturerEntity instance = new ManufacturerEntity();
        
        obj.setName("One");
        instance.setName("Polaris");
        
        int expResult = 1;
        int result = instance.compareTo(obj);
        assertEquals(expResult, result);
        
    }
    /**
     * Test of toString method, of class ManufacturerEntity.
     */
    @org.junit.Test
    public void testToString() {
        
        System.out.println("Test toString");
        
        //CREATE AN INSTANCE OF THE CLASS
        ManufacturerEntity instance = new ManufacturerEntity();
        //CREAT AN INSTANCE OF THE NAME
        String instanceName = "Polaris";
        //SET THE NAME IN THE CLASS
        instance.setName(instanceName);
        
        String expResult = "Polaris";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }
    
}
