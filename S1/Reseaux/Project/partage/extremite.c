#include "extremite.h"
#include "iftun.h"
#include <arpa/inet.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>



//#define FALSE 0
//#define PORT 123

/** Ouvre une socket serveur, attend une connexion,
 * puis effectue la copie bidirectionnelle des données recues
 * de la socket vers le tunnel et vice-versa.
 * 
 * @param tunfd : file descriptor du tunnel
 **/
void ext_out(int tunfd , char *port) {
  
  int serS, n,len,on; // Description de socket
  struct addrinfo * resolution; //résolution
  struct addrinfo indice = {AI_PASSIVE,PF_INET6, SOCK_STREAM,0,
                                0,NULL,NULL,NULL}

  struct sockaddr_in client ; // adresse de socket de client.
  int erreur = getaddrinfo(NULL,port,&indice,&resolution); // code d'erreur.

  // Le stderr est le flux standare de sortie de l'erreur.
  if(erreur<0) {
    fprint(stderr, "Résolution : %s\n",gai_strerror(erreur)); //The gai_strerror() function shall return a text string describing an error value for the getaddrinfo()
                                          //  and getnameinfo() functions listed in the <netdb.h> header.
    exit(2);
  }

  // La creation de la socket de type TCP et IP et l'ouverture de serveur.
  s = socket(resolution->ai_family,resolution -> ai_socktype,resolution -> ai_protocol);
  /*
  le ai_family indique la famille d'adresse désirée des adresses renvoyées. */
  if (s<0) {
    perror("extt_out: socket est faild");
    exit(3);
  }

// L'utilisation de port en vitesse.
    err = sets


    struct sockaddr_in6 server_addr;
    bzero(&server_addr, sizeof(server_addr));
    server_addr.sin6_family = AF_INET6;
    server_addr.sin6_addr = in6addr_any;
    server_addr.sin6_port = htons(PORT);

    err = bind(server_sd, (struct sockaddr *)&server_addr, sizeof(server_addr));
    if (err < 0) {
      perror("ext_out: bind() failed");
      break;
    }

    err = listen(server_sd, 0);
    if (err < 0) {
      perror("ext_out: listen() failed");
      break;
    }

    printf("Ready for client connect().\n");

    // wait for one client
    struct sockaddr_in6 client_addr;
    socklen_t client_len = sizeof(client_addr);

    client_sd = accept(server_sd, (struct sockaddr *)&client_addr, &client_len);
    if (client_sd < 0) {
      perror("ext_out: accept() failed");
      break;
    }

    // getpeername(client_sd, (struct sockaddr *)&client_addr, &client_len);
    char str[INET6_ADDRSTRLEN];
    if (inet_ntop(AF_INET6, &client_addr.sin6_addr, str, sizeof(str))) {
      printf("Client connected ip: %s port: %d\n", str,
             ntohs(client_addr.sin6_port));
    }

    // copying
    // copy(conn_fd, tunfd);
   // bidirectional_copy(client_sd, tunfd);
  } while (FALSE);
//#include "tunalloc.h"

  // close
  if (server_sd != -1 && close(server_sd) < 0) {
    perror("ext_out: failed to close the server socket descriptor");
  }

  if (client_sd != -1 && close(client_sd) < 0) {
    perror("ext_out: failed to close the client socket descriptor");
  }
}

/** Ouvre une socket client, se connecte au serveur,
 * puis effectue la copie bidirectionnelle des données recues
 * de la socket vers le tunnel et vice-versa.
 * 
 * @param tunfd : file descriptor du tunel
 * @param *server : adresse IP du serveur distant
 **/
void ext_in(int tunfd, const char *server) {
  int err, server_sd = -1;
  char server_port[12];
  sprintf(server_port, "%d", PORT);
  struct in6_addr server_addr;
  struct addrinfo hints, *res = NULL;

  do {
    bzero(&hints, sizeof(hints));
    hints.ai_flags = AI_NUMERICSERV;
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;

    err = inet_pton(AF_INET, server, &server_addr);
    if (err == 1) {
      hints.ai_family = AF_INET;
      hints.ai_flags |= AI_NUMERICHOST;
    } else {
      err = inet_pton(AF_INET6, server, &server_addr);
      if (err == 1) {
        hints.ai_family = AF_INET6;
        hints.ai_flags |= AI_NUMERICHOST;
      }
    }

    err = getaddrinfo(server, server_port, &hints, &res);
    if (err != 0) {
      printf("Host not found --> %s\n", gai_strerror(err));
      if (err == EAI_SYSTEM)
        perror("ext_in: getaddrinfo() failed");
      break;
    }

    // connect to server
    server_sd = socket(res->ai_family, res->ai_socktype, res->ai_protocol);
    if (server_sd < 0) {
      perror("ext_in: socket() failed");
      break;
    }

    err = connect(server_sd, res->ai_addr, res->ai_addrlen);
    if (err < 0) {
      perror("ext_in: connect() failed");
      break;
    }

    // copying
    // copy(tunfd, server_fd);
    //bidirectional_copy(tunfd, server_sd);
  } while (FALSE);

  // close
  if (server_sd != -1 && close(server_sd) < 0) {
    perror("ext_in: failed to close server socket descriptor");
  }

  if (res != NULL) {
    freeaddrinfo(res);
  }
}



