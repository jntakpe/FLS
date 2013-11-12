package fr.sg.fmk.web;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.DatatablesRequest;
import fr.sg.fmk.dto.DatatablesResponse;
import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.exception.BusinessCode;
import fr.sg.fmk.service.DatatablesParams;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.MessageManager;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.ParameterizedType;

/**
 * Contr�leur abstrait des �crans liste et d�tail.
 * Les m�thodes non 'private' doivent �tre surcharg�es si elles ne correspondent pas au fonctionnement souhait�.
 *
 * @author cegiraud
 * @author jntakpe
 */
public abstract class GenericController<T extends GenericDomain> {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    protected MessageManager messageManager;

    /**
     * M�thode permettant de r�cup�rer le service � utiliser.
     *
     * @return interface du service.
     */
    protected abstract GenericService<T> getService();

    /**
     * Affiche la page liste
     *
     * @return le chemin de la page � afficher. Pour modifier le nom de la vue de la liste � afficher,
     *         veuillez utiliser {@link fr.sg.fmk.web.GenericController#getListViewPath()}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        return new ModelAndView(getListViewPath());
    }

    /**
     * Renvoi toutes les donn�es d'une table. Datatables g�re ensuite le filtrage, le tri et la pagination.
     *
     * @return entit�s � afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<T> list() {
        return getService().findAll();
    }

    /**
     * Renvoi les donn�es d�j� filtr�es, tri�es et pagin�es en fonction des param�tres Datatables
     *
     * @param datatablesRequest �tat de la liste DataTables
     * @return entit�s filtr�es, tri�es et pagin�es � afficher
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public DatatablesResponse<T> page(@DatatablesParams DatatablesRequest datatablesRequest) {
        return new DatatablesResponse<T>(getService().page(datatablesRequest), datatablesRequest.getCallCounter());
    }

    /**
     * Renvoi une entit� en fonction de l'identifiant pour l'affichage de popup
     *
     * @param id identifiant de l'entit�
     * @return message indiquant si l'entit� a bien �t� r�cup�r�e
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public T populate(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) throw getService().createBussinessException(BusinessCode.ENTITY_NOT_FOUND, id);
        return domain;
    }

    /**
     * Affiche l'�cran d�tail de cr�ation d'un nouveau �l�ment
     *
     * @return page d�tail
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView detail() throws Exception {
        return new ModelAndView(getDetailViewPath()).addObject("domain", getDomainClass().newInstance());
    }

    /**
     * Affiche l'�cran d�tail correspondant � l'�l�ment poss�dant cette id
     *
     * @param id identifiant de l'�l�ment � afficher
     * @return page d�tail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView(getDetailViewPath());
        T domain = getService().findOne(id);
        if (domain == null) throw getService().createBussinessException(BusinessCode.ENTITY_NOT_FOUND, id);
        return mv.addObject("domain", domain);
    }

    /**
     * Cr�� ou modifie l'entit� (utilis� pour les appels non-AJAX)
     *
     * @param domain             entit� � sauvegarder
     * @param redirectAttributes attributs de redirection lus sur la page suivante
     * @return page � afficher
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute T domain, RedirectAttributes redirectAttributes) {
        boolean isNew = domain.getId() == null;
        T entity = getService().save(domain);
        String msg = messageManager.getMessage(isNew ? "create.success" : "update.success", entity);
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(msg));
        String username = FmkUtils.getCurrentUsername();
        messageManager.logMessage(isNew ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return new ModelAndView(getRedirectListView());
    }

    /**
     * Cr�� ou modifie l'entit� (utilis� pour les appels AJAX)
     *
     * @param domain entit� � sauvegarder
     * @return message indiquant le r�sultat de l'op�ration
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute T domain) {
        boolean isNew = domain.getId() == null;
        T entity = getService().save(domain);
        String username = FmkUtils.getCurrentUsername();
        String msg = messageManager.getMessage(isNew ? "create.success" : "update.success", entity);
        messageManager.logMessage(isNew ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return ResponseMessage.getSuccessMessage(msg, entity);
    }

    /**
     * Supprime l'entit� correspondante � l'identifiant lors d'un appel non-AJAX.
     * La page sera donc recharg�e � l'issue de la suppression de l'entit�.
     *
     * @param id identifiant de l'entit� � supprimer
     * @return page � afficher
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView(getRedirectListView());
        T domain = getService().findOne(id);
        domain = null;
        if (domain == null) throw getService().createBussinessException(BusinessCode.ENTITY_NOT_FOUND, id);
        getService().delete(domain);
        messageManager.logMessage("MSG00003", LogLevel.INFO, FmkUtils.getCurrentUsername(), domain);
        String msg = messageManager.getMessage("delete.success", domain);
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(msg));
        return mv;
    }

    /**
     * Supprime l'entit� correspondante � l'identifiant lors d'un appel AJAX.
     * L'entit� sera supprim�e c�t� serveur (Database).
     * Le client(JavaScript) se chargera de la suppression de l'entit� dans la table.
     *
     * @param id identifiant de l'�l�ment � supprimer
     * @return message indiquant le r�sultat de la suppression
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) throw getService().createBussinessException(BusinessCode.ENTITY_NOT_FOUND, id);
        getService().delete(domain);
        messageManager.logMessage("MSG00003", LogLevel.INFO, FmkUtils.getCurrentUsername(), domain);
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("delete.success", domain));
    }

    /**
     * Renvoi le message de confirmation de suppression d'une ligne
     *
     * @param id identifiant de l'entit�
     * @return le message confirmation de suppression de cette entit�
     */
    @RequestMapping(value = "/{id}/message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage displayConfirmMsg(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) throw getService().createBussinessException(BusinessCode.ENTITY_NOT_FOUND, id);
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("popup.confirm.delete.msg", domain));
    }

    /**
     * R�cup�re le nom de la vue de la liste � afficher.
     * A surcharger si le nom de la vue est diff�rent de 'lists/' + NOM_ENTITE + '_list'.
     *
     * @return le nom de la vue � afficher
     */
    public String getListViewPath() {
        return "lists/" + getDomainClass().getSimpleName().toLowerCase() + "_list";
    }

    /**
     * R�cup�re le chemin de la vue du d�tail � afficher.
     * A surcharger si le chemin de la vue est diff�rent de 'details/' + NOM_ENTITE + '_detail'.
     *
     * @return le nom de la vue � afficher
     */
    public String getDetailViewPath() {
        return "details/" + getDomainClass().getSimpleName().toLowerCase() + "_detail";
    }

    /**
     * Renvoi la page de la liste depuis un d�tail
     *
     * @return page liste
     */
    protected final RedirectView getRedirectListView() {
        String baseUri = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        if (baseUri.charAt(0) != '/') baseUri = "/" + baseUri;
        return new RedirectView(baseUri, true);
    }

    /**
     * M�thode renvoyant l'entit� de la couche domain/model
     *
     * @return ressource utilis�e par le contr�lleur
     */
    protected final Class<T> getDomainClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
