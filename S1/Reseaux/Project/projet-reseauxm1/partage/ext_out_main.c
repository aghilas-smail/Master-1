#include "extremite.h"

int main (int argc, char** argv){

  int tunfd = tun_alloc(argv[1]);
  char config[2000];
  sprintf(config, "sudo ./configure-tun.sh %s %s", argv[1], argv[2]);
  system(config);
  char route[2000];
  sprintf(route, "sudo ./configure-route.sh fc00:1234:4::/64 %s", argv[1]);
  system(route);
  ext_out(tunfd, "123");

  return 0;
}
