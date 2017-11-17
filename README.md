# ThreadPi
Threaded Pi calculation for Software Engineering, CS1530, at Pitt.

This is a reasonably efficient multi-threaded solution.

Always expects 2 arguments, the first a number of iterations and the second a number of threads.

Out of an err of laziness, there will be a calculation error if the number of iterations is not evenly divisible by the number of threads. If this is a case, there will be a warning, but the program will continue. I probably will fix this at some point.
