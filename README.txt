For General information:

The Following files are relevant:

bootstrapInfo.txt
README.txt

The bootstrapInfo file contains all the info be gained from our bootstrapping
with our group name. The README, this file, is all the bookeeping info on which
files are relevant to which sections of the project.

For Part 1:

The Following files are relevant:

part1.py
Part1Rounds.txt
friends.csv

Running the python script part1.py will generate the friends.csv file. The
Part1Rounds.txt is hard-coded into the python script and must be in the same
working directory when it is run to correctly produce the friends.csv file.
The Friends.csv file contains the solutions to part1 of the assignment, the 
friends of all the 0 peers in a csv format

For Part 2:

The Following files are relevant:

Part2Crack.java
Part2Gather.java
Part2GatherThread.java
Part2Attack.java
Part2Friends.csv
private.txt
public.txt
output.txt

Running the Part2Crack.java will generate the lists of all the public and
private nodes IDs and sockets. This information is written to the public.txt
and private.txt files. There is a good deal of bookeeping information which
is printed to the console while Part2Crack is running, this can be ignored.
When running Part2Crack you may also encounter times where it does not stop
or encounters an error. This occurs because it reads irrelevant information
from the rounds (ACK or OFFER messages are being read). As long as this is run
between rounds while there are no other possible messages being sent to interfere,
it will complete rather quickly.

Running Part2Gather.java will begin the collection of data from all the public
peers on the network. This requires the public.txt file be in your working 
directory to run as well  as Part2GatherThread.java when compiled as the program
uses 120 threads to maintain the connections to each of the public nodes
within the mix. The gathered data from public nodes is written to 
the console, so we redirected it to a text file when running, output.txt. 
We ran this for about 9 hours to gather our rounds, and observation of the 
output.txt file will demonstrate our convention of writing the index of 
the node followed by 1 colon, and time stamp, and another colon before each 
message a particular node sent is displayed. We did this for our own bookkeeping
purposes when we later parsed the data.

Part2Attack.java will parse the output.txt file and determine the friends of 
our targeted nodes. the output.txt file must be in the same working directory
when Part2Attack.java is run, as it is hard-coded. When run it outputs the 
solution in csv format to the console. We piped the output solution to a 
.csv file, the Part2Friends.csv file. This contains the target nodes and their
friends in the appropriate format.

For Part 3:

The Following files are relevant:

Part3Crack.java
pubToPriv.txt
Part3Attack.java
Part3Friends.csv (Not in the file)
output.txt

Part3Crack, when run, generates lists, for all the target private nodes, of 
their 8 connected public nodes. This is needed for our attack method. This 
is written to the console, so we piped it to a text file, pubToPriv.txt

Part3Attack.java, when run, generates the friends of the
private peers. This requires the output.txt file and the pubToPriv.txt file
both be in your working directory for this to run. The solution, like part 2,
is sent to the console. We piped this to Part3Friends.csv. As with the above
solutions, this was in csv format.

*****our solution never fully worked, so the Part3Friends.csv is not present*****
