// Crée une interface virtuelle TUN.
int tun_alloc(char *dev);

// Compléter la bibliothèque iftun avec une fonction avec deux descripteurs de
// fichiers en paramètres src et dst, qui, étant donné un descripteur src
// correspondant à une interface TUN, recopie perpétuellement toutes les données
// lisibles sur src dans le fichier décrit par dst.
void copy(int src, int dst);

void bidirectional_copy(int fd1, int fd2);