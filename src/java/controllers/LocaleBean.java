/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author hp
 */
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable {

    private Locale locale = new Locale("eng"); // Langue par défaut

    public Locale getLocale() {
        return locale;
    }

    public void changeLanguage(String language) {
        locale = new Locale(language);
    }
}
