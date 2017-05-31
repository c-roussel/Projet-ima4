
#include <stdio.h>
#include "analog.h"
#include "capteur.h"


//  *********************************
//  Gestion des capteurs et de la LED
//  *********************************

const unsigned char channel1 = 0; //a0 x
const unsigned char channel2 = 1; //a1 y
const unsigned char channel3 = 2; //a2 z


//Fonction de recuperation de valeurs de l'accelerometre
//IN : void
//OUT : int [xyz] : valeur de l'accelerometre sur l'axe désiré
uint8_t get_x(){
  ad_init(channel1);
  int x = ad_sample();
  // #ifdef DEBUG
  // printf("Accelerometre x : %d\n",x);
  // #endif
  return x ;
}
uint8_t get_y(){
  ad_init(channel2);
  int y = ad_sample();
  // #ifdef DEBUG
  // printf("Accelerometre y : %d\n",y);
  // #endif
  return y ;
}
uint8_t get_z(){
  ad_init(channel3);
  int z = ad_sample();
  // #ifdef DEBUG
  // printf("Accelerometre z : %d\n",z);
  // #endif
  return z ;
}
