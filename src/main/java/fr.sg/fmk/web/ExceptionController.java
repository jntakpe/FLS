package fr.sg.fmk.web;


import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.exception.AjaxException;
import fr.sg.fmk.exception.FmkException;
import fr.sg.fmk.service.MessageManager;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


/**
 * Classe interceptant toutes les exceptions sortant de la couche web
 *
 * @author jntakpe
 */
@ControllerAdvice
public class ExceptionController {

    @Autowired
    private MessageManager messageManager;

    /**
     * Capte les exceptions framework non gérées par la couche web, reviens à la page précédente et transmet l'erreur
     *
     * @param e exception du type FmkException
     * @return la page précédente avec l'erreur
     */
    @ExceptionHandler(FmkException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleFmkException(FmkException e, HttpServletRequest request) {
        e.printStackTrace();
        FlashMap map = RequestContextUtils.getOutputFlashMap(request);
        map.put("responseMessage", ResponseMessage.getErrorMessage(e.getMessage()));
        String referer = request.getHeader("referer");
        View view = referer != null ? new RedirectView(referer) : new RedirectView(FmkUtils.ERROR_VIEW, true);
        return new ModelAndView(view);
    }

    /**
     * Gère les exceptions sur les requêtes AJAX
     *
     * @param e exception encapsulant l'exception d'origine
     * @return entité contenant le message à afficher et le code de retour HTTP
     */
    @ExceptionHandler(AjaxException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseMessage handleAjaxException(AjaxException e) {
        e.printStackTrace();
        Throwable cause = e.getCause();
        if (e.getCause() instanceof FmkException)
            return ResponseMessage.getErrorMessage(cause.getMessage(), cause.getStackTrace());
        return ResponseMessage.getErrorMessage(messageManager.getMessage("unknown.error"));
    }

    /**
     * Capte les exceptions non catchées de la couche web et affiche l'écran d'erreur.
     *
     * @param e exception
     * @return page d'affichage standard des erreurs
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleUnknownException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        FlashMap map = RequestContextUtils.getOutputFlashMap(request);
        map.put("exception", e);
        return new ModelAndView(new RedirectView(FmkUtils.ERROR_VIEW, true));
    }

}
