from bluetooth import *

socket = BluetoothSocket(RFCOMM)
print(socket.connect(("00:19:10:08:55:8D", 1)))
print("bluetooth connected!")
tmp = 1
while True:
    tmp+=1
    data = socket.recv(1024)
    print("%s %d" %(data, tmp))
    
socket.close()
