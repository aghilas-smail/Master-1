#include "iftun.h"
#include <arpa/inet.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

#define PORT 123

void ext_out(int tunfd, char* port);
void ext_in(int tunfd, const char *server,  char* port);
