import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part3Crack {
	
	// Constructor
	public Part3Crack(String args[]) throws Exception {
		
		// Set vars
		int i = 0,j = 0,k = 0;
		HashMap<String,Integer> Private = new HashMap<String,Integer>();
		ArrayList<String> peerID = new ArrayList<String>();
		//ArrayList<Integer> peerSoc = new ArrayList<Integer>();
		ArrayList<Socket> sockets = new ArrayList<Socket>();
		HashMap<String,Integer> Public = new HashMap<String,Integer>();
		HashMap<Integer,String> PublicRev = new HashMap<Integer,String>();
		String nextPeer = "";
		String peerCMD = "PEERS\n";
		String pID = "";
		int pSocket = 0;
		boolean done=false;
		String b1 = "80b370d237f9e98408995812a5b73e32c35d5bf6ebf025df6b2426103eda51b7";
		String b2 = "80500146a93cca92b3cc800197657a3170bc8decc379a15dc793f275fb89e1b1";
		String b3 = "8023be734e56021878a40619dc5450d6bf1d6975cd7be6f975aebcb8d6418f12";
		/*String[] target = new String[5];
		target[0] = "800ab9bccb64c1062061b4f504f7b8f7234390912f84d59d3b36d71b7e7ef932";
		target[1] = "807c4b96f91d45187216942ef3c31fef87b1ff239bcdf78bab7b68bcffa47884";
		target[2] = "802d9cf1fc235c4ba22b8eec76a18ab0b5c650c79d7e5d1db1b2d07f6ba7bdf4";
		target[3] = "80fe58ac060027269d020c4705dc4fcd18feca127c444442ef2ff58c2b383cea";
		target[4] = "801ecf8ee5e39f2a7869388a8f0408999fc96923ba73c28c783bd856ca8bf378";*/
		HashMap<String,List<Integer>> private_ids = new HashMap<String,List<Integer>>();
		List<Integer> temp_list;
		//List<List<Integer>> private_ids = new ArrayList<List<Integer>>(120);
		/*ArrayList<Integer> t1 = new ArrayList<Integer>();
		ArrayList<Integer> t2 = new ArrayList<Integer>();
		ArrayList<Integer> t3 = new ArrayList<Integer>();
		ArrayList<Integer> t4 = new ArrayList<Integer>();
		ArrayList<Integer> t5 = new ArrayList<Integer>();*/
		// Add Bootstrap peers
		peerID.add(b1);
		peerID.add(b2);
		peerID.add(b3);
		Public.put(b1,15053);
		Public.put(b2,15074);
		Public.put(b3,15049);
		PublicRev.put(15053,b1);
		PublicRev.put(15074,b2);
		PublicRev.put(15049,b3);
		String mixIP = "160.36.57.98";
		sockets.add(new Socket(mixIP,15053));
		sockets.add(new Socket(mixIP,15074));
		sockets.add(new Socket(mixIP,15049));
		
		while(!done){
		//for(k = 0;k < 100;k++){
			done=true;
			if(private_ids.size()<120){
				done=false;
			} else {
				for(String key : private_ids.keySet()){
					temp_list = private_ids.get(key);
					if(temp_list.size()!=8){
						done=false;
					}
				}
			}
			for(i = 0;i < sockets.size();i++){
				try{
					BufferedWriter writeTemp = new BufferedWriter(new OutputStreamWriter(sockets.get(i).getOutputStream()));
					BufferedReader readTemp = new BufferedReader(new InputStreamReader(sockets.get(i).getInputStream()));
					writeTemp.write(peerCMD);writeTemp.flush();
					for(j = 0;j < 8;j++){
						nextPeer = readTemp.readLine();
						try{
							pID = nextPeer.substring(0,64);
							if(private_ids.containsKey(pID)){
								temp_list = private_ids.get(pID);
								if(!temp_list.contains(sockets.get(i).getPort())){
									temp_list.add(sockets.get(i).getPort());
									private_ids.put(pID,temp_list);
								}
							}
							/*if(target[0].compareTo(pID) == 0){if(!t1.contains(sockets.get(i).getPort())){t1.add(sockets.get(i).getPort());}}
							if(target[1].compareTo(pID) == 0){if(!t2.contains(sockets.get(i).getPort())){t2.add(sockets.get(i).getPort());}}
							if(target[2].compareTo(pID) == 0){if(!t3.contains(sockets.get(i).getPort())){t3.add(sockets.get(i).getPort());}}
							if(target[3].compareTo(pID) == 0){if(!t4.contains(sockets.get(i).getPort())){t4.add(sockets.get(i).getPort());}}
							if(target[4].compareTo(pID) == 0){if(!t5.contains(sockets.get(i).getPort())){t5.add(sockets.get(i).getPort());}}*/
							pSocket = Integer.parseInt(nextPeer.substring(78,83));
							if(!peerID.contains(pID)){
								peerID.add(pID);
								try{
									//System.out.print(pSocket + "  " + pID + "\n");
									Socket curP = new Socket(mixIP,pSocket);
									//System.out.print(pID + " is public\n");
									Public.put(pID,pSocket);
									PublicRev.put(pSocket,pID);
									sockets.add(curP);
								}catch(java.net.ConnectException e){
									//System.out.print(pID + " is private\n");
									Private.put(pID,pSocket);
									temp_list = new ArrayList<Integer>();
									private_ids.put(pID,temp_list);
								}
								//System.out.print("Size: " + peerID.size() + " " + Public.size() + " " + Private.size() + "\n");
							}
						}catch(java.lang.NullPointerException e){
							System.err.print("Caught Some Junk, carry on\n");
						}
						catch(Exception e){
							System.err.print("Caught Some Junk again, carry on\n");
						}
					}
					//readTemp.close();
					//writeTemp.close();
					//System.out.print(" THE PEER: " + sockets.get(i) + "\n");
				}catch(java.net.SocketException e){
					System.out.print("Socket was killed\n");
				}
			}
		//}
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
				prO.flush();
			}else if(Public.containsKey(peerID.get(i))){
				puO.write(peerID.get(i) + " " + Public.get(peerID.get(i)) + "\n");
				puO.flush();
				c2++;
			}else{
				System.out.print("Something went wrong\n");
			}
		}
		privOut.close();
		pubOut.close();
		//System.out.print("check:" + c1 + "  " + c2 + "\n");
		for(String key : private_ids.keySet()){
			temp_list = private_ids.get(key);
			System.out.print("Node " + key + "\n");
			for(i=0;i<temp_list.size();i++){
				System.out.print("> " + PublicRev.get(temp_list.get(i)) + "\n");
			}
		}
		/*System.out.print("Node " + target[0] + "\n");
		for(i = 0;i < t1.size();i++){
			System.out.print("> " + PublicRev.get(t1.get(i)) + "\n");
		}
		System.out.print("Node " + target[1] + "\n");
		for(i = 0;i < t2.size();i++){
			System.out.print("> " + PublicRev.get(t2.get(i)) + "\n");
		}
		System.out.print("Node " + target[2] + "\n");
		for(i = 0;i < t3.size();i++){
			System.out.print("> " + PublicRev.get(t3.get(i)) + "\n");
		}
		System.out.print("Node " + target[3] + "\n");
		for(i = 0;i < t4.size();i++){
			System.out.print("> " + PublicRev.get(t4.get(i)) + "\n");
		}
		System.out.print("Node " + target[4] + "\n");
		for(i = 0;i < t5.size();i++){
			System.out.print("> " + PublicRev.get(t5.get(i)) + "\n");
		}*/
	}
	
	public static void main(String args[]) throws Exception {
		new Part3Crack(args);
	}//END OF MAIN
}