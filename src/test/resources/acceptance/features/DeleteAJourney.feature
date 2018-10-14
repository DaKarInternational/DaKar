Feature: Delete a journey

  As a driver, I want to delete a journey

  Scenario Outline: Delete a journey
    Given journey with the following details
    When <userName> delete this journey <id>
    Then The journey <id> is deleted with <userName> as owner
    Examples:
      | id                                    | userName | destination |
      | 28356590-332e-43e0-ba7c-50c6a98e41a8  |  Dakar   | Vietnam     |

