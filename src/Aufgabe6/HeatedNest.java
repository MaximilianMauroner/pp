import Annotations.Author;
import Annotations.PostCondition;
import Annotations.PreCondition;

@Author(name = "Lukas Leskovar")
public class HeatedNest implements Nest {

    private Filling filling;
    private final int id;
    private final double width;
    private final double height;
    private final int power;

    @PreCondition(condition = "width > 0, height > 0, power > 0")
    @PostCondition(condition = "this.id() == id && this.width() == width && this.height() == height && this.getPower() == power && adds this to allNests")
    @Author(name = "Lukas Leskovar")
    public HeatedNest(int id, double width, double height, int power) {
        if (!checkID(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
        this.id = id;
        this.width = width;
        this.height = height;
        this.power = power;
        Nest.allNests.add(this);
    }

    @PostCondition(condition = "returns the id of the nest")
    @Author(name = "Lukas Leskovar")
    @Override
    public int id() {
        return this.id;
    }

    @PostCondition(condition = "returns width")
    @Author(name = "Lukas Leskovar")
    @Override
    public double width() {
        return this.width;
    }

    @PostCondition(condition = "returns height")
    @Author(name = "Lukas Leskovar")
    @Override
    public double height() {
        return this.height;
    }

    @PostCondition(condition = "returns the power if the nest is heated, otherwise 0")
    @Author(name = "Lukas Leskovar")
    @Override
    public int getPower() {
        return this.power;
    }

    @PostCondition(condition = "returns the volume of the water tank if the nest is air-conditioned, otherwise 0")
    @Author(name = "Lukas Leskovar")
    @Override
    public double getTankVolume() {
        return 0;
    }

    @PostCondition(condition = "returns the filling of the nest")
    @Author(name = "Lukas Leskovar")
    @Override
    public Filling getFilling() {
        return this.filling;
    }

    @PreCondition(condition = "filling != null")
    @PostCondition(condition = "this.filling() == filling, old filling is lost")
    @Author(name = "Lukas Leskovar")
    @Override
    public void setFilling(Filling filling) {
        this.filling = filling;
    }

    @PostCondition(condition = "returns a string representation of the nest")
    @Author(name = "Christopher Scherling")
    @Override
    public String toString() {
        return "HeatedNest{" +
                "filling=" + filling +
                ", id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", tankVolume=0" +
                ", power=" + power +
                '}';
    }
}
