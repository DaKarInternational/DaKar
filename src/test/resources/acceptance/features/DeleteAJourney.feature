Feature: Delete a journey

  As a driver, I want to delete a journey

  Scenario Outline: Delete a journey
    Given journey with the following details:
      |id           | 28356590-332e-43e0-ba7c-50c6a98e41a8  |
      |destination  | Vietnam                               |
      |price        | 1000                                  |
      |owner        | Dakar                                 |
    When Delete this journey
    Then The journey is deleted
    Examples:

