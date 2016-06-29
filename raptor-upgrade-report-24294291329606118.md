
# Project upgrade report to Raptor 2.0
## Project details
Name | Description
---- | -----------
Path to project POM |	pom.xml
Upgrade job ID | Some(24294291329606118)
Full upgrade log | [link](raptor-upgrade-debug-24294291329606118.log)
Upgrade warnings only log | [link](raptor-upgrade-warn-24294291329606118.log)

     ## Summary

| Operation | Details |
| ---- | ----------- |
|[com.ebay.uaas.raptor.rule.RaptorPlatformVersionUpdateRule](#RaptorPlatformVersionUpdateRule) | impacted 6 file(s) |
|[com.ebay.uaas.raptor.rule.ServerRuntimeConfigMigrationRule](#ServerRuntimeConfigMigrationRule) | impacted 0 file(s) |
|[com.ebay.uaas.raptor.rule.MavenExcludeAndReplaceDependenciesRule](#MavenExcludeAndReplaceDependenciesRule) | impacted 1 file(s) |
|[com.ebay.rtran.maven.MavenExcludeDependenciesRule](#MavenExcludeDependenciesRule) | impacted 0 file(s) |

### RaptorPlatformVersionUpdateRule
Modify the dependencies and plugins to be managed by RaptorPlatform:
      
#### File [promowebweb/pom.xml](promowebweb/pom.xml)
|Operation|artifact|
|------|----|
|RemovePluginVersion|Plugin [com.ebay.raptor.build:assembler-maven-plugin]|
