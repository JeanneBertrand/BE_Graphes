package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	private int nbSommets;
	private static int nbInstance=0; 
	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
		this.nbSommets=0; 
	}

	
	protected ShortestPathSolution createSolution(Label[] labels, ShortestPathData data) {
		ShortestPathSolution solution = null;
		// Destination has no predecessor, the solution is infeasible...
		if (labels[data.getDestination().getId()].getPere()==null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		else {

			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());

			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = labels[data.getDestination().getId()].getPere();
			while (arc != null) {
				arcs.add(arc);
				arc = labels[arc.getOrigin().getId()].getPere();
			}
			// Reverse the path...
			Collections.reverse(arcs);
			
			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));

		}
		nbInstance++; 
		System.out.println("instance numéro : "+nbInstance);
		return solution;
	}
	
	
	protected Label[] Init(ShortestPathData data, BinaryHeap<Label> tas, int nbNodes) {
		Label[] labels=new Label[nbNodes];
		//initialisation du tableau de Label
		int i;
		for(i=0;i<nbNodes;i++) {
			labels[i]=new Label(data.getGraph().get(i));
		}
		//on met d'abord tout les couts à 0 et on insère le Label origin dns le tas
		int idorigin=data.getOrigin().getId(); 
		labels[idorigin].setCost(0); 
		tas.insert(labels[idorigin]); 
		notifyOriginProcessed(data.getOrigin());
		return labels;
	}
	
	protected void chooseArc(Label courant, Arc currentSuc,BinaryHeap<Label> tas, Label[] labels) {
		int idcurrentSuc=currentSuc.getDestination().getId();
		Label labelCurrentSuc = labels[idcurrentSuc];
		if(!labelCurrentSuc.isMarque()) {
			notifyNodeReached(currentSuc.getDestination());
			
			//incrémentation du nombre de sommets qui est utilisé dans les tests de performance
			this.nbSommets++; 
			
			//pour chaque successeur on regarde si le cout du sucesseur est supérieur au cout du Label courant + le cout du successeur
			if (labelCurrentSuc.getCost() > (courant.getCost()+data.getCost(currentSuc))) {
				labelCurrentSuc.setCost(courant.getCost()+data.getCost(currentSuc));
				tas.insert(labelCurrentSuc);
				labelCurrentSuc.setPere(currentSuc);
			}
		}
	}
	
	public int getNbSommets() {
		return nbSommets;
	}


	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets;
	}


	@Override
	protected ShortestPathSolution doRun() {
		
		ShortestPathData data = getInputData();
		BinaryHeap<Label> tas=new BinaryHeap<Label>();
		int nbNodes=data.getGraph().size();
		
		Label[] labels = Init(data, tas, nbNodes);

		boolean isReached = false;
		Label courant;
		List<Arc> succesors = new ArrayList<Arc>();

		//on s'arrête quand la destination est atteinte ou quand le tas est vide, c'est-à-dire quand il n'y a plus de noeuds à explorer
		//(il peut en rester dans le graphe si elle n'est pas connexe)
		
		while(!isReached && !tas.isEmpty()) {
			if (!isReached) {
				courant=tas.deleteMin();
				courant.setMarque(true);
				Node nodeCourant =courant.getSommetCourant();
				if (nodeCourant==data.getDestination()) {
					isReached=true;
				} else {
					notifyNodeMarked(nodeCourant);
					succesors=nodeCourant.getSuccessors();
					for (Arc currentSuc : succesors) {
						if (!data.isAllowed(currentSuc))
							continue;
						chooseArc(courant, currentSuc, tas, labels);
					}
				}
			}
		}
		
		return createSolution(labels, data);
	}

}
