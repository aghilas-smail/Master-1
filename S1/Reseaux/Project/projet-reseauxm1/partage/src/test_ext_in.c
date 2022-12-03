#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "extremite.h"
#include "iftun.h"

// <tun name> <remote server ip address>
int main(int argc, char *argv[]) {
  if (argc != 3) {
    return -1;
  }

  int tunfd = tun_alloc(argv[1]);
  ext_in(tunfd, argv[2]);

  return 0;
}
