#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h> 
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <errno.h>
#include <fcntl.h>
#include <linux/if.h>
#include <linux/if_tun.h>

int tun_alloc(char *dev);
void copy(int src, int dst);
void bidirectional_copy(int fd1, int fd2);
