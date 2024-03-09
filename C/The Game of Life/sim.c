#include "sim.h"

/**
 * gets x mod N (works for negative numbers as well! Use this instead of %)
 */
size_t mod(int x, size_t N) { return (x + x / N * N) % N; }

/**
 * process one row of the board
 */
static void do_row(Cell *dest, Cell *src, size_t row, size_t rows, size_t cols) {
	/*
	push {r5-r10, fp, lr}
	add fp, sp, FP_OFFSET
	*/

	/*
	mov r10, r0 
	str r10, [fp, -dest]
	mov r9, r1 --> src r9
	mov r8, r2 --> row r8
	mov r7, r3 --> rows r7
	ldrb r6, [fp, 4] --> cols r6
	
	mov r5, 0 --> i r5
	.Lloop
	cmp r5, r6
	bge .endloop
	
	// top_left
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r2, r0 // r2 = mod(row-1, rows)
	sub r0, r5, 1 // r0 = i - 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i-1, cols)
	mul r2, r2, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	mov r4, r2

	// top 
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r2, r0 // r2 = mod(row-1, rows)
	mul r2, r2, r6 // get index
	add r2, r2, r5
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// top_right
	sub r0, r8, 1 // r0 = row - 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r2, r0 // r2 = mod(row-1, rows)
	add r0, r5, 1 // r0 = i + 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i+1, cols)
	mul r2, r2, r6 // get index
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
	mov r2, r0 // r2 = mod(row-1, rows)
	sub r0, r5, 1 // r0 = i - 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i-1, cols)
	mul r2, r2, r6 // get index
	add r2, r2, r3
	ldrb r2, [r9, r2]
	add r4, r2

	// bot
	add r0, r8, 1 // r0 = row + 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r2, r0 // r2 = mod(row+1, rows)
	mul r2, r2, r6 // get index
	add r2, r2, r5
	ldrb r2, [r9, r2]
	add r4, r4, r2 

	// bot_right
	add r0, r8, 1 // r0 = row + 1
	mov r1, r7 // r1 = rows
	bl mod
	mov r2, r0 // r2 = mod(row+1, rows)
	add r0, r5, 1 // r0 = i + 1
	mov r1, r6 // r1 = cols
	bl mod
	mov r3, r0 // r3 = mod(i+1, cols)
	mul r2, r2, r6 // get index
	add r2, r2, r3 
	ldrb r2, [r9, r2] 
	add r4, r4, r2  ------> l_n r4

	mul r1, r6, r8
	add r1, r1, r5 ------> index r1
	ldrb r0, [r9, r1] ------> src[index] r0

	.Lifcheck:
	cmp r0, 1
	bne .Lelse

		.Lif_1:
		cmp r4, 2
		bge .Lelse_1
		cmp r4, 3
		ble .Lelse_1

		// dest[index] = 0 ------??
		mov r2, 0 
		add r5, fp, -dest
		str r2, [r5, r1]
		b .endcheck
		
		.Lelse_1: 
		mov r3, 1
		add r5, fp, -dest
		str r3, [r5, r1]
		b .endcheck

	.Lelse:
		.Lif_2:
		cmp r4, 3
		bne .Lelse_2
		cmp r4, 6
		bne .Lelse_2

		mov r3, 1
		add r5, fp, -dest
		str r3, [r5, r1]
		b .endcheck
		
		.Lelse_2: 
		mov r2, 0 
		add r5, fp, -dest
		str r2, [r5, r1]
		b .endcheck
	.endcheck:


	add r5, r5, 1
	b .Lloop
	.endloop:

	*/

	
	for (size_t i = 0; i < cols; i++) {
		
        size_t top_left = get_index(cols, mod(row-1, rows), mod(i-1, cols));
		size_t top = get_index(cols, mod(row-1, rows), i);
		size_t top_right = get_index(cols, mod(row-1, rows), mod(i+1, cols));
		size_t left = get_index(cols, row, mod(i-1, cols));
		size_t right = get_index(cols, row, mod(i+1, cols));
		size_t bot_left = get_index(cols, mod(row+1, rows), mod(i-1, cols));
		size_t bot = get_index(cols, mod(row+1, rows), i);
		size_t bot_right = get_index(cols, mod(row+1, rows), mod(i+1, cols));
		size_t live_neighbor = src[top_left] + src[top] + src[top_right] + src[left] + src[right] 
								+ src[bot_left] + src[bot] + src[bot_right];
		size_t index = get_index(cols, row, i);
		if (src[index] == 1) {
			if (live_neighbor < 2 || live_neighbor > 3) {
				dest[index] = 0; 
			}
			else {
				dest[index] = 1; 
			}
		} 
		else {
			if (live_neighbor == 3 || live_neighbor == 6) {
				dest[index] = 1; 
			}
			else {
				dest[index] = 0; 
			}
		}
	}
}



/**
 * perform a simulation for "steps" generations
 *
 * for steps
 *   calculate the next board
 *   swap current and next
 */

	/*
	.equ FP_OFFSET, 28
	.equ fifth, FP_OFFSET + 4
	.equ FRMADD, fifth - FP_OFFSET
	*/ 

void sim_loop(Board *board, unsigned int steps) {
	/* 
	push {r4-r9, fp, lr} 
	add fp, sp, FP_OFFSET
	sub sp, sp, FRMADD // set up sp

	mov r9, r0 --> baord r9
	mov r5, r1 --> steps r5
	*/ 
	unsigned int initial = 0;
	// mov r6, 0 --> ini r6
	while (initial < steps) {
		for (size_t i = 0; i < board->nrows; i++) { 
			do_row(board->next_buffer, board->buffer, i, board->nrows, board->ncols);
			
		}
		swap_buffers(board);
		board->gen++;
		initial++;
	}
	/*
	.Lloop 
	cmp r6, r5
	bge .endloop

		mov r7, 0 --> i r7
		ldr r8, [r9, 8] --> nrow r8
		.Lfor:
			cmp r7, r8
			bge .endfor 
				ldr r0, [r9, 20]
				ldr r1, [r9, 16]
				mov r2, r7
				ldr r3, [r9, 8] 
				ldr r4, [r9, 12]
				str r4, [fp, -fifth]
				bl do_row
			add r7, r7, 1 
			b .Lfor
		.endfor:
		mov r0, r9
		bl swap_buffers
		ldr r1, [r9, 24] --> gen r1
		add r1, r1, 1 
		str r1, [r9, 24]
		add r6, r6, 1

	b .Lloop
	.endloop:
	*/
}


