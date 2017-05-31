#include <avr/io.h>
#include <stdbool.h>
#include <avr/interrupt.h>
#include "iic.h"
#include "serial.h"
#include "capteur.h"

//temperature minimale en demi degré
int MIN_EXCITATION = 74;


//temperature
int tmp ;
uint8_t excitation_utilisateur = 0 ;
uint8_t excitation_partenaire = 0 ;

//frequence arduino pro mini = 8MHz 
//Si prescaler = 1024 => frequence d'init timer = 7.8125KHz ;
//OCR2A peut aller de 0 à 255 avec une résolution de 1tick = 1/7.8125kHz = 128microsecondes
//On peut donc aller de 0ms (interdit) à 32,640ms
//nous augmenterons cette periode via clk_div de 100fois: la plage est donc : 0ms à 3.264s 
void changer_frequence_interruption(int ms){
  if(ms >3264) ms = 3264;
  OCR2A = (ms / 0.128)/100;
}

void init_timer(){
// initialize Timer2
cli();          // disable global interrupts
TCCR2A = 0;     // set entire TCCR1A register to 0
TCCR2B = 0;     // same for TCCR1B

// set compare match register to desired timer count:
OCR2A = 0;

// turn on CTC mode:
TCCR2B |= (1 << WGM12);

// Set CS10 and CS12 bits for 1024 prescaler:
TCCR2B |= (1 << CS10);
TCCR2B |= (1 << CS11);
TCCR2B |= (1 << CS12);

// enable timer compare interrupt:
TIMSK2 |= (1 << OCIE2A);
changer_frequence_interruption(500);
sei();
}


int test = 1;
 /*********************************************************************
 Interrupt Routine
 **********************************************************************/
 // timer2 en comparaison
int clk_div = 100;
ISR(TIMER2_COMPA_vect){
  cli();
  clk_div--;
  if(!clk_div){
    tmp = recup_temp();
    excitation_utilisateur = tmp - MIN_EXCITATION ;
  #ifdef DEBUG
    printf("temperature = %d demi degres\n,Niveau d'excitation correspondant :%d\n",tmp, excitation_utilisateur);
  #endif
  clk_div = 100;
  }
  sei();
}

/*
ISR(TIMER1_COMPA_vect)
{
cpt = (cpt +1)%256 ;
if(consigne < cpt)
PORTD = 0xFF ;
else PORTD = 0x00 ;
  }
*/

int main(void)
{
	int tmp ;
#ifdef DEBUG
  MIN_EXCITATION = recup_temp();
  init_printf();
  printf("begin\n");
#endif    
  TW_init();
  changer_frequence_interruption(500);
  init_timer();
  for(;;){}
  
  
  return 0;
}
