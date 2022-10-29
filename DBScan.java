import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class DBScan{
    
    //Instance variables
    private List db; /* list of all points; */
    private double eps;
    private double minPts;
    private int clusterCounter; /* Cluster counter */

    //Constructor
    public DBScan(List<Point3D> db){
        this.db = db;
        clusterCounter = 0;
    }

    public double setEps(double eps){
        this.eps = eps;
        return eps;
    }
    
    public double setMinPts(double minPts){
        this.minPts = minPts;
        return minPts;
    }

    public int getNumberOfClusters(){
        return clusterCounter;
    }

    public List<Point3D> getPoints(){
        return db;
    }

    /* pushes all elements of list, neighbours into stack, stack */
    private static void pushAll(Stack stack, NearestNeighbours neighbours){
        while(Point3D p : neighbours.db){
            stack.push(p);
        }
    }
    
    //minPts: The minimum number of points (a threshold) in the neighborhood of a point for this one to be considered to belong to a dense region.
    //eps: A distance measure that is used to identify the points in the neighborhood of any point
    public void findClusters(List<Point3D> db, double eps, double minPts){
        for (Point3D p : db) {
            if (p.getClusterLabel() != null) /* Already processed */
                continue;
            NearestNeighbours neighbours = new NearestNeighbours(db, p, eps); /* Find neighbors */
            if (neighbours.length() < minPts) { /* Density check */
                p.setClusterLabel(-1); /* Label as Noise */
            }

            clusterCounter++; /* next cluster label */
            p.setClusterLabel(clusterCounter); /* Label initial point */

            Stack<Point3D> stack = new Stack<Point3D>();
            pushAll(stack, neighbours); /* Neighbors to expand */

            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); /* Process point Q */
                if (q.getClusterLabel() == -1) /* The point is Noise */
                    q.setClusterLabel(clusterCounter); /* Noise becomes border pt */
                else if (q.getClusterLabel() != null) /* Previously processed */
                    continue;
                q.setClusterLabel(clusterCounter); /* Label neighbor */
                neighbours = new NearestNeighbours(db, q, eps); /* Find neighbors */
                if (neighbours.length() >= minPts) { /* Density check */
                    pushAll(stack, neighbours); /* Add neighbors to stack */
                }
            }
        }
    }

    public static List<Point3D> read(String filename){
        String input;
        double x, y, z;

        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            input = r.readLine();
            while ((input = r.readLine()) != null) {
                String[] array = input.split(",");

                x = Double.valueOf(array[0]);
                y = Double.valueOf(array[1]);
                z = Double.valueOf(array[2]);

                this.db.add( new Point3D(x, y, z) );
            }

            r.close();
        } catch (IOException e) {}

        return db;
    }

    public void save(String filename){
        String input;
        double x, y, z;
        List<Point3D> db = new ArrayList<Point3D>();

        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.write("x,y,z,C,R,G,B");
            while (this.db.size() > 0) {
                Point3D p = db.pop();
                w.write( p.getX() + ", " + p.getY() + ", " + p.getZ() + ", " p.getClusterLabel() );
            }

            w.close();
        } catch (IOException e) {}

        return db;
    }

    public static void main(String[] args){
        String filename = args[0];
        double eps = Double.valueOf(args[1]);
        double minPts = Double.valueOf(args[2]);

        System.out.println("TRACE: filename eps minPts: " + filename + " " + eps + " " + minPts);

        DBScan scene = new DBScan(new ArrayList<Point3D>());
        scene.setEps(eps);
        scene.setMinPts(minPts);
        scene.findClusters(read(filename), eps, minPts);
        scene.save(filename.substring(0, filename.length() - 4) + "_clusters.csv");
    }

}