#ifndef iic
#define iic

#include <util/delay.h>
#include <avr/interrupt.h>
#include <stdio.h>
#include <stdlib.h>
#define ERREUR -1
#define SUCCES 0
#define PRECISION_TMP 0.004
void TW_init(void);
void TW_start(void);
void TW_stop(void);
void TW_write(uint8_t data);
uint8_t TW_read(void);

uint8_t TW_readNACK(void);
uint8_t TWIGetStatus(void);
int config_capteur_temp(uint8_t cmd);
int recup_temp(void);

#endif