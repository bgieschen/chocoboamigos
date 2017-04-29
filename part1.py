import sys
import csv
from collections import Counter
from collections import defaultdict
f = open("part1Rounds.txt")
#o = open("friends.csv",'w')

a0list = []
a0 = ['a0',a0list]
b0list = []
b0 = ['b0',b0list]
c0list = []
c0 = ['c0',c0list]
d0list = []
d0 = ['d0',d0list]
e0list = []
e0 = ['e0',a0list]
f0list = []
f0 = ['f0',f0list]
g0list = []
g0 = ['g0',g0list]
h0list = []
h0 = ['h0',h0list]
i0list = []
i0 = ['i0',i0list]
j0list = []
j0 = ['j0',j0list]
k0list = []
k0 = ['k0',k0list]
l0list = []
l0 = ['l0',l0list]
m0list = []
m0 = ['m0',m0list]
n0list = []
n0 = ['n0',n0list]
o0list = []
o0 = ['o0',o0list]
p0list = []
p0 = ['p0',p0list]
q0list = []
q0 = ['q0',q0list]
r0list = []
r0 = ['r0',r0list]
s0list = []
s0 = ['s0',s0list]
t0list = []
t0 = ['t0',t0list]
u0list = []
u0 = ['u0',u0list]
v0list = []
v0 = ['v0',v0list]
w0list = []
w0 = ['w0',w0list]
x0list = []
x0 = ['x0',x0list]
y0list = []
y0 = ['y0',y0list]
z0list = []
z0 = ['z0',z0list]

plist = [a0,b0,c0,d0,e0,f0,g0,h0,i0,j0,k0,l0,m0,n0,o0,p0,q0,r0,s0,t0,u0,v0,w0,x0,y0,z0]

while True:
	line1 = f.readline()
	line2 = f.readline()
	if not line2: break

	tlist = []
	words = line1.split("'")
	for word in words:
		if word.endswith('0'):
			tlist.append(word)
	#print tlist




#for line in f:
	#tlist = []
	#words = line.split("'")
	#for word in words:
		#if word.endswith('0'):
			#tlist.append(word)
	#print tlist

	#line = next(f)
	alist = []
	awords = line2.split("'")
	for aword in awords:
		if len(aword) == 2:
			if (not (aword.endswith(' '))):
				if (not (aword.endswith('\n'))):
					alist.append(aword)
					#print alist


	for element in tlist:
		for target in plist:
			if target[0] == element:
				target[1].extend(alist)

	#line = next(f)

#for blank in plist:
#	print "/n"
#	print blank

with open('friends.csv', 'w') as csvfile:
	#writer = csv.writer(csvfile)
	for section in plist:
		c = Counter(section[1])
		#print section[0]
		#print c
		#for node, count in c.most_common(2):
			#print '%s: %d' % (node, count)
		
		#print '%s,%s,%s'%(section[0],c.most_common(1)[0][0],c.most_common(2)[1][0])
		string = '%s,%s,%s\n'%(section[0],c.most_common(1)[0][0],c.most_common(2)[1][0])
		csvfile.write(string)
		#for letter in section:
			#print letter
			#print section[letter]
		#print Counter(section[1])

