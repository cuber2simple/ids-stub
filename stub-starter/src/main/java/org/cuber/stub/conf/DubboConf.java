package org.cuber.stub.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:dubbo/dubbo-*.xml"})
public class DubboConf {

}
