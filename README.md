# Projet IMA4 : Sextoy connecté

### Groupe : 
STIEVENARD Thomas

ROUSSEL Cédric

## Partie arduino

Se placer dans le dossier du sextoy correspondant et utiliser soit make upload, soit make debug.

Make debug remplace la liaison série effectuée avec le module bluetooth par une liaison avec l'ordinateur permettant d'utiliser la fonction printf.

Veuillez noter que le prototype correspondant au pénis ne marche qu'en mode debug car le module bluetooth utilisé n'est pas fonctionnel.

Une fois le code téléversé sur l'arduino, en branchant le prototype sur secteur, il ne reste plus qu'à suivre les instructions de la partie Android.

## Partie Android

Il y a deux dossier disponible pour la partie Android, un comportant seulement les codes, fichier.xml .png et le manifest, et un dossier comportant tout ce qui est necessaire pour compiler

Afin de compiler l'application Android, ouvrez Android studio, cliquer sur ouvrir, choisissez "ApplicationAndroid dans le bon répertoire.

Android studio va ouvrir le projet, ensuite cliquez sur run, ou faîtes maj+F10, pour compiler l'application sur un téléphone en mode développeur. L'application se lancera automatiquement sur le téléphone

Il sera surement nécessaires de mettre à jours certains fichiers d'Android studio pour pouvoir compiler.

<p>Une fois l'application compilée sur le téléphone :

Cliquez sur contact.

cliquez sur un contact, ou sur le bouton '+' pour en ajouter.

Une fois un contact choisi, vous pouvez lancer une communication UDP avec le bouton 'téléphone'
, envoyer un sms avec le bouton 'message' ou suprrimer le contact avec le bouton 'croix'

Si vous lancez une communication UDP, appuyez sur le '-' pour ne pas utiliser de périphérique bluetooth
, le bouton 'croix' pour quitter la page, et le bouton 'PAIRED DEVICES' pour afficher la liste de périphériques appairés sur le téléphone.</p>

<p>En cliquant sur un le nom d'un de ces périphériques vous pouvez lancer la connection avec. Si la connecion est effectuée vous entrez sur une page pour gérée la connection UDP

En cliquant sur 'Désactivé' le bouton affiche 'Activé' et lance la communication bluetooth.

En cliquant sur 'croix' couper toutes les communications (Bluetooth et UDP)</p>


Si vous avez cliquez sur le '-' pour ne pas utiliser de périphérique bluetooth, la page lancée disposera du même bouton 'Désactivé/Activé'
, mais aussi de quatre boutons permettant de choisr le niveau d'excitation envoyé à l'autre utilisateur. 

