// TSP_NN.java 
// S.Somaskandan, DCU, 2016

package pathFinder;

import java.util.ArrayList;

public class TSP_NN {
	private ArrayList<Point2D> conPoints=new ArrayList<Point2D>();
	private DistanceMatrix distMat;
	private boolean[] check;

	//	Constructor to load in the points
	public TSP_NN( ArrayList<Point2D> p){
		this.conPoints=p;
		this.distMat= new DistanceMatrix(this.conPoints.size());
		calculateDistance(this.distMat);
		check=new boolean[p.size()];
	}

	//	Initializing the DistanceMatrix class with the points
	private void calculateDistance(DistanceMatrix dist){
		Point2D[] tmp=this.conPoints.toArray(new Point2D[]{});
		for(int i=0;i<this.conPoints.size();i++){
			dist.addPoint(tmp[i]);
		}
	}

	//	Sets the given point true so that it won't be visited again
	public void setTrue(Point2D t){
		this.check[conPoints.indexOf(t)]=true;
	}

	//	Computes the next point given the current point 
	public Point2D findNextpoint(Point2D current){
		double shortestDistance=Double.POSITIVE_INFINITY;
		double distance=0.0;
		int nextPoint=0;

		this.check[conPoints.indexOf(current)]=true;
		for(int i=0;i<this.conPoints.size();i++){ 
			if(!this.check[i]){						//	Checks if a point has already been visited
				distance=this.distMat.getDistance(conPoints.indexOf(current), i);
				
				//	Computes the next point considering the distance between the current point and a different unvisited point in the map
				if(distance < shortestDistance){	
					shortestDistance=distance;
					nextPoint=i;
				}
			}
		}
		return this.conPoints.get(nextPoint);
	}
}
