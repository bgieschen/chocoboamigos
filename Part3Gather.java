import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part3Gather{

	//Constructor
	public Part3Gather(){}
	
	//Main function
	public static void main(String args[]) throws Exception {
		String mixIP = "160.36.57.98";
		String[] target = new String[5];
		target[0] = "800ab9bccb64c1062061b4f504f7b8f7234390912f84d59d3b36d71b7e7ef932";
		target[1] = "807c4b96f91d45187216942ef3c31fef87b1ff239bcdf78bab7b68bcffa47884";
		target[2] = "802d9cf1fc235c4ba22b8eec76a18ab0b5c650c79d7e5d1db1b2d07f6ba7bdf4";
		target[3] = "80fe58ac060027269d020c4705dc4fcd18feca127c444442ef2ff58c2b383cea";
		target[4] = "801ecf8ee5e39f2a7869388a8f0408999fc96923ba73c28c783bd856ca8bf378";
		ArrayList<String> t1 = new ArrayList<String>();
		ArrayList<String> t2 = new ArrayList<String>();
		ArrayList<String> t3 = new ArrayList<String>();
		ArrayList<String> t4 = new ArrayList<String>();
		ArrayList<String> t5 = new ArrayList<String>();
		
		int i = 0,j = 0;
		String ID = "";
		String pID = "";
		String nextPeer = "";
		String peerCMD = "PEERS\n";
		int port = 0;
		Scanner parser = new Scanner(new FileReader("public.txt"));
		
		for(i = 0;i < 120;i++){
			ID = parser.next();
			port = parser.nextInt();
			System.out.print(ID + " " + port + "\n");
			//try{
				BufferedWriter writeTemp = new BufferedWriter(new OutputStreamWriter(new Socket(mixIP,port).getOutputStream()));
				BufferedReader readTemp = new BufferedReader(new InputStreamReader(new Socket(mixIP,port).getInputStream()));
				writeTemp.write(peerCMD);writeTemp.flush();
			//	for(j = 0;j < 8;j++){
					nextPeer = readTemp.readLine();
					System.out.print("::" + nextPeer + "\n");
					//pID = nextPeer.substring(0,64);
					//if(target[0].compareTo(pID) == 0){t1.add(ID);}
					//if(target[1].compareTo(pID) == 0){t2.add(ID);}
					//if(target[2].compareTo(pID) == 0){t3.add(ID);}
					//if(target[3].compareTo(pID) == 0){t4.add(ID);}
					//if(target[4].compareTo(pID) == 0){t5.add(ID);}
			//	}
			//}catch(java.net.SocketException e){
			//	System.err.print("Socket was killed\n");
			//}
			//catch(java.lang.NullPointerException e){
			//	System.err.print("Caught Some Junk, carry on\n");
			//}
		}
		for(i = 0;i < 5;i++){
			System.out.print("Node " + target[i] + "\n");
			for(j = 0;j < t1.size();j++){
				System.out.print("> " + t1.get(j) + "\n");
			}
		}
		
		
	}//END OF MAIN
}