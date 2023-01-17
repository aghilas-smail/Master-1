/* tokenize: un programme pour tokenizer un texte
 * avec un algorithme glouton a partir d'un lexique
 * code sous la forme d'un arbre de prefixe
 * Frederic Bechet - octobre 2015
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/*................................................................*/

void ERREUR(char *ch1,char *ch2)
{
fprintf(stderr,"ERREUR : %s %s\n",ch1,ch2);
exit(0);
}

/*................................................................*/

/* utilitaires */

int separateur_fort(char c)
{
static char t[]={' ','\n','\t','.',':',';',',','"','\'','-','_','!','?','(',')','[',']','#','\0'};
int i;
for(i=0;(t[i])&&(t[i]!=c);i++);
return t[i];
}

int espace(char c)
{
static char t[]={' ','\n','\t','\0'};
int i;
for(i=0;(t[i])&&(t[i]!=c);i++);
return t[i];
}

/*................................................................*/

/* arbre de prefixe */

typedef struct type_noeud {
    char car;
    int code;
    struct type_noeud *fg,*fd;
} ty_noeud;

ty_noeud *new_noeud(char car, int code)
{
ty_noeud *pt;
pt=(ty_noeud*)malloc(sizeof(ty_noeud));
pt->car=car;
pt->code=code;
pt->fg=pt->fd=NULL;
return pt;
}

ty_noeud *ajoute_mot(ty_noeud *lexique, char *mot, int code)
{
if (lexique==NULL) lexique=new_noeud(*mot,*(mot+1)?0:code);
if (lexique->car==*mot)
 {
 if ((*(mot+1)=='\0')||(*(mot+1)=='\n')) lexique->code=code;
 else lexique->fg=ajoute_mot(lexique->fg,mot+1,code);
 }
else lexique->fd=ajoute_mot(lexique->fd,mot,code);
return lexique;
}

ty_noeud *lire_lexique(FILE *file)
{
char ch[1000],mot[1000];
int code;
ty_noeud *racine=NULL;
while(fgets(ch,1000,file))
 {
 if (sscanf(ch,"%d\t%s\t",&code,mot)!=2) { fprintf(stderr,"Mauvais format: %s\n",ch); exit(0); }
 racine=ajoute_mot(racine,mot,code);
 }
return racine;
}

/*................................................................*/

/* tokenizeur */

int tokenize(ty_noeud *lexique, char *buffer, int indice, int *code)
{
int imot;
*code=0; imot=indice;
while ((buffer[indice])&&(lexique))
 {
 if ((buffer[indice]==lexique->car)||((buffer[indice]==' ')&&(lexique->car=='_')))
  {
  if ((lexique->code!=0)&&((separateur_fort(buffer[indice+1]))||(separateur_fort(buffer[indice]))))
   {
   *code=lexique->code;
   imot=indice;
   }
  lexique=lexique->fg;
  indice++;
  }
 else lexique=lexique->fd;
 }
return imot;
}

/*................................................................*/

/* traiter un corpus */

#define TAILLE_BUFFER   50000

int main(int argc, char **argv)
{
FILE *file;
ty_noeud *lexique=NULL;
char buffer[TAILLE_BUFFER],car_sepa=' ';
int i,j,size,indice,code,ifcode,ifoov,ifline,ifnooov,nb;

ifcode=ifoov=ifline=ifnooov=0;
if (argc>1)
 for(nb=1;nb<argc;nb++)
  if (!strcmp(argv[nb],"-lex"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(file=fopen(argv[++nb],"rt"))) ERREUR("can't open:",argv[nb]);
   lexique=lire_lexique(file);
   fclose(file);
   }
  else
  if (!strcmp(argv[nb],"-code")) ifcode=1;
  else
  if (!strcmp(argv[nb],"-oov")) ifoov=1;
  else
  if (!strcmp(argv[nb],"-no_oov")) ifnooov=1;
  else
  if (!strcmp(argv[nb],"-line")) { ifline=1; car_sepa='\n'; }
  else
  if (!strcmp(argv[nb],"-h"))
   {
   fprintf(stderr,"Syntax: %s [-h] -lex <file> [-code -oov -no_oov -line]\n",argv[0]);
   exit(0);
   }
  else ERREUR("unknown option:",argv[nb]);

if (!lexique) ERREUR("bad syntax, check '-h'","");

while(fgets(buffer,TAILLE_BUFFER,stdin))
 {
 size=strlen(buffer);
 /* tokenization corpus */
 for(i=0;i<size;i++)
  if (!espace(buffer[i]))
   {
   j=tokenize(lexique,buffer,i,&code);
   if (code)
    {
    if ((ifcode)&&(!ifoov)) printf("%d%c",code,car_sepa);
    for(;i<=j;i++) if ((!ifoov)&&(!ifcode)) printf("%c",buffer[i]);
    if ((!ifoov)&&(!ifcode)) printf("%c",car_sepa);
    i--;
    }
   else
    { /* mot inconnu, on affiche le code '0' et on avance au prochain mot */
    if (ifcode) if (!ifnooov) printf("0%c",car_sepa);
    for (j=i;(i<size)&&(!separateur_fort(buffer[i]));i++) if (!ifcode) if (!ifnooov) printf("%c",buffer[i]);
    if (i>j) i--; /* si jamais le separateur fort est aussi inconnu, on avance quand meme */
    if (!ifcode) if (!ifnooov) printf("%c",car_sepa);
    }
   }
  else if ((buffer[i]=='\n')&&(!ifoov)) { if (!ifline) printf("\n"); else printf("<SAUT-LIGNE/>\n"); }
 }

exit(0);
}
  
