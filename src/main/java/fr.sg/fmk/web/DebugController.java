package fr.sg.fmk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Controller utilisé pour accéder aux informations de debug du framework
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    /**
     * Affiche les différentes méthodes des contrôleur mappées
     *
     * @return nom de la vue à afficher
     */
    @RequestMapping(value = "/endpoints", method = RequestMethod.GET)
    public ModelAndView show() {
        ModelAndView mv = new ModelAndView("fmk/endpoints");
        return mv.addObject("endpoints", this.handlerMapping.getHandlerMethods());
    }
}
