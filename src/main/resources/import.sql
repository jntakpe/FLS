-- Fichier chargé au démarrage du serveur si JPA est configuré en mode create,create-drop ou update
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (1, 0, 'default.timeout', 'N', 'Durée d''affichage des alertes', '10000');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (2, 0, 'default.date.format', 'X', 'Format date par défaut', 'dd/MM/yyyy');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (3, 0, 'default.success.message', 'X', 'Message de succés par défaut', 'Opération effectuée.');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (4, 0, 'default.error.message', 'X', 'Message d''erreur par défaut', 'Une erreur inconnue est survenue.');