//NAME: Olyver Yau
//NETID: oky1
//SIM INPUT:
//OUTPUT: 1 2 6 24 120 153

//COMP412 Lab
//compute factorials 1! through 5! and then compute the sum of all the factorial


    loadI   1 => r1
    loadI   4 => r0

    loadI   2048 => r11
    add     r11,r0 => r12
    add     r12,r0 => r13
    add     r13,r0 => r14
    add     r14,r0 => r15
    store   r1 => r11

    add     r1,r1 => r2
    mult    r2,r1 => r2
    store   r2 => r12
    add     r1,r1 => r2


    add     r1,r2 => r3
    mult    r3,r2 => r3
    mult    r3,r1 => r3
    store   r3 => r13
    add     r1,r2=> r3

    add     r1,r3 => r4
    mult    r4,r1 => r4
    mult    r4,r2 =>r4
    mult    r4,r3 =>r4
    store   r4 => r14
    add     r1,r3 => r4

    add     r1,r4 => r5
    mult    r1,r5 => r5
    mult    r2,r5 => r5
    mult    r3,r5 => r5
    mult    r4,r5 => r5
    store   r5 => r15

    add r15,r0 => r17

    load r11 =>r11
    load r12 => r12
    load r13 => r13
    load r14 => r14
    load r15 => r15

    add r11,r12 => r16
    add r16,r13 => r16
    add r16,r14 => r16
    add r16,r15 => r16
    store r16 => r17

    output 2048
    output 2052
    output 2056
    output 2060
    output 2064
    output 2068

