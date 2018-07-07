//mouvement

int ledPine = 5;  // LED on Pin 5 of Arduino
int pirPin = 7; // Input for HC-S501
int pirValue; // Place to store read PIR Value


//force
int fsrAnalogPin = 0; // FSR branché sur pin Analog 0
int LEDpin = 13;
int fsrReading;  // Lecture analogique de la tension du pont 
//    diviseur FSR + Resistance Pull-Down
int LEDbrightness;

//fumee
const int MQPin = A15;

// humidite
#include <SimpleDHT.h>
int pinDHT11 = 2;
SimpleDHT11 dht11;

//servomoteur
/*#include <Servo.h>
Servo myservo;  // create servo object to control a servo
// twelve servo objects can be created on most boards
int pos = 0;    // variable to store the servo position
*/
//piezo
const int sensorPin=0;
const int ledPin= 13;
const int threshold= 100;

void setup() {

  // mouvement
  pinMode(ledPine, OUTPUT);
  pinMode(pirPin, INPUT);
  digitalWrite(ledPine, LOW);
  // humidite , fumee et force
  Serial.begin(9600);

 
  pinMode(ledPin, OUTPUT);
  //force
  pinMode(LEDpin, OUTPUT);
  
  //servomoteur
  //myservo.attach(9);  // attaches the servo on pin 9 to the servo object
}

void loop() {

  // force
   fsrReading = analogRead(fsrAnalogPin);
    Serial.print("Analog reading = ");
    Serial.println(fsrReading);
    // Nous devons convertir la valeur analogique lue (0-1023) 
    // en une valeur utilisable par analogWrite (0-255).
    // C'est ce que fait l'instruction map!
    LEDbrightness = map(fsrReading, 0, 1023, 0, 255);
    // LED gets brighter the harder you press
    delay(1000);
  
  //mouvement
  pirValue = digitalRead(pirPin);
  digitalWrite(ledPine, pirValue);
  
  // fumee
  Serial.println(analogRead(MQPin));
  delay(1000);
  
  // humidite
  
  byte temperature = 0;
  byte humidity = 0;
  byte data[40] = {0};

  if (dht11.read(pinDHT11, &temperature, &humidity, data)) {
    return;
  }
  
  Serial.print("Sample RAW Bits: ");
  for (int i = 0; i < 40; i++) {
    Serial.print((int)data[i]);
    if (i > 0 && ((i + 1) % 4) == 0) {
      Serial.print(' ');
    }
  }
  Serial.println("");
  
  Serial.print("Sample OK: ");
  Serial.print((int)temperature); Serial.print(" *C, ");
  Serial.print((int)humidity); Serial.println(" %");
  
  // DHT11 sampling rate is 1HZ.
  delay(100);
  // piezo
  int val= analogRead(sensorPin);
    if (val >= threshold)
    {
    digitalWrite(ledPin, HIGH);
    delay(5000);
    digitalWrite(ledPin, LOW);
    }
    else
    digitalWrite(ledPin, LOW);
   delay(100);
  // servo moteur
 /* for (pos = 0; pos <= 220; pos += 1) { // goes from 0 degrees to 180 degrees
    // in steps of 1 degree
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }
  for (pos = 220; pos >= 0; pos -= 1) { // goes from 180 degrees to 0 degrees
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }*/
  
    
}

