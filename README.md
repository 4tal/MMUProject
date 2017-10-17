# MMUProject


In the last couple of months we built from scratch MMU - Memory menagement unit - That's the part in the Operating System that in charge of allocating system resources to processes according to many different parameter - Caching Algorithms.

We develop the MMU with software architecture principles:
1. SingelTone:
  1. We use it to enforce only one instance of a class, Main window, Logger, Database access.
2. Strategy:
  1. a way to encapsulate different approaches for class behavior and decide which one to use during runtime Caching Algorithms.
3. MVC:
  1. Model - incharge of the logic.
  2. View - The inteface with the User.
  3. Controller - Connects the 2.
  
What is Process?
Process is a computer program instance.
For Example Google Chrome is a computer program and Every new Tab is a process.

How the OS prioritize CPU time per process?

In our project we implemented 3:
1. LRU.
2. NFU.
3. RANDOM.


Installation instructions:
1. Open project Of the caching Algorithm part from:
  https://github.com/4tal/CacheAlgorithms
2. Make sure the test are working and than create a Jar file (not excecutable).
3. Add the JAR File to MMU.
4. Add another external JAR - 
