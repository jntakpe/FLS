package fr.sg.fmk.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Classe contenant les m�thodes utilitaires du framework
 *
 * @author jntakpe
 */
public final class FmkUtils {

    /**
     * URI de l'�cran d'accueil
     */
    public static final String PORTAL_VIEW = "/portal";

    /**
     * URI de l'�cran d'erreur
     */
    public static final String ERROR_VIEW = "/error";

    /**
     * Constructeur inaccessible
     */
    private FmkUtils() {
    }

    /**
     * R�cup�re le login de l'utilisateur courant
     *
     * @return login courant
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * R�les de l'utilisateur courant
     *
     * @return r�les de l'utilisateur connect�
     */
    public static Collection<? extends GrantedAuthority> getCurrentRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
