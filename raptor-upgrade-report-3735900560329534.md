
# Project upgrade report to Raptor 2.0
## Project details
Name | Description
---- | -----------
Path to project POM |	pom.xml
Upgrade job ID | Some(3735900560329534)
Full upgrade log | [link](raptor-upgrade-debug-3735900560329534.log)
Upgrade warnings only log | [link](raptor-upgrade-warn-3735900560329534.log)

     ## Summary

| Operation | Details |
| ---- | ----------- |
|[com.ebay.uaas.raptor.rule.RemoveAssemblerPluginRule](#RemoveAssemblerPluginRule) | impacted 0 file(s) |
|[com.ebay.rtran.maven.MavenRemoveDependenciesRule](#MavenRemoveDependenciesRule) | impacted 0 file(s) |
|[com.ebay.rtran.maven.MavenExcludeDependenciesRule](#MavenExcludeDependenciesRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.RaptorPlatformVersionUpdateRule](#RaptorPlatformVersionUpdateRule) | impacted 3 file(s) |
|[com.ebay.uaas.raptor.rule.MigratePESConfigurationRule](#MigratePESConfigurationRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.FixAssemblerMavenPluginRule](#FixAssemblerMavenPluginRule) | impacted 1 file(s) |

### RaptorPlatformVersionUpdateRule
Modify the dependencies and plugins to be managed by RaptorPlatform:
      
#### File [promowebweb/pom.xml](promowebweb/pom.xml)
|Operation|artifact|
|------|----|
|RemovePluginVersion|Plugin [com.ebay.raptor.build:assembler-maven-plugin]|
