
package org.usd.csci.manufacturer;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.usd.csci.utility.AbstractFacade;
import org.usd.csci.utility.InvalidEntityException;

/**
 *
 * @author Mike Benton CSC280
 */
@Stateless
public class ManufacturerEntityFacade extends AbstractFacade<ManufacturerEntity> {
    
    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() { 
        return em;
    }

    public ManufacturerEntityFacade() {
        super(ManufacturerEntity.class);
    }
    
    //**************ADDED FOR ASSIGNEMENT- OVERRIDE CREATE METHOD FROM ABSTRACT FACADE
    /**
     * create(@override)                Receives a ManufacturerEntity Object and
     *                                  creates a record in the database
     * 
     * @param manufacturer              ManufacturerEntity Object
     * 
     * @throws InvalidEntityException   If Object is null or business rules are not met
     */
    @Override
    public void create(ManufacturerEntity manufacturer)throws InvalidEntityException {
        
        //CHECK THE ENTITY
        if(manufacturer == null){
            throw new InvalidEntityException("Parameter cannot be null");
        }
        //CHECK FOR NULL FIELDS
        if(manufacturer.getName() == null){
            throw new InvalidEntityException("Manufacturer name cannot be null");
        }
        if(manufacturer.getEmail() == null){
            throw new InvalidEntityException("Manufacturer email cannot be null");
        }
        if(manufacturer.getRep() == null){
            throw new InvalidEntityException("Manufacturer rep cannot be null");
        }
        
        ManufacturerEntity locEntity;
        
        //CHECK FOR ID
        locEntity = find(manufacturer.getManufacturerId());//SEE IF ID EXISTS     
        if(locEntity != null){
            throw new InvalidEntityException("manufacturerId must be unique");
        }
        
        //CHECK FOR NAME
        locEntity = findByName(manufacturer.getName()); //RETURN A RECORD WITH THAT NAME (PARAMETER)
        if(locEntity != null){
            throw new InvalidEntityException("Name must be unique");
        }
        
        //CHECK FOR EMAIL
        locEntity = findByEmail(manufacturer.getEmail()); //RETURN A RECORD WITH THAT EMAIL (PARAMETER)
        if(locEntity != null){
            throw new InvalidEntityException("email must be unique");
        }
        
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.create(manufacturer);
    }
    
    //**********ADDED FOR ASSIGNEMENT- OVERRIDE EDIT METHOD FROM ABSTRACT FACADE
    /**
     * edit(@Override)                  Receives a ManufacturerEntity Object, checks
     *                                  for business rules, and passes into the database
     * 
     * @param manufacturer              ManufacturerEntity Object
     * 
     * @throws InvalidEntityException   If business rules are not met
     */
    @Override
    public void edit(ManufacturerEntity manufacturer) throws InvalidEntityException {
        
        //RETURN RECORD BY PRIMARY KEY MATCH (19984800)
        ManufacturerEntity locEntity = find(manufacturer.getManufacturerId());
                                                                                
        //CHECK THE ENTITY FOR NULL
        if(locEntity == null){
            throw new InvalidEntityException("This record is not in the database: IN EDIT METHOD");
        }
        //CREATE A NEW ENTITY FROM findByName() TO TEST IF THE NAME IS ALREADY EXISTS
        ManufacturerEntity matchingEntity = findByName(manufacturer.getName());
                                                                                
        //DOES A RECORD WITH THIS NAME EXIST?
        if(matchingEntity != null && !(manufacturer.getManufacturerId().equals(matchingEntity.getManufacturerId()))){
            throw new InvalidEntityException("Name must be unique: The name you entered already exists in the database");
        }   
        
        matchingEntity = findByEmail(manufacturer.getEmail());
        
        //CHECK THE DATABASE IF THE EMAIL IS UNIQUE, OR ALREADY EXISTS
        if(matchingEntity != null && !(manufacturer.getManufacturerId().equals(matchingEntity.getManufacturerId()))){  
        //CHECK THE DATABASE IF THE EMAIL IS UNIQUE, OR ALREADY EXISTS
           // matchingEntity = findByEmail(manufacturer.getEmail());

            //if(matchingEntity.getManufacturerId() != locEntity.getManufacturerId()){
                throw new InvalidEntityException("Email must be unique: The e-mail address you entered already exists in the database");
            //}
        }
        System.out.println("Ready to edit+++++++++++++++++++++");
        //IF ALL THE BUSINESS RULES ARE PASSED
        super.edit(manufacturer);
        
    }
    
