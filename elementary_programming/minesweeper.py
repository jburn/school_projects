import haravasto as ha
import random as rand
import time

class Game:
    def __init__(self, height, width, mines):
        self.height = height
        self.width = width
        self.mines = mines
        self.starttime = 0
        self.drawMap = []
        self.gameMap = []
        self.free_coords = []
        self.statistics = {
            "moves":0,
            "time":0,
            "result":None,
            "gamelength":0
        }
        
    def initialize_maps(self):
        for n in range(self.height):
            self.drawMap.append([])
            self.gameMap.append([])
            for m in range(self.width):
                self.drawMap[n].append(" ")
                self.gameMap[n].append(" ")
                self.free_coords.append((m, n))
        
    def set_mines(self):
        for n in range(self.mines):
            coords = rand.choice(self.free_coords)
            self.gameMap[coords[1]][coords[0]] = "x"
            self.free_coords.remove(coords)

    def count_mines(self, x, y):
        mine_amount = 0
        height = len(self.gameMap) - 1
        width = len(self.gameMap[0]) - 1
        for j in range(y - 1, y + 2):
            for i in range(x - 1, x + 2):
                if (j >= 0 and j <= height) and (i >= 0 and i <= width):
                    if self.gameMap[j][i] == "x":
                        mine_amount += 1
        return mine_amount

    def add_numbers(self):
        for ycoord, j in enumerate(self.gameMap):
            for xcoord, i in enumerate(j):
                if i != "x":
                    self.gameMap[ycoord][xcoord] = "{}".format(self.count_mines(xcoord, ycoord))
    
    def flood(self, x, y):
        queue = [(x, y)]
        height = self.height - 1
        width = self.width - 1
        while queue:
            value = queue.pop()
            for j in range(value[1] - 1, value[1] + 2):
                for i in range(value[0] - 1, value[0] + 2):
                    if ((0 <= j <= height) and (0 <= i <= width)):
                        if  self.gameMap[j][i] == "0" and self.drawMap[j][i] == " ":
                            queue.append((i, j))
                        self.drawMap[j][i] = self.gameMap[j][i]

    def mapcheck(self):
        numb = 0
        for j in range(self.height):
            for i in range(self.width):
                if (self.drawMap[j][i] == " " or self.drawMap[j][i] == "f"):
                    numb += 1
        if numb == self.mines:
            return 0
        else:
            return 1
    
    def loss(self):
        self.drawMap = self.gameMap
        self.statistics["gamelength"] = time.time() - self.starttime
        self.statistics['result'] = "Loss"
        add_statistics()
        print("Date: {date} | Game length: {length:.0g} seconds | Moves: {moves} | Result: {result} | Game dimensions: {width}x{height} | Mines: {mines}\n".format(
            date=time.strftime("%d-%m-%Y %H:%M", time.localtime()),
            length=self.statistics["gamelength"],
            moves=self.statistics["moves"],
            result=self.statistics["result"],
            width=self.width,
            height=self.height,
            mines=self.mines
        ))
        ha.lopeta()

    def check_win(self):
        if self.mapcheck() == 0:
            self.statistics["result"] = "Win"
            self.statistics["gamelength"] = time.time() - self.starttime
            add_statistics()
            print("Date: {date} | Game length: {length:.0g} seconds | Moves: {moves} | Result: {result} | Game dimensions: {width}x{height} | Mines: {mines}\n".format(
                date=time.strftime("%d-%m-%Y %H:%M", time.localtime()),
                length=self.statistics["gamelength"],
                moves=self.statistics["moves"],
                result=self.statistics["result"],
                width=self.width,
                height=self.height,
                mines=self.mines
            ))
            ha.lopeta()


def menu():
    print("Welcome to Minesweeper")
    choice = input("(p)lay, (s)tatistics or (q)uit: ")
    return choice

def game_settings():
    try:
        while True:
            height = int(input("Insert game grid height: "))
            if 3 < height < 20:
                break
            else:
                print("Invalid height value")

        while True:
            width = int(input("Insert game grid width: "))
            if 3 < width < 20:
                break
            else:
                print("Invalid width value")

        while True:
            mines = int(input("Insert the amount of mines: "))
            if 0 < mines < (height * width):
                break
            else:
                print("Invalid amount of mines")

    except ValueError:
        print("Invalid input")
    
    return height, width, mines

def mouse_handler(x, y, button, edit_button):
    x = int(x / 40)
    y = int(y / 40)

    if button == ha.HIIRI_VASEN:
        game.check_win()
        game.statistics['moves'] += 1
        if game.gameMap[y][x] == "x":
            game.drawMap[y][x] = game.gameMap[y][x]
            game.loss()
        elif game.gameMap[y][x] == "0":
            game.flood(x, y)
        else:
            game.drawMap[y][x] = game.gameMap[y][x]

    if button == ha.HIIRI_OIKEA:
        game.statistics['moves'] += 1
        if game.drawMap[y][x] == ' ':
            game.drawMap[y][x] = 'f'

        elif game.drawMap[y][x] == 'f':
            game.drawMap[y][x] = ' '
    
def draw_map():
    ha.tyhjaa_ikkuna()
    ha.piirra_tausta()
    ha.aloita_ruutujen_piirto()
    for y, j in enumerate(game.drawMap):
        for x, i in enumerate(j):
            xcoord = x * 40
            ycoord = y * 40
            ha.lisaa_piirrettava_ruutu(i, xcoord, ycoord)
    ha.piirra_ruudut()

def initialization():
    window_height = game.height * 40
    window_width = game.width * 40
    ha.lataa_kuvat("spritet")
    ha.luo_ikkuna(window_width, window_height)
    ha.aseta_piirto_kasittelija(draw_map)
    ha.aseta_hiiri_kasittelija(mouse_handler)
    game.starttime = time.time()
    ha.aloita()

def add_statistics():
    with open("stats.txt", "a") as stat_file:
        stat_file.write("Date: {date} | Game length: {length:.0g} seconds | Moves: {moves} | Result: {result} | Game dimensions: {width}x{height} | Mines: {mines}\n".format(
            date=time.strftime("%d-%m-%Y %H:%M", time.localtime()),
            length=game.statistics["gamelength"],
            moves=game.statistics["moves"],
            result=game.statistics["result"],
            width=game.width,
            height=game.height,
            mines=game.mines
        ))
        stat_file.close()

def open_statistics():
    stat_file = open("stats.txt", "r")
    content = stat_file.read()
    print(content)
    stat_file.close()

if __name__ == "__main__":
    while True:
        choice = menu()

        if choice == 'p':
            height, width, mines = game_settings()
            game = Game(height, width, mines)
            game.initialize_maps()
            game.set_mines()
            game.add_numbers()
            initialization()

        elif choice == 's':
            open_statistics()

        elif choice == 'q':
            break

        else:
            print("Invalid input")
