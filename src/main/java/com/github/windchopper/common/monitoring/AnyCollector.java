package com.github.windchopper.common.monitoring;

public abstract class AnyCollector implements AnyCollectorMXBean {

    protected boolean enabled;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
