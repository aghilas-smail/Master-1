/* evaluation d'un fichier avec des label ref et hyp en entites nommees */

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

/* format :

0	Zurich	np	O	O
1	a	v	O	O
2	progressé	v	O	O
3	de	prep	O	O
4	deux	det	O	O
5	vingt	adj	O	O
6	quatre	pro	O	O
7	pour_cent	adv	O	O
8	,	ponctw	O	O
9	Francfort	np	B-geoloc	O
10	un	det	O	O
11	vingt	nc	O	O
12	neuf	adj	O	O
13	pour_cent	adv	O	O
14	,	ponctw	O	O
15	Londres	np	B-geoloc	B-geoloc
16	zéro	nc	O	O
17	quatre	adj	O	I-geoloc
18	vingt	adj	O	I-geoloc
19	dix	adj	O	O
20	sept	adj	O	O
21	,	ponctw	O	O
22	alors_que	csu	O	O
23	Londres	np	B-geoloc	B-geoloc
24	est	v	O	O
25	directement	adv	O	O
26	engagée	v	O	O
27	dans	prep	O	O
28	le	det	O	O
29	conflit	nc	O	O
30	,	ponctw	O	O
31	et	coo	O	O
32	Paris	np	B-geoloc	O

liste des labels:
B-geoloc
B-org
B-person
B-product
I-geoloc
I-org
I-person
I-product
O

*/

#define NB_NE	4

char *T_en[4]={"geoloc","org","person","product"};

int indice_en(char *ch)
{
int i;
for(i=0;i<NB_NE;i++) if (!strcmp(T_en[i],ch)) return i;
return -1;
}

typedef struct
{
int nbref,nbhyp,nbok;
double p,r,f;
} type_eval;

type_eval Tevalstrict[NB_NE];
type_eval Tevaldetect[NB_NE];

void init_eval(type_eval *pt)
{
pt->nbref=pt->nbhyp=pt->nbok=0;
pt->p=pt->r=pt->f=0;
}

void evalue_score(type_eval *pt)
{
if (pt->nbhyp>0) pt->p=(double)(pt->nbok*100)/(double)pt->nbhyp; else pt->p=0;
if (pt->nbref>0) pt->r=(double)(pt->nbok*100)/(double)pt->nbref; else pt->r=0;
if ((pt->p+pt->r)>0) pt->f=(2*pt->p*pt->r)/(pt->p+pt->r); else pt->f=0;
}

void affiche_eval(type_eval *pt)
{
printf("Precision: %05.2lf - Rappel: %05.2lf - F1: %05.2lf - nbref=%d nbhyp=%d nbok=%d\n",pt->p,pt->r,pt->f,pt->nbref,pt->nbhyp,pt->nbok);
}

/*................................................................*/

#define MAX_FIELD	60

#define IREF		3
#define IHYP		4

