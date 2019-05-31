package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class PerformanceTest {

	//String basePath = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/";
	String graphPath = "C:\\Users\\lehob\\Documents\\gaby/BE_graphe-master/";
	String[] graphNames = { "haute-garonne" };
	List<ArcInspector> filters = ArcInspectorFactory.getAllFilters();

	//comparaison A* et Dijkstra avec seulement la carte du Chili (car assez grande pour faire de nombreux trajets diff�rents)
	public void testPerf(int filter, String nomFichier) throws IOException{
		List<Graph> graphs = new ArrayList<>();
		GraphReader reader;
		for(String path : graphNames) {
			reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphPath + path + ".mapgr"))));

			graphs.add(reader.read());
		}

		ArrayList<Integer> origins = new ArrayList<Integer>(); 
		ArrayList<Integer> destinations = new ArrayList<Integer>();

		//on r�cup�re des points dans le fichier points.txt
		LireFichier read = new LireFichier(origins, destinations); 
		read.Lecture("points.txt");

		ArrayList<Resultat> resultats= new ArrayList<Resultat>(); 
		ShortestPathSolution sol;
		DijkstraAlgorithm dij;
		AStarAlgorithm ast; 
		ShortestPathData data;
		float length; 
		long t1, t2; 

		for (int i = 0; i<origins.size(); i++) {
			length = 0.0f; 
			//avec la carte du chili 
			data = new ShortestPathData (graphs.get(0),graphs.get(0).get(origins.get(i)),graphs.get(0).get(destinations.get(i)), filters.get(filter));

			//calcul du temps d'�xecution de dijkstra
			dij = new DijkstraAlgorithm(data); 
			t1=System.nanoTime(); 
			sol=dij.doRun(); 
			t2=System.nanoTime()-t1; 

			//verification de l'existence d'un chemin 
			if (sol.isFeasible()) {
				length = sol.getPath().getLength(); 
			}
			//cr�ation d'un objet resultat pour stocker les informations du test
			Resultat res = new Resultat(origins.get(i), destinations.get(i), length ,t2,dij.getNbSommets(),t2,dij.getNbSommets());

			//calcul du temps d'execution pour A*
			ast = new AStarAlgorithm(data); 
			t1=System.nanoTime(); 
			ast.doRun(); 
			t2=System.nanoTime()-t1; 

			//on complete les valeurs manquantes
			res.setNbSommetsAStar(ast.getNbSommets());
			res.setTempsAStar(t2);

			//ajout des resultats de ce test � l'ArrayList de tous les resultats
			resultats.add(res); 
		}
		//une fois tous les test effectu�s, on ecrit les informations dans notre fichier resultats
		EcrireFichier ecrire = new EcrireFichier(nomFichier, resultats); 
	}
	@Test 
	public void allTests() throws IOException{
		//on fait les tests de performance en temps et en distance 
		testPerf(0, "resultats_distance.csv" ); 
		testPerf(2, "resultats_temps.csv"); 
	}
}


