///////////////////////////////////////////////////////////////////////////////
//
// Title: The WorkoutBuilder class creates a linked list of LinkedExercises. There are 3 types of
// exercises that can be added to this list: WARMUP, PRIMARY, and COOLDOWN.
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

public class WorkoutBuilder implements ListADT<Exercise> {
  private int cooldownCount; // the amount of cooldown exercises in this list
  private LinkedExercise head; // the first element in this list
  private int primaryCount; // the amount of primary exercises in this list
  private int size; // the amount of total exercises in this list
  private LinkedExercise tail; // the last element in this list
  private int warmupCount; // the amount of warmup exercises in this list

  /**
   * Increases the count of whatever exercise is passed into the method
   * 
   * @param newObject the exercise to reference and increase the count of
   */
  private void incrementType(Exercise newObject) {
    if (newObject.getType().equals(WorkoutType.WARMUP)) { // If warmup, increase warmupCount
      warmupCount++;
    } else if (newObject.getType().equals(WorkoutType.PRIMARY)) { // If primary, increase
                                                                  // primaryCount
      primaryCount++;
    } else { // If else, increase cooldownCount
      cooldownCount++;
    }
  }

  /**
   * Decrease the count of whatever exercise is passed into the method
   * 
   * @param newObject the exercise to reference and decrease the count of
   */
  private void decrementType(Exercise newObject) {
    if (newObject.getType().equals(WorkoutType.WARMUP)) { // If warmup, decrease warmupCount
      warmupCount--;
    } else if (newObject.getType().equals(WorkoutType.PRIMARY)) { // If primary, decrease
                                                                  // primaryCount
      primaryCount--;
    } else { // If else, decrease cooldownCount
      cooldownCount--;
    }
  }

  /**
   * Updates the tail to make sure it is what it is supposed to be
   */
  private void updateTail() {
    LinkedExercise currentNode = head;
    while (currentNode.getNext() != null) { // Loop through every node in the list
      currentNode = currentNode.getNext();
    }
    tail = currentNode;
  }

  /**
   * Adds a new exercise to the list
   * 
   * @param the new exercise to be added
   */
  public void add(Exercise newObject) {
    // Case where the list is empty
    if (isEmpty()) {
      head = new LinkedExercise(newObject);
      tail = head;
      incrementType(newObject);
      size++;
      return;
    }

    // Case where the exercise is of type WARMUP
    if (newObject.getType().equals(WorkoutType.WARMUP)) {
      head = new LinkedExercise(newObject, head);
      warmupCount++;
      size++;
      updateTail();
    }

    // Case where the exercise is of type PRIMARY
    else if (newObject.getType().equals(WorkoutType.PRIMARY)) {
      // Adds primary exercise after the warmup and updates the tail
      if (head.equals(tail) && head.getExercise().getType().equals(WorkoutType.WARMUP)) {
        LinkedExercise exerciseToAdd = new LinkedExercise(newObject);
        tail.setNext(exerciseToAdd);
        tail = exerciseToAdd;
      }
      // Adds primary exercise before any primary or cooldown exercises and updates the head
      else if (head.equals(tail) && (head.getExercise().getType().equals(WorkoutType.PRIMARY)
          || head.getExercise().getType().equals(WorkoutType.COOLDOWN))) {
        head = new LinkedExercise(newObject, head);
      }
      // Case where the list contains more than one element
      else {
        LinkedExercise currentNode = head;
        for (int i = 0; i < warmupCount - 1; i++) { // Loop through the list and add the exercise
                                                    // after all warmups and before all primary
                                                    // exercises
          currentNode = currentNode.getNext();
        }
        currentNode.setNext(new LinkedExercise(newObject, currentNode.getNext()));
      }
      primaryCount++;
      size++;
    }

    // Case where the exercise is of type COOLDOWN
    else if (newObject.getType().equals(WorkoutType.COOLDOWN)) {
      // Case where there is only one element in the list
      if (head.equals(tail)) {
        head.setNext(new LinkedExercise(newObject));
      }
      // Case where there is more than one element in the list
      else {
        LinkedExercise exerciseToAdd = new LinkedExercise(newObject);
        tail.setNext(exerciseToAdd);
        tail = exerciseToAdd;
      }
      cooldownCount++;
      size++;
    }
  }

