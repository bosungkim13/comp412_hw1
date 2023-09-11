//NAME: James Lockard
//NETID: jml13
//SIM INPUT: -i 2048 5 8 -10 13 3 4
//OUTPUT: 62 -150 -89
// This program when given two vectors, takes the cross product of the two
// vectors and returns the resulting vector.
//
// Example input: -i 2048 5 8 -10 13 3 4
// The example input represents a cross product looking like this
// (5i + 8j - 10k) x (13i + 3j + 4k)
//
// and would result in an output of:
// 62 -150 -89
// which would represent the vector
// (62i - 150j - 89k)

// decide where the output should be
loadI 1028 => r900
loadI 1032 => r901
loadI 1036 => r902


// load the inputs
loadI 2048 => r0 // i1
loadI 2052 => r1 // j1
loadI 2056 => r2 // k1
loadI 2060 => r3 // i2
loadI 2064 => r4 // j2
loadI 2068 => r5 // k2

// load in such a way that r0 has further next use than
// r5 from the previous block
load r5 => r5
load r4 => r4
load r3 => r3
load r2 => r2
load r1 => r1
load r0 => r0

// compute the third minor
mult r0, r4 => r30 // i1 * j2
mult r3, r1 => r31 // i2 * j1

// compute the second minor
mult r0, r5 => r20 // i1 * k2
mult r3, r2 => r21 // i2 * k1

// compute the first minor
mult r1, r5 => r10 // j1 * k2
mult r4, r2 => r11 // j2 * k1

// find i3
sub r10, r11 => r40

// find j3
sub r21, r20 => r41

// find k3
sub r30, r31 => r42

//store the results
store r40 => r900
store r41 => r901
store r42 => r902

//output the results
output 1028
output 1032
output 1036




