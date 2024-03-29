package jsf;

import jpa.entities.Client;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.ClientFacade;

import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.ShortConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.entities.ClientPK;

@ManagedBean(name = "clientController")
@SessionScoped
public class ClientController {

    private Client current;
    private DataModel items = null;
    @EJB
    private jpa.session.ClientFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

//added from me
    //  private String clientName;
    //  private short clientDepartmentNumber;

    /* private ClientPK createAndGetPrimaryKeyComposite() {
     ClientPK primarykeycomposite = null;

     if (this.clientName != null && (!this.clientName.isEmpty()) && this.clientDepartmentNumber > 0) {
     primarykeycomposite = new ClientPK(clientName, clientDepartmentNumber);
     }
     return primarykeycomposite;

     }
     */
    public ClientController() {
//this.clientName="";
//this.clientDepartmentNumber= 0;
    }

//end code added from me
    public Client getSelected() {
        if (current == null) {
            current = new Client();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ClientFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Client) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Client();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            /*
             // added from me   
             ClientPK pk = createAndGetPrimaryKeyComposite();
             current.setClientPK(pk);

             // end code added from me
             */
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ClientCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Client) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ClientUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Client) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ClientDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public String getClientName() {
        return this.getSelected().getClientPK().getClientName();
    }

    public void setClientName(String clientName) {

        //  this.clientName = clientName;
        getSelected().getClientPK().setClientName(clientName);
    }

    public short getClientDepartmentNumber() {
        return this.getSelected().getClientPK().getClientDepartmentNumber();

    }

    public void setClientDepartmentNumber(short clientDepartmentNumber) {
        // this.clientDepartmentNumber = clientDepartmentNumber;
        getSelected().getClientPK().setClientDepartmentNumber(clientDepartmentNumber);
    }

    @FacesConverter(forClass = Client.class)
    public static class ClientControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClientController controller = (ClientController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clientController");
            return controller.ejbFacade.find(getKey(value));
        }

        jpa.entities.ClientPK getKey(String value) {
            jpa.entities.ClientPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.ClientPK();
            key.setClientName(values[0]);
            key.setClientDepartmentNumber(Short.parseShort(values[1]));
            return key;
        }

        String getStringKey(jpa.entities.ClientPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getClientName());
            sb.append(SEPARATOR);
            sb.append(value.getClientDepartmentNumber());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Client) {
                Client o = (Client) object;
                return getStringKey(o.getClientPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ClientController.class.getName());
            }
        }

    }

}
