import java.util.List;
import java.util.ArrayList;

/** represents all points in the neighbourhood of a certain {@link Point3D}
 * implements a rangeQuery algorithm using a {@link KDtree}
 * @author Jordan Lau 300240600 */
public class NearestNeighboursKD {

	/** the point cloud database */
	private KDtree db;

	/** @param db the point cloud database */
	public NearestNeighboursKD(List<Point3D> db) {
		this.db = new KDtree();

		for (Point3D p : db) {
			this.db.insert(p, this.db.root, 0);
		}
	}

	/** getter for db tree
	 * @return the {@link KDtree} containing all points in the point cloud database */
	public KDtree getTree(){
		return this.db;
	}

	/** finds the neighbours of a point
     * O(logn) complexity in average case?
     * @param p the point
     * @param eps the distance within which another point is considered "a neighbour"
     * @param neighbours the list of neighbours
     * @param node the current node to be compared/searched
     * @return list of points that are neighbours */
    public List<Point3D> rangeQuery(Point3D p, double eps, List neighbours, KDnode node) {
        if (node == null) //
			return new ArrayList<Point3D>();
		if (p.distance(node.point) < eps) //this node's point is within the specified epislon of p
			neighbours.add(node.point); //add the current node to the 
		if (p.get(node.axis) - eps <= node.value) 
			return rangeQuery(p, eps, neighbours, node.left); //search the left subtree
		if (p.get(node.axis) + eps > node.value)
			return rangeQuery(p, eps, neighbours, node.right); //search the right subtree
		return neighbours;
    }
	
}