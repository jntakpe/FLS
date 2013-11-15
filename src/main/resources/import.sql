-- Fichier charg� au d�marrage du serveur si JPA est configur� en mode create,create-drop ou update
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (1, 0, 'default.timeout', 'N', 'Dur�e d''affichage des alertes', '10000');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (2, 0, 'default.date.format', 'X', 'Format date par d�faut', 'dd/MM/yyyy');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (3, 0, 'default.success.message', 'X', 'Message de succ�s par d�faut', 'Op�ration effectu�e.');
INSERT INTO public.parameter (id, version, code, format, label, value) VALUES (4, 0, 'default.error.message', 'X', 'Message d''erreur par d�faut', 'Une erreur inconnue est survenue.');