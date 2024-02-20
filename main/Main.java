
package main;
import heroes.Hero;
import heroes.Rogue;
import heroes.Pyro;
import heroes.Wizard;
import heroes.Knight;
import fileio.implementations.FileReader;
import fileio.implementations.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
public final class Main {
    private Main() { }
    public static void main(final String[] args) {
        ArrayList<Hero> heroList = new ArrayList<Hero>();
        int numLin, numCol, numPers, coordX, coordY, numRounds;
        String race, moves, selectAChar, terrainsLine;

        try {
            FileReader fileReader = new FileReader(args[0]);
            numLin = fileReader.nextInt();
            numCol = fileReader.nextInt();
            String[][] dreptunghi2D = new String[numLin][numCol]; // Matricea de terenuri
            for (int i = 0; i < numLin; i++) {
                terrainsLine = fileReader.nextWord();
                for (int j = 0; j < numCol; j++) {
                    // Introduce terenurile in matrice caracter cu caracter
                    dreptunghi2D[i][j] = Character.toString(terrainsLine.charAt(j));
                }
            }
            numPers = fileReader.nextInt();
            /*Citeste eroii si coordonatele lor initiale, instantiaza si ii introduce
            in ArrayList-ul de eroi */
            for (int k = 0; k < numPers; k++) {
                race = fileReader.nextWord();
                coordX = fileReader.nextInt();
                coordY = fileReader.nextInt();
                if (race.equals("W")) {
                    Hero wiz = new Wizard(coordX, coordY);
                    heroList.add(wiz);
                }
                if (race.equals("P")) {
                    Hero pyro = new Pyro(coordX, coordY);
                    heroList.add(pyro);
                }
                if (race.equals("K")) {
                    Hero knight = new Knight(coordX, coordY);
                    heroList.add(knight);
                }
                if (race.equals("R")) {
                    Hero rogue = new Rogue(coordX, coordY);
                    heroList.add(rogue);
                }
            }

            numRounds = fileReader.nextInt();
            /*Pentru fiecare runda, selecteaza caracterele pe rand si muta eroii in functie
            de comanda primita, daca acestia nu sunt paralizati. Pentru fiecare erou, se
            verifica daca sunt afectati de DoT siprimesc damage, dupa caz */
            for (int i = 0; i < numRounds; i++) {
                moves = fileReader.nextWord();
                for (int j = 0; j < heroList.size(); j++) {
                    selectAChar = Character.toString(moves.charAt(j));
                    Hero h = heroList.get(j);
                    if (h.isAlive()) {
                        if (selectAChar.equals("U") && !h.isParalysed()) {
                            h.goUp();
                        }
                        if (selectAChar.equals("D") && !h.isParalysed()) {
                            h.goDown();
                        }
                        if (selectAChar.equals("L") && !h.isParalysed()) {
                            h.goLeft();
                        }
                        if (selectAChar.equals("R") && !h.isParalysed()) {
                            h.goRight();
                        }
                        h.reduceParalysisCounter();
                        if (h.shouldTakeDoT()) {
                            h.takesDoT();
                        }
                    }
                }
                /* Se verifica daca doi eroi in viata se afla pe aceeasi pozitie si
                daca se verifica conditiile, cei doi eroi accepta lupta unul cul altul.
                Ordinea nu este relevanta decat atunci cand un erou este de tipul Wizard
                (deoarece trebuie sa se calculeze mai intai damage-ul dat de inamic) */
                for (int j = 0; j < heroList.size(); j++) {
                    for (int k = j + 1; k < heroList.size(); k++) {
                        Hero hero1 = heroList.get(j);
                        Hero hero2 = heroList.get(k);
                        if (hero1.isAlive() && hero2.isAlive()) {
                            if (hero1.getX() == hero2.getX() && hero1.getY() == hero2.getY()) {
                                if (hero1.getHeroType() != "W") {
                                hero2.accept(hero1, dreptunghi2D[hero2.getX()][hero2.getY()]);
                                hero1.accept(hero2, dreptunghi2D[hero1.getX()][hero1.getY()]);
                                } else {
                                hero1.accept(hero2, dreptunghi2D[hero1.getX()][hero1.getY()]);
                                hero2.accept(hero1, dreptunghi2D[hero2.getX()][hero2.getY()]);
                                }
                            }
                            if (hero1.isTheWinner(hero2)) {
                                hero1.gainExperience(hero2);
                            }
                            if (hero2.isTheWinner(hero1)) {
                                hero2.gainExperience(hero1);
                            }
                            if (hero1.isAlive()) {
                                hero1.checkLevelUp();
                            }
                            if (hero2.isAlive()) {
                                hero2.checkLevelUp();
                            }
                        }
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(args[1]);
            for (int i = 0; i < heroList.size(); i++) {
                Hero h = heroList.get(i);
                fileWriter.writeWord(h.getHeroType());
                fileWriter.writeWord(" ");
                if (!h.isAlive()) {
                    fileWriter.writeWord("dead");
                } else {
                    fileWriter.writeInt(h.getLevel());
                    fileWriter.writeWord(" ");
                    fileWriter.writeInt(h.getCurrentXP());
                    fileWriter.writeWord(" ");
                    fileWriter.writeInt(h.getCurrentHP());
                    fileWriter.writeWord(" ");
                    fileWriter.writeInt(h.getX());
                    fileWriter.writeWord(" ");
                    fileWriter.writeInt(h.getY());
                }
                    fileWriter.writeNewLine();
                }
            fileWriter.writeNewLine();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
