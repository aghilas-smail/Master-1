// local server => tunfd
void ext_out(int tunfd);

// tunfd => remote server
void ext_in(int tunfd, const char *server);
