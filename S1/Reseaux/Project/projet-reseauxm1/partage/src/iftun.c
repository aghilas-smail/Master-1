#include "iftun.h"

#include <errno.h>
#include <fcntl.h>
#include <linux/if.h>
#include <linux/if_tun.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <sys/select.h>
#include <unistd.h>

#define max(a, b) ((a) > (b) ? (a) : (b))

/** Créé l'interface correspondant au tunnel
 * et retourne le descripteur de fichier correspondant.
 * 
 * @param dev : nom du tunnel
 **/
int tun_alloc(char *dev) {
  struct ifreq ifr;
  int fd, err;

  if ((fd = open("/dev/net/tun", O_RDWR)) < 0) {
    perror("alloc tun");
    exit(-1);
  }

  memset(&ifr, 0, sizeof(ifr));

  /* Flags: IFF_TUN   - TUN device (no Ethernet headers)
   *        IFF_TAP   - TAP device
   *
   *        IFF_NO_PI - Do not provide packet information
   */
  ifr.ifr_flags = IFF_TUN | IFF_NO_PI;
  if (*dev)
    strncpy(ifr.ifr_name, dev, IFNAMSIZ);

  if ((err = ioctl(fd, TUNSETIFF, (void *)&ifr)) < 0) {
    close(fd);
    return err;
  }
  strcpy(dev, ifr.ifr_name);
  return fd;
}

/** Ecrit le contenu d'un buffer vers le descripteur de 
 * fichier de destination.
 * 
 * @param dest : descripteur de fichier de destination
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
 * Copie le flux de données provenant du descripteur
 * source vers le descripteur dest.
 * 
 * @param src : descripteur de fichier source
 * @param dst : descripteur de fichier de destination
 **/
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

/** Effectue la copie bidirectionnelle des 
 * données entre les
 * deux descripteurs de fichiers
 **/
void bidirectional_copy(int fd1, int fd2) {
  char buf[1500];
  int fm, fd1_bytes_read = 1, fd2_bytes_read = 1;
  fd_set fds;

  fm = max(fd1, fd2) + 1;

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
