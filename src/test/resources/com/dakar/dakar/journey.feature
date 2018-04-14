Feature: Create a journey

  As a user, I would have a journey
  
  Scenario: Chose a destination
  Given Daker wants to go to a journey 
  When  User choses a destination Vietnam
  Then  Journey is created
  
  Scenario Outline: Chose a destination
   Given <userName> wants to go to a journey  
   When User choses a destination <destination>  
   Then Journey is created

   Examples: 
     | userName | destination |
     | Dakar 	| Vietnam 	  |