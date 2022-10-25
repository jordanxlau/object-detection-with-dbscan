public class Point3D{

	private double x;
	private double y;
	private double z;
	private int cluster;

	public point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
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

	public double distance(Point3D point){
		System.out.println("NOT IMPLEMENTED YET");
		return 2.2;
	}

	public static void main(String[] args){
		List l = new LinkedList<Integer>();
		l.add(1);
		l.add(3);
	}

}