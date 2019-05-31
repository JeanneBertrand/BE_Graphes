package org.insa.algo.shortestpath;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
		super(data);
	}

	//on red�finit l'initialisation pour ajouter des LabelStar � notre tableau et plus des Label
	@Override
	protected Label[] Init(ShortestPathData data, BinaryHeap<Label> tas, int nbNodes) {
		LabelStar[] labelStars=new LabelStar[nbNodes];

		int i;
		for(i=0;i<nbNodes;i++) {
			labelStars[i]=new LabelStar(data.getGraph().get(i),data);
			
		}
		int idorigin=data.getOrigin().getId(); 
		labelStars[idorigin].setCost(0);
		tas.insert(labelStars[idorigin]); 
		notifyOriginProcessed(data.getOrigin());
		
		
		return labelStars;
	}



}
