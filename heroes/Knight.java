
package heroes;

public final class Knight extends Hero {

    private final int knightBaseHP = 900;
    private final int knightHPLevelUp = 80;
    private final float terrainModif = 1.15f;
    private final int executeBaseDamage = 200;
    private final int executeDamageIncrease = 30;
    private final float executeRogueModif = 1.15f;
    private final float executeKnightModif = 1.00f;
    private final float executePyroModif = 1.10f;
    private final float executeWizardModif = 0.8f;
    private final float executePercentageCap = 0.40f;
    private final float executeBasePercentage = 0.2f;
    private final float executeLevelRate = 0.01f;
    private float executePercentage;
    private final int slamBaseDamage = 100;
    private final int slamDamageIncrease = 40;
    private final float slamRogueModif = 0.8f;
    private final float slamKnightModif = 1.2f;
    private final float slamPyroModif = 0.9f;
    private final float slamWizardModif = 1.05f;
    private final int paralysisDuration = 1;
    public Knight() { }

    public Knight(final int x, final int y) {
        super(x, y);
        this.maxHP = knightBaseHP;
        this.currentHP = maxHP;
        this.hpLevelUp = knightHPLevelUp;
        this.heroType = "K";
    }

    @Override
    public void firstSpell(final Hero enemy, final String terrain) {
        executePercentage = executeBasePercentage + (executeLevelRate * this.level);
        if (executePercentage > executePercentageCap) {
            executePercentage = executePercentageCap;
        }
        if (enemy.currentHP < (executePercentage * enemy.maxHP)) {
            damageFirstAbility = enemy.currentHP;
        } else {
            damageFirstAbility = executeBaseDamage + (executeDamageIncrease * this.level);
        }
        if (terrain.equals("L")) {
            damageFirstAbility *= terrainModif;
            damageFirstAbility = Math.round(damageFirstAbility);
        }
        damageBeforeRaceModif += damageFirstAbility;
        if (enemy.heroType.equals("R")) {
            damageFirstAbility *= executeRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageFirstAbility *= executeKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageFirstAbility *= executePyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageFirstAbility *= executeWizardModif;
        }
        damageFirstAbility = Math.round(damageFirstAbility);
        this.totalDamage += damageFirstAbility;
    }

    @Override
    public void secondSpell(final Hero enemy, final String terrain) {
        damageSecondAbility = slamBaseDamage + (slamDamageIncrease * this.level);
        enemy.roundsLeftParalysis = paralysisDuration;
        if (terrain.equals("L")) {
            damageSecondAbility *= terrainModif;
            damageSecondAbility = Math.round(damageSecondAbility);
        }
        damageBeforeRaceModif += damageSecondAbility;
        if (enemy.heroType.equals("R")) {
            damageSecondAbility *= slamRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageSecondAbility *= slamKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageSecondAbility *= slamPyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageSecondAbility *= slamWizardModif;
        }
        damageSecondAbility = Math.round(damageSecondAbility);
        this.totalDamage += damageSecondAbility;
    }
}
