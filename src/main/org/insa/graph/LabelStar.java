package org.insa.graph;

import org.insa.algo.AbstractInputData;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label> {

	private double costDestination;
	
	public LabelStar(Node node, ShortestPathData data) {
		super(node);
		
		//on rajoute le cout � vol d'oiseau jusqu'� la destination
		Double dist = Point.distance(this.getSommetCourant().getPoint(),data.getDestination().getPoint());
		
		//si l'algo est lanc� en mode temps, le cout � la destination correspond au temps de trajet
		if(data.getMode()==AbstractInputData.Mode.TIME ) {
			int maxSpeed = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed());
			this.costDestination = dist/((double) maxSpeed/3.6);
		} else {
		this.costDestination= dist;
		}
	}
	
	//on red�finit getTotalCost() 
	public double getTotalCost() {
		return  (this.getCost() + this.costDestination);
	}
}

