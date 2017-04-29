import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part2Attack{
	public Part2Attack(){}
	
	public static void main(String args[]) throws Exception {
		int i = 0;
		int output_id;
		int timestamp;
		
		String message;
		String target0 = "806966aa3ff417c916fffc87046c7f886e3933577948de20f0dfa4a3ce513ff7";
		String target1 = "80d6e13dd6d6bd383aa29eccaefc67ea40d8f7863570cb57e6eaf9ed71f9e012";
		String target2 = "8044b8a5d0afc708e32f293dcd58468e9b7f7fd1d85b5a30fdee2e0e7b2cc661";
		String target3 = "805f2aec5c3f98790d8afa9219279fde4aaa5fe7924e857d6810bc9399511abe";
		String target4 = "80292158866c2107dda1c71987e3da44882eadeec59bdf8e55760ae6f5cb75c1";
		String ID  = "";
		String raw = "";	
		
		String[] PublicID = new String[120];
		HashMap<String,HashMap<String,Integer>> TargetFriends = new HashMap<String,HashMap<String,Integer>>(); 
		
		FileReader pubOut = new FileReader("public.txt");
		BufferedReader puO = new BufferedReader(pubOut);

		
		//Read in public IDs and assign them indices corresponding to the output data
		for(i=0;i<120;i++){
			raw = puO.readLine();
			ID = raw.substring(0,64);	
			PublicID[i] = ID;
		}
		puO.close();
		pubOut.close();
		
		//Parsing from output file
		try{
			Scanner parser = new Scanner(new FileReader("output.txt"));
			parser.useDelimiter(":");
			while(parser.hasNextLine()){
				//parse each variable
				output_id = parser.nextInt();
				timestamp = parser.nextInt();
				message = parser.nextLine().substring(1);
				
				//process data
				
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}