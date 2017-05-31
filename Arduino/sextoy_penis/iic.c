
#include "iic.h"

void TW_init(void)  
{
	//set SCL to 400kHz
	TWSR = 0x00;
	TWBR = 0x06;
	//enable TWI
	TWCR = (1<<TWEN);
}

void TW_start(void) 
{
    TWCR = (1<<TWINT)|(1<<TWSTA)|(1<<TWEN);
    while ((TWCR & (1<<TWINT)) == 0);
}

void TW_stop(void)
{
    TWCR = (1<<TWINT)|(1<<TWSTO)|(1<<TWEN);
}

void TW_write(uint8_t data)
{
    TWDR = data;
    TWCR = (1<<TWINT)|(1<<TWEN);
    while ((TWCR & (1<<TWINT)) == 0);
}

uint8_t TW_read(void)
{
    TWCR = (1<<TWINT)|(1<<TWEN)|(1<<TWEA);
    while ((TWCR & (1<<TWINT)) == 0);
    return TWDR;
}
uint8_t TW_readNACK(void)
{
    TWCR = (1<<TWINT)|(1<<TWEN);
    while ((TWCR & (1<<TWINT)) == 0);
    return TWDR;
}
uint8_t TWIGetStatus(void)
{
	uint8_t status;
	//mask status
	status = TWSR & 0xF8;
	return status;
}

int config_capteur_temp(uint8_t cmd){
	uint8_t r_data,addr;
	
	TW_start();
	r_data = TWIGetStatus();
	#ifdef DEBUG
    printf("CONFIG - start : %x\n", r_data);
    #else
    //verif de succes du start
    if(r_data != 0x08) return ERREUR;
    #endif

    addr = 0x98 | 0b10000000;
    TW_write(addr);
	r_data = TWIGetStatus();
	#ifdef DEBUG
    printf("CONFIG - adresse capteur: %x\n", r_data);
    #else
   	//verif de recepetion d'ack apres envoie d'un SLA en ecriture
    if(r_data != 0x18) return ERREUR;
    #endif
    
    TW_write(0x01);
	r_data = TWIGetStatus();
	#ifdef DEBUG
    printf("CONFIG - adresse registre config: %x\n", r_data);
    #else
    //verif de reception d'ack apres envoie d'un octet
    if(r_data != 0x28) return ERREUR;
    #endif



	TW_write(cmd); //Lancement d'une lecture en One shot.
	r_data = TWIGetStatus();
	#ifdef DEBUG
    printf("CONFIG - envoie de la config: %x\n", r_data);
    #else
    //verif de reception d'ack apres envoie d'un octet
    if(r_data != 0x28) return ERREUR;
    #endif
 	
    TW_stop();
    return SUCCES ;
}


int recup_temp(void){
    uint8_t r_data1,r_data2,r_data;//,addr;

    TW_start();
    r_data = TWIGetStatus();
    //verif de succes du start
    if(r_data != 0x08) return ERREUR;
    #ifdef debug
    printf("data : %x\n",r_data);
    #endif
    TW_write(0x98);
    r_data = TWIGetStatus();
    //verif de recepetion d'ack apres envoie d'un SLA en ecriture
    if(r_data != 0x18) return ERREUR;
    #ifdef DEBUG
    printf("data : %x\n",r_data);
    #endif
    TW_write(0x00);
    r_data = TWIGetStatus();
    //verif de reception d'ack apres envoie d'un octet
    if(r_data != 0x28) return ERREUR;
    #ifdef DEBUG
    printf("data : %x\n",r_data);
    #endif
    TW_start();
    r_data = TWIGetStatus();
    //verif de restart
    if(r_data != 0x10) return ERREUR;
    #ifdef DEBUG
    printf("data : %x\n",r_data);
    #endif
    TW_write(0x99);
    r_data = TWIGetStatus();
    //verif de reception d'ack apres envoie d'un SLA en lecture
    if(r_data != 0x40) return ERREUR;
    #ifdef DEBUG
    printf("data : %x\n",r_data);
#endif
    r_data1 = TW_read();
    r_data = TWIGetStatus();
    //verif de reception d'ack apres lecture d'un octet
    if(r_data != 0x50) return ERREUR;
    #ifdef DEBUG
    printf("data : %x\n",r_data);
#endif
    r_data2 = TW_readNACK() ;
    r_data = TWIGetStatus();
    //verif de reception d'ack apres lecture d'un octet SANS ACK
    if(r_data != 0x58) return ERREUR; 

    #ifdef DEBUG
    printf("data : %x\n",r_data);
#endif
    TW_stop();


  return (r_data1 + r_data2/128)*2;

    }