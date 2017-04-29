import java.util.*;
import java.io.*;
import java.net.*;
import java.time.Instant;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;

public class Part2GatherThread extends Thread{
	private int ID = 0;
	private int port = 0;
	String mixIP = "160.36.57.98";
	Date today;
	

	//Constructor
	public Part2GatherThread(int uID,int uPort){
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
				System.out.println(ID + ":" + System.currentTimeMillis()/1000 + ":" + nextLine);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}