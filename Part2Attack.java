import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part2Attack{
	public Part2Attack(){}
	
	public static void main(String args[]) throws Exception {
		int i = 0,j = 0;
		int round = 0;
		int output_id;
		int timestamp;
		int current_time=0;
		int max1, max2;
		int friend1, friend2;
		int[] target_senders = new int[5];
		int[] final_friends = new int[10];
		int[] temp_array;
		
		boolean isOffer=true;
		boolean isOrigin=false;
		boolean isDestination=false;
		
		String[] target = new String[5];
		target[0] = "806966aa3ff417c916fffc87046c7f886e3933577948de20f0dfa4a3ce513ff7";
		target[1] = "80d6e13dd6d6bd383aa29eccaefc67ea40d8f7863570cb57e6eaf9ed71f9e012";
		target[2] = "8044b8a5d0afc708e32f293dcd58468e9b7f7fd1d85b5a30fdee2e0e7b2cc661";
		target[3] = "805f2aec5c3f98790d8afa9219279fde4aaa5fe7924e857d6810bc9399511abe";
		target[4] = "80292158866c2107dda1c71987e3da44882eadeec59bdf8e55760ae6f5cb75c1";
		
		String[] PublicID = new String[120];
		String message;
		String ID  = "";
		String raw = "";
		
		HashMap<String,int[]> TargetFriends = new HashMap<String,int[]>();
		
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
		
		//initialize HashMap values
		for(i=0;i<5;i++){
			temp_array = new int[120];
			Arrays.fill(temp_array,0);
			TargetFriends.put(target[i],temp_array);
		}
		
		//Parsing from output file
		try{
			Scanner parser = new Scanner(new FileReader("output.txt"));
			parser.useDelimiter(":");
			while(parser.hasNextLine()){
				output_id = parser.nextInt();
				timestamp = parser.nextInt();
				message = parser.nextLine().substring(1);
				
				if(message.equals("OFFER") && isOffer==false){
				//Brand new round, reset
					round++;
					isOffer = true;
					isOrigin = true;
					current_time = timestamp;
					Arrays.fill(target_senders,0);
				} else if(message.equals("ACK") && isOffer==true){
				//first ACK message
					isOffer = false;
					isDestination=true;
					current_time = timestamp;
				}
				
				if(Math.abs(timestamp-current_time)>2){
					//next 10 second iteration (with 2 second buffer)
					current_time = timestamp;
					isOrigin=false;
					isDestination=false;
				}
				
				if(isOrigin){
					for(i=0;i<5;i++){
						if(PublicID[output_id].equals(target[i])){
							target_senders[i]=1;
						}
					}
				}
				
				if(isDestination){
					for(i=0;i<5;i++){
						if(target_senders[i]==1){
							temp_array = TargetFriends.get(target[i]);
							temp_array[output_id]++;
							TargetFriends.put(target[i],temp_array);
						}
					}
				}
			}
			parser.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		for(i=0;i<5;i++){
			max1=0;
			max2=0;
			friend1=0;
			friend2=0;
			temp_array = TargetFriends.get(target[i]);
			for(j=0;j<120;j++){
				if(temp_array[j]>=max1){
					max2=max1;
					max1=temp_array[j];
					friend2=friend1;
					friend1=j;
				}
			}
			final_friends[2*i]=friend1;
			final_friends[2*i+1]=friend2;
		}
			
		for(i=0;i<5;i++){
			System.out.println(target[i]+","+PublicID[final_friends[2*i]]+","+PublicID[final_friends[2*i+1]]);
		}
	}
}