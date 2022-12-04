import java.util.List;
import java.util.ArrayList;

/** represents all points in the neighbourhood of a certain {@link Point3D}
 * implements a rangeQuery algorithm using a {@link KDtree}
 * @author Jordan Lau 300240600 */
public class NearestNeighboursKD {
	
	private class KDnode {

			public Point3D point;
			public int axis; //the axis that this point uses to compare
			public double value; //the value of the point AT the axis it gets compared at
			public KDnode left;
			public KDnode right;

			public KDnode(Point3D pt, int axis) {
				this.point= pt;
				this.axis= axis;
				this.value= pt.get(axis);
				left= right= null;
			}
		
	}
	
	private class KDtree {
		
		private KDnode root;
		private int currentAxis;

		/** constructs an empty tree */
		public KDtree() {
			root = null;
		}

		/** inserts an element into the KD tree
		 * @param p the point being inserted
		 * @param node the node to compare with (root of the subtree)
		 * @param currentAxis the axis on which the comparison of the point will be made
		 * @return the node inserted */
		public KDnode insert(Point3D p, KDnode node, int currentAxis) {
			if (node == null)
				node = new KDnode(p, currentAxis);
			else if (p.get(currentAxis) <= node.value)
				node.left = insert(p, node.left, (currentAxis + 1) % 3);
			else
				node.right = insert(p, node.right, (currentAxis + 1) % 3);

			return node;
		}

	}

	/** the point cloud database */
	private KDtree db;

	/** @param db the point cloud database */
	public NearestNeighboursKD(List<Point3D> db) {
		for (Point3D p : db) {
			this.db.insert(p, this.db.root, 0);
		}
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