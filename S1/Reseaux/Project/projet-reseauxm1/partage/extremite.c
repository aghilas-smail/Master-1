#include "extremite.h"
#define PORT 123


void ext_out(int tunfd, char* port){
    
    int err, on = 1, server_sd = -1, client_sd = -1;

  do {
    // open server
    server_sd = socket(AF_INET6, SOCK_STREAM, 0);
    if (server_sd < 0) {
      perror("ext_out: socket() failed");
      break;
    }

    err = setsockopt(server_sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));
    if (err < 0) {
      perror("ext_out: setsockopt(SO_REUSEADDR) failed");
      break;
    }

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
    bidirectional_copy(client_sd, tunfd);
  } while (0);

  // close
  if (server_sd != -1 && close(server_sd) < 0) {
    perror("ext_out: failed to close the server socket descriptor");
  }

  if (client_sd != -1 && close(client_sd) < 0) {
    perror("ext_out: failed to close the client socket descriptor");
  }
}

void ext_in(int tunfd, const char* server, char* port){
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
    bidirectional_copy(tunfd, server_sd);
  } while (0);

  // close
  if (server_sd != -1 && close(server_sd) < 0) {
    perror("ext_in: failed to close server socket descriptor");
  }

  if (res != NULL) {
    freeaddrinfo(res);
  }
}
