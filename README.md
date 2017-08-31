# Manufacturer - Final Project
CSC 280 Software Engineering // Final project incorporating: Regular Expressions, JUnit Testing, and MVC

## Project Directive:
The Manufacturer project is a multi-week assignment working in Netbeans IDE to gain a basic understanding of several JEE concepts:

### Assignment 1:
The objective of this assignment is gain familiarity with JEE JPA Entity Beans.  You will create a JEE Entity Bean from a database table in local Netbeans Derby database.  An *entity bean* is a POJO (Plain Old Java Object) with annotations that represent a business object in a persistant storage mechanism. The entity that will be created will represent a MANUFACTURER table in the sample Derby database.  
#### Code Written:
* manufacturer/src/main/java/org/usd/csci/manufacturer/**ManufacturerEntity.java**
  * Override the equals() and hashcode() methods : A Manufacturer is equal is their names are equal - Line 353
  * Override the toString() method : Return the Manufacturer's name - Line 385
  * Override the compareTo() method : A Manufacturer is equal if there names are equal - Line 375
  * Modify regular expressions according to assignment directives - Lines 49-110
  
### Assignment 2:
The objective of this assignment is to familiarize you with JUnit testing of a JEE Entity.  You will create a JUnit test from an existing class. In this exercise, you will be testing your Manufacturer Entity from assignment 1.
#### Code Written:  
* manufacturer/src/test/java/org/usd/csci/manufacturer/**ManufacturerEntityTest.java**
  * Write test methods for each validation rule -both valid and invalid
    * testSetManufacturerId() - Line 55___________testInvalidSetZip() - Line 271  
    * testSetName() - Line 71_____________________testInvalidSetZip2() - Line 285
    * testInvalidSetName() - Line 85______________testSetPhone() - Line 298
    * testInvalidSetName2() - Line 99_____________testInvalidSetPhone() - Line 312
    * testSetAddressline1() - Line 112____________testInvalidSetPhone2() - Line 326
    * testSetInvalidAddressline1() - 126__________testSetFax() - Line 339
    * testSetAddressline2() - Line 140____________testInvalidSetFax() - Line 353
    * testSetInvalidAddressline2() - Line 154_____testInvalidSetFax2() - Line 367
    * testSetCity() - Line 168____________________testSetEmail() - Line 380
    * testInvalidSetCity() - Line 182_____________testInvalidSetEmail() - Line 394
    * testInvalidSetCity2() - Line 196____________testInvalidSetEmail2() - Line 408
    * testSetState() - Line 210___________________testSetRep() - Line 421
    * testSetInvalidState() - Line 224____________testInvalidSetRep() - Line 435
    * testSetInvalidState2() - Line 244___________testInvalidSetRep2() - Line 449
    * testSetZip() - Line 257
  * Create test methods for compareTo(), equals() and toString() methods
  
### Assignment 3:

  
#### Code Written:  
* manufacturer/src/main/java/org/usd/csci/utility/**InvalidEntityException.java**
  * Extend an Exception class with an overloaded constructor() that recieves a String
#### Code Written:  
* manufacturer/src/main/java/org/usd/csci/manufacturer/**ManufacturerEntityFacade.java**
  * Override AbstractFacade create() and edit() methods.  Verify for correct information - Lines 41, 92
  * Create findByName() method - finds a record by name - Line 137
  * Create findByEmail() method - finds a record by email - Line 198
  * Create findByCityAndState() - finds a list of records by city & state - Line 229
#### Code Written:  
* manufacturer/src/test/java/org/usd/csci/manufacturer/**ManufacturerEntityFacadeTest.java**
  * Create tests for overridden create() and edit() methods
    * testCreateManufacturer() - Line 133
    * testCreateManufacturerAlreadyExists() - Line 180
    * testCreateManufacturerNoRep() - Line 212
    * testEditManufacturer() - Line 246
    * testEditManufacturerNameAlreadyExists() - Line 280
    * testEditManufacturerEmailAlreadyExists() - Line 331
  * Create tests for findByName() method
    * testFindByName() - Line 378
    * testFindByNameNotInDatabase() - Line 420
  * Create tests for findByEmail() method
    * testFindByEmail() - Line 463
    * testFindByEmailNotInDatabase() - Line 505
  * Create tests for findByCityAndState() method
    * testFindByCityAndState() - Line 548
    * testFindByCityAndStateNotInDatabase() - Line 595
#### Code Written:    
* manufacturer/src/main/java/org/usd/csci/product/**ProductCodeEntity.java**
  * Modify Product Code to take 2 uppercase letters - Line 43
  * Modify Discount Code to take character A -Z Uppercase - Line 103
#### Code Written:  
* manufacturer/src/main/java/org/usd/csci/product/**ProductEntity.java**
  * Add verification and business rules:
    * Purchase cost greater than 0 - Line 56
    * Quantity on Hand not negative - Line 60
    * Markup between 0 and 100 - Line 64-65
    * Available is TRUE or FALSE - Line 69
  * Override compareTo(), products are equal if id's are equal - Line 268
  * Override toString(), return product id appended to product description - Line 286
  * Add @NamedQuery for additional methods - Line 40-43
#### Code Written:
* manufacturer/src/main/java/org/usd/csci/product/**ProductCodeEntityFacade.java**
  * Override create() and edit() methods to verify Product Code is unique - Line 42, Line 77
#### Code Written:
* manufacturer/src/main/java/org/usd/csci/product/**ProductEntityFacade.java**
  * Override the create(), and edit() methods - add business rules - Line 47, Line 102
  * Implement findAllByProduct() method - Line 161
  * Implement findAllByManufacturer() method - Line 188
  * Implement findUnderQOHLimit() method - Line 216
  * Implement findAllAvailable() method - Line 244
#### Code Written:  
* manufacturer/src/test/java/org/usd/csci/product/**ProductEntityFacadeTest.java**
  * Create tests for create() method
    * testCreate() - Line 114
    * testCreatePurchaseCostNotSet() - Line 139
    * testCreateIdNotUnique() - Line 169
    * testCreateInvalidManufacturer() - Line 199
    * testCreateInvalidProductCode() - Line 234
  * Create tests for edit() method
    * testEdit() - Line 270
    * testEditQuantityOnHand() - Line 300
    * testEditPurchaseCostNotSet() - Line 336
    * testEditInvalidManufacturer() - Line 371
    * testEditInvalidProductCode() - Line 410
  * Create test for findAllByProductCode() method - Line 449
  * Create test for findAllByManufacturer() - Line 479
  * Create test for FindUnderQOHLimit() - Line 510
  * Create test for findAllAvailable() - Line 541
#### Code Written:  
* manufacturer/src/main/webapp/productEntity/**List.xhtml**
  * Add menu with search options for: By Manufacturer, By Product Code
    By Quantity-On-Hand-Limit, By All Available, By All - Lines 19-40
    
  
    
