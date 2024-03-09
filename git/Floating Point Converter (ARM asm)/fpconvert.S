
// === file header ===
	.arch armv6               // armv6 architecture
	.cpu cortex-a72           // our CPU model
	.arm                      // arm 32-bit IS
	.fpu vfp                  // floating point co-processor
	.syntax unified           // modern syntax
// === definitions applying to the entire source file ===
	// you could put the bit count of certain fields or masks here, for example
	// .equ EXAMPLE_DEF, 0xff
	// .data                  // uncomment if you need the data segment
	.text                     // start of text segment

// === fpconvert ===
	.global fpconvert         // make fpconvert global so the linker knows about it
	.type fpconvert, %function // define fpconvert to be a function
	.equ FP_OFFSET, 32 	      // (# of saved regs - 1) * 4 rounded up to multiples of 8
	.equ nine_bit, 0x100
fpconvert:
// function prologue (do not edit)
	push {r4-r10, fp, lr}     // save registers to stack
	add fp, sp, FP_OFFSET     // set frame pointer to frame base
// you can use any of r0-r10 inclusive
// r0 is the parameter to the function
// the value of r0 upon bx lr is the value returned from the function

// YOUR CODE BEGINS
// === fpconvert function body ===
	// TODO implement; here's one example for the recommended style:
	// instruction operands   // equivalent C code         ; human readable explanation
	// add r0, r1, EQU        // r0 = r1 + EQU             ; add EQU to r0 from r1
	lsr r1, r0, 15		// r1 = r0 + 15			; extract sign of r0	
	lsr r2, r0, 8		// r2 = r0 + 8			; extract mantissa of r0
	and r2, r2, 0x7f	// r2 = r0 & 0x7f		; leave only the exponent of r0
	and r3, r0, 0xff	// r3 = r0 & 0xff		; extract the mantissa of r0

	cmp r2, 0		// if (r2==0 && r3==0)		; check if r2 and r3 are equal to 0
	bne .Lelse1		// r1<<31 			; shift r1 to the left by 31 units
	cmp r3, 0		
 	bne .Lelse1
	lsl r0, r1, 31
	b .Lreturn
	
	.Lelse1:		// else if (r2==0 && r3!=0)	; check if r2 equal to 0 and r3 not equal to 0
	cmp r2, 0		// r2 = r2 + 65			; add r2 by 65
	bne .Lelse2		// 	while(r3 < 0x100)	; check bit by bit until the first 1
	cmp r3, 0		// 	r3<<1			; sift r3 to the left by 1 unit
	beq .Lelse2		// 	r2--			; subtract r2 by 1
	add r2, r2, 65		// r3 & 0xff			; r3 and 0xff
	loop:
	    cmp r3, nine_bit
	    bge .endloop
	    lsl r3, r3, 1
	    sub r2, r2, 1
	b loop
	.endloop:
	and r3, r3, 0xff
	b .Lelse4	
	
	.Lelse2:		// else if (r2==0x7f)		; check if r2 equal to 0x7f
	cmp r2, 0x7f		// convert_infinity(r0)		; call convert_infinity function
	bne .Lelse3
	bl convert_infinity
	b .Lreturn
			
	.Lelse3:		// else				; else for the normal case
	add r2, r2, 64		// r2 = r2 + 64			; add r2 by 64
	.Lelse4:
	lsl r1, r1, 31		// r1<<31			; shift r1 to the left by 31 units 
	lsl r2, r2, 23		// r2<<23			; shift r2 to the left by 23 units
	lsl r3, r3, 15		// r3<<15			; shift r3 to the left by 15 units
	orr r0, r1, r2		// r0 = r1 | r2			; r1 or r2 
	orr r0, r0, r3		// r0 = r0 | r3			; r0 or r3
	
.Lreturn:
		
	
// === end fpconvert function body ===
// YOUR CODE ENDS

// function epilogue - do not edit
	sub sp, fp, FP_OFFSET
	pop {r4-r10, fp, lr}      // must match list in prolog's push
	bx lr                     // return
// function footer
	.size fpconvert, (. - fpconvert) // set size for function

// === file footer ===
	.section .note.GNU-stack, "", %progbits // stack/data non-exec (linker)
	.end

