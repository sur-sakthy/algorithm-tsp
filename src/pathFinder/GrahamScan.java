// GrahamScan.java 
// S.Somaskandan, DCU, 2016

package pathFinder;

import java.util.ArrayList;
import java.util.Stack;


import edu.princeton.cs.introcs.StdDraw;

public class GrahamScan {
	public static Polygon2D findConvexHull (Polygon2D polygon){
		Point2D[] poly_points=null;
		Point2D p0=null;
		Stack<Point2D> H=new Stack<Point2D>();

		poly_points=polygon.asPointsArray();
		p0=lowestPoint(poly_points);
		poly_points=sortByAngleFrom(poly_points, p0);

		//	Getting the convex hull of a polygon
		H.push(poly_points[0]);
		H.push(poly_points[1]);

		for(int i=2;i<poly_points.length;i++){
			Point2D firstlast=H.pop();
			Point2D secondlast=H.peek();
			H.push(firstlast);
			while(Point2D.turningDirection(secondlast, firstlast, poly_points[i]) > 0.0){
				H.pop();
				firstlast=H.pop();
				secondlast=H.peek();
				H.push(firstlast);
			}
			H.push(poly_points[i]);
		}

		
		Stack<Point2D> tmp=new Stack<Point2D>();
		while(!H.isEmpty()){
			tmp.push(H.pop());
		}

		ArrayList<Point2D> Hull = new ArrayList<Point2D>();
		while(!tmp.isEmpty()){
			Hull.add(tmp.pop());
		}

		//	To get rid of colinear points
		for(int i=0;i<Hull.size()-2;i++){
			if(Point2D.turningDirection(Hull.get(i), Hull.get(i+1), Hull.get(i+2)) == 0 ){
				Hull.remove(i+1);
			}
			if(Point2D.turningDirection(Hull.get(Hull.size()-2),Hull.get(Hull.size()-1) , Hull.get(0)) == 0){
				Hull.remove(Hull.size()-1);
			}
		}


		Polygon2D convex=new Polygon2D(Hull.toArray(new Point2D[]{}));
		return convex;
	}


	//	Returns the lowest leftmost point in the convex hull 
	private static Point2D lowestPoint (Point2D[] points){
		Point2D min=new Point2D(0,0);
		Point2D miny = points[0];
		double minx = Double.POSITIVE_INFINITY;

		int i=0;

		//	Gets the lowest y point in a given polygon
		while(i<points.length){
			Point2D tmp= points[i];
			if(tmp.getY() <= miny.getY()){	miny=tmp;	}
			i++;
		}

		//	Gets the lowest x point given the lowest y value
		for(int j=0;j<points.length;j++){
			if(points[j].getY() == miny.getY()){
				if(points[j].getX() < minx){
					min=points[j];
					minx=points[j].getX();
				}
			}
			if(points[j].getX() == min.getX()){ }
		}

		return min;
	}

	//	Sorts the given points according to the polar angle  
	private static Point2D[] sortByAngleFrom (Point2D[] points, Point2D p){
		double maxy=0.0;
		int maxy_index=0;
		ArrayList<Point2D> polygon_points = new ArrayList<Point2D>();
		Point2D[] sorted=null;

		//	Removing the lowest point from the list of given points
		for(int a=0;a<points.length;a++){
			if(points[a].getX() == p.getX() && points[a].getY() == p.getY()){}
			else{ polygon_points.add(points[a]); }
		}
		points=polygon_points.toArray(new Point2D[]{});

		//	Using the cross product and the BubbleSort sorting technique, the points are sorted clockwise 
		Point2D tmp;
		for(int i=1;i<points.length;i++){
			boolean no_swap = true;
			for(int j=0;j<points.length-i;j++){
				if(Point2D.turningDirection(p, points[j], points[j+1]) > 0){ // do swap
					tmp=points[j];
					points[j]=points[j+1];
					points[j+1]=tmp;
					no_swap = false;
				}
			}
			if(no_swap) break;
		}

		//	Putting the lowest point back into the array
		polygon_points.clear();
		polygon_points.add(p);
		for(int a=0;a<points.length;a++){
			polygon_points.add(points[a]);
		}

		//	Getting the max y coordinate index given the lowest x coordinate to get rid of the middle points in the vertical axis setting the origin at the lowest point 
		for(int i=0;i<polygon_points.size();i++){
			if(polygon_points.get(i).getX() == p.getX() && polygon_points.get(i).getY() > maxy){
				maxy=polygon_points.get(i).getY();
				maxy_index=i;
			}
		}

		//	Removing the middle points on the vertical axis above the lowest point
		for(int i=0;i<polygon_points.size();i++){
			if(polygon_points.get(i).getX() == p.getX() && polygon_points.get(i).getY() != polygon_points.get(maxy_index).getY() && polygon_points.get(i).getY() != p.getY()){
				polygon_points.remove(i);
			}
		}

		//	Converting the ArrayList of sorted points to an array of Point2D[]
		sorted=polygon_points.toArray(new Point2D[]{});
		return sorted;
	}

	//	Draws the given points
	public static void draw(Point2D[] points){
		StdDraw.setPenRadius(0.015);
		for(int k=0;k<points.length;k++){
			points[k].draw();
		}
	}
}
