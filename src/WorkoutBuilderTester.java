///////////////////////////////////////////////////////////////////////////////
//
// Title: The WorkoutBuilderTester tests the WorkoutBuilder class's clear, add, remove, and get
// methods.
//
// Course: CS 300 Fall 2023
//
// Author: Remington Reichmann
// Email: rreichmann@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

public class WorkoutBuilderTester {

  /**
   * Tests the clear method from WorkoutBuilder class.
   * 
   * @return true if all tests are passed, false otherwise.
   */
  public static boolean testClear() {
    Exercise.resetIDNumbers(); // Reset ID's

    // Sample list to test
    WorkoutBuilder actualList = new WorkoutBuilder();
    actualList.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    actualList.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    actualList.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));
    actualList.clear();

    // Tests to make sure the list is empty and that all sizes are 0
    boolean test1 = actualList.isEmpty();
    boolean test2 = actualList.size() == 0;
    boolean test3 = actualList.getCooldownCount() == 0;
    boolean test4 = actualList.getPrimaryCount() == 0;
    boolean test5 = actualList.getWarmupCount() == 0;

    return test1 && test2 && test3 && test4 && test5;
  }

  /**
   * Tests the add method from WorkoutBuilder class.
   * 
   * @return true if all tests are passed, false otherwise.
   */
  public static boolean testAddExercises() {
    Exercise.resetIDNumbers(); // Reset ID's

    // Sample list to test
    WorkoutBuilder list = new WorkoutBuilder();
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));

    // Expected and actual values
    String expectedList1 =
        "WARMUP: Stretch (1) -> PRIMARY: Squats (2) -> COOLDOWN: Walk (3) -> END";
    String actualList1 = list.toString();

    Exercise.resetIDNumbers(); // Reset ID's

    // Recreating the sample list to test
    WorkoutBuilder list2 = new WorkoutBuilder();
    list2.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list2.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list2.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));

    // Expected and actual values
    String expectedList2 =
        "WARMUP: Stretch (2) -> PRIMARY: Squats (1) -> COOLDOWN: Walk (3) -> END";
    String actualList2 = list2.toString();

    Exercise.resetIDNumbers(); // Reset ID's

    // Recreating the sample list to test
    WorkoutBuilder list3 = new WorkoutBuilder();
    list3.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list3.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list3.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));
    list3.add(new Exercise(WorkoutType.PRIMARY, "Bench"));

    // Expected and actual values
    String expectedList3 =
        "WARMUP: Stretch (2) -> PRIMARY: Bench (4) -> PRIMARY: Squats (1) -> COOLDOWN: Walk (3) -> END";
    String actualList3 = list3.toString();

    // Return true if all tests are passed, false otherwise
    return expectedList1.equals(actualList1) && expectedList2.equals(actualList2)
        && expectedList3.equals(actualList3);
  }

  /**
   * Helper method to test the remove method in WorkoutBuilder that takes an exerciseID as a param
   * 
   * @param list the list to test
   * 
   * @return true if all tests are passed, false otherwise.
   */
  private static boolean testRemoveByID(WorkoutBuilder list) {
    // Try and catch is used to make sure unexpected errors are caught
    try {
      // Removes all exercises and checks after every remove that the list contains the correct
      // values
      list.removeExercise(1);
      if (!(list.toString().equals("PRIMARY: Squats (2) -> COOLDOWN: Walk (3) -> END"))
          || list.size() != 2 || list.getWarmupCount() != 0 || list.getPrimaryCount() != 1
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(2);
      if (!(list.toString().equals("COOLDOWN: Walk (3) -> END")) || list.size() != 1
          || list.getWarmupCount() != 0 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(3);
      if (!(list.toString().equals("")) || list.size() != 0 || list.getWarmupCount() != 0
          || list.getPrimaryCount() != 0 || list.getCooldownCount() != 0) {
        return false;
      }
      // Return true if list contains the correct values every time.
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Helper method to check remove method in WorkoutBuilder when removing in a different order.
   * 
   * @param list the list to test
   * 
   * @return true if all tests are passed, false otherwise.
   */
  private static boolean testRemoveOutOfOrderByID(WorkoutBuilder list) {
    // Try and catch is used to make sure unexpected errors are caught
    try {
      // Removes all exercises and checks after every remove that the list contains the correct
      // values
      list.removeExercise(2);
      if (!(list.toString().equals("WARMUP: Stretch (1) -> COOLDOWN: Walk (3) -> END"))
          || list.size() != 2 || list.getWarmupCount() != 1 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(3);
      if (!(list.toString().equals("WARMUP: Stretch (1) -> END")) || list.size() != 1
          || list.getWarmupCount() != 1 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 0) {
        return false;
      }

      list.removeExercise(1);
      if (!(list.toString().equals("")) || list.size() != 0 || list.getWarmupCount() != 0
          || list.getPrimaryCount() != 0 || list.getCooldownCount() != 0) {
        return false;
      }
      // Return true if list conatins the correct values every time.
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Helper method that tests remove in WorkoutBuilder that takes the workout type as a param
   * 
   * @param list the list to test
   * 
   * @return true if all tests are passed, false otherwise.
   */
  private static boolean testRemoveByType(WorkoutBuilder list) {
    // Try and catch blocks are used to make sure unexpected exceptions are caught
    try {
      // Removes all exercises and checks after every remove that the list contains the correct
      // values
      list.removeExercise(WorkoutType.WARMUP);
      if (!(list.toString().equals("PRIMARY: Squats (2) -> COOLDOWN: Walk (3) -> END"))
          || list.size() != 2 || list.getWarmupCount() != 0 || list.getPrimaryCount() != 1
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(WorkoutType.PRIMARY);
      if (!(list.toString().equals("COOLDOWN: Walk (3) -> END")) || list.size() != 1
          || list.getWarmupCount() != 0 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(WorkoutType.COOLDOWN);
      if (!(list.toString().equals("")) || list.size() != 0 || list.getWarmupCount() != 0
          || list.getPrimaryCount() != 0 || list.getCooldownCount() != 0) {
        return false;
      }
      // Return true if list contains the correct values after every remove call
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Helper method that tests the remove method in WorkoutBuilder, but removes in a different order.
   * 
   * @param list the list to test
   * 
   * @return true if all tests are passed, false otherwise.
   */
  private static boolean testRemoveOutOfOrderByType(WorkoutBuilder list) {
    // Try and catch is used to make sure unexpected exceptions are caught
    try {
      // Removes all exercises and checks after every remove that the list contains the correct
      // values
      list.removeExercise(WorkoutType.PRIMARY);
      if (!(list.toString().equals("WARMUP: Stretch (1) -> COOLDOWN: Walk (3) -> END"))
          || list.size() != 2 || list.getWarmupCount() != 1 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 1) {
        return false;
      }

      list.removeExercise(WorkoutType.COOLDOWN);
      if (!(list.toString().equals("WARMUP: Stretch (1) -> END")) || list.size() != 1
          || list.getWarmupCount() != 1 || list.getPrimaryCount() != 0
          || list.getCooldownCount() != 0) {
        return false;
      }

      list.removeExercise(WorkoutType.WARMUP);
      if (!(list.toString().equals("")) || list.size() != 0 || list.getWarmupCount() != 0
          || list.getPrimaryCount() != 0 || list.getCooldownCount() != 0) {
        return false;
      }
      // Return true if list contains the correct values after every remove call.
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Utilizes helper methods to make sure both remove methods in WorkoutBuilder work as intended
   * 
   * @return true if all test are passed
   */
  public static boolean testRemoveExercises() {
    Exercise.resetIDNumbers(); // Reset ID's

    // Sample list to test
    WorkoutBuilder list = new WorkoutBuilder();
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));

    boolean test1 = testRemoveByID(list);

    Exercise.resetIDNumbers(); // Reset ID's
    // Sample list to test
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));
    boolean test2 = testRemoveByType(list);

    Exercise.resetIDNumbers(); // Reset ID's
    // Sample list to test
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));
    boolean test3 = testRemoveOutOfOrderByID(list);

    Exercise.resetIDNumbers(); // Reset ID's
    // Sample list to test
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));
    boolean test4 = testRemoveOutOfOrderByType(list);

    return test1 && test2 && test3 && test4;
  }

  /**
   * Tests the get method in WorkoutBuilder class
   * 
   * @return true if all tests are passed, false otherwise
   */
  public static boolean testGetExercises() {
    Exercise.resetIDNumbers(); // Reset ID's

    boolean allTestsPassed = true;

    // Sample list to test
    WorkoutBuilder list = new WorkoutBuilder();
    list.add(new Exercise(WorkoutType.WARMUP, "Stretch"));
    list.add(new Exercise(WorkoutType.PRIMARY, "Squats"));
    list.add(new Exercise(WorkoutType.COOLDOWN, "Walk"));

    // This method checks to make sure the list gets the correct values after every call to get()
    String expected = "WARMUP: Stretch (1)";
    String actual = list.get(0).toString();
    if (!(expected.equals(actual))) {
      allTestsPassed = false;
    }

    expected = "PRIMARY: Squats (2)";
    actual = list.get(1).toString();
    if (!(expected.equals(actual))) {
      allTestsPassed = false;
    }

    expected = "COOLDOWN: Walk (3)";
    actual = list.get(2).toString();
    if (!(expected.equals(actual))) {
      allTestsPassed = false;
    }

    // Return true if get() returned the intended value every time
    return allTestsPassed;
  }

  // a test suite method to run all your test methods
  public static boolean runAllTests() {
    boolean clear = testClear(), add = testAddExercises(), remove = testRemoveExercises(),
        get = testGetExercises();

    System.out.println("test clear: " + (clear ? "pass" : "FAIL"));
    System.out.println("test add: " + (add ? "pass" : "FAIL"));
    System.out.println("test remove: " + (remove ? "pass" : "FAIL"));
    System.out.println("test get: " + (get ? "pass" : "FAIL"));

    // TODO: add calls to any other test methods you write

    return clear && add && remove && get;
  }

  public static void main(String[] args) {
    runAllTests();
    demo();
  }

  /**
   * Helper method to display the size and the count of different boxes stored in a list of boxes
   * 
   * @param list a reference to an InventoryList object
   * @throws NullPointerException if list is null
   */
  private static void displaySizeCounts(WorkoutBuilder list) {
    System.out.println(
        "  Size: " + list.size() + ", warmupCount: " + list.getWarmupCount() + ", primaryCount: "
            + list.getPrimaryCount() + ", cooldownCount: " + list.getCooldownCount());
  }

  /**
   * Demo method showing how to use the implemented classes in P07 Inventory Storage System
   * 
   * @param args input arguments
   */
  public static void demo() {
    // Create a new empty WorkoutBuilder object
    WorkoutBuilder list = new WorkoutBuilder();
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // Add a primary exercise to an empty list
    list.add(new Exercise(WorkoutType.PRIMARY, "5k run")); // adds PRIMARY: 5k run (1)
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "stretch")); // adds WARMUP: stretch (2) at the head
                                                           // of the list
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.PRIMARY, "bench press")); // adds PRIMARY: bench press (3)
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "upright row")); // adds WARMUP: upright row (4) at
                                                               // the head of the list
    System.out.println(list); // prints list's content
    list.add(new Exercise(WorkoutType.WARMUP, "db bench")); // adds WARMUP: db bench (5) at the head
                                                            // of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // Add more exercises to list and display its contents
    list.add(new Exercise(WorkoutType.COOLDOWN, "stretch")); // adds COOLDOWN: stretch (6) at the
                                                             // end of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.COOLDOWN, "sit-ups")); // adds COOLDOWN: sit-ups (7) at the
                                                             // end of the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: sit-ups (7) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.PRIMARY, "deadlift")); // adds PRIMARY: deadlift (8)
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: stretch (6) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.WARMUP); // removes WARMUP: db bench (5)
    System.out.println(list); // prints list's content
    list.removeExercise(3); // removes PRIMARY: bench press (3) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    try {
      list.removeExercise(25); // tries to remove box #25
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    // remove all warm-ups
    while (list.getWarmupCount() != 0) {
      list.removeExercise(WorkoutType.WARMUP);
    }
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(1); // removes PRIMARY: 5k run (1) from the list -> empty list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // adds COOLDOWN: walk (9) to the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(8); // removes PRIMARY: deadlift (8) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: walk (9) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.add(new Exercise(WorkoutType.WARMUP, "pull-up")); // adds WARMUP: pull-up (10) to the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
    list.removeExercise(10); // removes WARMUP: pull-up (10) from the list
    System.out.println(list); // prints list's content
    displaySizeCounts(list); // displays list's size and counts
  }

}
