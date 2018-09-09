Feature: Save a journey

  As a driver, I want to save the journey I made with my friends so that other peoples can book the same journey

  Scenario Outline: Save a journey
    When <userName> create the journey for destination: <destination>
    Then The journey <destination> is created with <userName> as owner
    Examples:
      | userName | destination |
      | Dakar    | Vietnam     |


  Scenario Outline: Search all the journeys that I contributed to
    Given <userName> participated to the creation of one journey
    When <userName> search his journeys
    Then <userName> gets all his journeys
    Examples:
      | userName | destination |
      | Dakar    | Vietnam     |

  Scenario Outline: Search all journeys request that matches the destination you've been to and suggest your journey
    Given <userName> participated to the creation of one journey
    When <userName> search his journeys
    Then <userName> gets all his journeys
    Examples:
      | userName | destination |
      | Dakar    | Vietnam     |
    
