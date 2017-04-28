import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part2GatherThread extends Thread{
	private String ID = "";
	private int port = 0;
	String mixIP = "160.36.57.98";
	

	//Constructor
	public Part2GatherThread(String uID,int uPort){
		this.ID = uID;
		this.port = uPort;
	}
	
	public void run(){
		String nextLine = "";
		try(
			// make the stdin buffer
			BufferedReader in = new BufferedReader(new InputStreamReader((new Socket(mixIP,port)).getInputStream()));
		){
			//wait for input, pass it to the console
			while((nextLine = in.readLine()) != null){
				System.out.println(ID + ":" + nextLine);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}