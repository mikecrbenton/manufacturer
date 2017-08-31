# Manufacturer - Final Project
CSC 280 Software Engineering // Final project incorporating: Regular Expressions, JUnit Testing, and MVC

## Assignment Directive:
NEED TO ADD----------

## Code In Project Written:
* manufacturer/src/main/java/org/usd/csci/manufacturer/**ManufacturerEntity.java**
  * Override the "equals()" method : A Manufacturer is equal is their names are equal - Line 353
  * Override the "toString()" method : Return the Manufacturer's name - Line 385
  * Override the "compareTo()" method : A Manufacturer is equal if there names are equal - Line 375
  * Modify regular expressions according to assignment directives - Lines 49-110
* manufacturer/src/test/java/org/usd/csci/manufacturer/**ManufacturerEntityTest.java**
  * Write test methods for each validation rule -both valid and invalid
  * Create test methods for compareTo(), equals() and toString() methods
* manufacturer/src/main/java/org/usd/csci/manufacturer/**InvalidEntityException.java**
  * Extend an Exception class with an overloaded constructor() that recieves a String
* manufacturer/src/main/java/org/usd/csci/manufacturer/**ManufacturerEntityFacade.java**
  * Override AbstractFacade create() and edit() methods.  Verify for correct information - Lines 41, 92
  * Create findByName() method - finds a record by name - Line 137
  * Create findByEmail() method - finds a record by email - Line 198
  * Create findByCityAndState() - finds a list of records by city & state - Line 229
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
* manufacturer/src/main/java/org/usd/csci/manufacturer/jsf/**ManufacturerEntityController.java**

    
