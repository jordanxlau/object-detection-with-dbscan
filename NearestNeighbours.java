import java.util.List;
import java.util.ArrayList;

/** represents all points in the neighbourhood of a certain {@link Point3D}
 * @author Jordan Lau */
public class NearestNeighbours{

    /** the point cloud database, in the form of a {@link List} */
	private	List<Point3D> db;

    /** @param db the point cloud database */
	public NearestNeighbours(List<Point3D> db){
		this.db = db;
	}
	
	/** finds the neighbours of a point
     * @param q the point
     * @param eps the distance within which another point is considered "a neighbour"
     * @return list of points that are neighbours */
    public List<Point3D> rangeQuery(Point3D q, double eps) {
        List<Point3D> neighbours = new ArrayList<Point3D>();
        for (Point3D p : this.db) { // Scan all points in db
            if (q.distance(p) <= eps) { // Compute distance
                neighbours.add(p); // Add to list of neighbours
            }
        }
        return neighbours;
    }

}