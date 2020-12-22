package Scrap;

import java.util.ArrayList;

import edu.princeton.cs.introcs.StdDraw;

public class PathFinder{

	private ArrayList<Point2D> path=new ArrayList<Point2D>();
	private DistanceMatrix distMat;
	private ArrayList<Point2D> shortestPath=new ArrayList<Point2D>();
	private boolean[] check;
	private double TotalDistance=0;
	private Point2D destination;
	private Point2D source;
	private Point2D[] pathArray=null;

	public void addPoints(Polygon2D pol){
		for(int i=0;i<pol.asPointsArray().length;i++){
			path.add(pol.asPointsArray()[i]);
		}
	}

	public void addSourcePoint(Point2D src){ 
		source=src;
		path.add(0, src);	
	}

	public void addDestinationPoint(Point2D dst){ 
		destination=dst;
		path.add(dst); 
	}

	public void calculateDistance(DistanceMatrix dist){
		for(int i=0;i<path.size();i++){
			dist.addPoint(path.toArray(new Point2D[]{})[i]);
		}
	}

	public double getTotalDistance(){ return TotalDistance; }

	public Point2D[] computePath(){
		double shortestDistance=Double.POSITIVE_INFINITY;
		int start, nextPoint=0;

		distMat= new DistanceMatrix(path.size());
		calculateDistance(distMat);

		check=new boolean[path.size()];


		for(int i=0;i<path.size()-1;i++){
			shortestPath.add(path.get(nextPoint));
			check[nextPoint]=true;
			start=nextPoint;
			double dist=0;
			
			for(int j=0;j<path.size();j++){
				if(!check[j]){
					dist=distMat.getDistance(start, j);
					if(dist <  shortestDistance){
						shortestDistance=dist;
						nextPoint=j;
					}
				}
				System.out.println(i+" I am: "+j);
			}

			TotalDistance += shortestDistance;
			shortestDistance=Double.POSITIVE_INFINITY;
			if(shortestPath.get(i).getX() == destination.getX() && shortestPath.get(i).getY() == destination.getY()){ break; }

		}
		System.out.println("break");
//		System.out.println(shortestPath.size());
//		int k=1;
//		while(k<shortestPath.size()){
//			if(Point2D.slope(shortestPath.get(k-1),shortestPath.get(k)) < 0.0){
//				shortestPath.remove(k);
//			}
//			System.out.println("while k: "+k);
//			k++;
//
//		}
		pathArray=shortestPath.toArray(new Point2D[]{});

		return pathArray;
	}

	public void draw(Point2D[] vertices) {
		for (int i=0; i<vertices.length-1; i++) {
			StdDraw.setPenRadius(0.005);
			StdDraw.line(vertices[i].getX(), vertices[i].getY(), vertices[i+1].getX(), vertices[i+1].getY());
		}
	}

}