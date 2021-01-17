#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
    char etunimi[21];
    char sukunimi[21];
    int jasennro;
    int vuosi;
    int maksut[5];
} tietue;

int input(void);
int paavalikko(void);
tietue lisaa(void);
void tulosta(tietue tieto);
tietue poista(void);

int main(void) {
    
    char string[30];
    tietue taulukko[100];
    int n = 0, i, size, index, choice;
    
    while(1) {
        switch (paavalikko()) {
            
            case 1 :
                taulukko[n] = lisaa();
                n++;
                continue;
                
            case 2 :
                printf("Anna poistettavan jasenen jasennumero > ");
                choice = input();
                size = n;
                for (i=0;i<size;i++) {
                    if (taulukko[i].jasennro == choice) {
                        index = i;
                    }
                }
                taulukko[index] = poista();
                continue;
            case 3 :
                printf("\nAnna muutettavan jasenen jasennumero > ");
                choice = input();
                size = n;
                for (i=0;i<size;i++) {
                    if (taulukko[i].jasennro == choice) {
                        index = i;
                    }
                }
                printf("\n1 Muuta etunimi\n2 Muuta sukunimi\n3 Muuta jasennumero\n4 Muuta liittyisvuosi\n5 Muuta jasenmaksutietoja\n\nValintasi > ");
                choice = input();
                if (choice == 1) {
                    printf("\nAnna uusi etunimi > ");
                    scanf("%s", taulukko[index].etunimi);
                    continue;
                }
                else if (choice == 2) {
                    printf("\nAnna uusi sukunimi > ");
                    scanf("%s", taulukko[index].sukunimi);
                    continue;
                }
                else if (choice == 3) {
                    printf("\nAnna uusi jasennumero > ");
                    scanf("%i", &taulukko[index].jasennro);
                    continue;
                }
                else if (choice == 4) {
                    printf("\nAnna uusi liittymisvuosi > ");
                    scanf("%i", &taulukko[index].vuosi);
                    continue;
                }
                else if (choice == 5) {
                    continue;
                }
                else {
                    scanf("%*s");
                    continue;
                }
                
            case 4 :
                printf("\nAnna jasennro > ");
                choice = input();
                for (i=0;i<n+1;i++) {
                    if (taulukko[i].jasennro == choice) {
                        index = i;
                    }
                }
                tulosta(taulukko[index]);
                continue;
                
            case 5 :
                size = n;
                for (i=0;i<size;i++) {
                    if (taulukko[i].jasennro != 0) {
                        tulosta(taulukko[i]);
                    }
                }
                continue;
                
            case 6 :
                printf("\n1 Haku etunimella\n2 Haku sukunimella\n3 Haku jasennumerolla\n4 Haku liittymisvuodella\n5 Haku jasenmaksurastien perusteella\n\nValintasi > ");
                choice = input();
                if (choice == 1) {
                    printf("\nAnna etunimi > ");
                    scanf("%s", string);
                    for (i=0;i<n;i++) {
                        if (strcmp(string, taulukko[i].etunimi) == 0) {
                            tulosta(taulukko[i]);
                        } 
                    }
                    continue;
                }
                else if (choice == 2) {
                    printf("\nAnna sukunimi > ");
                    scanf("%s", string);
                    for (i=0;i<n;i++) {
                        if (strcmp(string, taulukko[i].sukunimi) == 0) {
                            tulosta(taulukko[i]);
                        } 
                    }
                    continue;
                }
                else if (choice == 3) {
                    printf("\nAnna jasennumero > ");
                    scanf("%i", &choice);
                    for (i=0;i<n;i++) {
                        if (choice == taulukko[i].jasennro) {
                            tulosta(taulukko[i]);
                        }
                    }
                    continue;
                }
                else if (choice == 4) {
                    printf("\nAnna liittymisvuosi > ");
                    scanf("%i", &choice);
                    for (i=0;i<n;i++) {
                        if (choice == taulukko[i].vuosi) {
                            tulosta(taulukko[i]);
                        } 
                    }
                    continue;
                }
                else if (choice == 5) {
                    continue;
                }
                else {
                    scanf("%*s");
                    continue;
                }
                
            case 7 :
                exit(0);
                
            default :
                printf("Vaaranlainen syote\n");
        }
    }
    
    return 0;
}

int paavalikko(void) {
    int valinta;
    printf("\n1 Uuden jasenen lisaaminen\n2 Jasenen tietojen poisto\n3 Jasenen tietojen muuttaminen\n4 Tulosta jasen\n5 Tulosta rekisteri\n6 Haku\n7 Lopetus\n\nValintasi > ");
    scanf("%i", &valinta);
    return valinta;
}

tietue lisaa(void) {
    tietue tiedot;
    int i, input;
    
    printf("\nAnna Jasenen Etu- ja Sukunimi  > ");
    scanf("%s", tiedot.etunimi);
    scanf("%s", tiedot.sukunimi);
    
    printf("\nAnna jasennumero > ");
    scanf("%i", &tiedot.jasennro);
    
    printf("\nAnna liittymisvuosi > ");
    scanf("%i", &tiedot.vuosi);
    
    for (i=0;i<5;i++) {
        printf("\nAnna vuoden %i jasenmaksu > ", i+2016);
        scanf("%i", &tiedot.maksut[i]);
    }
    
    printf("\n");
    
    return tiedot;
}

void tulosta(tietue tieto) {
    printf("\n   ETUNIMI: %s\n", tieto.etunimi);
    printf("  SUKUNIMI: %s\n", tieto.sukunimi);
    printf("  JASENNRO: %i\n", tieto.jasennro);
    printf("LIITTVUOSI: %i\n", tieto.vuosi);
    printf("\n");
}

tietue poista(void) {
    tietue tiedot;
    int i;
    strcpy(tiedot.etunimi, " ");
    strcpy(tiedot.sukunimi, " ");
    tiedot.jasennro = 0;
    tiedot.vuosi = 0;
    for (i=0;i<5;i++) {
        tiedot.maksut[i] = 0;
    }
    
    return tiedot;
}


int input() { 
    int number; 
    scanf("%d", &number); 
    return (number); 
} 
