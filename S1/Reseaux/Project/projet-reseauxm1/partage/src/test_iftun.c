#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "iftun.h"

int main(int argc, char *argv[]) {
  int tunfd = tun_alloc(argv[1]);

  copy(tunfd, STDOUT_FILENO);

  return 0;
}
