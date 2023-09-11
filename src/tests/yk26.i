//NAME: Alice Kim
//NETID: yk26
//SIM INPUT:
//OUTPUT: 0 1 1 2 3 5 8 13 21

// Calculates the first 9 fibonacci numbers

//Calculations
loadI 0 => r0
loadI 1 => r1
add r0, r1 => r2
add r1, r2 => r3
add r2, r3 => r4
add r3, r4 => r5
add r4, r5 => r6
add r5, r6 => r7
add r6, r7 => r8

//Load addresses
loadI 128 => r9
loadI 132 => r10
loadI 136 => r11
loadI 140 => r12
loadI 144 => r13
loadI 148 => r14
loadI 152 => r15
loadI 156 => r16
loadI 160 => r17

//Stores fibonacci sequence to memory
store r0 => r9
store r1 => r10
store r2 => r11
store r3 => r12
store r4 => r13
store r5 => r14
store r6 => r15
store r7 => r16
store r8 => r17

//Outputs sequence
output 128
output 132
output 136
output 140
output 144
output 148
output 152
output 156
output 160



