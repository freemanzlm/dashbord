
# Project upgrade report to Raptor 2.0
## Project details
Name | Description
---- | -----------
Path to project POM |	pom.xml
Upgrade job ID | Some(1194707526330810)
Full upgrade log | [link](raptor-upgrade-debug-1194707526330810.log)
Upgrade warnings only log | [link](raptor-upgrade-warn-1194707526330810.log)

     ## Summary

| Operation | Details |
| ---- | ----------- |
|[com.ebay.uaas.raptor.rule.FixBatch26AssemblerMavenPluginRule](#FixBatch26AssemblerMavenPluginRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.MavenExcludeDependenciesRule](#MavenExcludeDependenciesRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.MavenAddPlugisnRule](#MavenAddPlugisnRule) | impacted 1 file(s) |
|[com.ebay.uaas.raptor.rule.RemoveAssemblerPluginRule](#RemoveAssemblerPluginRule) | impacted 0 file(s) |
|[com.ebay.rtran.maven.MavenRemoveDependenciesRule](#MavenRemoveDependenciesRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.RevertRaptorBeanPostProcessorUpdateRule](#RevertRaptorBeanPostProcessorUpdateRule) | impacted 1 file(s) |
|[com.ebay.uaas.raptor.rule.MavenExcludeAndReplaceDependenciesRule](#MavenExcludeAndReplaceDependenciesRule) | impacted 1 file(s) |
|[com.ebay.uaas.raptor.rule.RaptorPlatformVersionUpdateRule](#RaptorPlatformVersionUpdateRule) | impacted 5 file(s) |
|[com.ebay.uaas.raptor.rule.MigratePESConfigurationRule](#MigratePESConfigurationRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.FixAssemblerMavenPluginRule](#FixAssemblerMavenPluginRule) | impacted 1 file(s) |

### RaptorPlatformVersionUpdateRule
Modify the dependencies and plugins to be managed by RaptorPlatform:
      
#### File [promowebweb/pom.xml](promowebweb/pom.xml)
|Operation|artifact|
|------|----|
|RemovePluginVersion|Plugin [com.ebay.raptor.build:assembler-maven-plugin]|
