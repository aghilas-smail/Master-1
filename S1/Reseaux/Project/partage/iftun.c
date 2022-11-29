#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h> 
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <linux/if.h>
#include <linux/if_tun.h>




int tun_alloc(char *dev){
  struct ifreq ifr;
  int fd, err;

  if( (fd = open("/dev/net/tun", O_RDWR)) < 0 ){
    perror("alloc tun");
    exit(-1);
  }

  memset(&ifr, 0, sizeof(ifr));

  /* Flags: IFF_TUN   - TUN device (no Ethernet headers) 
   *        IFF_TAP   - TAP device  
   *
   *        IFF_NO_PI - Do not provide packet information  
   */ 
  ifr.ifr_flags = IFF_TUN; 
  //if( *dev )sudo ansible-playbook -c local -v /vagrant/config.yml
}      
// nouvelle fonction qui copie octet par octet depuis src vers dst 
//void copie
void copy(int src, int dst) {
  const size_t buf_size = 1500; // MTU
  char buf[buf_size];

  ssize_t bytes_read;
  while ((bytes_read = read(src, buf, buf_size)) > 0) {
    ssize_t total_bytes_written = 0;
    ssize_t bytes_to_write = bytes_read;

    while (bytes_to_write > 0) {
      ssize_t bytes_written =
          write(dst, buf + total_bytes_written, bytes_to_write);

      if (bytes_written == -1 && errno != EINTR) {
        perror("copy: error occured while writing.");
        return;
      }

      total_bytes_written += bytes_written;
      bytes_to_write -= total_bytes_written;
    }
  }

  if (bytes_read == -1 && errno != EINTR) {
    perror("copy: error occured while reading.");
    return;
  }
}

int main (int argc, char** argv){

  int tunfd;
  printf("Création de %s\n",argv[1]);
  tunfd = tun_alloc(argv[1]);
  printf("Faire la configuration de %s...\n",argv[1]);
  printf("Appuyez sur une touche pour continuer\n");
  getchar();
  printf("Interface %s Configurée:\n",argv[1]);
  system("sudo ./configure-tun.sh");
  printf("\ncopier vers la sortie standard :\n");
  //int dst = atoi(argv[2]);
  copy(tunfd, 1);
  printf("Appuyez sur une touche pour terminer\n");
  getchar();

  return 0;
}
