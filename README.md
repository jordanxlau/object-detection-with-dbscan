# object-detection-with-dbscan
In this project (submitted as part of the requirements for CSI 2510), I implement a data clustering algorithm, DBSCAN (Density-Based Spatial Clustering of Applications with Noise).

DBScan finds the nearest neighbours of all points within epsilon value. It then labels them as a cluster if there are greater than minPts number of points.

To run with 1.2 as eps and 10 as minPts:
```
java DBScan data.csv 1.2 10
```

To process neighbour points using KD trees, instead of linearlly, replace method DBScan.findClusters() with the following:
```
public void findClusters(){
        NearestNeighboursKD nn = new NearestNeighboursKD(this.db);

        for (Point3D p : this.db) {
            if (p.getClusterLabel() != 0) // Already processed
                continue;
            List<Point3D> pNeighbours = nn.rangeQuery(p, this.eps, new ArrayList<Point3D>(), nn.getTree().getRoot()); // Find neighbors
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
                List<Point3D> qNeighbours = nn.rangeQuery(q, this.eps, new ArrayList<Point3D>(), nn.getTree().getRoot()); // find neighbors of Q
                if (qNeighbours.size() >= this.minPts) { // neighbourhood contains minimum number of pts
                    pushAll(stack, qNeighbours); // Add neighbors to stack
                }
            }
        }
    }
```

Three experiments are also included to:

1. Validate the output of the linear and KD method of finding the nearest neighbours of a point.
2. Compare their execution times.
3. Test the integration of the KD nearest neighbour method with DBScan.