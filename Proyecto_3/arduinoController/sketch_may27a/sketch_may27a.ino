const int ledPin1 = 10;
const int ledPin2 = 8;
const int ledPin3 = 7;
const int ledPin4 = 6;
const int ledPin5 = 4;

void setup() {
  pinMode(ledPin1, OUTPUT); //Led pin 1
  pinMode(ledPin2,OUTPUT); //Led pin 2
  pinMode(ledPin3,OUTPUT); //Led pin 3
  pinMode(ledPin4,OUTPUT); //Led pin 4
  pinMode(ledPin5,OUTPUT); //Led pin 5
  pinMode(3,INPUT_PULLUP); //UpButton pin
  pinMode(5,INPUT_PULLUP); //DownButton pin 
  pinMode(9,INPUT_PULLUP); //SelectButton pin
  pinMode(12,OUTPUT); //Buzzer pin
  Serial.begin(9600);  // Initialize serial communication
}

void loop() {
  if (Serial.available()) {
    String message = Serial.readStringUntil('\n');  // Read the incoming message until newline
    message.trim();  // Remove leading/trailing whitespaces
    
    if (message == "1") {
      digitalWrite(10, HIGH);
      delay(100);
      digitalWrite(10, LOW);
      delay(100); 
    }
    if (message == "2") {
      digitalWrite(8, HIGH);
      delay(100);
      digitalWrite(8, LOW);
      delay(100); 
    }
    if (message == "3") {
      digitalWrite(7, HIGH);
      delay(100);
      digitalWrite(7, LOW);
      delay(100); 
    }
    if (message == "4") {
      digitalWrite(6, HIGH);
      delay(100);
      digitalWrite(6, LOW);
      delay(100); 
    }
    if (message == "5") {
      digitalWrite(4, HIGH);
      delay(100);
      digitalWrite(4, LOW);
      delay(100); 
    }
    if (message == "M") {
      digitalWrite(12, HIGH);
      delay(100);
      digitalWrite(12, LOW);
      delay(100);
      digitalWrite(12, HIGH);
      delay(100);
      digitalWrite(12, LOW);
      delay(100); 
    }
  }
  if(digitalRead(3)==LOW){
    Serial.write("U");
    delay(500);
  }
  if(digitalRead(5)==LOW){
    Serial.write("D");
    delay(500);
  }
  if(digitalRead(9)==LOW){
    Serial.write("S");
    delay(500);
  }
}