  /**
   * Resets the list by setting the head and tail to null and resetting all size and exercise counts
   */
  public void clear() {
    head = null;
    tail = null;
    size = 0;
    primaryCount = 0;
    cooldownCount = 0;
    warmupCount = 0;
  }

  /**
   * Getter method for an element in the list with the specified index
   * 
   * @param index the index of the element to get
   * 
   * @return the Exercise at the specified index
   */
  public Exercise get(int index) {
    // Making sure the index is valid
    if (index < 0 || index >= size()) {
      throw new ArrayIndexOutOfBoundsException("Invalid index");
    }

    LinkedExercise currentNode = head;
    for (int i = 0; i < index; i++) { // Loop through and get to the correct index in the list
      currentNode = currentNode.getNext();
    }
    return currentNode.getExercise();
  }

  /**
   * Getter method for the cooldownCount
   * 
   * @return the current cooldown count
   */
  public int getCooldownCount() {
    return cooldownCount;
  }

  /**
   * Getter method for the primaryCount
   * 
   * @return the current primary count
   */
  public int getPrimaryCount() {
    return primaryCount;
  }

  /**
   * Getter method for the warmupCount
   * 
   * @return the current warmup count
   */
  public int getWarmupCount() {
    return warmupCount;
  }

  /**
   * Utilizes a binary search approach to looking for a specific exercise in the list
   * 
   * @param findObject the exercise to look for in the list
   * 
   * @return the index that this exercise is at, -1 if it is not in the list
   */
  public int indexOf(Exercise findObject) {
    LinkedExercise currentNode = head;
    for (int i = 0; i < warmupCount + primaryCount + cooldownCount; i++) { // Loop through all
                                                                           // exercises in the list
      if (currentNode.getExercise().equals(findObject)) {
        return i;
      } else {
        currentNode = currentNode.getNext();
      }
    }
    return -1; // Return -1 if no object exists in the LinkedList
  }

  /**
   * Checks to see if there are any elements in the list
   * 
   * @return true if the list is empty, false otherwise
   */
  public boolean isEmpty() {
    if (head == null && tail == null && size == 0 && warmupCount == 0 && primaryCount == 0
        && cooldownCount == 0) {
      return true;
    }
    return false;
  }

  /**
   * Removes an exercise by a specified exerciseID
   * 
   * @param exerciseID the exerciseID that will be removed from the list
   * 
   * @return the remoevd exercise from the list
   * 
   * @throws NoSuchElementException if there is no element in the list with the specified exerciseID
   */
  public Exercise removeExercise(int exerciseID) throws NoSuchElementException {
    // If the list is empty, throw NoSuchElementException
    if (size() == 0) {
      throw new NoSuchElementException("This list is empty!");
    }

    // Case where the exercise to be removed is the last one in the list
    else if (size() == 1 && head.getExercise().getExerciseID() == exerciseID) {
      Exercise removedNode = head.getExercise();
      clear();
      return removedNode;
    }

    // Case where the exercise being removed is not the last in the list
    else {
      LinkedExercise currentNode = head;
      if (currentNode.getExercise().getExerciseID() == exerciseID) { // Check to see if the exercise
                                                                     // to be removed is the head.
                                                                     // If it is, update the head.
        Exercise removedNode = head.getExercise();
        head = head.getNext();
        decrementType(removedNode);
        size--;
        return removedNode;
      }
      while (currentNode.getNext() != null) { // Loop through all exercises in the list
        // If the next node is the tail and it is the exercise to be removed, update the tail.
        if (currentNode.getNext().equals(tail)
            && tail.getExercise().getExerciseID() == exerciseID) {
          Exercise removedNode = tail.getExercise();
          currentNode.setNext(null);
          decrementType(removedNode);
          size--;
          return removedNode;
        }
        // If the next node in the list is the exercise to be removed, then update the currentNode's
        // next and all size values
        else if (currentNode.getNext().getExercise().getExerciseID() == exerciseID) {
          Exercise removedNode = currentNode.getNext().getExercise();
          currentNode.setNext(currentNode.getNext().getNext());
          decrementType(removedNode);
          size--;
          return removedNode;
        }
      }

      // If there is no element in the list with that elementID, throw NoSuchElementException
      throw new NoSuchElementException("There is no element in the list with that exerciseID!");
    }
  }

