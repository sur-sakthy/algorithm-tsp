// Point2D.java
// S.Somaskandan, DCU, 2016

package pathFinder;

import edu.princeton.cs.introcs.StdDraw;

public class Point2D{

	private final double x;
	private final double y;

	public Point2D(double x, double y) { // constructor
		this.x = x;
		this.y = y;
	}

	public Point2D(Point2D p) { // copy constructor
		if (p == null)  System.out.println("Point2D(): null point!");
		x = p.getX();
		y = p.getY();
	}

	public double getX() { return x; }

	public double getY() { return y; }

	//	Returns the cross product value of 3 points
	public static double turningDirection(Point2D p1, Point2D p2, Point2D p3) {
		double z=((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()));
		return z; 
	}

	//	Returns the distance 
	public double distance(Point2D p){  return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y)); }

	@Override
	public String toString() {
		return ("(" + x + "," + y + ")");
	}

	public void draw() {
		StdDraw.point(x, y);
	}
}
