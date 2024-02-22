import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

/**
 * Experiment 3 - DBScan Integration
 *
 * @author Jordan Lau, 300240600
 *
*/ 
public class Exp3{

	/** helper method used in DBScan.findClusters()
     * pushes all elements of a list into a stack
     * @param stack the stack
     * @param neighbours the list */
    private static void pushAll(Stack stack, List<Point3D> neighbours){
        for (Point3D p : neighbours){
            stack.push(p);
        }
    }

	/** runs the DBScan algorithm using rangeQuery with KD trees
     * O(n) complexity */
    public static int findClusters(List<Point3D> db, double eps, double minPts, String type){
    	int clusterCounter = 0;
    	Neighbours nn;
    	//determine the implementation to use to find neighbors
    	if (type.equals("kd"))
    		nn = new NearestNeighboursKD(db);
    	else
    		nn = new NearestNeighbours(db);

        for (Point3D p : db) {
            if (p.getClusterLabel() != 0) // Already processed
                continue;
            
            List<Point3D> pNeighbours = nn.rangeQuery(p, eps); // find neighbors

            if (pNeighbours.size() < minPts) { // Density check
                p.setClusterLabel(-1); // Label as Noise
            }

            clusterCounter++; // move to next cluster label
            p.setClusterLabel(clusterCounter); // labels initial point

            Stack<Point3D> stack = new Stack<Point3D>();
            pushAll(stack, pNeighbours); // stack contains neighbors to expand
            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); // process point Q from stack
                if (q.getClusterLabel() == -1) // the point is Noise
                    q.setClusterLabel(clusterCounter); // Noise becomes border point
                if (q.getClusterLabel() != 0){ // point was previously processed
                    continue;
                }
                q.setClusterLabel(clusterCounter); // label neighbor with cluster number
                List<Point3D> qNeighbours = nn.rangeQuery(q, eps); // find neighbors of Q
                if (qNeighbours.size() >= minPts) { // neighbourhood contains minimum number of pts
                    pushAll(stack, qNeighbours); // Add neighbors to stack
                }
            }
        }

        return clusterCounter;
    }

    /** collects points from a .csv file
     * @param filename the file to read
     * @return the extracted list of points */
    public static List<Point3D> read(String filename){
        String input;
        double x, y, z;
        List<Point3D> db = new ArrayList<Point3D>();

        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            
            input = r.readLine(); // read first row (column labels)
            while ((input = r.readLine()) != null) { // read each subsequent row if possible
                String[] array = input.split(","); // process each point information
                x = Double.valueOf(array[0]);
                y = Double.valueOf(array[1]);
                z = Double.valueOf(array[2]);
                db.add( new Point3D(x, y, z) ); // add point to db
            }

            r.close();
        } catch (IOException e) {}

        return db;
    }

    public static void main(String[] args){
		String type = args[0].toLowerCase();
		String filename = args[1];
		double eps= Double.parseDouble(args[2]);
		double minPts = Double.parseDouble(args[3]);

    	long startTime = System.nanoTime();
    	List<Point3D> points = read(filename); // reads the csv file
		System.out.println( "number of clusters found: " + findClusters(points, eps, minPts, type) );
		long endTime = System.nanoTime();
		System.out.println("compute time for entire DBScan algorithm using " + type + " method (nanoseconds): " + (endTime - startTime));
    }
}