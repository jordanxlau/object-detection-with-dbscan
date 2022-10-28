import static java.lang.Math.*;

public class Point3D{

	private double x;
	private double y;
	private double z;
	private Integer clusterLabel; // label(P) of all P is initialized to undefined

	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		clusterLabel = null;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getZ(){
		return z;
	}

	public int getClusterLabel(){
		return clusterLabel;
	}

	public void setClusterLabel(int clusterLabel){
		this.clusterLabel = clusterLabel;
	}

	public double distance(Point3D point){
		return sqrt( pow(this.x - point.x, 2) + pow(this.x - point.x, 2) + pow(this.z - point.z, 2) );
	}

}