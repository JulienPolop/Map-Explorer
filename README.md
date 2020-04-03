# Map-Explorer
Carte interractive permettant de visualiser des données sur le développement des pays du monde.

![Image: Carte colorée à partir d'un indicateur](https://github.com/JulienPolop/Map-Explorer/blob/master/images/Map%20Color%C3%A9e.PNG)

### Contexte

Ce projet a été réalisé dans le cadre d'un cours d'initiations aux IHM dispensé par un intervenant de chez MBDA.

C'est le projet final du cours, celui sur lequel nous étions évalué. Il a été réalisé en binôme sous java Swing.


Nous avions à notre disposition une base de donnée sous, forme de csv, d'indicateurs de développement pour chaque pays, issus du World Developpment Indicators (https://databank.worldbank.org/source/world-development-indicators) ainsi que des classes déjà implémentées construisant le modèle de donnée extrait du CSV.


### Préparation
Nous devions concevoir une IHM pour pouvoir visualiser et choisir facilement les indicateurs que l'utilisateur veut pouvoir explorer. Pour cela nous avons utilisés quelques techniques vu en cours tel que la création de personas, ou profils utilisateurs: Qui serait interressé par l'application? Quel type de métier? Que veut-il voir apparaitre à l'intérieur? Quel est son niveau en informatique?

Puis à partir de cela nous avons réalisé les spécifications de l'application, ainsi que des maquettes papiers pour cadrer ce que nous voulions faire. 


### Application
Pour le projet, nous sommes partis sur une interface claire, lisible et épurée, tout en étant pratique pour les utilisateurs. Elle est composée de deux grosses parties : la barre du haut, qui permet de rechercher les indicateurs et les informations que l’on veut afficher, et  la carte, prenant la majorité de l’espace, que l’on va colorer en fonction du filtre choisi.

Nous avons donc une application véritablement graphique où la carte est au centre de l’attention. Cela permet également d’éviter de rebuter certaines personnes qui ne veulent pas se perdre dans des menus interminables.


Pour rechercher un indicateur nous avons optés pour deux solutions, des menus déroulant pour chercher les indicateurs dans les catégories générales puis les sous catégories, et une barre de recherche avec autocomplétion.

Pour les premiers menus, il y a 9 boutons représentants les 9 catégories générales (il manque des icones représentatives des catégories dans les boutons), lorsque l'on clique dessus, un menu déroulant apparait présentant les sous catégories, qui une fois choisis présentent à leur tour les indicateurs choisissables.

Ainsi si l'on veut choisir "% de population de + de 65 ans", il faut cliquer sur le bouton "Santé", choisir la sous-catégorie "Général", puis choisir cet indicateur. L'autre solution est de l'entrer directement dans la barre de recherche, si l'on sait ce que l'on cherche.

![Image: Présentation des menus](https://github.com/JulienPolop/Map-Explorer/blob/master/images/Menu%20Deroulant.PNG)


