Feature: Book a journey

  As a client, I can search a journey by destination

#  ignoring while we don't have the endpoint to search by destination
  @ignore
  Scenario Outline: Search journey by destination
    Given These journeys have been created
    When <userName> search a destination: <destination>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | destination | numbersJourneysFound |
      | Dakar    | Vietnam     | 2                    |


#  ignoring while we don't have the endpoint to search by destination
  @ignore
  Scenario Outline: Search by destination and don't find any matches
    Given These journeys have been created
    When <userName> search a journey: <destination>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | destination | numbersJourneysFound |
      | Dakar    | Vietnam     | 2                    |


  Scenario Outline: Show a journey
    Given These journeys have been created
    When <userName> show a journey: <id>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | id                                   | destination | numbersJourneysFound |
      | Dakar    | 28356590-332e-43e0-ba7c-50c6a98e41a8 | Vietnam     | 1                    |

  Scenario Outline: Search by criterias : destination
    Given These journeys have been created
    When <userName> search a journey by criteria : <destination>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | destination | numbersJourneysFound |
      | Dakar    | Vietnam     | 1                    |