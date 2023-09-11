//NAME: Ethan Steinberg
//NETID: ehs2
//SIM INPUT:
//OUTPUT: 26

// This test block attempts to calculate the probability of two indepent coin
// flips both simultaneously being heads using a monte carlo simulation.
//
// It does 100 coin flips.
//
// I use a Linear Congruential Generator for my random number generator
// The constants are taken from the C99 standard
//
// This test block is special in how it stress tests the situations where the 
// set of active registers is low. 
//
// This is especially important to my allocator's spill/restore register reuse
// optimization.

/////////////// SETUP  ///////////////

// I start with a "random" seed (calculated using C's rand function)
loadI 900963297 => r1

// This is the constant multiplier
loadI 1103515245 => r100
// This is the constant increment
loadI 12345 => r101

// How much to shift by.
loadI 31 => r102

// The output location
loadI 100 => r103

// The number of hits
loadI 0 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// ITERATION  ///////////////

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip A
rshift r1, r102 => r10

// advance RNG
mult r1, r100 => r1
add r1, r101 => r1

// Get coin flip B
rshift r1, r102 => r11

// Are they both heads?
mult r10, r11 => r12

// Increment the counter if both heads
add r5, r12 => r5

/////////////// OUTPUT  ///////////////

// Output result
store r5 => r103
output 100

