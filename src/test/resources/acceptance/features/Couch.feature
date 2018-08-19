Feature: Book a journey

  As a user, I can book a journey

  Scenario Outline: Choose a destination
    Given <userName> wants to go to a journey
    When User choses a destination : <destination>
    Then The journey <destination> is created

    Examples:
      | userName | destination |
      | Dakar 	| Vietnam 	  |

  Scenario: test
    When test
