import static java.lang.Math.*;
public class Point3D{

	private double x;
	private double y;
	private double z;
	private int clusterLabel; // label of all points is initialized to 0 (undefined)
	//label -1 means "Noise"

	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		clusterLabel = 0;
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

	public String toString(){
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}

	public double distance(Point3D point){
		return sqrt( pow(this.x - point.x, 2) + pow(this.y - point.y, 2) + pow(this.z - point.z, 2) );
	}

}