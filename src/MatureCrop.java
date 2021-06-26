public class MatureCrop extends Item {
    public MatureCrop(String name, int quantity) {
        super(quantity, name);
    }

    public MatureCrop removeOneCrop() {
        if (!(this.quantity <= 0)) {
            this.quantity = this.quantity - 1;
            return new MatureCrop(name, 1);
        } else {
            return null;
        }
    }
}
