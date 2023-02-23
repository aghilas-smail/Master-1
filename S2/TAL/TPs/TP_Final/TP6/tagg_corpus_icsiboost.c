/*  Tagg un corpus grace aux sorties d'icsiboost  */

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
0 0 0 1 -0.008100630986 -0.004102325015 -0.048938329428 0.002132675953
names:
auditeur , invité , journaliste , présentateur .
*/

#define MAX_LABEL	40

char *T_label[MAX_LABEL];

int load_names(char *fname)
{
FILE *file;
char ch[TailleLigne],*pt;
int i;

if (!(file=fopen(fname,"rt"))) ERREUR("can't read:",fname);
while((fgets(ch,TailleLigne,file))&&(ch[0]=='\n'));
if (feof(file)) ERREUR("bad names file:",fname);
for(i=0,pt=strtok(ch," \t,.");pt;pt=strtok(NULL," \t,."),i++)
 {
 if (i==MAX_LABEL) ERREUR("cste MAX_LABEL too small","");
 T_label[i]=strdup(pt);
 }
T_label[i]=NULL;
fclose(file);
return i-1;
}

/*................................................................*/

int main(int argc, char **argv)
{
int nb,nblabel,i,j,nbok[MAX_LABEL],nberreur[MAX_LABEL],nbdeletion[MAX_LABEL],nbinsertion[MAX_LABEL],imax,imax2,total;
char ch[TailleLigne],*filenames,*pt,*chdefault,*ptid,*chtitre;
int t_ref[MAX_LABEL],idefault;
double t_score[MAX_LABEL],prec,reca,threshold,minipost;
FILE *fcorpus,*fhypout;

minipost=-100000;
threshold=-100;
fcorpus=NULL;
fhypout=stdout;
filenames=NULL;
chdefault=NULL;
chtitre=NULL;
idefault=-1;
if (argc>1)
 for(nb=1;nb<argc;nb++)
  if (!strcmp(argv[nb],"-names"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   filenames=argv[++nb];
   }
  else
  if (!strcmp(argv[nb],"-threshold"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (sscanf(argv[++nb],"%lf",&threshold)!=1) ERREUR("bad format for:",argv[nb]);
   }
  else
  if (!strcmp(argv[nb],"-post"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (sscanf(argv[++nb],"%lf",&minipost)!=1) ERREUR("bad format for:",argv[nb]);
   }
  else
  if (!strcmp(argv[nb],"-corpus"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(fcorpus=fopen(argv[++nb],"rt"))) ERREUR("can't read:",argv[nb]);
   }
  else
  /*
  if (!strcmp(argv[nb],"-hypout"))
   {
   if (nb+1==argc) ERREUR("must have a value after argument;",argv[nb]);
   if (!(fhypout=fopen(argv[++nb],"wt"))) ERREUR("can't read:",argv[nb]);
   }
  else
  */
  if (!strcmp(argv[nb],"-h"))
   {
   fprintf(stderr,"Syntax: %s [-h] -names <file .names> -corpus <file>\n",argv[0]);
   exit(0);
   }
  else ERREUR("unknown option:",argv[nb]);

if (!filenames) ERREUR("bad syntax, check '-h'","");
nblabel=load_names(filenames);
if (0) fprintf(stderr,"nblabel=%d\n",nblabel);
for(i=0;i<nblabel;i++) nbok[i]=nberreur[i]=nbdeletion[i]=nbinsertion[i]=0;
if (chdefault)
 {
 for(i=0;(i<nblabel)&&(strcmp(T_label[i],chdefault));i++);
 if (i==nblabel) ERREUR("unknown label:",chdefault);
 idefault=i;
 }
for(nb=0;fgets(ch,TailleLigne,stdin);nb++)
 {
 if (0) fprintf(stderr,"%s",ch);
 for(i=0,pt=strtok(ch," \t\n");(pt)&&(i<nblabel);i++,pt=strtok(NULL," \t\n"))
  {
  if (sscanf(pt,"%d",&(t_ref[i]))!=1) ERREUR("bad format1:",pt);
  }
 if (!pt) ERREUR("bad format2:",ch);
 for(i=0;(pt)&&(i<nblabel);i++,pt=strtok(NULL," \t\n"))
  {
  if (sscanf(pt,"%lf",&(t_score[i]))!=1) ERREUR("bad format3:",pt);
  }
 if (pt) ERREUR("bad format4:",ch);
 for(imax2=-1,imax=0,i=1;i<nblabel;i++)
  {
  if (0) fprintf(stderr,"t_score[%d]=%lf  ",i,t_score[i]);
  if (t_score[i]>t_score[imax]) { imax2=imax; imax=i; }
  else
   {
   if (imax2==-1) imax2=i;
   else if (t_score[i]>t_score[imax2]) imax2=i;
   }
  }
 if (fcorpus)
  {
  if (!fgets(ch,TailleLigne,fcorpus)) ERREUR("files not aligned ??","");
  ptid=strtok(ch," ,.\t\n"); if (!ptid) ERREUR("hououh","");
  }

 if (((t_score[imax]-t_score[imax2])>=threshold)&&(t_score[imax]>=minipost))
  {
  if (t_ref[imax]==1) nbok[imax]++; else { nbinsertion[imax]++; }
  if (fhypout) fprintf(fhypout,"%s\t%s\t%lf\n",ptid,T_label[imax],t_score[imax]);
  }
 else
  {
  if (chdefault) imax=idefault; else imax=-1;
  if (fhypout) fprintf(fhypout,"%s\t%s\t%lf\n",ptid,imax!=-1?T_label[imax]:"NULL",t_score[imax]);
  }

 for(i=0;(i<nblabel)&&(t_ref[i]==0);i++);
 if (i<nblabel)
  {
  if (i!=imax) { nbdeletion[i]++; }
  if (0) fprintf(stderr,"\tmax=%d (%lf) => %s\n",imax,imax!=-1?t_score[imax]:-1,imax!=-1?t_ref[imax]==1?"succes":"echec":"delete");
  }
 }

for(total=i=0;i<nblabel;i++) total+=nbdeletion[i];

if (!fhypout)
 {
 printf("<li><h3> %s </h3></li>\n<ul>\n<li> <strong>Correct : %.2lf%% </strong> </li> <li> <strong>Erreurs: %d/%d (%.2lf%%)</strong> </li>\n",
		 chtitre?chtitre:"Resultats",(double)100-(double)(total*100)/(double)nb,total,nb,(double)(total*100)/(double)nb);
 //printf("At the section level\nERREUR: %d/%d (%.2lf%%)\n",total,nb,(double)(total*100)/(double)nb);
 printf("<li> Détail par étiquette\n<table>\n");
 for(i=0;i<nblabel;i++)
  {
  prec=(double)(nbok[i]*100)/(double)(nbok[i]+nbinsertion[i]);
  reca=(double)(nbok[i]*100)/(double)(nbok[i]+nbdeletion[i]);
  printf("<tr><td>%s</td><td>%d/%d</td><td>%.2lf%%</td><td>P=%.2lf</td><td>R=%.2lf</td><td>F=%.2lf</td></tr>\n",T_label[i],nbdeletion[i],nbdeletion[i]+nbok[i],
	(double)(nbdeletion[i]*100)/(double)(nbdeletion[i]+nbok[i]),prec,reca,(prec*reca*2)/(prec+reca));
  }
 printf("</table>\n</li>\n</ul>\n");
 }

if (fcorpus) fclose(fcorpus);
if (fhypout) fclose(fhypout);

exit(0);
}
  
