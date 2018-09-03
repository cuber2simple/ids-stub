package org.cuber.stub.conf;

import org.cuber.ids.IdPattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("ids")
@Component
public class IdsProperties {

    private List<IdPattern> patterns;

    public List<IdPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<IdPattern> patterns) {
        this.patterns = patterns;
    }
}