int main(int argc, char **argv)
{
int nb,i,ineref,inehyp,nbnepresent;
char ch[TailleLigne],*t_field[MAX_FIELD];
type_eval microf1strict,macrof1strict;
type_eval microf1detect,macrof1detect;

/*
if (argc>1)
 for(nb=1;nb<argc;nb++)
  if (!strcmp(argv[nb],"XXXX"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(file=fopen(argv[++nb],"rt"))) ERREUR("can't open:",argv[nb]);
   }
  else
  if (!strcmp(argv[nb],"-h"))
   {
   fprintf(stderr,"Syntax: %s [-h] \n",argv[0]);
   exit(0);
   }
  else ERREUR("unknown option:",argv[nb]);

if (0) ERREUR("bad syntax, check '-h'","");
*/

for(i=0;i<NB_NE;i++) { init_eval(&(Tevalstrict[i])); init_eval(&(Tevaldetect[i])); }
ineref=inehyp=-1;
while (fgets(ch,TailleLigne,stdin))
 {
 for (nb=1,t_field[0]=strtok(ch," \t\n\r");(nb<MAX_FIELD)&&(t_field[nb-1]);nb++) t_field[nb]=strtok(NULL," \t\n\r");
 if (nb==MAX_FIELD) ERREUR("cste MAX_FIELD too small","");
 if (nb>4)
  {
  if (t_field[IREF][0]=='B')
   {
   if ((ineref=indice_en(t_field[IREF]+2))==-1) ERREUR("unknown NE:",t_field[IREF]);
   Tevalstrict[ineref].nbref++; Tevaldetect[ineref].nbref++;
   }
  else
  if (t_field[IREF][0]=='I')
   {
   }
  else // O
   {
   // on regarde si c'est OK pour l'eval stricte
   if ((ineref!=-1)&&(ineref==inehyp)&&(t_field[IHYP][0]!='I')) Tevalstrict[ineref].nbok++;
   ineref=-1;
   }

  if (t_field[IHYP][0]=='B')
   {
   if ((inehyp=indice_en(t_field[IHYP]+2))==-1) ERREUR("unknown NE:",t_field[IHYP]);
   Tevalstrict[inehyp].nbhyp++; Tevaldetect[inehyp].nbhyp++;
   if (ineref==inehyp) Tevaldetect[inehyp].nbok++;
   }
  else
  if (t_field[IHYP][0]=='I')
   {
   if ((inehyp=indice_en(t_field[IHYP]+2))==-1) ERREUR("unknown NE:",t_field[IHYP]);
   if (inehyp!=ineref) inehyp=-1; // si c'est pas OK avec la ref, on l'efface pour mettre erreur dans l'eval strict
   }
  else inehyp=-1; // O
  }
 }
init_eval(&macrof1strict); init_eval(&microf1strict);
init_eval(&macrof1detect); init_eval(&microf1detect);
for(i=nbnepresent=0;i<NB_NE;i++) if ((Tevalstrict[i].nbref!=0)||(Tevalstrict[i].nbhyp!=0))
 {
 evalue_score(&(Tevalstrict[i])); evalue_score(&(Tevaldetect[i]));
 microf1strict.nbref+=Tevalstrict[i].nbref; microf1strict.nbhyp+=Tevalstrict[i].nbhyp; microf1strict.nbok+=Tevalstrict[i].nbok;
 microf1detect.nbref+=Tevaldetect[i].nbref; microf1detect.nbhyp+=Tevaldetect[i].nbhyp; microf1detect.nbok+=Tevaldetect[i].nbok;
 macrof1strict.p+=Tevalstrict[i].p; macrof1strict.r+=Tevalstrict[i].r; macrof1strict.f+=Tevalstrict[i].f;
 macrof1detect.p+=Tevaldetect[i].p; macrof1detect.r+=Tevaldetect[i].r; macrof1detect.f+=Tevaldetect[i].f;
 nbnepresent++;
 }
evalue_score(&microf1strict); evalue_score(&microf1detect);
macrof1strict.p/=(double)nbnepresent; macrof1strict.r/=(double)nbnepresent; macrof1strict.f=(macrof1strict.p*macrof1strict.r*(double)2)/(macrof1strict.p+macrof1strict.r);
macrof1detect.p/=(double)nbnepresent; macrof1detect.r/=(double)nbnepresent; macrof1detect.f=(macrof1detect.p*macrof1detect.r*(double)2)/(macrof1detect.p+macrof1detect.r);
macrof1strict.nbref=microf1strict.nbref; macrof1strict.nbhyp=microf1strict.nbhyp; macrof1strict.nbok=microf1strict.nbok;
macrof1detect.nbref=microf1detect.nbref; macrof1detect.nbhyp=microf1detect.nbhyp; macrof1detect.nbok=microf1detect.nbok;

printf("Evaluation stricte (labels et frontieres doivent etre corrects) :\n");
printf("  - macro-F1 : "); affiche_eval(&macrof1strict);
printf("  - micro-F1 : "); affiche_eval(&microf1strict);
printf("  Details par label :\n");
for(i=0;i<NB_NE;i++) if ((Tevalstrict[i].nbref!=0)||(Tevalstrict[i].nbhyp!=0)) { printf("    - %10s : ",T_en[i]); affiche_eval(&(Tevalstrict[i])); }
printf("\n");
printf("Evaluation detection (uniquement le token de debut et le type de l'entite doivent etre corrects) :\n");
printf("  - macro-F1 : "); affiche_eval(&macrof1detect);
printf("  - micro-F1 : "); affiche_eval(&microf1detect);
printf("  Details par label :\n");
for(i=0;i<NB_NE;i++) if ((Tevalstrict[i].nbref!=0)||(Tevalstrict[i].nbhyp!=0)) { printf("    - %10s : ",T_en[i]); affiche_eval(&(Tevaldetect[i])); }
exit(0);
}
  
