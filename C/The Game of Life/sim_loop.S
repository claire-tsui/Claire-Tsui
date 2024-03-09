// file header
	.arch armv6                  // armv6 architecture
	.arm                         // arm 32-bit instruction set
	.fpu vfp                     // floating point co-processor
	.syntax unified              // modern syntax

	// .extern                   // symbol import - external references
	// .equ                      // file level definitions
	// .bss                      // uncomment if needed
	// .data                     // uncomment if needed
	// .section .rodata          // uncomment if needed

	.text                        // start of text segment
	// function header (change main to function name in 5 places below)
	.global sim_loop             // make function global for linking to
	.type sim_loop, %function    // define as a function
	.equ EXIT_SUCCESS, 0         // return success (used by main)
	.equ EXIT_FAILURE, 1         // return failure (used by main)
	.equ FP_OFFSET, 28
	.equ fifth, FP_OFFSET + 4
	.equ FRMADD, fifth - FP_OFFSET
sim_loop:
	// function prologue
	push {r4-r9, fp, lr}		     // stack register save minimum: fp,lr (push in pairs!)
	add fp, sp, FP_OFFSET        // set frame pointer to frame base
	sub sp, sp, FRMADD 		// set up sp
	
	mov r9, r0			// store board to r9
	mov r5, r1 			// store steps to r5
	mov r6, 0 			// store 0 to initial
	
	.Lloop:
	cmp r6, r5			// initial < steps
	bge .endloop
	mov r7, 0 			// store i to r7
	ldr r8, [r9, 8] 		// store nrows to r8
		.Lfor:
		cmp r7, r8		// i < nrows
		bge .endfor 
		ldr r0, [r9, 20]	// set parameter r0 to next_buffer
		ldr r1, [r9, 16]	// set parameter r1 to buffer
		mov r2, r7		// set parameter r2 to i
		ldr r3, [r9, 8]		// set parameter r3 to nrows
		ldr r4, [r9, 12]	// set parameter r4 to ncols
		str r4, [fp, -fifth]
		bl do_row		// call do_row
		add r7, r7, 1 		// i++
		b .Lfor
		.endfor:
		mov r0, r9		// set parameter r0 to board
		bl swap_buffers		// call swap_buffers
		ldr r1, [r9, 24] 	// load gen to r1
		add r1, r1, 1 		// gen++
		str r1, [r9, 24]	// store gen to board struct
		add r6, r6, 1		// initial++
	b .Lloop
	.endloop:
	
	
	// function epilogue
	sub sp, fp, FP_OFFSET        // restore stack frame top
	pop {r4-r9, fp, lr}                 // remove frame and restore registers
	bx lr                        // return to caller
	// function footer
	.size sim_loop, (. - sim_loop) // set size for function
	// file footer
	.section .note.GNU-stack, "", %progbits // stack/data non-exec
.end
