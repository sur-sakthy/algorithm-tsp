// Point2D.java
// C.McArdle, DCU, 2016

package Scrap;

import edu.princeton.cs.introcs.StdDraw;

public class Point2D implements Comparable<Point2D>{

	private final double x;
	private final double y;
	private double angle;

	public Point2D(double x, double y) { // constructor
		this.x = x;
		this.y = y;
	}

	public Point2D(Point2D p) { // copy constructor
		if (p == null)  System.out.println("Point2D(): null point!");
		x = p.getX();
		y = p.getY();
	}

	public void setAngle(Point2D min){
		if (min.getX() == this.x && min.getY() == this.y) {
            angle=Double.POSITIVE_INFINITY;
        }
		angle=Math.atan2(min.getY() - this.y, min.getX() - this.x );
	}
	
	public double getAngle() { return angle; }

	public double getX() { return x; }

	public double getY() { return y; }

	public static double turningDirection(Point2D p1, Point2D p2, Point2D p3) {
		double z=((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()));
		return z; 
	}
	
//	public static int PolarAngleCalculation(Point2D p1, Point2D p2, Point2D p3) {
//		double area2 = (p2.x-p1.x)*(p3.y-p1.y)-(p2.y-p1.y)*(p3.x-p1.x);
//		if(area2<0) {return -1;} //right
//		else if(area2>0) {return 1;} //left
//		else {return 0;} //co-linear
//	}

	public double distance(Point2D p){ return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y)); }

	@Override
	public String toString() {
		return ("(" + x + "," + y + ")");
	}

	public void draw() {
		StdDraw.point(x, y);
	}


	@Override
	public int compareTo(Point2D o) {
		if(this.angle > o.angle)	return -1;
		else if(this.angle < o.angle)	return 1;
		else return 0;
	}

}
