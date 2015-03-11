/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import controller.BetegController;
import controller.OrvosController;
import entity.Orvos;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author gczuczor
 */
@FacesConverter("OrvosConverter")
public class OrvosConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                BetegController betegController = (BetegController) context.getExternalContext().getSessionMap().get("BetegController");
                for (Orvos orvos : betegController.getAllOrvos()) {
                    if (orvos.getId().equals(value)) {
                        return orvos;
                    }
                }
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "WARNING", "Nincs ilyen orvos."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Orvos o = (Orvos) value;
            return o.getId();
        } else {
            return null;
        }
    }

}
