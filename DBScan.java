public class DBScan{
    
    //Instance variables
    private List DB; //list of all points;
    private double eps;
    private double minPts;

    //Constructor
    public DBScan(Sequence<Point3D> sequence){

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

    public Sequence<Point3D> getPoints(){

    }

    public static Sequence<Point3D> read(String filename){

    }

    public void save(String filename){

    }

    /* NOTE: pushAll(stack, neighbours) means push all elements of list neighbours into stack stack */
    private static void pushAll(Stack stack, NearestNeighbors neighbours){

    }

    private void DBSCAN(Sequence db, double eps, double minPts) {
        int clusterCounter = 0; /* Cluster counter */

        for (Point3D p : db) {
            if (p.getClusterLabel() != null) /* Already processed */
                continue;
            NearestNeighbors neighbours = rangeQuery(db, p, eps); /* Find neighbors */
            if (abs(neighbours) < minPts) { /* Density check */
                p.setClusterLabel("Noise"); /* Label as Noise */
            }

            clusterCounter++; /* next cluster label */
            p.setClusterLabel(C); /* Label initial point */

            Stack stack = new ArrayStack();
            pushAll(stack, neighbours); /* Neighbors to expand */

            while (! stack.isEmpty() ) {
                Point3D q = stack.pop(); /* Process point Q */
                if (q.getClusterLabel() == Noise)
                    q.setClusterLabel(clusterCounter); /* Noise becomes border pt */
                else if (q.getClusterLabel() != undefined){} /* Previously processed */

                q.setClusterLabel(clusterCounter); /* Label neighbor */
                NearestNeighbors neighbours = rangeQuery(db, q, eps); /* Find neighbors */
                if (abs(neighbours) >= minPts) { /* Density check */
                    pushAll(stack, neighbours); /* Add neighbors to stack */
                }
            }
        }
    }

    private Sequence rangeQuery(Sequence db, Point3D q, double eps) {
        Sequence n = new LinkedList<>();//empty list
        for (Point3D p : db) { /* Scan all points in db */
            if (distance(q, p) <= eps) { /* Compute distance */
                n.add(p); /* Add to result */
            }
        }
        return n;
    }
}