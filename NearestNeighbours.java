import java.util.List;
import java.util.ArrayList;
public class NearestNeighbours{

	private	List<Point3D> db;

	public NearestNeighbours(List<Point3D> db){
		this.db = db;
	}
	
	//Find neighbors
    public List<Point3D> rangeQuery(Point3D q, double eps) {
        List<Point3D> neighbours = new ArrayList<Point3D>();
        for (Point3D p : this.db) { /* Scan all points in db */
            if (q.distance(p) <= eps) { /* Compute distance */
                neighbours.add(p); /* Add to result */
            }
        }
        return neighbours;
    }

}