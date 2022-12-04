import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

/** implements Density-Based Spatial Clustering of Applications with Noise algorithm
 * @author Jordan Lau 300240600 */
public class DBScan{
    
    /** list of all points */
    private List<Point3D> db;
    /** distance measure that is used to identify the points in the neighborhood of any point */
    private double eps;
    /** minimum number of points (a threshold) in the neighborhood of a point for this one to be considered to belong to a dense region */
    private double minPts;
    /** counter of total number of clusters found */
    private int clusterCounter;

    /** @param db the list of all points in the point cloud */
    public DBScan(List<Point3D> db){
        this.db = db;
        clusterCounter = 0;
    }

    /** @param eps the epsilon value */
    public double setEps(double eps){
        this.eps = eps;
        return eps;
    }
    
    /** @param minPts the minimum number of points */
    public double setMinPts(double minPts){
        this.minPts = minPts;
        return minPts;
    }

    /** @return the number of clusters found */
    public int getNumberOfClusters(){
        return clusterCounter;
    }

    /** @return all of the points in the point cloud */
    public List<Point3D> getPoints(){
        return db;
    }

    /** helper method used in DBScan.findClusters()
     * pushes all elements of a list into a stack
     * @param stack the stack
     * @param neighbours the list */
    private static void pushAll(Stack stack, List<Point3D> neighbours){
        for (Point3D p : neighbours){
            stack.push(p);
        }
    }
    
    /** implements the DBScan algorithm
     * O(n) complexity */
    public void findClusters(){
        for (Point3D p : this.db) {
            if (p.getClusterLabel() != 0) // Already processed
                continue;
            List<Point3D> pNeighbours = (new NearestNeighboursKD(this.db)).rangeQuery(p, this.eps); // Find neighbors
            if (pNeighbours.size() < this.minPts) { // Density check
                p.setClusterLabel(-1); // Label as Noise
            }

            this.clusterCounter++; // move to next cluster label
            p.setClusterLabel(this.clusterCounter); // labels initial point

            Stack<Point3D> stack = new Stack<Point3D>();
            pushAll(stack, pNeighbours); // stack contains neighbors to expand
            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); // process point Q from stack
                if (q.getClusterLabel() == -1) // the point is Noise
                    q.setClusterLabel(this.clusterCounter); // Noise becomes border point
                if (q.getClusterLabel() != 0){ // point was previously processed
                    continue;
                }
                q.setClusterLabel(this.clusterCounter); // label neighbor with cluster number
                List<Point3D> qNeighbours = (new NearestNeighboursKD(this.db)).rangeQuery(q, this.eps); // find neighbors of Q
                if (qNeighbours.size() >= this.minPts) { // neighbourhood contains minimum number of pts
                    pushAll(stack, qNeighbours); // Add neighbors to stack
                }
            }
        }
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

    /** creates a .csv file with columns x,y,z,C,R,G,B where x,y,z are the coordinates of the point cloud, C is the cluster label and R,G,B are the rgb colour values associated with each different cluster
     * @param filename the name of the file to be created */
    public void save(String filename){
        List<String> colours = this.getRGBs(); // get list of colours to be used
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            
            w.write("x,y,z,C,R,G,B"); // write first row (column labels)
            for (Point3D p : this.db) { // process all points in db
                w.write("\n" + p.getX() + ", " + p.getY() + ", " + p.getZ() + ", " + p.getClusterLabel() + ", " + colours.get(p.getClusterLabel() - 1)); // write point P info
            }

            w.close();
        } catch (IOException e) {}
    }


    /** helper method called in DBScan.save(String filename)
     * evenely distributes rgb colours, one for each cluster found (sometimes with additional colours leftover)
     * length of the list is the total number of clusters in this DBScan
     * @return list of evenely distributed rgb colours in the form "1.0, 1.0, 1.0" */
    private List<String> getRGBs(){
        try{
            List<String> colours = new ArrayList<String>();
            double r = 1, g = 0, b = 0; // start with pure red
            
            for (int c = 0; c < 1536; c+=1){ // traverses through 1536 different possible colours
                if (c < 256){
                    g+=(1.0/256); // increase green
                } else if (c >= 256 && c < 512){
                    r-=(1.0/256); // decrease red
                } else if (c >= 512 && c < 768){
                    b+=(1.0/256); // increase blue
                } else if (c >= 768 && c < 1024){
                    g-=(1.0/256); // decrease green
                } else if (c >= 1024 && c < 1280){
                    r+=(1.0/256); // increase red
                } else if (c >= 1280 && c < 1536){
                    b-=(1.0/256); // decrease blue
                }

                
                int x = 1536/this.clusterCounter;
                if (c % x == 0) // selects only every x colours to get the most even spread of colours
                    colours.add(r + ", " + g + ", " + b);
            }
            return colours;
        } catch (ArithmeticException ae){
            System.out.println("invalid eps or minPts value");
            System.exit(0);
            return null;
        }
    }

    /** main method, reads a file, labels clusters accordingly and produces a new file with each cluster labelled and associated with an rgb colour */
    public static void main(String[] args){
        String filename = args[0]; // process array, args
        double eps = Double.valueOf(args[1]);
        double minPts = Double.valueOf(args[2]);

        System.out.println("filename: " + filename); // print file name

        DBScan scene = new DBScan(read(filename)); // read point cloud file
        scene.setEps(eps);
        scene.setMinPts(minPts);
        scene.findClusters(); // execute DBScan algorithm on point cloud
        scene.save(filename.substring(0,filename.length() - 4) + "_" + eps + "_" + minPts + "_" + scene.getNumberOfClusters() + "_clusters.csv"); // save labelled clusters to new file
        
        System.out.println("clusters: " + scene.getNumberOfClusters()); // print number of clusters found
    }

}