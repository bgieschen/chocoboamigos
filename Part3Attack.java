import java.util.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;

public class Part3Attack{
	public Part3Attack(){}
	
	public static void main(String args[]) throws Exception {
		int i = 0,j = 0;
		int groupCounter=0;
		int round = 0;
		int output_id;
		int timestamp;
		int current_time=0;
		int max1, max2;
		int friend1, friend2;
		int[][] senders = new int[16][8];
		int[][] recipients = new int[16][8];
		int[] final_friends = new int[10];
		int[] temp_array;
		
		boolean isOffer=true;
		boolean isAPublicProof=false;
		boolean isFirstIteration=false;
		boolean isSecondIteration=false;
		
		String[] target = new String[5];
		target[0] = "800ab9bccb64c1062061b4f504f7b8f7234390912f84d59d3b36d71b7e7ef932";
		target[1] = "807c4b96f91d45187216942ef3c31fef87b1ff239bcdf78bab7b68bcffa47884";
		target[2] = "802d9cf1fc235c4ba22b8eec76a18ab0b5c650c79d7e5d1db1b2d07f6ba7bdf4";
		target[3] = "80fe58ac060027269d020c4705dc4fcd18feca127c444442ef2ff58c2b383cea";
		target[4] = "801ecf8ee5e39f2a7869388a8f0408999fc96923ba73c28c783bd856ca8bf378";
		
		String[][] target_groups = new String[5][8];
		String[][] private_groups = new String[120][8];
		
		HashMap<String,Integer> PublicID = new HashMap<String,Integer>();
		String[] PublicIDArray = new String[120];
		String[] sent_messages = new String[16];
		String[] public_proofs = new String[16];
		String[] proofs = new String[16];
		String message;
		String ID  = "";
		String raw = "";
		
		HashMap<String,int[]> TargetFriends = new HashMap<String,int[]>();
		
		FileReader pubOut = new FileReader("public.txt");
		BufferedReader puO = new BufferedReader(pubOut);
		Scanner group_parser = new Scanner(new FileReader("pubToPriv.txt"));

		
		//Read in public IDs and assign them indices corresponding to the output data
		for(i=0;i<120;i++){
			raw = puO.readLine();
			ID = raw.substring(0,64);
			PublicIDArray[i]=ID;
			PublicID.put(ID,i);
		}
		puO.close();
		pubOut.close();
		
		i=0;
		j=0;
		while(group_parser.hasNextLine()){
			message = group_parser.next();
			if(message.equals("NODE")){
				ID = group_parser.nextLine().substring(1);
				i=PublicID.get(ID);
			} else if(message.equals(">")){
				private_groups[i][j%8]=group_parser.nextLine().substring(1);
				j++;
			}
		}
		group_parser.close();	
		
		//initialize HashMap values
		for(i=0;i<5;i++){
			temp_array = new int[120];
			Arrays.fill(temp_array,0);
			TargetFriends.put(target[i],temp_array);
		}
		
		//initialize sender and recipient sentinel values
		for(i=0;i<16;i++){
			for(j=0;j<8;j++){
				senders[i][j]=-1;
				recipients[i][j]=-1;
			}
		}
		Arrays.fill(sent_messages,"");
		Arrays.fill(public_proofs,"");
		Arrays.fill(proofs,"");
		
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
					isFirstIteration=true;
					current_time = timestamp;
					for(i=0;i<16;i++){
						for(j=0;j<8;j++){
							senders[i][j]=-1;
							recipients[i][j]=-1;
						}
					}
					Arrays.fill(sent_messages,"");
					Arrays.fill(public_proofs,"");
					Arrays.fill(proofs,"");
				} else if(message.equals("ACK") && isOffer==true){
				//first ACK message
					isOffer = false;
					isFirstIteration=true;
					current_time = timestamp;
				}
				
				if(Math.abs(timestamp-current_time)>2){
					//next 10 second iteration (with 2 second buffer)
					current_time = timestamp;
					if(isFirstIteration){
						isFirstIteration = false;
						isSecondIteration = true;
					} else {
						isSecondIteration = false;
					}
				}
				
				if(isFirstIteration){
					if((!isOffer) && (!message.equals("ACK"))){
						for(i=0;i<16;i++){
							if(public_proofs[i].equals(message)){
								break;
							} else if(public_proofs[i].equals("")){
								public_proofs[i]=message;
							}
						}
					}
				}
				
				if(isSecondIteration){
					if(isOffer){
						if(message.charAt(0) == '0'){
							for(i=0;i<16;i++){
								if(sent_messages[i].equals("")){
									sent_messages[i]=message;
								}
								if(message.equals(sent_messages[i])){
									for(j=0;j<8;j++){
										if(senders[i][j]==-1){
											senders[i][j]=output_id;
											break;
										}
									}
								}
							}
						}
					} else {
						for(i=0;i<16;i++){
							if(message.equals(public_proofs[i])){
								isAPublicProof=true;
								break;
							}
						}
						if(!isAPublicProof){
							for(i=0;i<16;i++){
								if(!message.equals("ACK")){	
									if(proofs[i].equals("")){
										proofs[i]=message;
									}
									if(message.equals(proofs[i])){
										for(j=0;j<8;j++){
											if(recipients[i][j]==-1){
												recipients[i][j]=output_id;
												break;
											}
										}
									}
								}
							}
						}
						isAPublicProof=false;
					}
				}
				
				/*
				for(i=0;i<5;i++){
					for(j=0;j<8;j++){
						if(*/
						
			}
			parser.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		/*for(i=0;i<5;i++){
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
		}*/
	}
}