import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;  
import java.io.*;

/**
 * Incomplete Experiment 1 
 *
 * @author Robert Laganiere
 * @author Jordan Lau, 300240600
 *
*/ 
public class Exp1 {
  
    /** collects points from a .csv file
     * @param filename the file to read
     * @return the extracted list of points */
    public static List<Point3D> read(String filename){
        String input;
        double x, y, z;
        List<Point3D> db = new ArrayList<Point3D>();

        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
            
            input = r.readLine(); // read first row (column labels)
            while ((input = r.readLine()) != null) { // read each subsequent row if possible
                String[] array = input.split(","); // process each point information
                x = Double.valueOf(array[0]);
                y = Double.valueOf(array[1]);
                z = Double.valueOf(array[2]);
                db.add( new Point3D(x, y, z) ); // add point to db
            }

            r.close();
        } catch (IOException e) {}

        return db;
    }

	public static void main(String[] args) throws Exception {
		args = new String[]{"xerophthalmiology", "0.05", "Point_Cloud_1.csv", "-5.429850155", "0.807567048", "-0.398216823"};// xerophthalmiology 0.05 Point_Cloud_1.csv -5.429850155 0.807567048 -0.398216823

		// not reading args[0]
		double eps= Double.parseDouble(args[1]);

		// reads the csv file
		List<Point3D> points= Exp1.read(args[2]);

		Point3D query= new Point3D(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));

		// creates the NearestNeighbor instance
		NearestNeighboursKD nn= new NearestNeighboursKD(points);
		List<Point3D> neighbors= nn.rangeQuery(query,eps,new ArrayList<Point3D>(),nn.getTree().getRoot());
		System.out.println("TRACE: " + nn.getTree().getRoot().point);

		System.out.println("number of neighbors= "+neighbors.size());
		System.out.println(neighbors);
	}   
}