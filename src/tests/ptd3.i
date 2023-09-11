//NAME: Patrick Dunphy
//NETID: ptd3
//SIM INPUT: -i 1024 2 1 1 2 2 1 1 2
//OUTPUT: 5 4 4 5
// Multiplies two 2X2 matrices and outputs the result as:
//
//    |a   b|
//    |c   d|
//
//    OUTPUT:
//    a
//    b
//    c
//    d
//
// Input:
//
// -i 1024 e f g h i j k l
//
//    |e   f| * |i   j|
//    |g   h|   |k   l|
//

//load initial constants for accessing memory
loadI 1024 => r0
loadI 4 => r1

//load first matrix
load r0 => r2
add r0, r1 => r0
load r0 => r3
add r0, r1 => r0
load r0 => r4
add r0, r1 => r0
load r0 => r5

//load first matrix
add r0, r1 => r0
load r0 => r6
add r0, r1 => r0
load r0 => r7
add r0, r1 => r0
load r0 => r8
add r0, r1 => r0
load r0 => r9

// upper left corner of product
mult r2, r6 => r0
mult r3, r8 => r1
add r0, r1 => r10

//upper right corner of product
mult r2, r7 => r0
mult r3, r9 => r1
add r0, r1 => r11

//lower left corner of product
mult r4, r6 => r0
mult r5, r8 => r1
add r0, r1 => r12

//lower left corner of product
mult r4, r7 => r0
mult r5, r9 => r1
add r0, r1 => r13

//store and output product
loadI 1024 => r0
store r10 => r0
loadI 1028 => r0
store r11 => r0
loadI 1032 => r0
store r12 => r0
loadI 1036 => r0
store r13 => r0
output 1024
output 1028
output 1032
output 1036




