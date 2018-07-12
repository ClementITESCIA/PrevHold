import serial
import datetime
from time import sleep

CapteurID = 1
NomArd = 'Arduino_1'
ArduinoID = 1024
d = datetime.datetime.now()

usb_ports = ('/dev/ttyACM0', '/dev/ttyACM1')

while 1:
    for usb in usb_ports:
        try:
            arduino = serial.Serial(usb, 9600)
            print ('Connected to arduino at ' + usb)
            f = open('test.txt','w')
            f.write ('CapteurID' + ';')
            f.write('NomArd' + ';')
            f.write('ArduinoID' + ';')
            f.write('Arduino' + ';')
            f.write('date' + '\n')
            while 1:
                try:
                    output = arduino.readline()
                    f = open('test.txt','a')
                    f.write(str(CapteurID) + ';'),
                    f.write(NomArd + ';' )
                    f.write(str(ArduinoID) + ';')
                    f.write(str(output) + ';')
                    f.write(str(d) + '\n')
                    f.close()
                    
                    f = open ('test.txt','r')
                    t = f.read()
                   
                    
                    t1 = t.replace('b\'','')
                    t2 = t1.replace('\\r\\n\'','')
                    
                    print (t2)
                    f = open('test1.txt','w')
                    f.write(str(t2) + '\n')
                    f.close()
                except KeyboardInterrupt:
                    exit()
                except serial.SerialException:
                    print('Lost connection.Trying to reconnect...')
                    break
                except IOError:
                    print('IOError...')
                    pass
        except serial.SerialException:
            print('Arduino not found at ' + usb)
            pass
    sleep(10)
