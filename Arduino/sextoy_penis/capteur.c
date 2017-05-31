
#include "analog.h"
#include "capteur.h"


//  *********************************
//  Gestion des capteurs et de la LED
//  *********************************

const unsigned char channel1 = 0; //a0 x
const unsigned char channel2 = 1; //a1 y
const unsigned char channel3 = 2; //a2 z
const unsigned char channel4 = 3; //a3 capteur de temperature

//Fonction de modification de couleur de la LED
//IN : int colour : utiliser les mots definis : ROUGE VERT ou BLEU
//OUT : void
//NOTE : modifie le registre PORTB en conséquence de la couleur désirée
void couleur(int colour){
  PORTB = colour ;
}

//Fonction de recuperation de temperature
//IN : void
//OUT : int temp : valeur renvoyée par le capteur de temperature
uint8_t get_temp(){
  int temp ;
  ad_init(channel4);
  temp = ad_sample();
  _delay_ms(250);
  return (temp*(3.3/255)-0.5)*100; //conversion en degres
}

//Fonction de recuperation de valeurs de l'accelerometre
//IN : void
//OUT : int [xyz] : valeur de l'accelerometre sur l'axe désiré
uint8_t get_x(){
  ad_init(channel1);
  int x = ad_sample();
  _delay_ms(250);
  return x ;
}
uint8_t get_y(){
  ad_init(channel2);
  int y = ad_sample();
  _delay_ms(250);
  return y ;
}
uint8_t get_z(){
  ad_init(channel3);
  int z = ad_sample();
  _delay_ms(250);
  return z ;
}
