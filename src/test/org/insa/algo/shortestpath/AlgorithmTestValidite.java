package org.insa.algo.shortestpath;


import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlgorithmTestValidite {

	List<ArcInspector> filters = ArcInspectorFactory.getAllFilters();

	//String basePath = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/";
	String graphPath = "C:\\\\Users\\\\lehob\\\\Documents\\\\gaby/BE_graphe-master/";
	String[] graphNames = { "insa" };

	public void subTest(Graph graph, int origin, int dest, boolean feasible) {
		ShortestPathSolution solDij;
		ShortestPathSolution solBel;
		ShortestPathSolution solAst;
		DijkstraAlgorithm dij;
		AStarAlgorithm ast; 
		BellmanFordAlgorithm bel;
		ShortestPathData data;

		//une boucle pour tester les deux filtres, shortest path et fastest path (all roads allowed)
		for (int i=0; i<2; i=+2) {
			data = new ShortestPathData (graph,graph.get(origin),graph.get(dest), filters.get(i));
			dij = new DijkstraAlgorithm(data);
			ast = new AStarAlgorithm(data);
			solDij=dij.run(); 
			solAst=ast.run(); 
			if (feasible) {
				bel = new BellmanFordAlgorithm(data);
				solBel = bel.run();
				assertEquals(solDij.getPath(),solBel.getPath());
				assertEquals(solAst.getPath(),solBel.getPath());
			}else {
				assertEquals(solDij.getStatus(), Status.INFEASIBLE);
				assertEquals(solAst.getStatus(), Status.INFEASIBLE);
			}
		}
	}

	@Test
	public void testCartes () throws IOException {
		
		//on récupère le graphe sur lequel effectuer les tests
		List<Graph> graphs = new ArrayList<>();
		GraphReader reader;
		for(String path : graphNames) {
			reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphPath + path + ".mapgr"))));
			graphs.add(reader.read());
		}
		//test avec carte insa
		subTest(graphs.get(0),272,1160,true);
		subTest(graphs.get(0),1037,1306,false);
		subTest(graphs.get(0),301,301,false);
	}
	
	
	
	
	
	
	
	
	



}
