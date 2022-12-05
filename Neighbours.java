import java.util.List;

public abstract class Neighbours{
	public abstract List<Point3D> rangeQuery(Point3D q, double eps);
}