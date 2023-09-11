//NAME: Jessica Dawson
//NETID: jnd2
//SIM INPUT: -i 1024 3
//OUTPUT: 3 9 27 81

// This program computes the first 4 powers of the input value, with annoying
// things interspersed to confuse the allocator a little.

loadI 2048 => r0	// where to store output

// Load in a bunch of immediate values, and keep them all live
loadI 1 => r1
loadI 1 => r2
loadI 1 => r3
loadI 1 => r4
loadI 1 => r5
loadI 1 => r6
loadI 1 => r7
loadI 1 => r8
loadI 1 => r9
loadI 1 => r10
loadI 1 => r11
loadI 1 => r12
loadI 1 => r13
loadI 1 => r14
loadI 1 => r15
loadI 1 => r16
loadI 1 => r17
loadI 1 => r18

// Load in the input value
loadI 1024 => r19
load r19 => r20
// and output it
store r20 => r0
output 2048	// output: 3

// input^2
mult r20, r20 => r20 // all in the same source register, to make sure that works
mult r17, r20 => r21	// use one of those loadI'd registers
store r21 => r0
output 2048	// output: 9

// input^3
loadI 1024 => r1	
load r1 => r20		// get input from memory again
load r0 => r21		// get 9 from memory, too
mult r20, r21 => r22	// 3 * 9 = 27

// subtract input, then add it again, with useless adds in the middle,
// using registers from before
sub r22, r20 => r22
add r10, r1 => r100
add r11, r2 => r101
add r3, r12 => r102
add r4, r13 => r103
add r5, r14 => r104
add r6, r15 => r105
add r7, r16 => r106
add r8, r17 => r107
add r9, r18 => r108
add r10, r19 => r109
add r22, r20 => r22

// output 27
store r22 => r0
output 2048


// use all the random things we defined before, so they couldn't be discarded
mult r100, r22 => r110
mult r101, r110 => r110
add r102, r110 => r110
add r103, r110 => r110
add r104, r110 => r110
add r105, r110 => r110
add r106, r110 => r110
add r107, r110 => r110
add r108, r110 => r110
add r109, r110 => r110
add r103, r110 => r110
add r103, r110 => r110
add r103, r110 => r110
add r103, r110 => r110
add r110, r110 => r110
add r2, r19 => r21
add r1, r110 => r99
add r2, r110 => r99
add r3, r110 => r99
add r4, r110 => r99
add r5, r110 => r99
add r6, r110 => r99
add r7, r110 => r99
add r8, r110 => r99
add r9, r110 => r99

mult r20, r22 => r22 // 3 * 27 = 81
store r22 => r0
output 2048	// 81




