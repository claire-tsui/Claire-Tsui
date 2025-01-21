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

// === convert_infinity ===
// Returns a 32-bit IEEE +Inf or -Inf.
	.global convert_infinity
	.type convert_infinity, %function
	.equ FP_OFFSET, 32 	       // (# of saved regs - 1) * 4 rounded up to multiples of 8
convert_infinity:
// you should not depend on the values of r1-r3 because convert_infinity takes no arguments
	mov r1, 28
	mov r2, 61
	mov r3, 5
// function prologue (do not edit)
	push {r4-r10, fp, lr}    // save regs
	add fp, sp, FP_OFFSET
// you can use any of r0-r10 inclusive
// R0 is the parameter to the function
// the last value of R0 is the value
// returned from the function
// r4-r10 are local to this function
// changes to these values will not be reflected
// in the main function.

// YOUR CODE BEGINS
// === convert_infinity function body ===
	// TODO implement; here's one example for the recommended style:
	// instruction operands   // equivalent C code         ; human readable explanation
	// add r0, r1, EQU        // r0 = r1 + EQU             ; add EQU to r0 from r1
	lsr r1, r0, 15	 	  // r1 = r0<<15	       ; shift r0 to the right by 15 units
	mov r2, 0xff		  // r2 = 0xff		       ; store 0xff to r2
	lsl r1, r1, 31		  // r1<<31		       ; shift r1 to the left by 31 units
	lsl r2, r2, 23		  // r2<<23		       ; shift r2 to the left by 23 units
	orr r0, r1, r2		  // r0 = r1|r2		       ; r1 or r2
			
// === end convert_infinity function body ===
// YOUR CODE ENDS

// function epilogue (do not edit)
	sub	sp, fp, FP_OFFSET
	pop {r4-r10, fp, lr}       // restore regs
	bx lr                      // return
// function footer
	.size convert_infinity, (. - convert_infinity)

// === file footer ===
	.section .note.GNU-stack, "", %progbits // stack/data non-exec (linker)
	.end
