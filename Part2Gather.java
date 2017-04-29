import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part2Gather{

	//Constructor
	public Part2Gather(){}
	
	//Main function
	public static void main(String args[]) throws Exception {
		int i = 0;
		String raw = "";
		String ID = "";
		int port = 0;
		FileReader pubOut = new FileReader("public.txt");
		BufferedReader puO = new BufferedReader(pubOut);
		for(i = 0;i < 120;i++){
			raw = puO.readLine();
			ID = raw.substring(0,64);
			port = Integer.parseInt(raw.substring(65,70));
			new Part2GatherThread(i,port).start();
		}
		pubOut.close();
	}//END OF MAIN
}