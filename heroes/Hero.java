
package heroes;

public abstract class Hero {
    protected int coordX, coordY, level;
    protected int maxHP, currentHP, hpLevelUp, damageBeforeRaceModif;
    protected int currentXP, xpLevelUp;
    protected float damageFirstAbility, damageSecondAbility, damageOverTime, totalDamage;
    protected int roundsLeftDoT, roundsLeftParalysis;
    protected String heroType;
    private final int baseXPrequirement = 250;
    private final int baseXPgain = 200;
    private final int levelUpMultiplyer = 50;
    private final int gainExpMultiplyer = 40;

    public Hero() { }
    public Hero(final int x, final int y) {
        this.coordX = x;
        this.coordY = y;
        this.currentXP = 0;
        this.level = 0;
    }
    public final void goUp() {
        coordX--;
    }
    public final void goDown() {
        coordX++;
    }
    public final void goLeft() {
        coordY--;
    }
    public final void goRight() {
        coordY++;
    }
    public final int getX() {
        return coordX;
    }
    public final int getY() {
        return coordY;
    }
    public final String getHeroType() {
        return this.heroType;
    }
    public final int getLevel() {
        return this.level;
    }
    public final int getCurrentXP() {
        return this.currentXP;
    }
    public final int getCurrentHP() {
        return this.currentHP;
    }
    public final boolean isAlive() {
        if (this.currentHP <= 0) {
            return false;
        }
        return true;
    }
    public final boolean isParalysed() {
        if (this.roundsLeftParalysis != 0) {
            return true;
        }
        return false;
    }
    public final void reduceParalysisCounter() {
        if (this.roundsLeftParalysis > 0) {
            this.roundsLeftParalysis--;
        }
    }
    public final boolean shouldTakeDoT() {
        if (this.roundsLeftDoT != 0) {
            return true;
        }
        return false;
    }
    public final void takesDoT() {
        this.currentHP -= this.damageOverTime;
        this.roundsLeftDoT--;
    }
    public abstract void firstSpell(Hero enemy, String terrain);
    public abstract void secondSpell(Hero enemy, String terrain);

    public final void fightWith(final Hero enemy, final String terrain) {
        this.totalDamage = 0;
        this.damageBeforeRaceModif = 0;
        this.firstSpell(enemy, terrain);
        this.secondSpell(enemy, terrain);
        enemy.currentHP -= totalDamage;
    }

    public final void accept(final Hero enemy, final String terrain) {
        enemy.fightWith(this, terrain);
    }
    public final boolean isTheWinner(final Hero enemy) {
        if (this.isAlive() && !enemy.isAlive()) {
            return true;
        }
        return false;
    }
    public final void gainExperience(final Hero enemy) {
        this.currentXP += Math.max(0, baseXPgain - (this.level - enemy.level) * gainExpMultiplyer);
    }
    public final void calcExpLevelUp() {
        xpLevelUp = baseXPrequirement + (level * levelUpMultiplyer);
    }
    public final void checkLevelUp() {
        calcExpLevelUp();
        while (currentXP >= xpLevelUp) {
            level++;
            maxHP += hpLevelUp;
            currentHP = maxHP;
            calcExpLevelUp();
        }
    }
}
