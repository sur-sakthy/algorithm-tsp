package Scrap;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.introcs.StdDraw;

public class GrahamScan {

	private static int count;
	private static double maxx;
	private static double noz;

	public static Polygon2D findConvexHull (Polygon2D polygon){
		count=0;
		int last=0;
		Point2D[] poly_points=null;
		Point2D p0=null;
		ArrayList<Point2D> sorted_points=new ArrayList<Point2D>();
		Stack<Point2D> H=new Stack<Point2D>();

		poly_points=polygon.asPointsArray();
		//		System.out.println(Arrays.toString(poly_points));
		p0=lowestPoint(poly_points);
		sortByAngleFrom(poly_points, p0);
		System.out.println(Arrays.toString(poly_points));
		//				draw(poly_points);
		//		System.out.println("Noz: "+noz);
		if(count>2){
			last=collinear(poly_points);
			for(int i=count-1;i<poly_points.length;i++){
				if(poly_points[i].getAngle() == 0 && poly_points[i].getY() != maxx && noz > 2){}
				else{ sorted_points.add(poly_points[i]); }
				//				sorted_points.add(poly_points[i]);
			}
			sorted_points.add(poly_points[last]);
		}
		else if(count==2){
			for(int i=1;i<poly_points.length;i++){
				if(poly_points[i].getX() == p0.getX() && poly_points[i].getY() != maxx && noz > 2){}
				else{ sorted_points.add(poly_points[i]); }
				//				sorted_points.add(poly_points[i]);
			}
			sorted_points.add(poly_points[0]);
		}
		else{
			for(int i=0;i<poly_points.length;i++){
				if(poly_points[i].getAngle() == p0.getX() && poly_points[i].getY() != maxx && noz > 2){}
				else{ sorted_points.add(poly_points[i]); }
				//				sorted_points.add(poly_points[i]);
			}
		}


		sorted_points.remove(p0);
		sorted_points.add(0, p0);

		poly_points=sorted_points.toArray(new Point2D[]{});
		//		System.out.println(Arrays.toString(poly_points));
		//		draw(poly_points);

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
						if(Point2D.turningDirection(secondlast, firstlast, poly_points[i])==0.0){
							firstlast=H.pop();
							secondlast=H.peek();
						}
						
			H.push(poly_points[i]);
		}


		Polygon2D convex=new Polygon2D();
		while(!H.isEmpty()){
			convex.addPoint(H.pop());
		}
		System.out.println("Convex: "+Arrays.toString(convex.asPointsArray()));
		//		draw(convex.asPointsArray());
		return convex;
	}


	private static Point2D lowestPoint (Point2D[] points){
		Point2D min=new Point2D(0,0);
		Point2D miny = points[0];
		double minx = Double.POSITIVE_INFINITY;

		int i=0;
		while(i<points.length){
			Point2D tmp= points[i];
			if(tmp.getY() <= miny.getY()){	miny=tmp;	}
			i++;
		}

		for(int j=0;j<points.length;j++){
			if(points[j].getY() == miny.getY()){
				if(points[j].getX() < minx){
					min=points[j];
					minx=points[j].getX();
				}
				count++;
			}
		}
		//		System.out.println(count);
		System.out.println(min);

		return min;
	}

	private static void sortByAngleFrom (Point2D[] points, Point2D p){
		noz=0;
		maxx = 0;

		for(int i=0;i<points.length;i++){
			points[i].setAngle(p);
			if(points[i].getX() == p.getX()){ noz++; }

			if(points[i].getX() == p.getX()){
				if(points[i].getY() > maxx){
					maxx=points[i].getY();
				}
			}
		}
		Arrays.sort(points);

	}

	private static int collinear(Point2D[] points){
		double max=0.0;
		int index=0;
		for(int i=0;i<count-1;i++){
			if(points[i].getX() > max){
				max=points[i].getX();
				index=i;
			}
		}
		return index;
	}

	public static void draw(Point2D[] points){
		StdDraw.setPenRadius(0.02);
		StdDraw.setPenColor(Color.CYAN);
		points[0].draw();
//		StdDraw.pause(400);
		for(int k=1;k<points.length;k++){
			StdDraw.setPenColor(Color.RED);
			points[k].draw();
//			StdDraw.pause(200);
		}
	}
}
