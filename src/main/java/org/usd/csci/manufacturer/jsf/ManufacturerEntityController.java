package org.usd.csci.manufacturer.jsf;

import org.usd.csci.manufacturer.ManufacturerEntityFacade; //The facade that was not in .jsf package
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.jsf.util.JsfUtil;
import org.usd.csci.manufacturer.jsf.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.usd.csci.utility.InvalidEntityException;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import org.usd.csci.product.ProductCodeEntityFacade;
import org.usd.csci.product.ProductEntityFacade;

@Named("manufacturerEntityController")
@SessionScoped
public class ManufacturerEntityController implements Serializable {

    @EJB
    private ManufacturerEntityFacade ejbFacade;
    
    @EJB
    private ProductEntityFacade prodFacade;
    
    @EJB
    private ProductCodeEntityFacade prodCodeFacade;
    
    //ADDED FOR ASSIGNMENT
    private String searchLabel = "";            //FOR JSF SEARCH
    private String name = "";                   //FOR JSF SEARCH
    private String email = "";                  //FOR JSF SEARCH
    private String city = "";                   //FOR JSF SEARCH
    private String state = "";                  //FOR JSF SEARCH
    private List searchItems = new ArrayList(); //FOR JSF SEARCH
    
    
    private List<ManufacturerEntity> items = null;
    private ManufacturerEntity selected;
    
    public String getMessage() {
        System.out.println("Getting message ");
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    String message = "In the manufacturer controller";
    
    

    public ManufacturerEntityController() {
    }

    public ManufacturerEntity getSelected() {
        return selected;
    }

    public void setSelected(ManufacturerEntity selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ManufacturerEntityFacade getFacade() {
        return ejbFacade;
    }

    public ManufacturerEntity prepareCreate() {
        selected = new ManufacturerEntity();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ManufacturerEntity> update() {   //was void for return- changed on 3/23/15 in order to update the GUI
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityUpdated"));
        items = getFacade().findAll(); //re-populate the list from the database
        return items;
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ManufacturerEntity> getItems() {
        
        if (items == null) {
            items = getFacade().findAll();    
        }
        return items;
    }
    
   /**
    * prepareSearchName     sets the field "searchLabel" to the correct label,
    *                       initializes name to empty, and resets the ArrayList
    *                       "searchItems" to re-initialize it
    */ 
   public void prepareSearchName(){
       
       searchLabel = "Search By Name";
       name = "";
       //REMOVE ANY PREVIOUS ITEM SO THE LIST IS RE-INITIALIZED
       searchItems = new ArrayList();
   }
   
   /**
    * searchName        calls the Manufacturer Facades method findByName(), using
    *                   the field "name" as a parameter. The Manufacturer Entity
    *                   is added to the ArrayList "searchItems", and the List "items" is
    *                   initialized to "searchItems"
    */
   public void searchName(){
        
        try{
            
             ManufacturerEntity manufacturer = ejbFacade.findByName(name);
             searchItems.add(manufacturer); //THIS STEP WAS NEEDED TO MAKE WORK
             items = searchItems; 
            
        }catch(InvalidEntityException e){
            e.getMessage();
        }
   }
   
   /**
    * prepareSearchEmail    sets the field "searchLabel" to the correct label,
    *                       initializes email to empty, and resets the ArrayList
    *                       "searchItems" to re-initialize it
    */ 
   public void prepareSearchEmail(){
       
       searchLabel = "Search By Email";
       email = "";
       //REMOVE ANY PREVIOUS ITEM SO THE LIST IS RE-INITIALIZED
       searchItems = new ArrayList();
   }
   
   /**
    * searchEmail       calls the Manufacturer Facades method findByEmail, using
    *                   the field "email" as a parameter. The Manufacturer Entity
    *                   is added to the ArrayList "searchItems", and the List "items" is
    *                   initialized to "searchItems"
    */
   public void searchEmail(){
        
        try{
            
             ManufacturerEntity manufacturer = ejbFacade.findByEmail(email);
             searchItems.add(manufacturer); //THIS STEP WAS NEEDED TO MAKE WORK
             items = searchItems; 
            
        }catch(InvalidEntityException e){
            e.getMessage();
        }
   }
   
    /**
    * prepareSearchCityState     initializes the fields "city" and "state" so they
    *                            are empty Strings
    *                       
    */ 
    public void prepareSearchCityState(){
        
        city = "";
        state = "";
    }
    
    /**
     * searchCityState      the Manufacturer Facades method findByCityAndState()
     *                      is called and fields "city" and "state" are passed in
     *                      as parameters.  The controller field "items" is set to the returned
     *                      list<>.
     */
    public void searchCityState(){
        
        try{
            items = ejbFacade.findByCityAndState(city, state);
        }catch(InvalidEntityException e){
            e.getMessage();
        }
    }
    /**
     * prepareSearchAll     calls the Manufacturer Facade's findAll() method and
     *                      sets the List of "items" to the result
     */
    public void prepareSearchAll(){
       items = ejbFacade.findAll();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                } else if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                }else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                System.out.println("Do i get here?");
             //   Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ManufacturerEntity getManufacturerEntity(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ManufacturerEntity> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ManufacturerEntity> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ManufacturerEntity.class)
    public static class ManufacturerEntityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ManufacturerEntityController controller = (ManufacturerEntityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "manufacturerEntityController");
            return controller.getManufacturerEntity(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ManufacturerEntity) {
                ManufacturerEntity o = (ManufacturerEntity) object;
                return getStringKey(o.getManufacturerId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ManufacturerEntity.class.getName()});
                return null;
            }
        }

    }

    /**
     * getSearchLabel       returns the Controller's searchLabel
     * 
     * @return              String representing the searchLabel
     */
    public String getSearchLabel() {
        return searchLabel;
    }

    /**
     * setSearchLabel           receives a String representing a searchLabel
     *                  
     * 
     * @param searchLabel       String representing a searchLabel
     */
    public void setSearchLabel(String searchLabel) {
        this.searchLabel = searchLabel;
    }

    /**
     * getName              returns the Controller's name
     * 
     * @return              String representing the name
     */
    public String getName() {
        return name;
    }

    /**
     * setName             receives a String representing a name
     *                  
     * 
     * @param name         String representing a name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getEmail             returns the Controller's email
     *
     * @return              String representing the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail             receives a String representing an email
     *                  
     * 
     * @param email         String representing an email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getCity              returns the Controller's city
     * 
     * @return              String representing the city
     */
    public String getCity() {
        return city;
    }

    /**
     * setCity             receives a String representing a city
     *                  
     * 
     * @param city         String representing a city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * getState             returns the Controller's state
     * 
     * @return              String representing the state
     */
    public String getState() {
        return state;
    }

    /**
     * setState             receives a String representing a state
     *                  
     * 
     * @param state         String representing a state
     */
    public void setState(String state) {
        this.state = state;
    }
    
    
    
    
}
