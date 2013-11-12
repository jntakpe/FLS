package fr.sg.fmk.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Classe contenant les méthodes utilitaires du framework
 *
 * @author jntakpe
 */
public final class FmkUtils {

    /**
     * URI de l'écran d'accueil
     */
    public static final String PORTAL_VIEW = "/portal";

    /**
     * URI de l'écran d'erreur
     */
    public static final String ERROR_VIEW = "/error";

    /**
     * Constructeur inaccessible
     */
    private FmkUtils() {
    }

    /**
     * Récupère le login de l'utilisateur courant
     *
     * @return login courant
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Rôles de l'utilisateur courant
     *
     * @return rôles de l'utilisateur connecté
     */
    public static Collection<? extends GrantedAuthority> getCurrentRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
