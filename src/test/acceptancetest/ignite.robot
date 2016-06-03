*** Settings ***
Library  IgniteLibrary
Library  OperatingSystem

*** Testcases ***
Cache Defined in Spring XML
  ${path}  ${file}=  Split Path  ${SUITE_SOURCE}
  Start Cluster Node  testnode2  ${path}${/}ignite-config.xml
  Connect To Ignite  ${path}${/}ignite-config.xml
  Clear Cache  test-cache
  Cache Size Should Be  test-cache  0
