Feature: Delete a journey

  As a driver, I want to delete a journey

  Scenario: Delete a journey
    Given journey with the following details:
      | id                                             | destination  | price  | owner  |
      | 28356590-332e-43e0-ba7c-50c6a98e41a8           | Vietnam      | 1000   | Dakar  |
    When Delete this journey
    Then The journey is deleted

