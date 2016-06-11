package name.wind.common.fx;

import javafx.geometry.Insets;

public class MutableInsets {

    protected double top;
    protected double right;
    protected double bottom;
    protected double left;

    public MutableInsets(double top, double right, double bottom, double left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public MutableInsets(double topRightBottomLeft) {
        top = right = bottom = left = topRightBottomLeft;
    }

    public MutableInsets(Inset... insets) {
        for (Inset inset : insets) {
            inset.apply(this);
        }
    }

    public void addToTop(double value) {
        top += value;
    }

    public void addToRight(double value) {
        right += value;
    }

    public void addToBottom(double value) {
        bottom += value;
    }

    public void addToLeft(double value) {
        left += value;
    }

    public Insets toFxInsets() {
        return new Insets(top, right, bottom, left);
    }

    @Override public String toString() {
        return getClass().getSimpleName() + "[top = " + top + ", right = " + right + ", bottom = " + bottom + ", left = " + left + "]";
    }

}
