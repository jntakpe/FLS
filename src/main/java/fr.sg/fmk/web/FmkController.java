package fr.sg.fmk.web;

import fr.sg.fmk.service.MessageManager;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * Contr�leur des �crans g�r�s dirrectement par le framework
 *
 * @author jntakpe
 */
@Controller
public class FmkController {

    /**
     * R�le minimum pour authentifier un utilisateur
     */
    public static final String BASIC_ROLE = "ROLE_USER";

    @Autowired
    private MessageManager messageManager;

    /**
     * Affiche l'�cran d'accueil
     *
     * @return uri de l'�cran d'accueil
     */
    @RequestMapping(value = {"/portal", "/"}, method = RequestMethod.GET)
    public String portal() {
        return "fmk/portal";
    }

    /**
     * Si l'utilisateur est connect� renvoi vers le portail sinon renvoi vers la page de connexion en ajoutant un
     * message d'erreur
     *
     * @param request requ�te http
     * @param error   cause de l'�chec de l'authentification
     * @return vue � afficher
     */
    @RequestMapping(value = "/connexion", method = RequestMethod.GET)
    public ModelAndView connexion(HttpServletRequest request, @RequestParam(required = false) String error) {
        if (request.isUserInRole(BASIC_ROLE)) return new ModelAndView(new RedirectView(FmkUtils.PORTAL_VIEW, true));
        ModelAndView mv = new ModelAndView("fmk/connexion");
        if (error != null) {
            String msg;
            if ("sessionExpired".equals(error)) msg = messageManager.getMessage("session.expired");
            else if ("sessionExpiredDuplicateLogin".equals(error)) msg = messageManager.getMessage("session.duplicate");
            else {
                Object obj = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                if (obj != null && obj instanceof BadCredentialsException)
                    msg = messageManager.getMessage("bad.credentials");
                else msg = messageManager.getMessage("authentication.failure");
            }
            mv.addObject("error", error).addObject("authentMsg", msg);
        }
        return mv;
    }

    /**
     * Affiche l'�cran d'erreur
     *
     * @return uri de l'�cran d'erreur
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "fmk/error";
    }

}
