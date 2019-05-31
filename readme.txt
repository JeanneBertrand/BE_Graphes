Les tests de ce projet utilisent des fichiers contenus dans ce dossier : le fichier points.txt, les cartes et les fichiers resultats-temps.csv et resultats_distance.csv
Il faut donc modifier les chemins d'accès dans la classe PerformanceTest, LireFichier et AlgorithmTestValidite. 

Les entiers du fichier points.txt doivent aussi être modifié en cas de changement de carte (et le nom de la carte doit être modifié dans la classe PerformanceTest). 
On peut par exemple générer des entiers aléatoires sur random.org - integer generator. Les entiers doivent être générés entre 0 et le nombre de node- qui est fourni sur le site de téléchargement des cartes. 

Attention à supprimer les excels générés dans le dossier src/test/ en cas de Rerun des tests. 