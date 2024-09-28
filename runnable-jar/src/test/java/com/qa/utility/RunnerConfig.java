package com.qa.utility;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:runner.properties" })
public interface RunnerConfig extends Config {
    @Key("browser")
    String browser();

    @Key("url")
    String url();

}
