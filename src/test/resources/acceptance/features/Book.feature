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
    When <userName> search a destination: <destination>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | destination | numbersJourneysFound |
      | Dakar    | Vietnam     | 2                    |
