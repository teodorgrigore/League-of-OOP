package heroes;

public final class Rogue extends Hero {

    private final int rogueBaseHP = 600;
    private final int rogueHPLevelUp = 40;
    private final float terrainModif = 1.15f;
    private final int backstabBaseDamage = 200;
    private final int backstabDamageIncrease = 20;
    private int backstabCritCounter;
    private final float backstabCritModif = 1.50f;
    private final float backstabRogueModif = 1.20f;
    private final float backstabKnightModif = 0.90f;
    private final float backstabPyroModif = 1.25f;
    private final float backstabWizardModif = 1.25f;

    private final int paralysisBaseDamage = 40;
    private final int paralysisDamageIncrease = 10;
    private final int paralysisRoundsOvertime = 3;
    private final int paralysisRoundsOvertimeWoods = 6;
    private final float paralysisRogueModif = 0.90f;
    private final float paralysisKnightModif = 0.80f;
    private final float paralysisPyroModif = 1.20f;
    private final float paralysisWizardModif = 1.25f;
    public Rogue() { }

    public Rogue(final int x, final int y) {
        super(x, y);
        this.maxHP = rogueBaseHP;
        this.currentHP = maxHP;
        this.hpLevelUp = rogueHPLevelUp;
        this.heroType = "R";
        /* Counterul este initial -1 datorita conditiei ca primul Backstab
        aplicat pe un inamic pe terenul Woods este "critical hit". */
        this.backstabCritCounter = -1;
    }

    @Override
    public void firstSpell(final Hero enemy, final String terrain) {
        damageFirstAbility = backstabBaseDamage + (backstabDamageIncrease * this.level);
        if (this.backstabCritCounter == 2 || this.backstabCritCounter == -1) {
            if (terrain.equals("W")) {
                damageFirstAbility *= backstabCritModif;
                damageFirstAbility = Math.round(damageFirstAbility);
            }
            backstabCritCounter = 0;
        } else {
            backstabCritCounter++;
        }
        if (terrain.equals("W")) {
            damageFirstAbility *= terrainModif;
            damageFirstAbility = Math.round(damageFirstAbility);
        }
        damageBeforeRaceModif += damageFirstAbility;
        if (enemy.heroType.equals("R")) {
            damageFirstAbility *= backstabRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageFirstAbility *= backstabKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageFirstAbility *= backstabPyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageFirstAbility *= backstabWizardModif;
        }
        damageFirstAbility = Math.round(damageFirstAbility);
        this.totalDamage += damageFirstAbility;
    }

    @Override
    public void secondSpell(final Hero enemy, final String terrain) {
        damageSecondAbility = paralysisBaseDamage + (paralysisDamageIncrease * this.level);
        if (terrain.equals("W")) {
            enemy.roundsLeftParalysis = paralysisRoundsOvertimeWoods;
            enemy.roundsLeftDoT = paralysisRoundsOvertimeWoods;
            damageSecondAbility *= terrainModif;
            damageSecondAbility = Math.round(damageSecondAbility);
        } else {
            enemy.roundsLeftParalysis = paralysisRoundsOvertime;
            enemy.roundsLeftDoT = paralysisRoundsOvertime;
        }
        damageBeforeRaceModif += damageSecondAbility;
        if (enemy.heroType.equals("R")) {
            damageSecondAbility *= paralysisRogueModif;
        }
        if (enemy.heroType.equals("K")) {
            damageSecondAbility *= paralysisKnightModif;
        }
        if (enemy.heroType.equals("P")) {
            damageSecondAbility *= paralysisPyroModif;
        }
        if (enemy.heroType.equals("W")) {
            damageSecondAbility *= paralysisWizardModif;
        }
        enemy.damageOverTime = Math.round(damageSecondAbility);
        damageSecondAbility = Math.round(damageSecondAbility);
        this.totalDamage += damageSecondAbility;
    }
}
