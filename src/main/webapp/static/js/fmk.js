//Constructeur d'attributs pour les tables
function TableAttrs(field, defaultVal) {
    "use strict";
    this.field = field;
    this.defaultVal = defaultVal;
}
//Constructeur contenant les informations de la ligne courante
function RowInfos(rowId, row) {
    "use strict";
    this.rowId = rowId;
    this.row = row;
}

//Scope du framework
var fmk = {

    //Timeout courant
    currentTimeout: 0,

    //Racine du context web
    contextRoot: null,

    //Paramètres mis en cache (webStorage)
    parameters: {
        //Durée d'affichage des popups par défaut
        defaultTimeout: "default.timeout",
        //Format date par défaut
        defaultDateFormat: "default.date.format",
        //Message de succès par défaut
        defaultSuccessMessage: "default.success.message",
        //Message d'erreur par défaut
        defaultErrorMessage: "default.error.message"
    },

    //Attributs d'une table et leurs valeurs par défaut
    tableAttributes: {
        //Indique si les données doivent être rechargées après chaque modification ou suppression
        reload: new TableAttrs("reload", false),
        //Indique l'id de la popup permettant d'éditer la table
        editPopupId: new TableAttrs("edit-popup", "popup"),
        /**
         * base de l'uri afin de pouvoir appeler un contrôleur différent pour l'édition. Par exemple, si notre url
         * courante est '/fmk/param' et que nous souhaitons appeler l'url '/fmk/toto', la valeur doit être 'toto'
         */
        editUri: new TableAttrs("edit-uri", window.location.pathname),
        /**
         * base de l'uri afin de pouvoir appeler un contrôleur différent la suppression. Par exemple, si notre url
         * courante est '/fmk/param' et que nous souhaitons appeler l'url '/fmk/toto', la valeur doit être 'toto'.
         * Le message de confirmation de suppression sera également appelé à l'aide de cette uri
         */
        deleteUri: new TableAttrs("delete-uri", window.location.pathname)
    },

    /**
     * Récupère la valeur d'un élément mis en cache
     * @param code élément à récupérer en cache
     * @returns {*}
     */
    getParamValue: function (code) {
        "use strict";
        var ctx, path, param, params = [], result;
        if (sessionStorage.length === 0) { //webStorage non initialisé
            path = window.location.pathname;
            ctx = path.substring(0, path.substring(1).indexOf("/") + 1); //récupération du context root
            for (param in fmk.parameters) { //Création d'un tableau de paramètres
                if (fmk.parameters.hasOwnProperty(param)) {
                    params.push(fmk.parameters[param]);
                }
            }
            $.ajax({ //chargement du cache
                url: ctx + '/param/cache',
                data: {codes: params},
                traditional: true,
                async: false
            })
                .done(function (response) { //reponse contenant les valeurs du cache
                    var code;
                    if (response.success) {
                        for (code in response.data) { //Pour chaque paramètre
                            if (response.data.hasOwnProperty(code)) {
                                sessionStorage.setItem(code, response.data[code]); //Sauvegarde du paramètre
                            }
                        }
                    } else {
                        fmk.displayError(response);
                    }
                });
        }
        result = sessionStorage.getItem(code); //Récupération du paramètre
        if (result !== null) {
            return result;
        }
        throw "Le paramètre '" + code + "' est absent du cache";
    },

    /**
     * Renvoi la valeur d'un attribut de la table et si il existe sinon sa valeur par défaut
     * @param $table objet jQuery représentant la table
     * @param attr TableAttrs contenant le champ à rechercher et sa valeur par défaut
     * @returns {*} valeur de l'attribut
     */
    getTableAttr: function ($table, attr) {
        "use strict";
        if (!attr) {
            throw "Attribut inconnu";
        }
        if (!attr.field) {
            throw "Le paramètre de la méthode est l'objet 'TableAttrs' est non la valeur de son attribut 'field'.";
        }
        var res = $table.data(attr.field);
        return res === undefined ? attr.defaultVal : res;
    },


    /**
     * Affiche un message de succès dans le bandeau
     * @param [message] à afficher
     */
    displaySuccess: function (message) {
        "use strict";
        var alertDiv = $("#alert"), alertIcon = $('#alert-icon'), alertMessage = $('#alert-message');
        if (!message) {
            message = fmk.getParamValue(fmk.parameters.defaultSuccessMessage); //Récupération du message
        }
        clearTimeout(fmk.currentTimeout);
        alertMessage.text(message);
        if (alertDiv.hasClass('alert-danger')) {
            alertDiv.removeClass('alert-danger');
        }
        if (!alertDiv.hasClass('alert-success')) {
            alertDiv.addClass('alert-success');
        }
        if (alertIcon.hasClass('fa-warning')) {
            alertIcon.removeClass('fa fa-warning');
        }
        if (!alertIcon.hasClass('fa-check')) {
            alertIcon.addClass('fa fa-check');
        }
        alertDiv.addClass('in'); //Fait apparaitre l'alert
        fmk.currentTimeout = window.setTimeout(function () { //Fait disparaitre l'alerte au bout d'un certain temps
            alertDiv.removeClass('in');
        }, fmk.getParamValue(fmk.parameters.defaultTimeout));
    },

    /**
     * Affiche un message d'erreur dans le bandeau
     * @param [response] ResponseMessage contenant le message
     */
    displayError: function (response) {
        "use strict";
        var alertDiv = $("#alert"), alertIcon = $('#alert-icon'), alertMessage = $('#alert-message'), message;
        if (response) {
            if (response.message) {
                message = response.message; //Récupération du message
            } else {
                message = fmk.getParamValue(fmk.parameters.defaultErrorMessage);
            }
        } else {
            message = fmk.getParamValue(fmk.parameters.defaultErrorMessage); //Si pas de message on prend le default
        }
        clearTimeout(fmk.currentTimeout);
        alertMessage.text(message);
        if (alertDiv.hasClass('alert-success')) {
            alertDiv.removeClass('alert-success');
        }
        if (!alertDiv.hasClass('alert-danger')) {
            alertDiv.addClass('alert-danger');
        }
        if (alertIcon.hasClass('fa-check')) {
            alertIcon.removeClass('fa fa-check');
        }
        if (!alertIcon.hasClass('fa-warning')) {
            alertIcon.addClass('fa fa-warning');
        }
        alertDiv.addClass('in'); //Affichage de l'alerte
    },

    /**
     * Ajoute l'identifiant des formulaires aux requêtes de contrôle 'remote' de jQuery validation.
     * @param $event évennement
     * @param url de la fonction de contrôle
     * @param [id] identifiant représentant l'id de l'objet à contrôler
     * @returns {{url: *, data: {id: (*|jQuery)}}}
     */
    control: function ($event, url, id) {
        "use strict";
        var domainIdVal = id ? $('#' + id).val() : $event.closest('form').find("input[name='id']").val();
        if (!domainIdVal.length) {
            throw "L'identifiant du formulaire '" + id ? id : 'id' + "' est introuvable.";
        }
        return {
            url: url,
            data: {
                id: domainIdVal
            }
        };
    },

    /**
     * Créée une table gérée par le framework puis stocke une référence de la table en avec jQuery.data.
     * Peut aussi initialiser la validation de la popup.
     * @param id identifiant de la table
     * @param dtOptions paramètres de la table
     * @param [validRules] paramètres de validation de la popup
     */
    createTable: function (id, dtOptions, validRules) {
        "use strict";
        var $popup, dataTable, $popupForm, $popupSubmit, $table = $('#' + id);
        if (!$table.length) {
            throw "L'identifiant de la table est incorrect.";
        }
        if (dtOptions.bServerSide && !dtOptions.sAjaxDataProp) {
            dtOptions.sAjaxDataProp = "aaData"; //En cas de server-side, on wrap les données dans le champ aaData
        }
        dataTable = $table.dataTable(dtOptions); //Création de la dataTables
        $popup = $('#' + fmk.getTableAttr($table, fmk.tableAttributes.editPopupId));
        if ($popup.length) { //Si les règles de validation existent
            $popupForm = $popup.find('form');
            $popupSubmit = $popup.find('.modal-footer :submit');
            if (!$popupSubmit.attr('form')) {
                $popupSubmit.attr('form', $popupForm.attr('id'));
            }
            if ($popupForm.length === 1) {
                $popupForm.data('validForm', $popupForm.validate({ //Initialisation de la validation
                    submitHandler: function (form) {
                        fmk.saveRow(form, dataTable, $table);
                    },
                    rules: validRules
                }));
            } else {
                throw "Formulaire de la popup : '" + $popup.attr('id') + "' introuvable";
            }
        }
        $table.data('dt', dataTable); //On stocke une référence vers l'objet 'dataTable'
    },

    /**
     * Affiche le contenu d'une colonne de détail avec débranchement vers écran détail
     * @param [id] identifiant de la table (defaut = "id")
     * @param [width] taille de la colonne (defaut = 100)
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender:
     * Function}}
     */
    detailCol: function (id, width) {
        "use strict";
        return {
            mData: id || "id",
            sWidth: width || 100,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var path = window.location.pathname, detailUrl;
                detailUrl = path.match(/\/$/) ? path + data : path + "/" + data;
                return "<a href='" + detailUrl + "'><i class='fa fa-edit fa-lg'></i></a>";
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de modification avec débranchement vers une popup
     * @param [id] identifiant de la table (defaut = "id")
     * @param [width] taille de la colonne (defaut = 100)
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender:
     * Function}}
     */
    popupCol: function (id, width) {
        "use strict";
        return {
            mData: id || "id",
            sWidth: width || 100,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                return "<a href='javascript:;' class='edit-btn' onclick='fmk.displayEditPopup( " + data + ", $(this))'>"
                    + "<i class='fa fa-edit fa-lg'></i></a>";
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de suppression
     * @param [id] identifiant de la table (defaut = "id")
     * @param [width] taille de la colonne (defaut = 115)
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender:
      * Function}}
     */
    deleteCol: function (id, width) {
        "use strict";
        return {
            mData: id || "id",
            sWidth: width || 115,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var fct = "fmk.displayConfirmPopup(" + data + ", $(this))";
                return "<a href='javascript:;' onclick='" + fct + "'><i class='fa fa-trash-o fa-lg'></i></a>";
            }
        };
    },

    /**
     * Affiche la popup de confirmation d'une suppression de ligne
     * @param id identifiant de la ligne à supprimer
     * @param $event évennement
     */
    displayConfirmPopup: function (id, $event) {
        "use strict";
        var uri, $table = $event.closest("table[id^=dt_]"), $popup = $("#confirmDeletePopup"); //Récupération de la table
        if (!$table.length) {
            throw "La table est introuvable. Le nom d'une datatable doit commencer par 'dt_'.";
        }
        if (!$popup.length) {
            throw "La popup de confirmation n'est pas présente sur l'écran. Merci de l'importer.";
        }
        $popup.modal();
        uri = this.getTableAttr($table, this.tableAttributes.deleteUri);
        uri = uri.match(/\/$/) ? uri + id : uri + "/" + id;
        $.ajax(uri + '/message').
            done(function (response) { //Récupération du message à afficher dans la popup
                if (response.success) {
                    $('#delete-message').text(response.message);
                }
            });
        //Stockage des infos relatives à la ligne ayant déclenché l'événement
        $table.data("rowInfos", new RowInfos(id, $event.closest('tr')[0]));
        $('body').data("currentTable", $table.attr('id'));

    },

    /**
     * Supprime une ligne de la table
     */
    removeRow: function () {
        "use strict";
        var tableId = $('body').data('currentTable'), $table = $('#' + tableId), dataTable = $table.data('dt'),
            rowInfos = $table.data('rowInfos'), uri;
        uri = fmk.getTableAttr($table, fmk.tableAttributes.deleteUri);
        uri = uri.match(/\/$/) ? uri + rowInfos.rowId : uri + "/" + rowInfos.rowId;
        if (!dataTable.fnSettings().oInit.bServerSide) { //Appel AJAX
            $.ajax({
                type: 'DELETE',
                url: uri
            }).done(
                function (response) {
                    $('#confirmDeletePopup').modal('hide'); //On cache la popup de confirmation
                    if (response.success) {
                        fmk.displaySuccess(response.message);
                        if (fmk.getTableAttr($table, fmk.tableAttributes.reload)) { //Si les données sont rechargées
                            dataTable.fnReloadAjax(); //Rechargement des données
                        } else {
                            dataTable.fnDeleteRow(rowInfos.row); //Delete côté client
                        }
                    } else {
                        fmk.displayError(response);
                        dataTable.fnReloadAjax();
                    }
                }
            );
        } else { //Appel non-AJAX
            window.location = uri + "/delete";
        }
    },

    /**
     * Affiche la popup d'édition d'une ligne
     * @param id identifiant de la ligne
     * @param $event évennement ayant déclenché l'ouverture (click icone edit)
     */
    displayEditPopup: function (id, $event) {
        "use strict";
        var $table = $event.closest("table[id^=dt_]"), $popup, $newTitle, $editTitle, uri;
        $popup = $('#' + fmk.getTableAttr($table, fmk.tableAttributes.editPopupId));
        $newTitle = $popup.find('.new-title');
        $editTitle = $popup.find('.edit-title');
        if (!$popup.length) {
            throw "Popup introuvable";
        }
        $newTitle.hide();
        $editTitle.show();
        $popup.modal();
        uri = fmk.getTableAttr($table, fmk.tableAttributes.editUri);
        uri = uri.match(/\/$/) ? uri + id : uri + "/" + id;
        $.ajax({
            url: uri,
            contentType: "application/json"
        }).done(function (response) {
                fmk.populateForm($popup.find('form'), response);
                $table.data('rowInfos', new RowInfos(id, $event.closest('tr')[0]));
            });
    },

    /**
     * Ouvre une popup pour ajout d'une nouvelle ligne
     * @param $event évennement
     * @param [tableId] identifiant de la table
     */
    displayCreatePopup: function ($event, tableId) {
        "use strict";
        var $table, $popup, $newTitle, $editTitle;
        if (tableId) {
            $table = $('#' + tableId);
        } else {
            $table = $event.closest('.table-container').find('table[id^=dt_]');
            if (!$table.length) {
                $table = $event.parent('div').prev().find('table[id^=dt_]');
                if (!$table.length) {
                    $table = $event.closest('.row').children('table[id^=dt_]');
                }
            }
        }
        if (!$table.length) {
            throw "Table introuvable. Merci de spécifier un identifiant pour la table.";
        }
        $popup = $('#' + fmk.getTableAttr($table, fmk.tableAttributes.editPopupId));
        if (!$popup.length) {
            throw "Popup introuvable";
        }
        $newTitle = $popup.find('.new-title');
        $editTitle = $popup.find('.edit-title');
        $newTitle.hide();
        $editTitle.show();
        $popup.modal();
    },

    /**
     * Méthode de sauvegarde d'une ligne de la table
     * @param form formulaire de la popup
     * @param dataTable liste modifiée par la popup
     * @param $table table modifiée via la popup
     */
    saveRow: function (form, dataTable, $table) {
        "use strict";
        var rowInfos;
        if (!dataTable.fnSettings().oInit.bServerSide) { //Modifications en AJAX
            $(form).ajaxSubmit({
                type: 'put',
                success: function (response) {
                    $('#' + fmk.getTableAttr($table, fmk.tableAttributes.editPopupId)).modal('hide');
                    if (response.success) {
                        fmk.displaySuccess(response.message);
                        if (fmk.getTableAttr($table, fmk.tableAttributes.reload)) { //On recharge toutes les données
                            dataTable.fnReloadAjax();
                        } else { //On modifie juste la ligne concernée
                            if ($.isNumeric($(form).find("input[name='id']").val())) {
                                dataTable.fnDeleteRow($table.data('rowInfos').row);
                            }
                            dataTable.fnAddData(response.data);
                        }
                    } else {
                        fmk.displayError(response);
                    }
                }
            });
        } else { //Envoi du formulaire non-AJAX => rechargement complet de la page
            $(form).submit();
        }
    },

    /**
     * A partir d'un objet JSON rempli les champs d'un formulaire
     * @param $form objet jQuery représentant le formulaire
     * @param data objet JSON contenant les données
     */
    populateForm: function ($form, data) {
        $.each(data, function (field, value) {
            var $input = $form.find('[name=' + field + ']'), type = $input.attr("type");
            if (type === "radio" || type === "checkbox") {
                $input.each(function () {
                    if ($(this).attr('value') === value) {
                        $(this).attr("checked", value);
                    }
                });
            } else {
                $input.val(value);
            }
        });
    }
};

