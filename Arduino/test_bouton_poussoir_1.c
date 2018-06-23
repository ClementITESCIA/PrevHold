/*
le bouton poussoir est connecté au pin 2 pour un mode INPUT_PULLUP
la Led est connectée au pins 4 avec une résistance de 220Ω
*/

//déclaration des variables
int pinBouton, pinLed;
boolean etatAllumage;
void setup()
{
  //initialisation des variables
  Serial.begin(9600);
  pinBouton = 2;
  pinLed = 4;
  etatAllumage=0;
  //définition des modes
  pinMode(pinBouton, INPUT_PULLUP);
  pinMode(pinLed, OUTPUT);
}
void loop()
{
  Serial.print(etatAllumage);
  
  if (etatAllumage) //on teste si etatAllumage est à 1
  {
    digitalWrite(pinLed, HIGH);//on allume la LED
  }
  else //sinon
  {
    digitalWrite(pinLed, LOW); //on éteint la LED
  }
  //lecture de l'état du bouton et stockage dans etatBouton
  boolean etatPinBouton = digitalRead(pinBouton);
  Serial.println(etatPinBouton);
  //test des conditions
  if (!etatPinBouton)//si bouton appuyé (donc le pin indique 0 car il est en mode INPUT_PULLUP)
  {
    if (etatAllumage) //si etatAllumage à 1
    {
      etatAllumage=0; //on le passe à 0
    }
    else //sinon
    {
      etatAllumage=1; //on le passe à 1
    }
  }
  delay(200);
}