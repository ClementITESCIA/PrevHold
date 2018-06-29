
  int fsrAnalogPin = 0; // FSR branché sur pin Analog 0
  int LEDpin = 13; // connecter LED rouge sur pin 11 (pin PWM)
  int fsrReading;  // Lecture analogique de la tension du pont 
                   //    diviseur FSR + Resistance Pull-Down
  int LEDbrightness;

  void setup(void) {
    Serial.begin(9600); // Envoi de message de déboggage sur connexion série
                        // Visible dans le Moniteur Série d'Arduino IDE
    pinMode(LEDpin, OUTPUT);
  }

  void loop(void) {
    fsrReading = analogRead(fsrAnalogPin);
    Serial.print("Analog reading = ");
    Serial.println(fsrReading);
    // Nous devons convertir la valeur analogique lue (0-1023) 
    // en une valeur utilisable par analogWrite (0-255).
    // C'est ce que fait l'instruction map!
    LEDbrightness = map(fsrReading, 0, 1023, 0, 255);
    // LED gets brighter the harder you press
    analogWrite(LEDpin, LEDbrightness);
    delay(100);
  }
