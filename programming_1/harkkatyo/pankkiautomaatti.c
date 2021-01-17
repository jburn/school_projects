#include <stdio.h>
#include <Windows.h>
#include <stdlib.h>
#include <string.h>


static float bal;

int whattodo(void);
int login(void);
void menu(void);
void otto(void);
void saldo(void);
void tapahtumat(void);
void liittyma(void);


int main(void) {
    FILE *fp;
    char buffer[10];

    if (login() == 1) {
        printf("Vaara PIN!\n");
        exit(1);
    }
    else {
        if ((fp = fopen("bal.txt", "r")) == NULL) {
            printf("Virhe ladattaessa saldoa\n");
            exit(1);
        }
        else {
            fscanf(fp, "%[^\n]", buffer);
            bal = strtof(buffer, NULL);
            fclose(fp);
            printf("Tervetuloa\n");
            menu();
        }
    }
    return 0;
}

int login() {
    FILE *fPointer;
    char accname[5] = "";
    char accpin[10] = "";
    char inspin[10] = "";
    while (1) {
        printf("Syota tilin numero >");
        scanf("%s", accname);
        strcat(accname, ".acc");
        if ((fPointer = fopen(accname, "r")) == NULL){
            printf("Tilia ei loydy!\n");
            continue;
        }
        else {
            fscanf(fPointer, "%[^\n]", accpin);
            break;
        }
    }
    while (1) {
        printf("Syota PIN >");
        scanf("%s", inspin);
        if (strcmp(accpin,inspin) == 0) {
            return 0;
        }
        else if (strcmp(accpin,inspin) == 1){
            return 1;
        }
    }
    fclose(fPointer);
}

// SCANF FUNCTION
int whattodo() {
    int option;
    scanf("%i", &option);
    return (option);
}

// RETURN TO THE BEGINNING
void menu() {
    int choice;
    while(1) {
        printf("Valitse toiminto:\n1-Otto | 2-Saldo | 3-Tapahtumat | 4-Liittyman lataus | 5-Tilin valinta | 6-Lopeta\n> ");
        choice = whattodo();
        if (choice == 1) {
            // OTTO-OSIO
            otto();
            break;
        }
        else if (choice == 2) {
            //SALDO-OSIO
            saldo();
            break;
        }
        else if (choice == 3) {
            //TAPAHTUMAT-OSIO
            tapahtumat();
            break;
        }
        else if (choice == 4) {
            //LIITTYMÄOSIO
            liittyma();
            break;
        }
        else if (choice == 5) {
            //TILIN VALINTA-OSIO
            choice = 0;
            printf("[Tili valitaan]\n");
            menu();
        }
        else if (choice == 6) {
            printf("Tervetuloa uudelleen");
            break;
        }
        else {
            scanf("%*s");
            printf("Vaaranlainen syote\n");
            continue;
        }
    }
}

// RETURN TO OTTO-MENU
void otto() {
    FILE *fpoint, *eventpoint;
    float  loopamount, choice, amount = 0, fiftys = 0, twentys = 0;
    printf("Saldo talla hetkella: %.2f euroa\n", bal);
    printf("1-Syota summa tai 2-Alkuun\n> ");
    choice = whattodo();
    if (choice == 1) {
        printf("Syota nostettava summa\n> ");
        scanf("%f", &amount);
        if (bal < amount) {
            printf("Liian suuri summa\n");
            otto();
        }
        else if (amount <= 0){
            printf("Vaaranlainen summa\n");
            otto();
        }
        else {
            loopamount = amount;
            while ((loopamount > 49) && (loopamount != 60) && (loopamount != 80)) {
                loopamount -= 50;
                fiftys += 1;
            }
            while (loopamount > 19) {
                loopamount -= 20;
                twentys += 1;
            }
            if (loopamount != 0) {
                printf("Vaaranlainen summa\n");
                otto();
            }
            else {
                bal = bal - amount;

                eventpoint = fopen("events.txt", "a");
                fpoint = fopen("bal.txt", "w");

                fprintf(eventpoint, "Otto: %.2f euroa\n", amount);
                fprintf(fpoint, "%f", bal);

                fclose(eventpoint);
                fclose(fpoint);

                printf("Haluatko tiedot?\n1-Ei kiitos | 2-Naytolle | 3-Kuitille\n> ");
                choice = whattodo();
                if (choice == 1) {
                    printf("Tervetuloa uudelleen");
                }
                else if (choice == 2) {
                    if (twentys > 0) {
                        printf("Annetaan kayttajalle %.0f kpl 20 euron seteleita\n", twentys);
                    }
                    if (fiftys > 0) {
                        printf("Annetaan kayttajalle %.0f kpl 50 euron seteleita\n", fiftys);
                    }
                    printf("Jaljella oleva saldo: %.2f euroa\nTervetuloa uudelleen", bal);
                }
                else if (choice == 3) {
                    if (twentys > 0) {
                        printf("Annetaan kayttajalle %.0f kpl 20 euron seteleita\n", twentys);
                    }
                    if (fiftys > 0) {
                        printf("Annetaan kayttajalle %.0f kpl 50 euron seteleita\n", fiftys);
                    }
                    printf("Kuitti tulostuu...[Jaljella oleva saldo: %.2f euroa]\nTervetuloa uudelleen", bal);
                }
                else if (choice == 0) {
                    otto();
                }
                else {
                    printf("Vaaranlainen syote, HYVASTI\n");
                }
            }
        }
    }
    else if (choice == 2) {
        choice = 0;
        printf("Palataan alkuun\n");
        menu();
    }
    else if (choice == 0) {
        otto();
    }
    else {
        scanf("%*s");
        printf("Vaaranlainen syote\n");
        otto();
    }
}

void tapahtumat() {
    FILE *ePoint;
    int choice;
    char contents[1000];
    ePoint = fopen("events.txt", "r");
    while (1) {
        printf("Haluatko tiedot?\n1-Naytolle | 2-Kuitille\n> ");
        choice = whattodo();
        if (choice == 1) {
            while (fgets(contents, 1000, ePoint) != NULL) {
                printf("%s\n", contents);
            }
            printf("Tervetuloa uudelleen");
            break;
        }
        else if (choice == 2) {
            printf("Kuitti tulostuu...\nTervetuloa uudelleen");
            break;
        }
        else {
            scanf("%*s");
            printf("Vaaranlainen syote\n");
            continue;
        }
    }
}

void saldo() {
    printf("Saldo: %.2f euroa\n", bal);
    menu();
}

void liittyma() {
    int choice;
    while (1) {
        printf("Valitse operaattori:\n1-Operaattori1 | 2-Operaattori2 | 3-Alkuun\n> ");
        choice = whattodo();
        if (choice == 1 || choice == 2) {
            choice = 0;
            printf("[Puhelinnro syotetty]\n[Summa syotetty]\nHyvaksy?\n1-kylla | 2-ei\n> ");
            choice = whattodo();
            if (choice == 1) {
                printf("Puheaikaa ladataan...\nLataus valmis\nTervetuloa uudelleen");
                exit(0);
            }
            else if (choice == 2) {
                printf("Palataan alkuun\n");
                scanf("%*s");
                menu();
            }
            else {
                printf("Vaaranlainen syote, palataan alkuun\n");
                liittyma();
            }
        }
        else if (choice == 3) {
            printf("Palataan alkuun\n");
            scanf("%*s");
            menu();
        }
        else {
            scanf("%*s");
            printf("Vaaranlainen syote\n");
        }
    }
}
