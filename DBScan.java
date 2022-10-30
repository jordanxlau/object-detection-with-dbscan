import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class DBScan{
    
    //Instance variables
    private List<Point3D> db; /* list of all points; */
    private double eps; //eps: A distance measure that is used to identify the points in the neighborhood of any point
    private double minPts; //minPts: The minimum number of points (a threshold) in the neighborhood of a point for this one to be considered to belong to a dense region.
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
    private static void pushAll(Stack stack, List<Point3D> neighbours){
        for (Point3D p : neighbours){
            stack.push(p);
        }
    }
    
    public void findClusters(){
        for (Point3D p : this.db) {
            if (p.getClusterLabel() != 0) /* Already processed */
                continue;
            List<Point3D> pNeighbours = (new NearestNeighbours(this.db)).rangeQuery(p, this.eps); /* Find neighbors */
            if (pNeighbours.size() < this.minPts) { /* Density check */
                p.setClusterLabel(-1); /* Label as Noise */
            }

            this.clusterCounter++; /* next cluster label */
            p.setClusterLabel(this.clusterCounter); /* Label initial point */

            Stack<Point3D> stack = new Stack<Point3D>();
            pushAll(stack, pNeighbours); /* Neighbors to expand */
            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); /* Process point Q */
                if (q.getClusterLabel() == -1) /* The point is Noise */
                    q.setClusterLabel(this.clusterCounter); /* Noise becomes border pt */
                if (q.getClusterLabel() != 0){ /* Previously processed */
                    continue;
                }
                q.setClusterLabel(this.clusterCounter); /* Label neighbor */
                List<Point3D> qNeighbours = (new NearestNeighbours(this.db)).rangeQuery(q, this.eps); /* Find neighbors */
                if (qNeighbours.size() >= this.minPts) { /* Density check */
                    pushAll(stack, qNeighbours); /* Add neighbors to stack */
                }
            }
        }
    }

    public static List<Point3D> read(String filename){
        String input;
        double x, y, z;
        List<Point3D> db = new ArrayList<Point3D>();

        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            input = r.readLine();
            while ((input = r.readLine()) != null) {
                String[] array = input.split(",");

                x = Double.valueOf(array[0]);
                y = Double.valueOf(array[1]);
                z = Double.valueOf(array[2]);

                db.add( new Point3D(x, y, z) );
            }

            r.close();
        } catch (IOException e) {}

        return db;
    }

    public void save(String filename){
        List<String> colours = this.getRGBs();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.write("x,y,z,C,R,G,B");
            for (Point3D p : this.db) {
                w.write("\n" + p.getX() + ", " + p.getY() + ", " + p.getZ() + ", " + p.getClusterLabel() + ", " + colours.get(p.getClusterLabel() - 1));
            }

            w.close();
        } catch (IOException e) {}
    }


    //Returns a list of evenely distributed rgb colours in the form "1.0, 1.0, 1.0"
    //Length of the list is the total number of clusters in the implicit object
    //Should be called after findClusters()
    private List<String> getRGBs(){
        List<String> colours = new ArrayList<String>();
        double r = 1, g = 0, b = 0;

        //Go through a 1536 different colours
        for (int c = 0; c < 1536; c+=1){
            if (c < 256){
                g+=(1.0/256);
            } else if (c >= 256 && c < 512){
                r-=(1.0/256);
            } else if (c >= 512 && c < 768){
                b+=(1.0/256);
            } else if (c >= 768 && c < 1024){
                g-=(1.0/256);
            } else if (c >= 1024 && c < 1280){
                r+=(1.0/256);
            } else if (c >= 1280 && c < 1536){
                b-=(1.0/256);
            }

            //Select only every x colours to get the most even spread of colours
            int x = 1536/this.clusterCounter;
            if (c % x == 0)
                colours.add(r + ", " + g + ", " + b);
        }

        return colours;
    }

    public static void main(String[] args){
        args = new String[]{"Point_Cloud_1.csv", "1.5", "40"};
        
        String filename = args[0];
        double eps = Double.valueOf(args[1]);
        double minPts = Double.valueOf(args[2]);

        System.out.println("filename: " + filename);

        DBScan scene = new DBScan(read(filename));
        scene.setEps(eps);
        scene.setMinPts(minPts);
        scene.findClusters();
        scene.save(filename.substring(0,filename.length() - 4) + "_" + eps + "_" + minPts + "_" + scene.getNumberOfClusters() + "_clusters.csv");
    }

}