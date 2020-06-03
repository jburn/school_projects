import time
import random as rand
import haravasto as ha

arvot = {
"ikorkeus": 500,
"ileveys": 500,
"miinamaara": 10,
"pelitila": "kesken",
"tehtava": "null"
}

tilastot = {
"siirrot": 0,
"aika": 0,
"tilanne": "null",
"kentan_leveys": "null",
"kentan_korkeus": "null"
}

kentta = []
aluskentta = []
vapaat = []

def pyyda_tehtava():
    """
    Kysyy käyttäjältä mitä hän haluaa tehda. Sulkee valikon tai palauttaa syotteen.
    Jos valitaan uusi peli, luo kenttää ja piirrettävää ruudukkoa kuvaavat listat.
    """
    kentta.clear()
    aluskentta.clear()
    vapaat.clear()
    while arvot["tehtava"] == "null":
        tehtava = input("Valitse toiminto: Uusi peli(u), Lopeta(l) tai Tilastot(t): ")
        if tehtava not in ("u", "l", "t"):
            print("Vääränlainen syöte")
        elif tehtava == "u":
            while True:
                try:
                    leveys = int(input("Syötä pelilaudan leveys ruutuina: "))
                    korkeus = int(input("Syötä pelilaudan korkeus ruutuina: "))
                    if leveys <= 3 or korkeus <= 3:
                        print("Liian pieni kentta")
                        continue
                    if korkeus > 25 or leveys > 50:
                        print("Liian suuri kentta")
                        continue
                    arvot["ileveys"] = leveys * 40
                    arvot["ikorkeus"] = korkeus * 40
                    tilastot["kentan_leveys"] = leveys
                    tilastot["kentan_korkeus"] = korkeus
                    for j in range(korkeus):
                        kentta.append([])
                        aluskentta.append([])
                        for i in range(leveys):
                            kentta[j].append(" ")
                            aluskentta[j].append(" ")
                            vapaat.append((i, j))
                    miinat = int(input("Syötä miinojen määrä: "))
                    if miinat >= (leveys * korkeus) / 1.5:
                        print("liikaa miinoja")
                        continue
                    else:
                        arvot["miinamaara"] = miinat
                        arvot["tehtava"] = "peli"
                        break
                except ValueError:
                    print("Vääränlainen syöte")
                except IndexError:
                    print("Jokin meni vikaan")
        elif tehtava == "l":
            arvot["tehtava"] = "lopeta"
            break
        elif tehtava == "t":
            arvot["tehtava"] = "tilastot"
            break

def miinoita(miinakentta, miinat):
    """
    Miinoittaa annetun kentän annetulla miinamäärällä
    """
    for n in range(miinat):
        koordinaatti = vapaat[rand.randint(0, (len(vapaat) - 1))]
        x = koordinaatti[0]
        y = koordinaatti[1]
        miinakentta[y][x] = "x"
        vapaat.remove(koordinaatti)

def lisaa_numerot(kentta):
    """
    Lisää aluskenttään tyhjien ruutujen paikalle niitä ympyröiviä miinamääriä vastaavat numerot.
    """
    for ykoord, n in enumerate(kentta):
        for xkoord, m in enumerate(n):
            if m != "x":
                kentta[ykoord][xkoord] = "{}".format(laske_miinat(kentta, xkoord, ykoord))

def hiiri_kasittelija(x, y, nappi, muokkausnappi):
    """
    Käsittelijäfunktio klikkauksille.
    """
    x = int(x / 40)
    y = int(y / 40)
    if nappi == ha.HIIRI_VASEN:
        tilastot["siirrot"] = tilastot["siirrot"] + 1
        if aluskentta[y][x] == "x":
            arvot["pelitila"] = "havio"
            havio()
        elif kentta[y][x] == " ":
            if laske_miinat(aluskentta, x, y) == 0:
                kentta[y][x] = "{}".format(laske_miinat(aluskentta, x, y))
                tulva(kentta, x, y)
            elif laske_miinat(aluskentta, x, y) > 0:
                kentta[y][x] = "{}".format(laske_miinat(aluskentta, x, y))
    elif nappi == ha.HIIRI_OIKEA and kentta[y][x] == " ":
        kentta[y][x] = "f"
    elif nappi == ha.HIIRI_OIKEA and kentta[y][x] == "f":
        kentta[y][x] = " "

