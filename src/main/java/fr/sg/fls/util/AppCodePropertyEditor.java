package fr.sg.fls.util;

import fr.sg.fls.enums.AppCode;

import java.beans.PropertyEditorSupport;

/**
 * @author jntakpe
 */
public class AppCodePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(AppCode.parse(text));
    }

}
