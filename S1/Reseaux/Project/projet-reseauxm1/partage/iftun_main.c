#include "iftun.h"

int main (int argc, char** argv){

  int tunfd;
  printf("Création de %s\n",argv[1]);
  tunfd = tun_alloc(argv[1]);
  printf("Faire la configuration de %s...\n",argv[1]);
  system("chmod +x configure-tun.sh");
  system("./configure-tun.sh");
  printf("Appuyez sur une touche pour continuer\n");
  getchar();
  printf("Interface %s Configurée:\n",argv[1]);
  //system("ip addr");
  copy(tunfd, 1);
  printf("Appuyez sur une touche pour terminer\n");
  getchar();

  return 0;
}