$(function () {
    "use strict";
    var $alert = $('#alert'), $wrapTable = $('table.wrap'), $autoSearch = $('.auto-search');

    /**
     * Gestion des recherches
     */
    function searchHandler() {
        var dataTable, tableId, $dt, $search = $(this).closest('.auto-search'), name = $(this).attr('name'), settings;
        if (!name) {
            throw "Impossible d'effectuer une recherche si l'attribut name de l'input n'est pas renseigné.";
        }
        tableId = $search.data('table-id');
        if (!tableId) { //Si le développeur n'a pas spécifié sur quelle table la recherche doit être effectuée
            $dt = $('table[id^=dt_]');
            if ($dt.length !== 1) {
                throw "Impossible de definir la table sur laquelle la recherche doit être effectuée.";
            }
            tableId = $dt.attr('id');
        }
        dataTable = $('#' + tableId).data('dt'); // Récupération de la dataTable
        if (!dataTable) {
            throw "DataTable introuvable. Aucune DataTable pour l'id : '" + tableId + "'.";
        }
        settings = dataTable.fnSettings();
        for (var i = 0, iLen = settings.aoColumns.length; i < iLen; i++) {
            if (settings.aoColumns[i].mData == name) {
                dataTable.fnFilter($(this).val(), i);
                break;
            }
        }
    }

    //Display les alertes déjà initialisées
    if ($alert.hasClass('alert-success')) {
        $alert.addClass('in');
        fmk.currentTimeout = window.setTimeout(function () { //Fait disparaitre l'alerte au bout d'un certain temps
            $alert.removeClass('in');
        }, fmk.getParamValue(fmk.parameters.defaultTimeout));
    }
    if ($alert.hasClass('alert-danger')) {
        $alert.addClass('in');
    }

    //Ajoute la classe 'wrap-cell' sur les cellules qui sont wrappées
    $wrapTable.on('mouseenter', 'td', function (e) {
        var target = e.target, $target = $(target);
        if (target.offsetWidth < target.scrollWidth) {
            $target.addClass('wrap-cell');
        }
    });

    //Gère le wrapping et unwrapping des cellules ayant la classe 'wrap-cell'
    $wrapTable.on('click', 'td.wrap-cell', function (e) {
        var target = e.target, $target = $(target);
        if ($target.hasClass('unwrap')) {
            $target.removeClass('unwrap');
        } else {
            $target.addClass('unwrap');
        }
    });

    //Lance la recherche sur keyup de champ texte
    $autoSearch.find('input').keyup(searchHandler);

    //Lance la recherche sur modification de select
    $autoSearch.find('select').change(searchHandler);

    //Recherche après click sur bouton
    $('.click-search').find('.trigger-search').click(function () {
        var dataTable, tableId, $dt, $search = $(this).closest('.click-search'), searchData = {};
        tableId = $search.data('table-id');
        if (!tableId) { //Si le développeur n'a pas spécifié sur quelle table la recherche doit être effectuée
            $dt = $('table[id^=dt_]');
            if ($dt.length !== 1) {
                throw "Impossible de definir la table sur laquelle la recherche doit être effectuée.";
            }
            tableId = $dt.attr('id');
        }
        dataTable = $('#' + tableId).data('dt'); // Récupération de la dataTable
        if (!dataTable) {
            throw "DataTable introuvable. Aucune DataTable pour l'id : '" + tableId + "'.";
        }
        $search.find('input , select').each(function () {
            var name = $(this).attr('name'), val = $(this).val();
            if (!name) {
                throw "Impossible d'effectuer une recherche si l'attribut name de l'input n'est pas renseigné.";
            }
            searchData[name] = val;
        });
        dataTable.fnMultiFilter(searchData);
    });

    $('.reset-search').click(function () {
        var dataTable, tableId, $dt, $search = $(this).closest('.click-search');
        tableId = $search.data('table-id');
        if (!tableId) { //Si le développeur n'a pas spécifié sur quelle table la recherche doit être effectuée
            $dt = $('table[id^=dt_]');
            if ($dt.length !== 1) {
                throw "Impossible de definir la table sur laquelle la recherche doit être effectuée";
            }
            tableId = $dt.attr('id');
        }
        dataTable = $('#' + tableId).data('dt'); // Récupération de la dataTable
        if (!dataTable) {
            throw "DataTable introuvable. Aucune DataTable pour l'id : '" + tableId + "'.";
        }
        dataTable.fnFilterClear();
    });

    //Fermeture des alertes après click sur 'close'
    $('#close-alert').click(function () {
        $(this).parent('.alert').removeClass('in');
    });

    //Callback de fermeture de la popup. On nettoie le formulaire.
    $('.modal.edit-popup').on('hide.bs.modal', function () {
        var $form = $(this).find('form');
        $form.data('validForm').resetForm(); //Nettoie le formulaire au niveau validation
        $form.find('.has-error').removeClass('has-error'); //Enlève les inputs en erreur
        $form.find(":hidden").val('');
    });

    //Gestion des erreurs centralisée
    $(document).ajaxError(function (event, xhr) {
        fmk.displayError(xhr['responseJSON']);
        $('.modal').modal('hide');
    });

});