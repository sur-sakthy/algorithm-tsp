package Scrap;

import java.util.Comparator;

public class PolarOrder implements Comparator<Point2D>{
	public int compare(Point2D q1, Point2D q2) {
		double dx1 = q1.getX() - x;
		double dy1 = q1.getY() - y;
		double dx2 = q2.getX() - x;
		double dy2 = q2.getY() - y;

		if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
		else if (dy2 >= 0 && dy1 < 0) return 1;    // q1 below; q2 above
		else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
			if      (dx1 >= 0 && dx2 < 0) return -1;
			else if (dx2 >= 0 && dx1 < 0) return 1;
			else                          return  0;
		}
		else return -PolarAngleCalculation(Point2D.this, q1, q2);     // both above or below
	}
}
