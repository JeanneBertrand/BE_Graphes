package org.insa.graph;

import org.insa.algo.AbstractInputData;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label> {

	private double costDestination;
	
	public LabelStar(Node node, ShortestPathData data) {
		super(node);
		
		//on rajoute le cout à vol d'oiseau jusqu'à la destination
		Double dist = Point.distance(this.getSommetCourant().getPoint(),data.getDestination().getPoint());
		
		//si l'algo est lancé en mode temps, le cout à la destination correspond au temps de trajet
		if(data.getMode()==AbstractInputData.Mode.TIME ) {
			int maxSpeed = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed());
			this.costDestination = dist/((double) maxSpeed/3.6);
		} else {
		this.costDestination= dist;
		}
	}
	
	//on redéfinit getTotalCost() 
	public double getTotalCost() {
		return  (this.getCost() + this.costDestination);
	}
}

