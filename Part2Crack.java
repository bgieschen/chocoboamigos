import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part2Crack {
	
	// Constructor
	public Part2Crack(String args[]) throws Exception {
		
		// Set vars
		int i = 0,j = 0;
		HashMap<String,Integer> Private = new HashMap<String,Integer>();
		ArrayList<String> peerID = new ArrayList<String>();
		//ArrayList<Integer> peerSoc = new ArrayList<Integer>();
		ArrayList<Socket> sockets = new ArrayList<Socket>();
		HashMap<String,Integer> Public = new HashMap<String,Integer>();
		String nextPeer = "";
		String peerCMD = "PEERS\n";
		String pID = "";
		int pSocket = 0;
		String b1 = "8071ec3d76ba25b1e44ebb68ef7f49d64cb61355aa3194b82670d3456b6fef3f";
		String b2 = "80d6e13dd6d6bd383aa29eccaefc67ea40d8f7863570cb57e6eaf9ed71f9e012";
		String b3 = "80400b262a7720103da17f6e48652fa3739e347ecfb18e93d74b9589fd8cfcc6";
		
		// Add Bootstrap peers
		peerID.add(b1);
		peerID.add(b2);
		peerID.add(b3);
		Public.put(b1,15021);
		Public.put(b2,15082);
		Public.put(b3,15075);
		String mixIP = "160.36.57.98";
		sockets.add(new Socket(mixIP,15021));
		sockets.add(new Socket(mixIP,15082));
		sockets.add(new Socket(mixIP,15075));
		
		while(peerID.size() < 240){
			for(i = 0;i < sockets.size();i++){
				try{
					BufferedWriter writeTemp = new BufferedWriter(new OutputStreamWriter(sockets.get(i).getOutputStream()));
					BufferedReader readTemp = new BufferedReader(new InputStreamReader(sockets.get(i).getInputStream()));
					writeTemp.write(peerCMD);writeTemp.flush();
					for(j = 0;j < 8;j++){
						nextPeer = readTemp.readLine();
						try{
							pID = nextPeer.substring(0,64);
							//System.out.print(nextPeer.substring(0,1) + "\n");
							pSocket = Integer.parseInt(nextPeer.substring(78,83));
							if(!peerID.contains(pID)){
								peerID.add(pID);
								//peerSoc.add(pSocket);
								try{
									//System.out.print(pSocket + "  " + pID + "\n");
									Socket curP = new Socket(mixIP,pSocket);
									System.out.print(pID + " is public\n");
									Public.put(pID,pSocket);
									sockets.add(curP);
								}catch(java.net.ConnectException e){
									System.out.print(pID + " is private\n");
									Private.put(pID,pSocket);
								}
								System.out.print("Size: " + peerID.size() + " " + Public.size() + " " + Private.size() + "\n");
							}
						}catch(java.lang.NullPointerException e){
							System.err.print("Caught Some Junk, carry on\n");
						}
					}
					//readTemp.close();
					//writeTemp.close();
					//System.out.print(" THE PEER: " + sockets.get(i) + "\n");
				}catch(java.net.SocketException e){
					System.out.print("Socket was killed\n");
				}
			}
		}
		//System.out.print("SIZE: " + peerID.size() + "\n");
		FileWriter privOut = new FileWriter("private.txt");
		BufferedWriter prO = new BufferedWriter(privOut);
		FileWriter pubOut = new FileWriter("public.txt");
		BufferedWriter puO = new BufferedWriter(pubOut);
		//FileWriter allOut = new FileWriter("all.txt");
		//BufferedWriter alO = new BufferedWriter(allOut);
		int c1 = 0,c2 = 0;
		for(i = 0;i < 240;i++){
			//alO.write(peerID.get(i) + " " + peerSoc.get(i) + "\n");
			if(Private.containsKey(peerID.get(i))){
				c1++;
				prO.write(peerID.get(i) + " " + Private.get(peerID.get(i)) + "\n");
			}else if(Public.containsKey(peerID.get(i))){
				puO.write(peerID.get(i) + " " + Public.get(peerID.get(i)) + "\n");
				c2++;
			}else{
				System.out.print("Something went wrong\n");
			}
		}
		privOut.close();
		pubOut.close();
		System.out.print("check:" + c1 + "  " + c2 + "\n");
	}
	
	public static void main(String args[]) throws Exception {
		new Part2Crack(args);
	}//END OF MAIN
}