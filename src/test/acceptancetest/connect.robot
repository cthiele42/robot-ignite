*** Settings ***
Library  IgniteLibrary

*** Testcases ***
Connect To Ignite
  [Setup]  Start Cluster Node  testnode1
  Connect To Ignite
  Cluster Node Count Should Be  2