def piirra_kentta():
    """
    Käsittelijäfunktio, joka piirtää kaksiulotteisena listana kuvatun miinakentän
    ruudut näkyviin peli-ikkunaan. Funktiota kutsutaan aina kun pelimoottori pyytää
    ruudun näkymän päivitystä.
    """
    ha.tyhjaa_ikkuna()
    ha.piirra_tausta()
    ha.aloita_ruutujen_piirto()
    for y, j in enumerate(kentta):
        for x, i in enumerate(j):
            xkoord = x * 40
            ykoord = y * 40
            ha.lisaa_piirrettava_ruutu(i, xkoord, ykoord)
    ha.piirra_ruudut()

def paivitys_kasittelija(kulunut_aika):
    """
    Käsittelee ajan kulkua ja tarkistaa samalla onko peli voittotilanteessa.
    """
    tilastot["aika"] = (tilastot["aika"] + (1 / 60))
    if tarkista_kentta() == arvot["miinamaara"]:
        arvot["pelitila"] = "voitto"
        ha.lopeta()

def laske_miinat(alue, x, y):
    """
    Laskee annetun koordinaatin ympärillä olevat miinat
    """
    miinat = []
    korkeus = len(alue) - 1
    leveys = len(alue[0]) - 1
    for j in range(y - 1, y + 2):
        for i in range(x - 1, x + 2):
            if (j >= 0 and j <= (len(alue) - 1)) and (i >= 0 and i <= (len(alue[0]) - 1)):
                if aluskentta[j][i] == "x":
                    miinat.append((i, j))
    return len(miinat)

def laske_tyhjat(kentta, x, y):
    """
    Laskee annetun koordinaatin ympärillä olevat nollat
    """
    tyhjat = []
    korkeus = len(kentta) - 1
    leveys = len(kentta[0]) - 1
    for j in range(y - 1, y + 2):
        for i in range(x - 1, x + 2):
            if (j >= 0 and j <= len(kentta) - 1) and (i >= 0 and i <= len(kentta[0]) - 1):
                if kentta[j][i] == "0":
                    tyhjat.append((i, j))
    return len(tyhjat)

def tulva(kentta, x, y):
    """
    Tulvatäytön toteuttava funktio.
    """
    lista = [(x, y)]
    korkeus = len(kentta) - 1
    leveys = len(kentta[0]) - 1
    while lista:
        arvo = lista.pop()
        for j in range(arvo[1] - 1, arvo[1] + 2):
            for i in range(arvo[0] - 1, arvo[0] + 2):
                if ((0 <= j <= korkeus) and (0 <= i <= leveys) and aluskentta[j][i] == "0" and kentta[j][i] == " ") or ((0 <= j <= korkeus) and (0 <= i <= leveys) and kentta[j][i] == " " and laske_tyhjat(aluskentta, i, j) > 0):
                    kentta[j][i] = aluskentta[j][i]
                    if kentta[j][i] == "0":
                        lista.append((i, j))

def tarkista_kentta():
    """
    Laskee moniko piirretyn kentän ruutu täsmää aluskentän kanssa.
    """
    vaarat = []
    for ykoord, j in enumerate(kentta):
        for xkoord, i in enumerate(j):
            if kentta[ykoord][xkoord] != aluskentta[ykoord][xkoord]:
                vaarat.append((xkoord, ykoord))
    return len(vaarat)

def lisaa_tilastoihin():
    """
    Kirjoittaa tilastot.txt tiedostoon kaiken tarvittavan infon.
    """
    with open("tilastot.txt", "a+") as tiedosto:
        tiedosto.write(
        "Ajankohta: {ajankohta}  Pelin kesto: {kesto} minuuttia  Siirrot: {siirrot}  Lopputulos: {lopputulos}  Kentän koko: {kentan_leveys} x {kentan_korkeus}  Miinamäärä: {miinat} \n".format(
        ajankohta=(time.strftime("%Y-%m-%d %H:%M", time.gmtime())), 
        kesto=round((tilastot["aika"] / 60), 1), 
        siirrot=tilastot["siirrot"],
        lopputulos=tilastot["tilanne"], 
        kentan_leveys=tilastot["kentan_leveys"],
        kentan_korkeus=tilastot["kentan_korkeus"],
        miinat=arvot["miinamaara"]
        ))
        tiedosto.close()

