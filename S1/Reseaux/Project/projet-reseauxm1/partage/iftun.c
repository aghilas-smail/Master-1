#include "iftun.h"

int tun_alloc(char *dev)
{
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
  if( *dev )
    strncpy(ifr.ifr_name, dev, IFNAMSIZ);

  if( (err = ioctl(fd, TUNSETIFF, (void *) &ifr)) < 0 ){
    close(fd);
    return err;
  }
  strcpy(dev, ifr.ifr_name);
  return fd;
}

/** 
 * Copier les données du descripteur source src vers le descripteur dest.
 * @param src : descripteur de fichier source
 * @param dst : descripteur de fichier de destination
 **/
void copy(int src, int dst) {
  const size_t buf_size = 1500;
  char buf[buf_size];

  ssize_t bytes_read;
  while ((bytes_read = read(src, buf, buf_size)) > 0) {
    ssize_t total_bytes_written = 0;
    ssize_t bytes_to_write = bytes_read;

    while (bytes_to_write > 0) {
      ssize_t bytes_written =
          write(dst, buf + total_bytes_written, bytes_to_write);

      if (bytes_written == -1) {
        perror("copy: error occured while writing.");
        return;
      }

      total_bytes_written += bytes_written;
      bytes_to_write -= total_bytes_written;
    }
  }

  if (bytes_read == -1) {
    perror("copy: error occured while reading.");
    return;
  }
}

/**
 * Ecrit le contenu d'un buffer dans un descripteur de fichier destination.
 * @param dest : descripteur de fichier destination
 * @param buf : buffer
 * @param buf_size : taille du buffer
 **/
int write_all(int dst, char *buf, const size_t buf_size) {
  ssize_t total_bytes_written = 0;
  ssize_t bytes_to_write = buf_size;

  while (bytes_to_write > 0) {
    ssize_t bytes_written =
        write(dst, buf + total_bytes_written, bytes_to_write);

    if (bytes_written == -1 && errno != EINTR) {
      perror("write_all: error occured while writing.");
      return -1;
    }

    total_bytes_written += bytes_written;
    bytes_to_write -= total_bytes_written;
  }

  return 0;
}

/**
 * Effectuer la copie bidirectionnelle des données entre deux descripteurs de fichiers.
 * @param fd1 : premier descripteur de fichier
 * @param fd2 : deuxième descripteur de fichier
 **/
void bidirectional_copy(int fd1, int fd2) {
  char buf[1500];
  int fm, fd1_bytes_read = 1, fd2_bytes_read = 1;
  fd_set fds;

  if (fd1 > fd2) fm = fd1 + 1;
  else fm = fd2 + 1;

  while (fd1_bytes_read > 0 || fd2_bytes_read > 0) {
    FD_ZERO(&fds);
    FD_SET(fd1, &fds);
    FD_SET(fd2, &fds);

    select(fm, &fds, NULL, NULL, NULL);

    if (FD_ISSET(fd1, &fds)) {
      fd1_bytes_read = read(fd1, buf, sizeof(buf));
      if (fd1_bytes_read == -1 && errno != EINTR) {
        perror("bidirectional_copy: error occured while reading fd1.");
        return;
      }

      if (write_all(fd2, buf, fd1_bytes_read) != 0) {
        perror("bidirectional_copy: error occured while writing to fd2.");
        return;
      }
    }

    if (FD_ISSET(fd2, &fds)) {
      fd2_bytes_read = read(fd2, buf, sizeof(buf));
      if (fd2_bytes_read == -1 && errno != EINTR) {
        perror("bidirectional_copy: error occured while reading fd2.");
        return;
      }

      if (write_all(fd1, buf, fd2_bytes_read) != 0) {
        perror("bidirectional_copy: error occured while writing to fd1.");
        return;
      }
    }
  }
}
