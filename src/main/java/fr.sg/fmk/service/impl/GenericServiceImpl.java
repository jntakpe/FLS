package fr.sg.fmk.service.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ColumnProp;
import fr.sg.fmk.dto.DatatablesRequest;
import fr.sg.fmk.dto.Unicity;
import fr.sg.fmk.exception.BusinessCode;
import fr.sg.fmk.exception.BusinessException;
import fr.sg.fmk.repository.FmkRepository;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.MessageManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * Implémentation des services usuels
 *
 * @author jntakpe
 */
@Service
public abstract class GenericServiceImpl<T extends GenericDomain> implements GenericService<T> {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    protected MessageManager messageManager;

    /**
     * Renvoi le repository à utiliser pour requêter la base de données
     *
     * @return le repository à utiliser
     */
    public abstract FmkRepository<T> getRepository();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return getRepository().count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T> page(DatatablesRequest datatablesRequest) {
        return getRepository().findAll(buildFilterQuery(datatablesRequest), buildPageRequest(datatablesRequest));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return getRepository().exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        getRepository().delete(id);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    @Transactional
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T save(T entity) {
        return getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isAvaillable(Unicity unicity) {
        Class<T> domainClass = getDomainClass();
        String fieldName = unicity.getField();
        Field field = ReflectionUtils.findField(domainClass, fieldName);
        if (field == null) throw createBussinessException(BusinessCode.ENTITY_FIELD_MISSING, fieldName, domainClass);
        Class<?> fieldClass = ReflectionUtils.findField(getDomainClass(), fieldName).getType();
        String upperName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class<? extends CrudRepository> repo = getRepository().getClass();
        String name = "findBy" + upperName;
        String nameIC = "findBy" + upperName + "IgnoreCase";
        Method method = ReflectionUtils.findMethod(repo, name, fieldClass);
        if (method == null && (method = ReflectionUtils.findMethod(repo, nameIC, fieldClass)) == null)
            throw createBussinessException(BusinessCode.REPOSITORY_METHOD_MISSING, fieldName, repo, name, nameIC);
        T entity = (T) ReflectionUtils.invokeMethod(method, getRepository(), unicity.getValue());
        return entity == null || entity.getId().equals(unicity.getId());
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public BusinessException createBussinessException(BusinessCode code, Object... errorParams) {
        return new BusinessException(messageManager.getMessage(code, errorParams), code);
    }


    /**
     * Construit un prédicat de filtrage l'aide des paramètres envoyés par DataTables.
     * Par défaut recherche pour chaque champ de recherche renseigné une requête de type :
     * SELECT * FROM table WHERE UPPER(champ1) LIKE('%' + valeur1 + '%') AND UPPER(champ2) LIKE('%' + valeur2 + '%').
     * Doit être étendue si la recherche par défaut ne convient pas.
     *
     * @param datatablesRequest état de la liste DataTables
     * @return predicat contenant les termes de la recherche
     * @see <a href="http://www.querydsl.com/static/querydsl/2.1.0/reference/html/ch03.html">QueryDSL doc</a>
     */
    protected Predicate buildFilterQuery(DatatablesRequest datatablesRequest) {
        Class<T> domainClass = getDomainClass();
        PathBuilder<T> pathBuilder = new PathBuilder<T>(domainClass, domainClass.getSimpleName().toLowerCase());
        BooleanBuilder builder = new BooleanBuilder();
        for (ColumnProp prop : datatablesRequest.getColumnProps()) {
            if (StringUtils.isNotBlank(prop.getSearch()))
                builder.and(pathBuilder.getString(prop.getName()).containsIgnoreCase(prop.getSearch()));
        }
        return builder;
    }

    /**
     * Construit un objet utilisé pour faire une requête de pagination et de tri.
     *
     * @param dr état de la liste DataTables
     * @return Objet contenant les informations de pagination
     * @see <a href="http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html#web-pagination">Web pagination</a>
     */
    private Pageable buildPageRequest(DatatablesRequest dr) {
        int pageNumber = dr.getDisplayStart() / dr.getDisplaySize();
        int size = dr.getDisplaySize();
        if (dr.hasSortedColumn()) return new PageRequest(pageNumber, size, resolveSort(dr.getColumnProps()));
        else return new PageRequest(pageNumber, size);
    }

    /**
     * Renvoi un objet contenant les propriétés de tri des colonnes
     *
     * @param columnProps propriétés de chaque colonne
     * @return propriétés de tri des colonnes
     */
    private Sort resolveSort(List<ColumnProp> columnProps) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for (ColumnProp columnProp : columnProps)
            if (columnProp.isSorted()) orders.add(new Sort.Order(columnProp.getSortDirection(), columnProp.getName()));
        return new Sort(orders);
    }

    /**
     * Méthode renvoyant l'entité de la couche domain/model
     *
     * @return ressource utilisée par le contrôlleur
     */
    private Class<T> getDomainClass() {
        ParameterizedType genericSuperclass = ((ParameterizedType) this.getClass().getGenericSuperclass());
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

}
