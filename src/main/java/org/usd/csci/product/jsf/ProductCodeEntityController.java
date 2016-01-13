package org.usd.csci.product.jsf;

import org.usd.csci.product.ProductCodeEntity;
import org.usd.csci.product.jsf.util.JsfUtil;
import org.usd.csci.product.jsf.util.JsfUtil.PersistAction;
import org.usd.csci.product.ProductCodeEntityFacade;

import java.io.Serializable;
import java.util.Collections;
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


@Named("productCodeEntityController")
@SessionScoped
public class ProductCodeEntityController implements Serializable {

    @EJB
    private ProductCodeEntityFacade ejbFacade;
    private List<ProductCodeEntity> items = null;
    private ProductCodeEntity selected;

    public ProductCodeEntityController() {
    }

    public ProductCodeEntity getSelected() {
        return selected;
    }

    public void setSelected(ProductCodeEntity selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductCodeEntityFacade getFacade() {
        return ejbFacade;
    }

    public ProductCodeEntity prepareCreate() {
        selected = new ProductCodeEntity();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Product").getString("ProductCodeEntityCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Product").getString("ProductCodeEntityUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Product").getString("ProductCodeEntityDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProductCodeEntity> getItems() {
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

    public ProductCodeEntity getProductCodeEntity(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ProductCodeEntity> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProductCodeEntity> getItemsAvailableSelectOne() {
        List<ProductCodeEntity> list = getFacade().findAll();
        Collections.sort(list); 
        return list;
    }

    @FacesConverter(forClass = ProductCodeEntity.class)
    public static class ProductCodeEntityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductCodeEntityController controller = (ProductCodeEntityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productCodeEntityController");
            return controller.getProductCodeEntity(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProductCodeEntity) {
                ProductCodeEntity o = (ProductCodeEntity) object;
                return getStringKey(o.getProdCode());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProductCodeEntity.class.getName()});
                return null;
            }
        }

    }

}
