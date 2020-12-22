// FindPath.java 
// S.Somaskandan, DCU, 2016

package pathFinder;

import java.awt.Color;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdDraw;

public class FindPath {
	private ArrayList<Point2D> Path=new ArrayList<Point2D>();
	private ArrayList<Point2D[]> Map=new ArrayList<Point2D[]>();
	private ArrayList<Point2D> Points=new ArrayList<Point2D>();
	private Point2D source;
	private Point2D destination;
	private int index;
	private static double TotalDistance=0.0;

	//	Method to load in all the points into the class to aid in the path finding
	public void insert(Polygon2D poly){
		Point2D[] temp=poly.asPointsArray();
		for(int i=0;i<temp.length;i++){
			Points.add(temp[i]);		//	Storing each point in a list
		}
		Map.add(temp);					//	Storing each polygon in a list in the form of an array of Point2D
	}


	//	Method to add in the source point
	public void setSourcepoint(Point2D src){ 
		Points.add(src);
		source=src; 
	}

	//	Method to add in the destination point
	public void setDestinationpoint(Point2D dst){
		Points.add(dst);
		destination=dst; 
	}

	//	Method to determine which polygon contains the current point
	private int whichPolygon(Point2D p){
		Point2D[] tmp;

		for(int i=0;i<Map.size();i++){
			tmp=Map.get(i);
			for(int j=0;j<tmp.length;j++){
				if((p.getX() == tmp[j].getX()) && (p.getY() == tmp[j].getY())){	
					index=j;
					return i; }
			}
		}
		return -1;
	}

	//	Method to find the path
	public Point2D[] findRoute(String draw){
		Point2D[] realPath;
		Point2D current;
		int nextPolygon;
		ArrayList<Integer> adjvertices=new ArrayList<Integer>();
		double[] distance;
		double previousDistance;
		double shorDist=Double.POSITIVE_INFINITY;
		int nextPoint=0;

		TSP_NN nn=new TSP_NN(this.Points);

		/////////////////////////////////////////////////////////////////////////////////////////

		Path.add(source);		//	Add the source point to the list of Path	
		current=source;			
		current=nn.findNextpoint(current);		//	Finds the next closest point to the current point
		Path.add(current);

		//		Main for loop that gets the path
		for(int z=0;z<Points.size();z++){
			//	Checks if -v is present in the command line	
			if(draw != null){
				StdDraw.setPenColor(Color.BLACK);
				draw_point(current);
			}

			nextPolygon=this.whichPolygon(current);			//	Calls the whichPolygon method to find out the polygon on which the current lies on.
			previousDistance=current.distance(this.destination);	//	Stores the distance of the current point to the destination point

			//	Sets up the graph for a polygon connecting the adjacent vertices
			Graph g=new Graph(Map.get(nextPolygon).length);
			for(int j=0;j<Map.get(nextPolygon).length-1;j++){ g.addEdge(j, j+1); }
			g.addEdge(Map.get(nextPolygon).length-1, 0);
			adjvertices=g.adj(index);		//	Gets the adjacent vertices to the current vertex

			//	Checks if -v is present in the command line
			if(draw != null){
				for(int i=0;i<adjvertices.size();i++){
					StdDraw.setPenColor(Color.CYAN);
					draw_point(Map.get(nextPolygon)[adjvertices.get(i)]);
				}
				draw(Path.toArray(new Point2D[]{}));
			}

			//	Initializing the distance array with the size of the adjacent vertices list  
			distance=new double[adjvertices.size()];

			//	Getting the distance from the adjacent vertices to the destination point and storing them in the distance array
			for(int i=0;i<adjvertices.size();i++){
				Point2D adjacent=new Point2D(Map.get(nextPolygon)[adjvertices.get(i)]);
				distance[i]=adjacent.distance(this.destination);
				nn.setTrue(Map.get(nextPolygon)[adjvertices.get(i)]);
			}

			//	Getting the shortest distance from the 'distance' array
			for(int j=0;j<distance.length;j++){
				if(distance[j] < shorDist){
					shorDist=distance[j];
					nextPoint=adjvertices.get(j);
				}
			}

			//	If the new distance is less than the current distance,
			//	add the calculated next point to the array of path after some more checks
			if(previousDistance < shorDist){
				//	Setting the whole polygon to true
				for(int i=0;i<Map.get(nextPolygon).length;i++){
					nn.setTrue(Map.get(nextPolygon)[i]);
				}
				Point2D next=nn.findNextpoint(current);		//	Finds the next closest point to the current point

				//	If the distance from the next point to the destination is less than previous distance
				//	let the next point equal to the current point so it can be added to the list of Path
				// 	Else find a different next point
				if(next.distance(this.destination) < previousDistance){
					current=next;
				}
				else{
					nn.setTrue(next);
					next=nn.findNextpoint(current);
				}
				Path.add(current);						//	Add the current point 
				if(current==destination){break;}		//	if the current point is equal to the destination point, then break;
			}	
			else{
				Path.add(Map.get(nextPolygon)[nextPoint]);
				current=Map.get(nextPolygon)[nextPoint];
				if(current==destination){break;}
			}
			shorDist=Double.POSITIVE_INFINITY;			//	Reset shorDist so that it can be used for next calculation 
		}

		realPath=Path.toArray(new Point2D[]{});			//	Converting the ArrayList 'Path' to an array of Pointr2D[]
		//	Checks if -v is present in the command line
		if(draw != null){
			StdDraw.setPenColor(Color.CYAN);
			draw(new Point2D[]{realPath[realPath.length-2], realPath[realPath.length-1]});
		}
		//	Calculates the distance 
		setDistance(realPath);
		return realPath;								//	Returns the array of Point2D[]

		/////////////////////////////////////////////////////////////////////////////////////////
	}

	//	Calculates the total distance of the path and stores the value inside a global variable
	private static void setDistance(Point2D[] points){
		for(int i=0;i<points.length-1;i++){
			TotalDistance=TotalDistance+points[i].distance(points[i+1]);
		}
	}

	//	Return the total distance calculated
	public double getDistance(){
		return TotalDistance;
	}

	//	Draws the path without connecting the source to the destination
	public void draw(Point2D[] vertices) {
		for (int i=0; i<vertices.length-1; i++) {
			StdDraw.pause(300);
			StdDraw.setPenRadius(0.009);
			StdDraw.line(vertices[i].getX(), vertices[i].getY(), vertices[i+1].getX(), vertices[i+1].getY());
		}
	}

	//	Draws a point
	public void draw_point(Point2D point) {
		StdDraw.pause(300);
		StdDraw.setPenRadius(0.02);
		StdDraw.point(point.getX(), point.getY());
	}


}
