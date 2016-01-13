package org.usd.csci.product.jsf;

import org.usd.csci.product.ProductEntity;
import org.usd.csci.product.jsf.util.JsfUtil;
import org.usd.csci.product.jsf.util.JsfUtil.PersistAction;
import org.usd.csci.product.ProductEntityFacade;

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
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import org.usd.csci.product.ProductCodeEntity;
import org.usd.csci.product.ProductCodeEntityFacade;
import org.usd.csci.utility.InvalidEntityException;

@Named("productEntityController")
@SessionScoped
public class ProductEntityController implements Serializable {
    
    //ADDED FOR ASSIGNMENT
    private String searchLabel = "";
    private List searchItems = new ArrayList();
    private Object selectedItem;
    private Integer searchType = 1; 
    
    //ADDED FOR ASSIGNMENT
    private int quantityLimit = 0;

    @EJB
    private ProductEntityFacade ejbFacade;
    @EJB
    private ManufacturerEntityFacade manFacade;        //ADDED FOR ASSIGNMENT
    @EJB
    private ProductCodeEntityFacade productCodeFacade; //ADDED FOR ASSIGNMENT
    
    private List<ProductEntity> items = null;
    private ProductEntity selected;

    public ProductEntityController() {
    }

    public ProductEntity getSelected() {
        return selected;
    }

    public void setSelected(ProductEntity selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductEntityFacade getFacade() {
        return ejbFacade;
    }

    public ProductEntity prepareCreate() {
        selected = new ProductEntity();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Product").getString("ProductEntityCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Product").getString("ProductEntityUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Product").getString("ProductEntityDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProductEntity> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Product").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Product").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProductEntity getProductEntity(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProductEntity> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProductEntity> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /**
     * prepareSearch        receives an int representing the user choice. A switch
     *                      case presents options.
     * 
     * @param type          int representing type
     * 
     * throws               InvalidEntityException 
     */
    public void prepareSearch(int type){
        
        searchType = type;
        
        try {
            switch (type){
                case 1:
                    searchLabel = "Manufacturer ";
                    searchItems = manFacade.findAll();
                    break;
                case 2:
                    searchLabel = "Product Code ";
                    searchItems = productCodeFacade.findAll();
                    break;
                case 3:
                    items = ejbFacade.findAll();
                    break;
                case 4:
                    items = ejbFacade.findAllAvailable("TRUE");
                    break;
            }
        }catch(InvalidEntityException e){
            JsfUtil.addErrorMessage(e, "Unable to find all available:" +
                    e.getMessage());
        }
    }
    
    /**
     * search       the field "searchType" is passed into a switch statement and
     *              either the Manufacturer Facade or Product Code Entity Facade
     *              is called.  The facade returns a list to the field "items"
     */
    public void search() {
        
        try {
            switch (searchType) {

                case 1:
                    ManufacturerEntity manufacturer = manFacade.findByName((String)selectedItem);
                    items = ejbFacade.findAllByManufacturer(manufacturer);
                    break;
                case 2:
                    ProductCodeEntity productCode = productCodeFacade.find((String)selectedItem);
                    items = ejbFacade.findAllByProductCode(productCode);
                    break;
            }
        }catch(InvalidEntityException e){
            JsfUtil.addErrorMessage(e, "Unable to search:" +
                    e.getMessage());
        }
    }

    /**
     * prepareSearchQOH     the field "quantityLimit" is initialized to 0, and
     *                      field "searchLabel" is set to the correct label
     */
    public void prepareSearchQOH(){
        
        searchLabel = "Quantity On Hand ";
        quantityLimit = 0;
    }
    
    /**
     * searchQOH        the Product Entity Facade is called with method 
     *                  findUnderQOHLimit() and quantityLimit as a parameter.  
     *                  The results are returned to the List "items"
     */
    public void searchQOH(){
        
        try{
            items = ejbFacade.findUnderQOHLimit(quantityLimit);
        }catch(InvalidEntityException e){
            e.getMessage();
        }
    }
    
    
    @FacesConverter(forClass = ProductEntity.class)
    public static class ProductEntityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductEntityController controller = (ProductEntityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productEntityController");
            return controller.getProductEntity(getKey(value));
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
            if (object instanceof ProductEntity) {
                ProductEntity o = (ProductEntity) object;
                return getStringKey(o.getProductId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProductEntity.class.getName()});
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
     * getSearchItems       returns the Controller's searchItems
     * 
     * @return              List representing the searchItems
     */
    public List getSearchItems() {
        return searchItems;
    }

    /**
     * getSelectedItems       returns the Controller's selectedItem
     * 
     * @return                Object representing the selectedItem
     */
    public Object getSelectedItem() {
        return selectedItem;
    }

    /**
     * getSearchType          returns the Controller's searchType
     * 
     * @return                Integer representing the searchType
     */
    public Integer getSearchType() {
        return searchType;
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
     * setSearchItems           receives a String representing a List of searchItems
     *                  
     * 
     * @param searchLabel       List representing searchItems
     */
    public void setSearchItems(List searchItems) {
        this.searchItems = searchItems;
    }

    /**
     * setSelectedItem          receives an Object representing a selectedItem
     *                  
     * 
     * @param selectedItem      Object representing a selectedItem
     */
    public void setSelectedItem(Object selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * setSearchType            receives an Integer representing a searchType
     *                  
     * 
     * @param searchType        Integer representing a searchType
     */
    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    /**
     * getQuantityLimit       returns the Controller's quantityLimit
     * 
     * @return                int representing the quantityLimit
     */
    public int getQuantityLimit() {
        return quantityLimit;
    }

    /**
     * setQuantityLimit            receives an int representing a quantityLimit
     *                  
     * 
     * @param quantityLimit        int representing a quantityLimit
     */
    public void setQuantityLimit(int quantityLimit) {
        this.quantityLimit = quantityLimit;
    }
    
    
    
    

}
