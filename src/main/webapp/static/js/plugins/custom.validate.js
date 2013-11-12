/**
 * Modifications de jQuery validation
 */

/**
 * Objet envoyé lors d'une validation de contrainte d'unicité
 * @param field champ auquel s'applique la contrainte d'unicité
 * @param id identifiant de l'entité
 * @param value valeur du champ surlequel s'applique la contrainte d'unicité
 */
function ValidUnicity(field, id, value) {
    "use strict";
    this.field = field;
    this.id = id;
    this.value = value;
}

/** Modification pour le plugin jQuery validation afin de respecter le theme Bootstrap */
$.validator.setDefaults({
    errorClass: 'help-block',
    errorElement: 'span',
    validClass: 'has-success',
    highlight: function (element, errorClass, validClass) {
        "use strict";
        var $element;
        if (element.type === 'radio') {
            $element = this.findByName(element.name);
        } else {
            $element = $(element);
        }
        $element.parents("div.form-group").addClass("has-error");
    },
    unhighlight: function (element, errorClass, validClass) {
        "use strict";
        var $element;
        if (element.type === 'radio') {
            $element = this.findByName(element.name);
        } else {
            $element = $(element);
        }
        $element.removeClass(errorClass).addClass(validClass);

        if ($element.parents("div.form-group").find(errorClass).length === 0) {
            $element.parents("div.form-group").removeClass("has-error");
        }
    }
});

/** Ajout d'une méthode regex */
jQuery.validator.addMethod("regex", function (value, element, param) {
    "use strict";
    if (this.optional(element)) {
        return true;
    }
    if (typeof param === 'string') {
        param = new RegExp('^(?:' + param + ')$');
    }
    return param.test(value);
}, "Format invalide.");


/** Ajout d'une méthode pour les contrôles d'unicité */
$.validator.addMethod("unicity", function (value, element, param) {
    "use strict";
    var loc, pathname, args, url, root, module, endModule;
    pathname = window.location.pathname.substring(1);
    loc = param === true ? "control" : param;
    if (pathname.match(/\/$/)) {
        pathname = pathname.substring(pathname.length - 1);
    }
    root = pathname.substring(0, pathname.indexOf("/"));
    if ($.isNumeric(pathname.substring(pathname.lastIndexOf("/")))) {
        endModule = pathname.lastIndexOf("/");
    } else {
        endModule = pathname.length;
    }
    module = pathname.substring(pathname.indexOf("/"), endModule);
    url = "/" + root + module + "/" + loc;
    args = {
        url: param === true ? url : param,
        data: new ValidUnicity(element, $(element).closest('form').find("input[name='id']").val(), value)
    };
    $.validator.messages.remote = "Contrainte d'unicité non respectée, veuillez modifier cette valeur.";
    return $.validator.methods.remote.call(this, value, element, args);
}, "Contrainte d'unicité non respectée, veuillez modifier cette valeur.");

/** Message de validation en francais */
$.extend($.validator.messages, {
    required: "Ce champ est obligatoire.",
    remote: "Veuillez corriger ce champ.",
    email: "Veuillez fournir une adresse électronique valide.",
    url: "Veuillez fournir une adresse URL valide.",
    date: "Veuillez fournir une date valide.",
    dateISO: "Veuillez fournir une date valide (ISO).",
    number: "Veuillez fournir un numéro valide.",
    digits: "Veuillez fournir seulement des chiffres.",
    creditcard: "Veuillez fournir un numéro de carte de crédit valide.",
    equalTo: "Veuillez fournir encore la même valeur.",
    accept: "Veuillez fournir une valeur avec une extension valide.",
    maxlength: $.validator.format("Veuillez fournir au plus {0} caractères."),
    minlength: $.validator.format("Veuillez fournir au moins {0} caractères."),
    rangelength: $.validator.format("Veuillez fournir une valeur qui contient entre {0} et {1} caractères."),
    range: $.validator.format("Veuillez fournir une valeur entre {0} et {1}."),
    max: $.validator.format("Veuillez fournir une valeur inférieur ou égal à {0}."),
    min: $.validator.format("Veuillez fournir une valeur supérieur ou égal à {0}."),
    maxWords: $.validator.format("Veuillez fournir au plus {0} mots."),
    minWords: $.validator.format("Veuillez fournir au moins {0} mots."),
    rangeWords: $.validator.format("Veuillez fournir entre {0} et {1} mots."),
    letterswithbasicpunc: "Veuillez fournir seulement des lettres et des signes de ponctuation.",
    alphanumeric: "Veuillez fournir seulement des lettres, nombres, espaces et soulignages",
    lettersonly: "Veuillez fournir seulement des lettres.",
    nowhitespace: "Veuillez ne pas inscrire d'espaces blancs.",
    ziprange: "Veuillez fournir un code postal entre 902xx-xxxx et 905-xx-xxxx.",
    integer: "Veuillez fournir un nombre non décimal qui est positif ou négatif.",
    vinUS: "Veuillez fournir un numéro d'identification du véhicule (VIN).",
    dateITA: "Veuillez fournir une date valide.",
    time: "Veuillez fournir une heure valide entre 00:00 et 23:59.",
    phoneUS: "Veuillez fournir un numéro de téléphone valide.",
    phoneUK: "Veuillez fournir un numéro de téléphone valide.",
    mobileUK: "Veuillez fournir un numéro de téléphone mobile valide.",
    strippedminlength: $.validator.format("Veuillez fournir au moins {0} caractères."),
    email2: "Veuillez fournir une adresse électronique valide.",
    url2: "Veuillez fournir une adresse URL valide.",
    creditcardtypes: "Veuillez fournir un numéro de carte de crédit valide.",
    ipv4: "Veuillez fournir une adresse IP v4 valide.",
    ipv6: "Veuillez fournir une adresse IP v6 valide.",
    require_from_group: "Veuillez fournir au moins {0} de ces champs."
});
