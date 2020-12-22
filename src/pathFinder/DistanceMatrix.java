// DistanceMatrix.java 
// S.Somaskandan, DCU, 2016

package pathFinder;

public class DistanceMatrix {

	private double[][] dist;	// distances between points
	private Point2D[] points;	// set of points (city locations)
	private int N; 				// current number of points
	private int size;			// total number of points

	public DistanceMatrix(int size) { // constructor
		dist = new double[size][size];
		points = new Point2D[size];
		this.size = size;
	}

	public void addPoint(Point2D p) {
		if (N == size) return;
		
		points[N] = new Point2D(p); // make a copy
		// calculate distances from new point to points previously added
		if (N > 0) {
			for (int i=0; i<N; i++) {
				dist[N][i] = p.distance(points[i]);
				dist[i][N] = p.distance(points[i]);
			}
		}
		N++;
	}

	//	Returns the distance between 2 given points in the map
	public double getDistance(int i, int j) {
		return dist[i][j];
	}

	//	Returns the total number of points in the map
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++)
				s += String.format( "%.3f", dist[i][j]) + " ";
			s += "\n";
		}
		return s;
	}

}
