Feature: Book a journey

  As a client, I can search a journey

  Scenario Outline: Search journey by destination
    Given These journeys have been created
    When <userName> search a destination: <destination>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | destination | numbersJourneysFound |
      | Dakar    | Vietnam     | 1                    |

  Scenario Outline: Search journey by price
    Given These journeys have been created
    When <userName> search a price <price>
    Then <userName> find <numbersJourneysFound> destinations matching <price>
    Examples:
      | userName | price | numbersJourneysFound |
      | Dakar    | 1000  | 1                    |

  Scenario Outline: Search journey by destination and price
    Given These journeys have been created
    When <userName> search a destination <destination> and a price <price>
    Then <userName> find <numbersJourneysFound> destinations matching <price>
    Examples:
      | userName | destination | price | numbersJourneysFound |
      | Dakar    | Vietnam  | 1000     | 1                    |

  Scenario Outline: Search journey by destination and price no matches
    Given These journeys have been created
    When <userName> search a destination <destination> and a price <price>
    Then <userName> find <numbersJourneysFound> destinations no matching <price>
    Examples:
      | userName | price | numbersJourneysFound |
      | Dakar    | 1000  | 1                    |

  Scenario Outline: Show a journey
    Given These journeys have been created
    When <userName> show a journey: <id>
    Then <userName> find <numbersJourneysFound> destinations matching <destination>
    Examples:
      | userName | id                                   | destination | numbersJourneysFound |
      | Dakar    | 28356590-332e-43e0-ba7c-50c6a98e41a8 | Vietnam     | 1                    |