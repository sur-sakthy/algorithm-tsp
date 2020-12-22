package pathFinder;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Main {

	public static void main(String[] args) {
		Polygon2D convex=new Polygon2D();
		FindPath fp=new FindPath();
		Point2D[] shortest=null;

		ShapeMap map2=new ShapeMap(args[0]);
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(Color.BLUE);
		map2.draw();
		StdDraw.setPenColor(Color.GREEN);
		StdDraw.setPenRadius(0.02);
		map2.sourcePoint().draw();
		StdDraw.setPenColor(Color.RED);
		map2.destinationPoint().draw();

		final long startTime=System.currentTimeMillis();
		for(Polygon2D poly: map2){
			convex=GrahamScan.findConvexHull(poly);
			StdDraw.setPenColor(Color.RED);
			convex.draw();
			fp.insert(convex);
		}
		fp.setSourcepoint(map2.sourcePoint());
		fp.setDestinationpoint(map2.destinationPoint());
		if(args.length <= 1){ shortest=fp.findRoute(null); }
		else{ shortest=fp.findRoute(args[1]); }
		final long runTime=System.currentTimeMillis() - startTime;

		System.out.println("Execution time (ms) = "+ runTime);
		System.out.println("Length of path found = "+fp.getDistance());
		StdDraw.setPenColor(Color.BLACK);
		fp.draw(shortest);	
	}
}
