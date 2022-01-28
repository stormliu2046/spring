package com.robert.config;

import com.robert.entity.User;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * @author robert
 * @date 2022/1/27
 */
public class StringToUserPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        User user = new User();
        user.setName(text);
        this.setValue(user);
    }
}
