import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DBScan{
    
    //Instance variables
    private List db; /* list of all points; */
    private double eps;
    private double minPts;
    private int clusterCounter; /* Cluster counter */

    //Constructor
    public DBScan(List<Point3D> db, double minPts, double eps){
        this.db = db;
        this.minPts = minPts;
        this.eps = eps;
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


    public int getNumberOfClusters(){
        return clusterCounter;
    }

    public List<Point3D> getPoints(){

    }

    public static List<Point3D> read(String filename){

    }

    public void save(String filename){

    }

    /* NOTE: pushAll(stack, neighbours) means push all elements of list neighbours into stack stack */
    private static void pushAll(Stack stack, NearestNeighbours neighbours){

    }

    public static void main(String[] args){
        String filename = args[0];
        int eps = Integer.valueOf(args[1]);
        int minPts = Integer.valueOf(args[2]);

        System.out.println("TRACE:");
        System.out.println("filename, eps, minPts: " + filename + eps + minPts);
    }

}