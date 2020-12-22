# Convex Hulls and Path Finding

The project was to design, implement and demonstrate an algorithm to find the shortest path, from a source to a destination point, avoiding a number of obstacles on a 2D plane. The obstacles have straight-line edges (simple polygons) but are not necessarily convex.

The algorithm used was the Travelling Salesman Problem (TSP) algorithm. The convex hulls of buildings (2D coordinates) was computed and constructed prior to the application of the TSP algorithm so as to avoid going through buildings. 

## Project Structure
- The .TXT files in the root of the project contains the coordinates for the buildings as well as the source and the destination points
- The src directory contains the solution
- Project_Instructions.pdf contains the problem description - academic project
- Project_Report.pdf contains the solution (design and implementation) description 
