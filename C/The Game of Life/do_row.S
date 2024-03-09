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
	.global do_row               // make function global for linking to
	.type do_row, %function      // define as a function
	.equ FP_OFFSET, 32           // (#regs pushed -1) * 4 is distance from sp
	.equ EXIT_SUCCESS, 0         // return success (used by main)
	.equ EXIT_FAILURE, 1         // return failure (used by main)
	.equ dest, FP_OFFSET +4
	.equ FRMADD, dest - FP_OFFSET
do_row:
	// function prologue
	push {r4-r10, fp, lr}                // stack register save minimum: fp,lr (push in pairs!)
	add fp, sp, FP_OFFSET        // set frame pointer to frame base
	sub sp, sp, FRMADD
	
	mov r10, r0 
	str r10, [fp, -dest]
	mov r9, r1 
	mov r8, r2 
	mov r7, r3 
	ldr r6, [fp, 4] 
	
	mov r5, 0 
	.Lloop:
	cmp r5, r6
	bge .endloop

	// top_left
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row-1, rows)
	sub r0, r5, 1 // r0 = i - 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i-1, cols)
	mul r2, r10, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	mov r4, r2

	// top 
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row-1, rows)
	mul r2, r10, r6 // get index
	add r2, r2, r5
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// top_right
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row-1, rows)
	add r0, r5, 1 // r0 = i + 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i+1, cols)
	mul r2, r10, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r4, r2

	// left
	sub r0, r5, 1 // r0 = i - 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i-1, cols)
	mul r2, r8, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// right
	add r0, r5, 1 // r0 = i + 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i+1, cols)
	mul r2, r8, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// bot_left
	add r0, r8, 1 // r0 = row + 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row-1, rows)
	sub r0, r5, 1 // r0 = i - 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i-1, cols)
	mul r2, r10, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r4, r2

	// bot
	add r0, r8, 1 // r0 = row + 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row+1, rows)
	mul r2, r10, r6 // get index
	add r2, r2, r5
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// bot_right
	add r0, r8, 1 // r0 = row + 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r10, r0 // r2 = mod(row+1, rows)
	add r0, r5, 1 // r0 = i + 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i+1, cols)
	mul r2, r10, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r4, r2  

	mul r1, r6, r8
	add r1, r1, r5 
	ldrb r0, [r9, r1] 

	.Lifcheck:
	cmp r0, 1
	bne .Lelse

		.Lif_1:
		cmp r4, 2
		blt .Lelse_1
		cmp r4, 3
		bgt .Lelse_1

		// dest[index] = 0 ---??
		mov r2, 1 
		ldr r0, [fp, -dest]
		strb r2, [r0, r1]
		b .endcheck
		
		.Lelse_1: 
		mov r3, 0
		ldr r0, [fp, -dest]
		strb r3, [r0, r1]
		b .endcheck

	.Lelse:
		.Lif_2:
		cmp r4, 3
		beq .Lelse_2
		cmp r4, 6
		beq .Lelse_2

		mov r3, 0
		ldr r0, [fp, -dest]
		strb r3, [r0, r1]
		b .endcheck
		
		.Lelse_2: 
		mov r2, 1
		ldr r0, [fp, -dest]
		strb r2, [r0, r1]
		b .endcheck
	.endcheck:


	add r5, r5, 1
	b .Lloop
	.endloop:
	
	// function epilogue
	sub sp, fp, FP_OFFSET        // restore stack frame top
	pop {r4-r10, fp, lr}                 // remove frame and restore registers
	bx lr                        // return to caller
	// function footer
	.size do_row, (. - do_row)  // set size for function
	// file footer
	.section .note.GNU-stack, "", %progbits // stack/data non-exec
.end
