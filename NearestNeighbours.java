import java.util.List;
import java.util.LinkedList;

public class NearestNeighbours{//ARE WE ALLOWED TO NOT HAVE THIS BE A TYPE OF SEQUENCE?

	private	List<Point3D> list;

	public NearestNeighbours(List db, Point3D q, double eps){
		list = rangeQuery(db, q, eps);  /* Find neighbors */
	}
	
	//Helper method
    private List rangeQuery(List<Point3D> db, Point3D q, double eps) {
        List neighbours = new LinkedList<Point3D>();
        for (int i = 0; i < db.size(); i++) { /* Scan all points in db */ //CAN THIS BE A FOR-EACH LOOP?
            Point3D p = db.get(i);
            if (q.distance(p) <= eps) { /* Compute distance */
                neighbours.add(p); /* Add to result */
            }
        }
        return neighbours;
    }

    //Instance methods
    public int length(){
    	return list.size();
    }

}