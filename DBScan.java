public class DBScan{
    
    //Instance variables
    private List DB; //list of all points;
    private label(P) = null; // label(P) of all P is initialized to undefined
    private double eps;
    private double minPts;

    //Constructor
    public DBScan(List<Point3D>){

    }

    public double setEps(double eps){
        this.eps = eps;
        return eps;
    }
    
    public double setMinPts(double minPts){
        this.minPts = minPts;
        return minPts;
    }
    
    public void findClusters(){
        DBSCAN(DB, eps, minPts);
    }


    public int getNumberOfClusters(){

    }

    public List<Point3D> getPoints(){

    }

    public static List<point3D> read(String filename){

    }

    public void save(String filename){

    }

    /* NOTE: pushAll(stack, neighbours) means push all elements of list neighbours into stack stack */
    private static void pushAll(){

    }

    private void DBSCAN(Sequence DB, double eps, double minPts) {
        int clusterCounter = 0; /* Cluster counter */
        
        for (Point3D p : DB) {
            if (label(p) != undefined) /* Already processed */
                continue;
            NearestNeighbors neighbours = rangeQuery(DB, p, eps); /* Find neighbors */
            if (abs(neighbours) < minPts) { /* Density check */
                label(P) = Noise; /* Label as Noise */
                continue;
            }

            clusterCounter++; /* next cluster label */
            label(P) = C; /* Label initial point */

            Stack stack = new ArrayStack();
            pushAll(stack, neighbours); /* Neighbors to expand */

            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); /* Process point Q */
                if (label(q) = Noise)
                    label(q) = clusterCounter; /* Noise becomes border pt */
                if (label(q) != undefined)
                    continue; /* Previously processed */
                label(q) = clusterCounter; /* Label neighbor */
                NearestNeighbors neighbours = rangeQuery(DB, q, eps); /* Find neighbors */
                if (abs(neighbours) >= minPts) { /* Density check */
                    S.pushAll(neighbours) /* Add neighbors to stack */
                }
            }
        }
    }

    private Sequence rangeQuery(Sequence DB, Q, double eps) {
        Sequence N = empty list;
        for (Point3D p : DB) { /* Scan all points in DB */
            if (distance(Q, p) <= eps) { /* Compute distance */
                N.add(p); /* Add to result */
            }
        }
        return N;
    }
}
/* Reference: https://en.wikipedia.org/wiki/DBSCAN */