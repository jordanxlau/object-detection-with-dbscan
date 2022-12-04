public class KDtree {

	public class KDnode {

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

	/** getter for the root
	 * @return the {@link KDnode} that is the root of this tree */
	public KDnode getRoot(){
		return this.root;
	}

}