  /**
   * Checks the case where no element of type is in the list
   * 
   * @param type the WorkoutType to consider
   * 
   * @return true if the type is nonexistent in the list, false otherwise
   */
  private boolean checkNoSuchElementExceptionCase(WorkoutType type) {
    if (type.equals(WorkoutType.WARMUP) && warmupCount == 0) {
      return true;
    }
    if (type.equals(WorkoutType.PRIMARY) && primaryCount == 0) {
      return true;
    }
    if (type.equals(WorkoutType.COOLDOWN) && cooldownCount == 0) {
      return true;
    }
    return false;
  }

  /**
   * Removes an element from the list of the specified type. This method only removes one instance
   * of the specified type.
   * 
   * @param type the type to remove from the list
   * 
   * @return the removed exercise
   * 
   * @throws NoSuchElementException if there is no element in the list of the specified type
   */
  public Exercise removeExercise(WorkoutType type) throws NoSuchElementException {
    // Case where the list is empty. If it is, throw NoSuchElementException
    if (size() == 0) {
      throw new NoSuchElementException("This list is empty!");
    }
    // Case where the list contains one element and is removed.
    else if (size() == 1 && head.getExercise().getType().equals(type)) {
      Exercise removedNode = head.getExercise();
      clear();
      return removedNode;
    }
    // Checks to see if there are any elements with type in the list
    if (checkNoSuchElementExceptionCase(type)) {
      throw new NoSuchElementException("There are no exercises in this list of type " + type + "!");
    }

    // Case where the specified type is WARMUP. Update the head, update size values, and return the
    // removed head.
    if (type.equals(WorkoutType.WARMUP)) {
      Exercise removedNode = head.getExercise();
      head = head.getNext();
      decrementType(removedNode);
      size--;
      return removedNode;
    }

    // Case where the specified type is PRIMARY
    else if (type.equals(WorkoutType.PRIMARY)) {
      LinkedExercise currentNode = head;
      // Case where the removed node is the head
      if (currentNode.getExercise().getType().equals(WorkoutType.PRIMARY)) {
        Exercise removedNode = head.getExercise();
        head = head.getNext();
        decrementType(removedNode);
        size--;
        return removedNode;
      }

      // Case where the removed node is not the head
      while (currentNode.getNext() != null) { // Loop through all elements in the list
        // if the tail is to be removed, update the sizes, update the tail, and return the tail.
        if (currentNode.getNext().equals(tail) && tail.getExercise().getType().equals(type)) {
          Exercise removedNode = tail.getExercise();
          currentNode.setNext(null);
          decrementType(removedNode);
          size--;
          return removedNode;
        }
        // If any other node other than the head or tail is being removed, update the sizes, remove
        // the node, and set the currentNode to the next node of the removed node.
        else if (currentNode.getNext().getExercise().getType().equals(type)) {
          Exercise removedNode = currentNode.getNext().getExercise();
          currentNode.setNext(currentNode.getNext().getNext());
          decrementType(removedNode);
          size--;
          return removedNode;
        }
      }

      // This code should never run since the checkNoSuchElementExceptionCase method checks this
      // case, but it is put here just in case.
      throw new NoSuchElementException("There is no element in the list of type " + type + "!");
    }

    // Case where the specified type is COOLDOWN. Remove the tail and update sizes and the tail.
    else {
      Exercise removedNode = tail.getExercise();
      LinkedExercise currentNode = head;
      while (currentNode.getNext() != null && !(currentNode.getNext().equals(tail))) { // Loop
                                                                                       // through
                                                                                       // all nodes
                                                                                       // in the
                                                                                       // list
        currentNode = currentNode.getNext();
      }
      currentNode.setNext(null);
      decrementType(removedNode);
      size--;
      return removedNode;
    }
  }

  /**
   * Getter method for the size of the list
   * 
   * @return the size of this list
   */
  public int size() {
    return size;
  }

  /**
   * The string version of this WorkoutBuilder is the concatenation of all LinkedExercise(s) in the
   * list
   * 
   * @return the string version of this WorkoutBuilder object
   */
  public String toString() {
    String output = ""; // The string that all LinkedExercise(s) will be added to
    LinkedExercise currentNode = head;
    while (currentNode != null) { // Loop through the list
      output += currentNode.toString();
      currentNode = currentNode.getNext();
    }

    return output;
  }
}
