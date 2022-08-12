package bg.softuni.battle_ships.models.DTO;

import javax.validation.constraints.Positive;

public class StartBattleDTO {

    @Positive
    private int attackedId;

    @Positive
    private int defenderId;

    public StartBattleDTO() {

    }

    public int getAttackedId() {
        return attackedId;
    }

    public void setAttackedId(int attackedId) {
        this.attackedId = attackedId;
    }

    public int getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(int defenderId) {
        this.defenderId = defenderId;
    }
}

