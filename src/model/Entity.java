package model;


import enums.Entities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {

    private Entities kind;
    private double weight;
    private int countOnCell;
    private int speed;
    private double saturationLimit;

    public Entity(Entities kind) {
        this.kind = kind;
        this.weight = kind.getWeight();
        this.countOnCell = kind.getCountOnCell();
        this.speed = kind.getSpeed();
        this.saturationLimit = kind.getSaturationLimit();
    }

    public String toString() {
        return this.getKind().toString();
    }
}
