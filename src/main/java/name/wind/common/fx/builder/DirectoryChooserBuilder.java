package name.wind.common.fx.builder;

import javafx.stage.DirectoryChooser;

import java.util.function.Supplier;

public class DirectoryChooserBuilder extends AbstractBuilder<DirectoryChooser, DirectoryChooserBuilder> {

    public DirectoryChooserBuilder(Supplier<DirectoryChooser> targetSupplier) {
        super(targetSupplier);
    }

}
