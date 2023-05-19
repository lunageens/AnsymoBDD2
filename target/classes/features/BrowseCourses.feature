# Created by lunag at 17/05/2023
  # This is Gherkin file for BDD scenario
  @Exercise5

Feature: Browse from home page to all course pages
  As a user of the ansymore.uantwerpen.be site
  I want to navigate to all the courses listed in the "menu" section of the homepage
  So that I can see information about each course and verify that each course has a professor.

  Scenario: Dummy scenario
    Given a dummy scenario

  # press ctrl to hover over steps and go to step definitions
  Scenario Outline: Verify all courses from homepages (names and professors)
    Given the user navigates to <url> homepage
    When the user clicks the <linkText> link in the menu section
    Then the user should see a page with all the courses listed
    And for each course, the user should see the name of the course and the name of the professor teaching the course
    Examples:
      | url                              | linkText  |
      | "https://ansymore.uantwerpen.be" | "Courses" |

