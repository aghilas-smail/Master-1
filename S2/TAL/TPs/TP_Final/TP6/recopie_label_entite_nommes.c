/* recopie les hypotheses label EN produites par icsiboost dans le fichier corpus original */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

/*................................................................*/

#define TailleLigne     8000

#define True    1
#define False   0

void ERREUR(char *ch1,char *ch2)
{
fprintf(stderr,"ERREUR : %s %s\n",ch1,ch2);
exit(0);
}

void ERREURd(char *ch1, int i)
{
fprintf(stderr,"ERREUR : %s %d\n",ch1,i);
exit(0);
}

/*................................................................*/

/* format:

- fichier annotation
190	O	0.001894
191	O	0.001819
192	B-org	-0.000004
193	O	0.004387
194	O	0.001589
195	O	0.002341
196	O	0.001480
197	O	0.001454
198	O	0.001389
199	O	0.001252
200	O	0.001566
201	O	0.001541
202	O	0.001091
203	O	0.001386
204	O	0.001662
205	B-person	0.001775
206	I-person	0.002045
207	O	0.003182
209	O	0.002639
210	O	0.001919
211	O	0.003567

- fichier original
21      rappelle        v       O
22      l'      det     O
23      objectif        nc      O
24      ,       ponctw  O
25      casser  v       O
26      l'      det     O
27      appareil        nc      O
28      de      prep    O
29      production      nc      O
30      d'      prep    O
31      armes   nc      O
32      de      prep    O
33      destructions    nc      O
34      massives        adj     O
35      de      prep    O
36      Saddam  np      B-person
37      Hussein np      I-person
38      .       ponctw  O
*/

/*................................................................*/

#define MAX_FIELD	60

int main(int argc, char **argv)
{
int nb,nbcorp,nbhypo;
char chcorp[TailleLigne],ch[TailleLigne],*t_field[MAX_FIELD];
FILE *filehypo=stdin,*filecorp=NULL;

if (argc>1)
 for(nb=1;nb<argc;nb++)
  if (!strcmp(argv[nb],"-corpus"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(filecorp=fopen(argv[++nb],"rt"))) ERREUR("can't open:",argv[nb]);
   }
  else
  if (!strcmp(argv[nb],"-hyp"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(filehypo=fopen(argv[++nb],"rt"))) ERREUR("can't open:",argv[nb]);
   }
  else
  if (!strcmp(argv[nb],"-h"))
   {
   fprintf(stderr,"Syntax: %s [-h] -corpus <file> -hyp <file>\n",argv[0]);
   exit(0);
   }
  else ERREUR("unknown option:",argv[nb]);

if ((!filehypo)&&(!filecorp)) ERREUR("bad syntax, check '-h'","");

for (nbhypo=-1;fgets(chcorp,TailleLigne,filecorp);)
 {
 if ((chcorp[0]=='\n')||(chcorp[0]=='\r')||(chcorp[0]=='\t')||((int)(chcorp[0])==10)) printf("\n");
 else
  {
  strtok(chcorp,"\n\r");
  if (sscanf(chcorp,"%d\t",&nbcorp)!=1) ERREUR("Bad index in corpus:",chcorp);
  if (nbhypo<nbcorp)
   {
   if (fgets(ch,TailleLigne,filehypo))
    {
    for (nb=1,t_field[0]=strtok(ch," \t\n\r");(nb<MAX_FIELD)&&(t_field[nb-1]);nb++) t_field[nb]=strtok(NULL," \t\n\r");
    if (nb==MAX_FIELD) ERREUR("cste MAX_FIELD too small","");
    if (sscanf(t_field[0],"%d",&nbhypo)!=1) ERREUR("bad format:",t_field[0]);
    }
   }
  printf("%s\t%s\n",chcorp,nbhypo==nbcorp?t_field[1]:"O");
  }
 }
fclose(filehypo); fclose(filecorp);
exit(0);
}
 
