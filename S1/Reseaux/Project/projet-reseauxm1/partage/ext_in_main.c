#include "extremite.h"

int main (int argc, char** argv){

  int tunfd = tun_alloc(argv[1]);
  char config[1600];
  sprintf(config, "sudo ./configure-tun.sh %s %s", argv[1], argv[2]);
  system(config);
  char route[1600];
  sprintf(route, "sudo ./configure-route.sh fc00:1234:3::/64 %s", argv[1]);
  system(route);
  ext_in(tunfd, argv[3], "123");

  return 0;
}
