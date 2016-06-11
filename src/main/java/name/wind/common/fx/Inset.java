package name.wind.common.fx;

public abstract class Inset {

    public static class Top extends Inset {

        public Top(double value) {
            super(value);
        }

        @Override public void apply(MutableInsets insets) {
            insets.addToTop(value);
        }

    }

    public static class Right extends Inset {

        public Right(double value) {
            super(value);
        }

        @Override public void apply(MutableInsets insets) {
            insets.addToRight(value);
        }

    }

    public static class Bottom extends Inset {

        public Bottom(double value) {
            super(value);
        }

        @Override public void apply(MutableInsets insets) {
            insets.addToBottom(value);
        }

    }

    public static class Left extends Inset {

        public Left(double value) {
            super(value);
        }

        @Override public void apply(MutableInsets insets) {
            insets.addToLeft(value);
        }

    }

    protected double value;

    protected Inset(double value) {
        this.value = value;
    }

    protected abstract void apply(
        MutableInsets insets
    );

}
