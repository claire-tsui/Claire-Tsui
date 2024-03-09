// file header
	.arch armv6                  // armv6 architecture
	.arm                         // arm 32-bit instruction set
	.fpu vfp                     // floating point co-processor
	.syntax unified              // modern syntax

	// .extern                   // symbol import - external references
	// .equ                      // file level definitions
	.equ EOF, -1
	// .bss                      // uncomment if needed
	// .data                     // uncomment if needed
	.section .rodata          	
	mode: .string "r"
	fmt: .string "%zu %zu"

	.text                        // start of text segment
	// function header (change main to function name in 5 places below)
	.global create_board         // make function global for linking to
	.type create_board, %function// define as a function
	.equ FP_OFFSET, 28           // (#regs pushed -1) * 4 is distance from sp
	.equ EXIT_SUCCESS, 0         // return success (used by main)
	.equ EXIT_FAILURE, 1         // return failure (used by main)
	.equ file, FP_OFFSET + 4
	.equ col, file + 4
	.equ row, col + 4
	.equ FRMADD, row - FP_OFFSET
create_board:
	// function prologue
	push {r4-r9, fp, lr}         // stack register save minimum: fp,lr (push in pairs!)
	add fp, sp, FP_OFFSET        // set frame pointer to frame base
	sub sp, sp, FRMADD		// set sp
	mov r4, r0 			// store filename to r4

	mov r0, 28			// size of board 28
	bl malloc			// call malloc
	mov r5, r0			// load board to r5

	mov r6, 0			// store 0 to r6
	str r6, [r5, 24] 		// store board->gen to r6

	mov r0, r4			// set parameter r0 to filename(r4)
	ldr r1, =mode			// set parameter r1 to "r"
	bl fopen			// call fopen
	mov r4, r0			// store file to r4
	
	mov r0, r4			// set parameter r0 to file(r4)
	ldr r1, =fmt			// set parameter r1 to "%zu %zu"
	add r2, r5, 8			// set parameter r2 to &board->nrows
	add r3, r5, 12			// set parameter r3 to &board->ncols
	bl fscanf			// call fsanf

	ldr r7, [r5, 8]			// load nrows to r2
	ldr r8, [r5, 12]		// load ncols to r3

	mul r0, r7, r8 			// set parameter r0 to r2*r3
	mov r1, 1			// set parameter r1 to size of Cell 
	bl calloc 			// call calloc
	str r0, [r5]	
	str r0, [r5, 16]		// store buf1 to buffer

	mul r0, r7, r8 			// set parameter r0 to r2*r3
	mov r1, 1			// set parameter r1 to size of Cell 
	bl calloc 			// call calooc
	str r0, [r5, 4]
	str r0, [r5, 20]		// store buf2 to next_buffer

	.Lloop:
	mov r0, r4			// set parameter r0 to file
	ldr r1, =fmt			// set parameter r1 to "%zu %zu"
	add r2, fp, -row		// set parameter r2 to &row
	add r3, fp, -col		// set parameter r3 to &col
	bl fscanf			// call fscanf
	cmp r0, EOF			// r0 ?= EOF
	beq .endloop		
		ldr r6, [fp, -row] 	// load row to r6
		ldr r7, [fp, -col] 	// load col to r7
		ldr r8, [r5, 12] 	// load ncols to r8
		mov r0, r8		// set parameter r0 to ncols
		mov r1, r6		// set parameter r1 to row
		mov r2, r7		// set parameter r2 to col
		bl get_index		// call get_index
		ldr r9, [r5, 16]	// load buffer to r9
		mov r3, 1		// store 1 to r3
		str r3, [r9, r0]	// store buffer[index] to 1
	b .Lloop
	.endloop:

	mov r0, r4			// set parameter r0 to file
	bl fclose			// call fclose
	mov r0, r5			// return board
			
	// function epilogue
	sub sp, fp, FP_OFFSET        // restore stack frame top
	pop {r4-r9, fp, lr}                 // remove frame and restore registers
	bx lr                        // return to caller
	// function footer
	.size create_board, (. - create_board) // set size for function
	// file footer
	.section .note.GNU-stack, "", %progbits // stack/data non-exec
.end
