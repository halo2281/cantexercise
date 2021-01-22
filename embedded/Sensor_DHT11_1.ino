#include <SoftwareSerial.h>
#include <DHT.h>
#define DHTPIN A0
#define DHTTYPE DHT11

int TX=8;
int RX=7;
SoftwareSerial bluetooth(RX, TX);

DHT dht(DHTPIN, DHTTYPE);
 
void setup(){
  Serial.begin(9600);
  bluetooth.begin(9600);
}
 
void loop(){
    int h = dht.readHumidity();
    int t = dht.readTemperature();
    
    bluetooth.print(h);
    bluetooth.print("/");
    bluetooth.print(t);
    delay(1000);
}
