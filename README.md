# Segment Tree

## Description

### September 2023

This is a simple implementation of a max segment tree. It supports the following operations:

- Update the value of a range of elements in the array
- Query the maximum value of a range in the array

### October 2023

- Add a generic way of applying a function to a range of elements in the array. Things like sum, min, max, etc. can be implemented this way.
- Efficiency can be improved by storing the tree in an array instead of a Node class. This will reduce CPU cache misses by increasing locality of reference.

## Future

- Implement Lazy Propagation to improve the efficiency of range updates.
- Add Thread-safety to Segment Trees.
