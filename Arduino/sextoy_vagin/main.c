#include <avr/io.h>
#include <stdbool.h>
#include <avr/interrupt.h>
#include "serial.h"
#include "capteur.h"

#define LEVEL1 'a'
#define LEVEL2 'b'
#define LEVEL3 'c'
#define LEVEL4 'd'
//temperature minimale en demi degré
int MIN_EXCITATION = 74;


//temperature
uint8_t excitation_utilisateur = 0 ;
uint8_t excitation_partenaire = 0 ;

//frequence arduino pro mini = 8MHz 
//Si prescaler = 1024 => frequence d'init timer = 7.8125KHz ;
//OCR2A peut aller de 0 à 255 avec une résolution de 1tick = 1/7.8125kHz = 128microsecondes
//On peut donc aller de 0ms (interdit) à 32,640ms

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

//periode d'interruption de 32.640 ms
OCR2A = 0xFF ;

sei();
}



void init_pwm_1()
{
  // initialize 
  cli();          // disable global interrupts

  //initialisation pwm sur broche 6 = PD6
  DDRD |= 0x40 ; 
  // PD6 is now an output
  TCCR0A = 0;
  // set none-inverting mode
  TCCR0B = 0;
  // set prescaler to 8 and starts PWM
  OCR0A= 2 ;
  DDRD |= (1 << DDD6);
  // PD6 is now an output
  TCCR0A |= (1 << COM0A1);
  // set none-inverting mode

  TCCR0A |= (1 << WGM00);
  // set fast PWM Mode

  TCCR0B |= (1 << CS00) |(1 << CS02) ;
  // set prescaler to 1024 and starts PWM
  
  sei();

}


void stop_pwm_1(){
  TCCR0A = 0;
  TCCR0B = 0 ;

}
void init_pwm_2()
{
  // initialize 
  cli();          // disable global interrupts

 
  //initialisation pwm sur broche 13 = PB1
  DDRB |= 0x02 ; 
  // PD6 is now an output
  TCCR1A = (1 << WGM10) | (1 << COM1A1);
  // set none-inverting mode
  TCCR1B = (1 << WGM12) | (1 << CS10) |(1 << CS12);
  // set prescaler to 8 and starts PWM
  OCR1A= 2 ;

  DDRB |= (1 << DDB1)|(1 << DDB2);
  // PB1 and PB2 is now an output
    
  TCCR1B |= (1 << CS10);
  // START the timer with no prescaler

  sei();

}

void stop_pwm_2(){
  TCCR1A = 0;
  TCCR1B = 0 ;

}
#define TAILLE 10

int x[TAILLE],y[TAILLE],z[TAILLE];
int dx,dy,dz;
int test = 1;
int cpt = 0;
 /*********************************************************************
 Interrupt Routine
 **********************************************************************/
 // timer2 en comparaison


//les "dérivés" varient de 0 à 7 en valeur absolue
int calc_deriv(int axe[TAILLE]){
  int moy_bas = 0;
  int moy_haut = 0;
  int i;
  for(i=0;i<TAILLE/2;i++) moy_bas += axe[i];
  for(i=TAILLE/2;i<TAILLE;i++) moy_haut += axe[i];
  return (moy_haut - moy_bas)/TAILLE/2 ;
}
int absolue(int a){
  if(a>0) return a;
  else return -a;
}


int periode_pwm = 15 ; 
int cpt_pwm = 15;
ISR(TIMER2_COMPA_vect){
  cli();
  cpt = (cpt+1) %TAILLE;
  x[cpt] = get_x();
  y[cpt] = get_y();
  z[cpt] = get_z();
  if(!cpt)
  {
  dx = calc_deriv(x);
  dy = calc_deriv(y);
  dz = calc_deriv(z);
  excitation_utilisateur = absolue(dx) + absolue(dy) + absolue(dz) ;
#ifdef DEBUG
  printf("dx : %d ; dy : %d ; dz : %d ; excitation_utilisateur : %d\n",dx,dy,dz,excitation_utilisateur);
#endif
  }
  

  cpt_pwm--;
  if(!cpt_pwm){
    if(test) test = 0;
    else test = 1;
    OCR0A =  13*test + 3;
    OCR1A = OCR0A;
    cpt_pwm = periode_pwm ;
  }
  sei();
}

void lovometre(uint8_t excitation_partenaire){
  switch(excitation_partenaire){
    case LEVEL1 :
      stop_pwm_1();
      stop_pwm_2();
      PORTB = (PORTB & 0b11110011) | 0b00001000 ;
      break;
    case LEVEL2 :
      PORTB = (PORTB & 0b11110011) | 0b00001000 ;
      init_pwm_1();
      stop_pwm_2();
      break;
    case LEVEL3 : 
      PORTB = (PORTB & 0b11110011) | 0b00001100 ;
      init_pwm_1();
      stop_pwm_2();
      break;
    case LEVEL4 :
      PORTB = (PORTB & 0b11110011) | 0b00001100 ;
      init_pwm_1();
      init_pwm_2();
      break;
    default:
      stop_pwm_1();
      stop_pwm_2();
      PORTB = (PORTB & 0b11110011) ;
  }
}
ISR(USART_RX_vect ){
  excitation_partenaire = get_serial() ;
  lovometre(excitation_partenaire);
  send_serial(48+excitation_utilisateur);
}

int main(void)
{
  DDRB |= 0x0C ;
#ifdef DEBUG
  init_printf();
  printf("begin\n");
#else
  init_serial(9600);
#endif
  init_timer();
  for(;;)
    {
    }
  
  
  return 0;
}
