#include "extremite.h"
#include "iftun.h"

typedef struct Conf{
    char* tun;
    char* inip;
    char* inport;
    char* options;
    char* outip;
    char* outport;
}Conf;

Conf* read_configuration_file(char* f){
    Conf* res = (Conf*)malloc(sizeof(Conf));
    FILE* file = fopen(f, "r");

    if (file == NULL){
        perror("error while opening file");
        return NULL;
    }

    char* l;
    size_t len;
    size_t r;
    while((r = getline(&l, &len, file)) != -1){
        if (l[0] == '#') continue;
        char* token = strtok(l, "=");
        if (strcmp("tun", token) == 0){
            token = strtok(NULL, "=");
            res->tun = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->tun, token);
        }
        if (strcmp("inip", token) == 0){
            token = strtok(NULL, "=");
            res->inip = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->inip, token);
        }
        if (strcmp("inport", token) == 0){
            token = strtok(NULL, "=");
            res->inport = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->inport, token);
        }
        if (strcmp("options", token) == 0){
            token = strtok(NULL, "=");
            res->options = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->options, token);
        }
        if (strcmp("outip", token) == 0){
            token = strtok(NULL, "=");
            res->outip = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->outip, token);
        }
        if (strcmp("outport", token) == 0){
            token = strtok(NULL, "=");
            res->outport = (char*)malloc(sizeof(char)*(strlen(token)+1));
            token[strlen(token)] = '\0';
            strcpy(res->outport, token);
        }
    }
    return res;
}

void print_conf(Conf *conf){
    printf("name : %s\n", conf->tun);
    printf("OUT IP : %s\n", conf->outip);
    printf("OUT PORT : %s\n", conf->outport);
    printf("OPTIONS : %s\n", conf->options);
    printf("IN IP : %s\n", conf->inip);
    printf("IN PORT : %s\n", conf->inport);
}

int main(int argc, char **argv){
    if (argc != 2) {
		printf("Usage : %s <configuration file>\n", argv[0]);
		exit(1);
	};

    Conf *conf = read_configuration_file(argv[1]);
    print_conf(conf);
    int tunfd = tun_alloc(conf->tun);
    system("ip a");
    char config[2000];
    sprintf(config, "./configure-tun.sh %s %s", conf->tun, conf->inip);
    system(config);
    char route[2000];
    system("chmod +x configure-route.sh");
    sprintf(route, "./configure-route.sh %s %s", conf->options, conf->tun);
    if (strcmp(conf->outip, "") == 0){
        ext_out(tunfd, conf->inport);
    }
    else {
        ext_in(tunfd, conf->outip, conf->outport);
    }
}