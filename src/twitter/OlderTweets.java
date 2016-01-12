package twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.Exporter;

public class OlderTweets {
	public static void main(String[] args) throws IOException, InterruptedException{
		//String query = "java -jar lib/got.jar querysearch=\"muse milano\" since=2015-10-01 until=2015-10-03";
		//System.out.println(query);
	    /*Process proc = Runtime.getRuntime().exec(query);
	    proc.waitFor();
	    // Then retreive the process output
	    InputStream in = proc.getInputStream();
	    InputStream err = proc.getErrorStream();

	    byte b[]=new byte[in.available()];
	    in.read(b,0,b.length);
	    System.out.println(new String(b));

	    byte c[]=new byte[err.available()];
	    err.read(c,0,c.length);
	    System.out.println(new String(c));*/
	/*	String[] command = {"java", "-jar", "lib/got.jar", "querysearch=\"muse milano\""};
		String q = "";  
		for (String s : command){
			q = q + " " + s; 
		}
		System.out.println(q);
		ProcessBuilder pb = new ProcessBuilder(command);
		
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String s = "";
		while((s = in.readLine()) != null){
		    System.out.println(s);
		}
		int status = p.waitFor();
		System.out.println("Exited with status: " + status);*/
		String[] arr = {"querysearch=muse milano", "since=2015-10-10", "until=2015-10-11"};
		System.out.println(Exporter.search(arr));
	}

}
