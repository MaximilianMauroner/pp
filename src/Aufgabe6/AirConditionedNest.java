import Annotations.*;

@Author(name = "Lukas Leskovar")
public class AirConditionedNest implements Nest {
    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final double tankVolume;


    @PreCondition(condition = "id > 0, id doesnt already exist, width > 0, height > 0, tankVolume > 0")
    @PostCondition(condition = "this.id() == id && this.width() == width && this.height() == height && this.getTankVolume() == tankVolume && adds this to allNests")
    @Author(name = "Lukas Leskovar")
    public AirConditionedNest(int id, double width, double height, double tankVolume) {
        if (!checkID(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
        this.id = id;
        this.width = width;
        this.height = height;
        this.tankVolume = tankVolume;
        Nest.allNests.add(this);
    }

    @PostCondition(condition = "returns the id of the nest")
    @Author(name = "Lukas Leskovar")
    @Override
    public int id() {
        return this.id;
    }

    @PostCondition(condition = "returns the width")
    @Author(name = "Lukas Leskovar")
    @Override
    public double width() {
        return this.width;
    }

    @PostCondition(condition = "returns the height")
    @Author(name = "Lukas Leskovar")
    @Override
    public double height() {
        return this.height;
    }

    @PostCondition(condition = "returns the power, always 0")
    @Author(name = "Lukas Leskovar")
    @Override
    public int getPower() {
        return 0;
    }

    @PostCondition(condition = "returns the tankVolume")
    @Author(name = "Lukas Leskovar")
    @Override
    public double getTankVolume() {
        return this.tankVolume;
    }

    @PostCondition(condition = "returns the filling")
    @Author(name = "Lukas Leskovar")
    @Override
    public Filling getFilling() {
        return this.filling;
    }


    @PreCondition(condition = "filling != null")
    @PostCondition(condition = "this.filling() == filling")
    @Author(name = "Lukas Leskovar")
    @Override
    public void setFilling(Filling filling) {
        this.filling = filling;
    }

    @PostCondition(condition = "returns a string representation of the nest")
    @Author(name = "Christopher Scherling")
    @Override
    public String toString() {
        return "AirConditionedNest{" + "filling=" + filling + ", id=" + id + ", width=" + width + ", height=" + height + ", depth=" + depth + ", tankVolume=" + tankVolume + '}';
    }
}
