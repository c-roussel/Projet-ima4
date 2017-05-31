export CC = avr-gcc

export MCU = atmega328p
export TARGET_ARCH = -mmcu=$(MCU)

export LDFLAGS = -g $(TARGET_ARCH) -lm -Wl,--gc-sections #	-Os

TARGET = usb
CPPFLAGS = -mmcu=$(MCU)
CFLAGS =  -Wall -I. -DF_CPU=8000000 -Os #-g

DUDE = avrdude          # Nano    / Uno      / Duemilanove / Mega
PROTOCOL = stk500v1     # stk500  / stk500v1 / arduino	   / stk500v2
SPEED = 57600         # 57600   / 115200   / 57600	   / 115200
SERIAL = /dev/ttyUSB0   # ttyUSB0 / ttyACM0  / ttyUSB0     / ttyACM0

C_SRC = $(wildcard *.c)
OBJS = $(C_SRC:.c=.o)

all: $(TARGET).hex

debug: CFLAGS += -DDEBUG
debug: upload
	
clean:
	rm -f *.o *.hex *.elf

%.o:%.c
	$(CC) -c $(CPPFLAGS) $(CFLAGS) $< -o $@

$(TARGET).elf: $(OBJS)
	$(CC) $(LDFLAGS) -o $@ $(OBJS)

$(TARGET).hex: $(TARGET).elf
	avr-objcopy -j .text -j .data -O ihex $(TARGET).elf $(TARGET).hex
	avr-objcopy -j .eeprom --set-section-flags=.eeprom="alloc,load" --change-section-lma .eeprom=0 -O ihex $(TARGET).elf eeprom.hex

upload: $(TARGET).hex
	stty -F $(SERIAL) hupcl
	$(DUDE) -D -F -v -p $(MCU) -c $(PROTOCOL) -b $(SPEED) -P $(SERIAL) -U flash:w:$(TARGET).hex
