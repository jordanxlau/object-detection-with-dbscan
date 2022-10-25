import java.util.*;

public class NearestNeighbours extends Sequence{

	private	List<Point3D> list;

	public NearestNeighbours(List<Point3D> list){
		this.list = list;
	}
	
	public List<Point3D> rangeQuery(double eps){
		return DBScan.rangeQuery();
	}

}