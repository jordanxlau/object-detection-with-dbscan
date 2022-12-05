/**
 * Experiment 3 - DBScan Integration
 *
 * @author Jordan Lau, 300240600
 *
*/ 
public class Exp3{

	/** implements the DBScan algorithm using rangeQuery with KD trees
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
}