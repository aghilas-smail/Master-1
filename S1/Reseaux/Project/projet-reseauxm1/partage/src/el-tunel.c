#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "extremite.h"
#include "iftun.h"

// <tun name> <tun interface ip address> [remote server ip address]
int main(int argc, char *argv[]) {
  if (argc != 3 && argc != 4) {
    printf("Usage: el-tunel <tun name> <tun interface ip address> [remote "
           "server ip address]\n");
    return -1;
  }

  printf("Création de %s\n", argv[1]);
  int tunfd = tun_alloc(argv[1]);
  if (tunfd < 0) {
    return -1;
  }

  char buf[1600];
  printf("Configuration de %s en %s\n", argv[1], argv[2]);
  sprintf(buf, "ip link set %s up", argv[1]);
  system(buf);
  sprintf(buf, "ip addr add %s dev %s", argv[2], argv[1]);
  system(buf);
  system("ip addr");

  switch (argc) {
  case 3: {
    printf("Lancement du serveur.\n");
    ext_out(tunfd);
  } break;
  case 4: {
    printf("Lancement du client avec comme serveur distant %s.\n", argv[3]);
    ext_in(tunfd, argv[3]);
  } break;
  }

  return 0;
}
