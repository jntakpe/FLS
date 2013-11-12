package fr.sg.fmk.web;

import fr.sg.fmk.constant.Format;
import fr.sg.fmk.domain.Parameter;
import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Contr�leur de l'�cran de param�trage
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/param")
public class ParameterController extends GenericController<Parameter> {

    @Autowired
    private ParameterService parameterService;

    /**
     * Initialise le cache stock� en session storage
     *
     * @param codes codes demand�s par le cache JS
     * @return ResponseMessage contenant les codes demand�s
     */
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage initJSCache(@RequestParam String[] codes) {
        Map<String, String> codesValuesMap = new HashMap<String, String>(codes.length);
        for (String code : codes) {
            Parameter parameter = parameterService.findByCode(code);
            codesValuesMap.put(code, parameter == null ? null : parameter.getValue());
        }
        return ResponseMessage.getSuccessMessage(codesValuesMap);
    }

    @Override
    public String getListViewPath() {
        return "fmk/parameter_list";
    }

    @Override
    public ModelAndView display() {
        return super.display().addObject("formats", Format.values());
    }

    @Override
    protected GenericService<Parameter> getService() {
        return parameterService;
    }
}
