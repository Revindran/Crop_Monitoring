 #define DHTTYPE DHT11   // DHT 11
#define dht_dpin 0      //GPIO-0 D3 pin of nodemcu

int Raw       = A0;      //Analog channel A0 as used to measure temperature
int threshold = 16;      //Nodemcu digital pin water sensor read-GPIO16---D0 of NodeMCU
int Solenoid = 13;       // GPIO13---D7 of NodeMCU--Motor connection
int cusLED = 12;
const char* ssid = "YOUR SSH";
const char* password = "YOUR PASSWORD";


DHT dht(dht_dpin, DHTTYPE); 
WiFiServer server(80);

void setup(void)
{ 
 pinMode(LED_BUILTIN, OUTPUT);
 pinMode(cusLED, OUTPUT);
  dht.begin();
  Serial.begin(9600);
  delay(10);
  pinMode(threshold,INPUT_PULLUP); //Pin#13 as output-Activate pullup at pin 13
  pinMode(Solenoid, OUTPUT);       //D7 as output
  digitalWrite(Solenoid, LOW);     //Deactivate Solenoid
  // Connect to WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);     //Begin WiFi
  
 while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
 
  // Start the server
  server.begin();
  Serial.println("Server started");
 
  // Print the IP address on serial monitor
  Serial.print("Use this URL to connect: ");
  Serial.print("http://");    //URL IP to be typed in mobile/desktop browser
  Serial.print(WiFi.localIP());
  Serial.println("/");
}



void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  } 
  // Wait until the client sends some data
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
  // Read the first line of the request
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();

 float h =0.0;  //Humidity level
 float t =0.0;  //Temperature in celcius 
 float f =0.0;  //Temperature in fahrenheit
 float percentage = 0.0; // Calculating percentage of moisture
 float reading    = 0.0; //Analog channel moisture read
 
  // Match the request
  int value = LOW;
  if (request.indexOf("/Up=ON") != -1)  {
     h = dht.readHumidity();    //Read humidity level
     t = dht.readTemperature(); //Read temperature in celcius
     f = (h * 1.8) + 32;        //Temperature converted to Fahrenheit
     reading = analogRead(Raw); //Analog pin reading output voltage by water moisture rain sensor
     percentage = (reading/1024) * 100;   //Converting the raw value in percentage

    if (reading<=110){  // If less mositure in soil start the motor otherwise stop
    digitalWrite(Solenoid, HIGH);
    value = HIGH;
    }
    else {
    digitalWrite(Solenoid, LOW);
    value = LOW;
    }
    
  }

  if (request.indexOf("/Solenoid=ON") != -1)  {   //Motor ON
    digitalWrite(Solenoid, HIGH);
    digitalWrite(cusLED, HIGH);
    value = HIGH;
  }
  if (request.indexOf("/Solenoid=OFF") != -1)  {  //Motor OFF
    digitalWrite(Solenoid, LOW);
    digitalWrite(cusLED, LOW);
    value = LOW;
  }

  // Return the response
  client.println("HTTP/1.1 200 OK");
  client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
  client.println("<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}");
  client.println("Content-Type: text/html");
  client.println(""); //  do not forget this one
  client.println("<!DOCTYPE HTML>");
  client.println("<html>");
 
  client.print("<h4 align=center>Temperature in Celsiusng</h4>");
  client.println("<h4 align=center>");
  client.print(t);
  client.print(" °C </h4>");
   client.println("<br>");  
   client.println("<br>");  

  //client.print("Temperature in Fahrenheit = ");
  //client.println(f);
  
  client.print("<h4 align=center>Humidity</h4> ");
  client.println();
  client.println("<h4 align=center>");
  client.print(h);
  client.print("</h4>");
  client.println("<br>");  
   client.println("<br>");  

  client.print("<h4 align=center>Moisture Level</h4> ");
    client.println();
  client.println("<h4 align=center>");
  client.print(percentage);
  client.print("  % </h4>");
  client.println("<br>");  
   client.println("<br>");  
  
  if(digitalRead(threshold)!=HIGH){ // Read digital output of soil sensor
  client.println("<h3 align=center>Water Detected</h3>");
   digitalWrite(Solenoid, LOW);
    digitalWrite(cusLED, LOW);
    value = LOW;
  }else {
     client.println("<h3 align=center>No Water Detected</h3>");
      digitalWrite(Solenoid, HIGH);
    digitalWrite(cusLED, HIGH);
    value = HIGH;
  }
  
  client.println("<br><br>");
  if(value == HIGH) 
    client.println("Motor/Pump Operational");
   else 
    client.print("Motor/Pump at Halt");
  
  client.println("<br><br>");
  client.println("<a align=center href=\"/Up=ON\"\"><button> Update </button></a><br />"); 
    
  client.println("<br><br>");
  client.println("<a  align=center href=\"/Solenoid=ON\"\"><button>Turn ON Motor</button></a>");
  client.println("<br><br>");
  client.println("<a  align=center href=\"/Solenoid=OFF\"\"><button>Turn OFF Motor</button></a><br />"); 
  client.println("</html>");
  delay(1);
  Serial.println("Client disonnected");
  Serial.println("");

   digitalWrite(LED_BUILTIN, LOW);   // Turn the LED on (Note that LOW is the voltage level
  // but actually the LED is on; this is because
  // it is active low on the ESP-01)
  delay(1000);                      // Wait for a second
  digitalWrite(LED_BUILTIN, HIGH);  // Turn the LED off by making the voltage HIGH
  delay(2000);   
}
