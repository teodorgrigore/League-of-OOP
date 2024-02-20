
package heroes;
public final class Pyro extends Hero {

    private final int pyroBaseHP = 500;
    private final int pyroHPLevelUp = 50;
    private final float terrainModif = 1.25f;
    private final int fireblastBaseDamage = 350;
    private final int fireblastDamageIncrease = 50;
    private final float fireblastRogueModif = 0.8f;
    private final float fireblastKnightModif = 1.2f;
    private final float fireblastPyroModif = 0.9f;
    private final float fireblastWizardModif = 1.05f;

    private final int igniteBaseDamage = 150;
    private final int igniteDamageIncrease = 20;
    private final int igniteBaseDoT = 50;
    private final int igniteDoTIncrease = 30;
    private final int igniteDuration = 2;
    private final float igniteRogueModif = 0.8f;
    private final float igniteKnightModif = 1.2f;
    private final float ignitePyroModif = 0.9f;
    private final float igniteWizardModif = 1.05f;
    public Pyro() { }

    public Pyro(final int x, final int y) {
        super(x, y);
        this.maxHP = pyroBaseHP;
        this.currentHP = this.maxHP;
        this.hpLevelUp = pyroHPLevelUp;
        this.heroType = "P";
    }
    @Override
    public void firstSpell(final Hero enemy, final String terrain) {
        damageFirstAbility = fireblastBaseDamage + (fireblastDamageIncrease * this.level);
        if (terrain.equals("V")) {
            damageFirstAbility *= terrainModif;
            damageFirstAbility = Math.round(damageFirstAbility);
        }
        damageBeforeRaceModif += damageFirstAbility;
        if (enemy.heroType.equals("R")) {
            damageFirstAbility *= fireblastRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageFirstAbility *= fireblastKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageFirstAbility *= fireblastPyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageFirstAbility *= fireblastWizardModif;
        }
        damageFirstAbility = Math.round(damageFirstAbility);
        this.totalDamage += damageFirstAbility;
    }

    @Override
    public void secondSpell(final Hero enemy, final String terrain) {
        damageSecondAbility = igniteBaseDamage + (igniteDamageIncrease * this.level);
        enemy.damageOverTime = igniteBaseDoT + (igniteDoTIncrease * this.level);
        enemy.roundsLeftDoT = igniteDuration;
        if (terrain.equals("V")) {
            damageSecondAbility *= terrainModif;
            damageSecondAbility = Math.round(damageSecondAbility);
            enemy.damageOverTime *= terrainModif;
            enemy.damageOverTime = Math.round(enemy.damageOverTime);
        }
        damageBeforeRaceModif += damageSecondAbility;
        if (enemy.heroType.equals("R")) {
            damageSecondAbility *= igniteRogueModif;
            enemy.damageOverTime *= igniteRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageSecondAbility *= igniteKnightModif;
            enemy.damageOverTime *= igniteKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageSecondAbility *= ignitePyroModif;
            enemy.damageOverTime *= ignitePyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageSecondAbility *= igniteWizardModif;
            enemy.damageOverTime *= igniteWizardModif;
        }
        enemy.damageOverTime = Math.round(enemy.damageOverTime);
        damageSecondAbility = Math.round(damageSecondAbility);
        this.totalDamage += damageSecondAbility;
    }
}
