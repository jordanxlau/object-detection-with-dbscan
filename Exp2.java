import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * Experiment 2 - Execution time
 *
 * @author Jordan Lau, 300240600
 *
*/ 
public class Exp2{

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

	public static void main(String[] args){
		args = new String[]{"kd", "0.5", "Point_Cloud_3.csv", "10"};

		String type = args[0].toLowerCase();
		double eps= Double.parseDouble(args[1]);
		List<Point3D> points= read(args[2]); // reads the csv file
		int step = Integer.parseInt(args[3]);
		
		long startTime, endTime, average;
		Point3D query;
		List<Long> times = new ArrayList<Long>();
		Neighbours nn;
		if (type.equals("kd"))
			nn = new NearestNeighboursKD(points);
		else
			nn = new NearestNeighbours(points);
		
		//run the tests
		for (int i = step; i < points.size(); i+=step){
			query = points.get(i);	

			startTime = System.nanoTime();
			nn.rangeQuery(query,eps);
			endTime = System.nanoTime();

			times.add(endTime - startTime); // in nanoseconds.
		}

		long sum = 0;
		for (long l : times)
			sum+=l;

		average = sum/times.size();

		System.out.println("average compute time for rangeQuery using " + type + " method (nanoseconds): " + average);

	}
}