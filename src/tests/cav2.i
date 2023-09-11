//NAME: Caleb Voss
//NETID: cav2
//SIM INPUT: -i 1024 3 7 5 10 5 4 3 8 3 2 9 8 4 3 4 9 4 2 2 10 2 4 4 5 0 1 0 5 3 8 0 4
//OUTPUT: 56 119 34 130 52 93 26 117 40 87 14 117 49 96 20 111

// Multiply two 4x4 matrices and print the results in row-major sequence.
// The input sequence of integers beginning at address 1024 defines the two
// input matrices. The first 16 values comprise the first matrix in row-major
// order. That is, the first 4 values comprise the first row, the next 4 comprise
// the second row, etc. The second set of 16 values comprises the second matrix,
// also in row-major order. Thus:
//
//      3  7  5 10         4  2  2 10
// A =  5  4  3  8    B =  2  4  4  5
//      3  2  9  8         0  1  0  5
//      4  3  4  9         3  8  0  4
//
//
// The output of the program is the product AB, again in row-major order:
//
//       56 119  34 130
// AB =  52  93  26 117
//       40  87  14 117
//       49  96  20 111
//
// The approach of the algorithm is to load all entries of both A and B into
// registers at once, then compute and output each entry of AB in order.

// Set up loading of matrices from memory:
// r0 is address to read from
// r1 is always 4 (to increment with)
// rmij will hold row i, col j of matrix m
loadI 1024 => r0
loadI    4 => r1

// Row 1 of matrix 1
load    r0 => r111
add r1, r0 => r0
load    r0 => r112
add r1, r0 => r0
load    r0 => r113
add r1, r0 => r0
load    r0 => r114
add r1, r0 => r0
// Row 2 of matrix 1
load    r0 => r121
add r1, r0 => r0
load    r0 => r122
add r1, r0 => r0
load    r0 => r123
add r1, r0 => r0
load    r0 => r124
add r1, r0 => r0
// Row 3 of matrix 1
load    r0 => r131
add r1, r0 => r0
load    r0 => r132
add r1, r0 => r0
load    r0 => r133
add r1, r0 => r0
load    r0 => r134
add r1, r0 => r0
// Row 4 of matrix 1
load    r0 => r141
add r1, r0 => r0
load    r0 => r142
add r1, r0 => r0
load    r0 => r143
add r1, r0 => r0
load    r0 => r144
add r1, r0 => r0

// Row 1 of matrix 2
load    r0 => r211
add r1, r0 => r0
load    r0 => r212
add r1, r0 => r0
load    r0 => r213
add r1, r0 => r0
load    r0 => r214
add r1, r0 => r0
// Row 2 of matrix 2
load    r0 => r221
add r1, r0 => r0
load    r0 => r222
add r1, r0 => r0
load    r0 => r223
add r1, r0 => r0
load    r0 => r224
add r1, r0 => r0
// Row 3 of matrix 2
load    r0 => r231
add r1, r0 => r0
load    r0 => r232
add r1, r0 => r0
load    r0 => r233
add r1, r0 => r0
load    r0 => r234
add r1, r0 => r0
// Row 4 of matrix 2
load    r0 => r241
add r1, r0 => r0
load    r0 => r242
add r1, r0 => r0
load    r0 => r243
add r1, r0 => r0
load    r0 => r244

// Multiply matrix 1 by matrix 2
// r3 holds each intermediate product
// r2 holds the sum for each entry
// r0 is the address to store to (always 1020)
loadI      1020 => r0

// 1,1
loadI         0 => r2
mult r111, r211 => r3
add      r3, r2 => r2
mult r112, r221 => r3
add      r3, r2 => r2
mult r113, r231 => r3
add      r3, r2 => r2
mult r114, r241 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 1,2
loadI         0 => r2
mult r111, r212 => r3
add      r3, r2 => r2
mult r112, r222 => r3
add      r3, r2 => r2
mult r113, r232 => r3
add      r3, r2 => r2
mult r114, r242 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 1,3
loadI         0 => r2
mult r111, r213 => r3
add      r3, r2 => r2
mult r112, r223 => r3
add      r3, r2 => r2
mult r113, r233 => r3
add      r3, r2 => r2
mult r114, r243 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 1,4
loadI         0 => r2
mult r111, r214 => r3
add      r3, r2 => r2
mult r112, r224 => r3
add      r3, r2 => r2
mult r113, r234 => r3
add      r3, r2 => r2
mult r114, r244 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 2,1
loadI         0 => r2
mult r121, r211 => r3
add      r3, r2 => r2
mult r122, r221 => r3
add      r3, r2 => r2
mult r123, r231 => r3
add      r3, r2 => r2
mult r124, r241 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 2,2
loadI         0 => r2
mult r121, r212 => r3
add      r3, r2 => r2
mult r122, r222 => r3
add      r3, r2 => r2
mult r123, r232 => r3
add      r3, r2 => r2
mult r124, r242 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 2,3
loadI         0 => r2
mult r121, r213 => r3
add      r3, r2 => r2
mult r122, r223 => r3
add      r3, r2 => r2
mult r123, r233 => r3
add      r3, r2 => r2
mult r124, r243 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 2,4
loadI         0 => r2
mult r121, r214 => r3
add      r3, r2 => r2
mult r122, r224 => r3
add      r3, r2 => r2
mult r123, r234 => r3
add      r3, r2 => r2
mult r124, r244 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 3,1
loadI         0 => r2
mult r131, r211 => r3
add      r3, r2 => r2
mult r132, r221 => r3
add      r3, r2 => r2
mult r133, r231 => r3
add      r3, r2 => r2
mult r134, r241 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 3,2
loadI         0 => r2
mult r131, r212 => r3
add      r3, r2 => r2
mult r132, r222 => r3
add      r3, r2 => r2
mult r133, r232 => r3
add      r3, r2 => r2
mult r134, r242 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 3,3
loadI         0 => r2
mult r131, r213 => r3
add      r3, r2 => r2
mult r132, r223 => r3
add      r3, r2 => r2
mult r133, r233 => r3
add      r3, r2 => r2
mult r134, r243 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 3,4
loadI         0 => r2
mult r131, r214 => r3
add      r3, r2 => r2
mult r132, r224 => r3
add      r3, r2 => r2
mult r133, r234 => r3
add      r3, r2 => r2
mult r134, r244 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 4,1
loadI         0 => r2
mult r141, r211 => r3
add      r3, r2 => r2
mult r142, r221 => r3
add      r3, r2 => r2
mult r143, r231 => r3
add      r3, r2 => r2
mult r144, r241 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 4,2
loadI         0 => r2
mult r141, r212 => r3
add      r3, r2 => r2
mult r142, r222 => r3
add      r3, r2 => r2
mult r143, r232 => r3
add      r3, r2 => r2
mult r144, r242 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 4,3
loadI         0 => r2
mult r141, r213 => r3
add      r3, r2 => r2
mult r142, r223 => r3
add      r3, r2 => r2
mult r143, r233 => r3
add      r3, r2 => r2
mult r144, r243 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// 4,4
loadI         0 => r2
mult r141, r214 => r3
add      r3, r2 => r2
mult r142, r224 => r3
add      r3, r2 => r2
mult r143, r234 => r3
add      r3, r2 => r2
mult r144, r244 => r3
add      r3, r2 => r2
store        r2 => r0
output       1020

// Phew!

