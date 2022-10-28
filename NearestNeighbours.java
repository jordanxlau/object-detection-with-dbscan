public class NearestNeighbours extends Sequence{

	private	Sequence<Point3D> list;

	public NearestNeighbours(Sequence<Point3D> list){
		this.list = list;
	}
	
	public List<Point3D> rangeQuery(double eps){
		return DBScan.rangeQuery();
	}

}