/*  echo / client simple
    Master Informatique 2012 -- Université Aix-Marseille
    Emmanuel Godard - Bilel Derbel
*/

import java.io.*;
import java.net.*;

public class EchoClient {

  public static void main(String[] args) throws IOException {
    Socket echoSocket; // la socket client
    String ip; // adresse IPv4 du serveur en notation pointée
    int port; // port TCP serveur
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out;
    BufferedReader in;
    boolean fini = false;

    /* Traitement des arguments */
    if (args.length != 2) {
        /* erreur de syntaxe */
      System.out.println("Usage: java EchoClient @server @port");
      System.exit(1);
    }
    ip = args[0];
    port = Integer.parseInt(args[1]);

    if (port > 65535) {
      System.err.println("Port hors limite");
      System.exit(3);
    }

    /* Connexion */
    System.out.println("Essai de connexion à  " + ip + " sur le port " + port + "\n");
    try {
      echoSocket = new Socket(ip, port);
      System.err.println("le n° de la socket est : " + echoSocket);
      /* Initialisation d'agréables flux d'entrée/sortie */
      out = new PrintWriter(echoSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    } catch (UnknownHostException e) {
      System.err.println("Connexion: hôte inconnu : " + ip);
      e.printStackTrace();
      return;
    }

    /* Session */
    try {
      while (true) {
        /* Jusqu'à fermeture de la socket (ou de stdin)     */
        /* recopier à l'écran ce qui est lu dans la socket  */
        /* recopier dans la socket ce qui est lu dans stdin */

        String lu;
        String tampon;

        /* réception des données */
        lu = in.readLine();
        if (lu == null) {
          System.err.println("Connexion terminée par l'hôte distant");
          break; /* on sort de la boucle infinie */
        }
        System.out.println("reçu: " + lu);

        if (fini == true) break; /* on sort de la boucle infinie */

        /* recopier dans la socket ce qui est entré au clavier */
        tampon = stdin.readLine();
        if (tampon == null) {
          fini = true;
          System.err.println("Connexion terminée !!");
          System.err.println("Hôte distant informé...");
          echoSocket.shutdownOutput(); /* terminaison explicite de la socket
                                          dans le sens client -> serveur */
          /* On ne sort pas de la boucle tout de suite ... */
        } else {
            /* envoi des données */
          out.println(tampon);
        }
      }

      /* On ferme tout */
      in.close();
      out.close();
      stdin.close();
      echoSocket.close();

      System.err.println("Fin de la session.");
    } catch (IOException e) {
      System.err.println("Erreur E/S socket");
      e.printStackTrace();
      System.exit(8);
    }

    return;
  }
}
