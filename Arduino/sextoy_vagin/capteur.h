#ifndef __CAPTEUR_H_


#define __CAPTEUR_H_
#include <util/delay.h>
#include <avr/io.h>


#define ROUGE 0xFE
#define BLEU 0xFD
#define VERT 0xFB
//Fonction de modification de couleur de la LED
//IN : int colour : utiliser les mots definis : ROUGE VERT ou BLEU
//OUT : void
//NOTE : modifie le registre PORTB en conséquence de la couleur désirée
void couleur(int colour);

//Fonction de recuperation de temperature
//IN : void
//OUT : int temp : valeur renvoyée par le capteur de temperature
uint8_t get_temp();

//Fonction de recuperation de valeurs de l'accelerometre
//IN : void
//OUT : int [xyz] : valeur de l'accelerometre sur l'axe désiré
uint8_t get_x();
uint8_t get_y();
uint8_t get_z();

#endif