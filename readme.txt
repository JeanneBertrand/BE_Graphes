Les tests de ce projet utilisent des fichiers contenus dans ce dossier : le fichier points.txt, les cartes et les fichiers resultats-temps.csv et resultats_distance.csv
Il faut donc modifier les chemins d'acc�s dans la classe PerformanceTest, LireFichier et AlgorithmTestValidite. 

Les entiers du fichier points.txt doivent aussi �tre modifi� en cas de changement de carte (et le nom de la carte doit �tre modifi� dans la classe PerformanceTest). 
On peut par exemple g�n�rer des entiers al�atoires sur random.org - integer generator. Les entiers doivent �tre g�n�r�s entre 0 et le nombre de node- qui est fourni sur le site de t�l�chargement des cartes. 

Attention � supprimer les excels g�n�r�s dans le dossier src/test/ en cas de Rerun des tests. 