def tilastotauki():
    """
    Tulostaa tilastot.txt tiedostot sisällön.
    """
    tiedosto = open("tilastot.txt", "r")
    sisalto = tiedosto.read()
    if sisalto != "":
        print(sisalto)
        tiedosto.close()
    else:
        print("Ei tilastoja")
        tiedosto.close()

def havio():
    """
    Tekee kaikki tarvittavat toimenpiteet pelin lopussa häviön sattuessa.
    (sanakirjojen muuttujien palauttaminen alkutilanteeseen jne.)
    """
    for ykoord, j in enumerate(kentta):
        for xkoord, i in enumerate(j):
            kentta[ykoord][xkoord] = aluskentta[ykoord][xkoord]
    tilastot["tilanne"] = "häviö"
    arvot["tehtava"] = "null"
    lisaa_tilastoihin()
    print("Hävisit pelin")
    print("Ajankohta: {ajankohta}  Pelin kesto: {kesto} minuuttia  Siirrot: {siirrot}  Lopputulos: {lopputulos}  Kentän koko: {kentan_leveys} x {kentan_korkeus}  Miinamäärä: {miinat} \n".format(
    ajankohta=(time.strftime("%Y-%m-%d %H:%M", time.gmtime())), 
    kesto=round((tilastot["aika"] / 60), 1), 
    siirrot=tilastot["siirrot"],
    lopputulos=tilastot["tilanne"], 
    kentan_leveys=(tilastot["kentan_leveys"]),
    kentan_korkeus=(tilastot["kentan_korkeus"]),
    miinat=arvot["miinamaara"]
    ))
    tilastot["siirrot"] = 0
    tilastot["aika"] = 0

def voitto():
    """
    Tekee kaikki tarvittavat toimenpiteet pelin lopussa voittotilanteessa.
    (sanakirjojen muuttujien palauttaminen alkutilanteeseen jne.)
    """
    print("Voitit pelin")
    arvot["tehtava"] = "null"
    arvot["pelitila"] = "null"
    tilastot["tilanne"] = "voitto"
    lisaa_tilastoihin()
    print("Ajankohta: {ajankohta}  Pelin kesto: {kesto} minuuttia  Siirrot: {siirrot}  Lopputulos: {lopputulos}  Kentän koko: {kentan_leveys} x {kentan_korkeus}  Miinamäärä: {miinat} \n".format(
    ajankohta=(time.strftime("%Y-%m-%d %H:%M", time.gmtime())),
    kesto=round((tilastot["aika"] / 60), 1),
    siirrot=tilastot["siirrot"],
    lopputulos=tilastot["tilanne"],
    kentan_leveys=(tilastot["kentan_leveys"]),
    kentan_korkeus=(tilastot["kentan_korkeus"]),
    miinat=arvot["miinamaara"]
    ))
    tilastot["siirrot"] = 0
    tilastot["aika"] = 0

def main():
    """
    Lataa pelin grafiikat, luo peli-ikkunan, asettaa siihen piirto-, hiiri- ja toistokäsittelijät.
    """
    ha.lataa_kuvat("spritet")
    ha.luo_ikkuna(arvot["ileveys"], arvot["ikorkeus"])
    ha.aseta_piirto_kasittelija(piirra_kentta)
    ha.aseta_hiiri_kasittelija(hiiri_kasittelija)
    ha.aseta_toistuva_kasittelija(paivitys_kasittelija)
    ha.aloita()

if __name__ == "__main__":
    print("Miinaharava")
    while True:
        pyyda_tehtava()
        if arvot["tehtava"] == "peli":
            arvot["pelitila"] = "kesken"
            miinoita(aluskentta, arvot["miinamaara"])
            lisaa_numerot(aluskentta)
            main()
            arvot["tehtava"] = "null"
            if arvot["pelitila"] == "voitto":
                voitto()
            continue
        elif arvot["tehtava"] == "tilastot":
            tilastotauki()
            arvot["tehtava"] = "null"
            continue
        elif arvot["tehtava"] == "lopeta":
            break
