package heroes;

public final class Wizard extends Hero {

    private final int wizardBaseHP = 400;
    private final int wizardHPLevelUp = 30;
    private final float terrainModif = 1.10f;
    private float drainBaseHP;
    private float drainPercent;
    private final float drainBasePercent = 20f;
    private final float drainPercentIncrease = 5f;
    private final float drainMultiplyer = 0.3f;
    private final float drainRogueModif = 0.8f;
    private final float drainKnightModif = 1.2f;
    private final float drainPyroModif = 0.9f;
    private final float drainWizardModif = 1.05f;
    private float deflectPercent;
    private final float deflectBasePercent = 35.00f;
    private final float deflectPercentIncrease = 2.00f;
    private final float deflectPercentCap = 70.00f;
    private final float deflectRogueModif = 1.2f;
    private final float deflectKnightModif = 1.4f;
    private final float deflectPyroModif = 1.3f;
    private final int percentModifier = 100;
    public Wizard() { }

    public Wizard(final int x, final int y) {
        super(x, y);
        this.maxHP = wizardBaseHP;
        this.currentHP = maxHP;
        this.hpLevelUp = wizardHPLevelUp;
        this.heroType = "W";
    }

    @Override
    public void firstSpell(final Hero enemy, final String terrain) {
        drainBaseHP = Math.min(drainMultiplyer * enemy.maxHP, enemy.currentHP);
        drainPercent = drainBasePercent + (drainPercentIncrease * this.level);
        if (terrain.equals("D")) {
            drainPercent *= terrainModif;
        }
        if (enemy.heroType.equals("R")) {
            drainPercent *= drainRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            drainPercent *= drainKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            drainPercent *= drainPyroModif;
        }
        if (enemy.heroType.equals("W")) {
            drainPercent *= drainWizardModif;
        }
        damageFirstAbility = (drainPercent / percentModifier) * drainBaseHP;
        damageFirstAbility = Math.round(damageFirstAbility);
        this.totalDamage += damageFirstAbility;
    }

    @Override
    public void secondSpell(final Hero enemy, final String terrain) {
        if (enemy.heroType != "Wizard") {
            deflectPercent = deflectBasePercent + (deflectPercentIncrease * this.level);
            if (deflectPercent > deflectPercentCap) {
                drainPercent = deflectPercentCap;
            }
            damageSecondAbility = (deflectPercent / percentModifier) * enemy.damageBeforeRaceModif;
            if (terrain.equals("D")) {
                damageSecondAbility *= terrainModif;
            }
            if (enemy.heroType.equals("R")) {
                damageSecondAbility *= deflectRogueModif;
            }
            if (enemy.heroType.equals("K")) {
                damageSecondAbility *= deflectKnightModif;
            }
            if (enemy.heroType.equals("P")) {
                damageSecondAbility *= deflectPyroModif;
            }
            damageSecondAbility = Math.round(damageSecondAbility);
            this.totalDamage += damageSecondAbility;
        }
    }
}