    //ADDED FOR ASSIGNMENT- FINDBYNAME()
    /**
     * findByName                           Receives a String, searches the database
     *                                      and returns a record if it exists
     * 
     * @param name                          String representing the name of the entity
     * 
     * @return                              Returns a ManufacturerEntity Object
     * 
     * @throws InvalidEntityException       If String is null 
     */
    public ManufacturerEntity findByName(String name) throws InvalidEntityException {
        
        if(name == null){
            throw new InvalidEntityException("parameter Name cannot be null");
        }
        //CREATE THE NAMED QUERY
        //createnamesQuery( name of query, name of class)
        TypedQuery<ManufacturerEntity> query = 
        em.createNamedQuery("ManufacturerEntity.findByName",ManufacturerEntity.class);
        
        //set the parameter- (string following the colon in JPA)
        query.setParameter("name", name);
        
        //RETURN A MANUFACTURERENTITY-  WILL BE EMPTY OR HAVE ONE RESULT
        List<ManufacturerEntity> list = query.getResultList();//all records with that name
        
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); //first element in list
    }
    
    
    
    public ManufacturerEntity findByRep(String rep) throws InvalidEntityException {                                   
                                                                                                                      
        System.out.println("In findByRep() method " + rep);
        
        if(rep == null){                                                                                              
            throw new InvalidEntityException("rep cannot be null");                                                   
        }                                                                                                            
        
        //CREATE THE NAMED QUERY
        //createnamesQuery( name of query, name of class)
        TypedQuery<ManufacturerEntity> query = 
        em.createNamedQuery("ManufacturerEntity.findByRep",ManufacturerEntity.class);
        
        //set the parameter- (string following the colon in JPA)
        query.setParameter("rep", rep);
        
        //RETURN A MANUFACTURERENTITY-  WILL BE EMPTY OR HAVE ONE RESULT
        List<ManufacturerEntity> list = query.getResultList();//all records with that name
        
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); //first element in list
        
    }
    
    //ADDED FOR ASSIGNMENT
    /**
     * findByEmail                      Receives a String, searches the database and
     *                                  returns a record if it exists
     * 
     * @param email                     String representing an email
     * 
     * @return                          ManufacturerEntity Object
     * 
     * @throws InvalidEntityException   If String is null 
     */
    public ManufacturerEntity findByEmail(String email) throws InvalidEntityException {
        
        if(email == null){
            throw new InvalidEntityException("parameter Email cannot be null");
        }
        
        TypedQuery<ManufacturerEntity> query = 
        em.createNamedQuery("ManufacturerEntity.findByEmail",ManufacturerEntity.class);
        
        query.setParameter("email", email);
        
        List<ManufacturerEntity> list = query.getResultList();
        
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    //ADDED FOR ASSIGNMENT
    /**
     * findByCityAndState               Receives 2 strings, searches the database
     *                                  and returns a List<> of matching records
     * 
     * @param city                      String representing a city
     * @param state                     String representing a state
     * 
     * @return                          List<ManufacturerEntity> 
     * 
     * @throws InvalidEntityException   If String city or state is null
     */
    public List<ManufacturerEntity> findByCityAndState(String city, String state) throws InvalidEntityException {
        
        //QUESTION: WHY NOT ABLE TO PUT IN ONE IF(state == null || city == null)
        if(state == null){
            throw new InvalidEntityException("parameter state cannot be null");
        }
        if(city == null){
            throw new InvalidEntityException("parameter city cannot be null");
        }
        
        TypedQuery<ManufacturerEntity> query = 
        em.createNamedQuery("ManufacturerEntity.findByCityAndState",ManufacturerEntity.class);
        
        //SET TWO PARAMETERS set().set()
        query.setParameter("city", city).setParameter("state", state);
        
        List<ManufacturerEntity> list = query.getResultList();
        
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
}
