/**
 * Assignment: SWLife
 * Section: (A, B, C)
 * Class: UCSD CSE30-fall2019
 *
 */
#include "board.h"
#include "cse30life.h"

/**
 * create a new board - STUDENT
 *
 * - malloc a boards structure
 * - set the generation to 0
 * - open the file (if it doesn't exist, return a NULL pointer
 * - read the first line which the number of rows
 * - read the second line which is the number of cols
 * - set the # of rows and # of cols in the boards structure
 * - malloc buf1 and buf2
 * - clear both board buffers
 * - read the file until done.  each row contains a row and a columns separted by
 *   white space dfor each line, setCell in the next buffer
 * - swap the buffers
 * - close the file
 * - return the boards pointer
 */
	// .section .rodata
	// mode: .string "r"
	// fmt: .string "%zu %zu"

	// .equ file, FP_OFFSET + 4
	// .equ col, file + 4
	// .equ row, row + 4
	
Board *create_board(const char *filename) {
	
	// push {r4-r9, fp, lr}
	// add fp, sp, FP_OFFSET
	// mov r4, r0 --> filename r4

  	Board* board = malloc(sizeof(Board));
	// mov r0, 28
	// bl malloc
	// ldr r5, [r0] --> board r5

    board->gen = 0;
	// mov r6, 0   
	// str r6, [r5, 24] --> gen r6
	                 

   	FILE* file = fopen(filename, "r");
	// move r0, r4
	// ldr r1, =mode
	// bl fopen
	// mov r4, r0 --> file r4

   	fscanf(file, "%zu %zu", &board->nrows, &board->ncols);
	// mov r0, r4
	// ldr r1, =fmt
	// add r2, r5, 8
	// add r3, r5, 12
	// bl fsacnf

	// ldr r2, [r5, 8]
	// ldr r3, [r5, 12]
	board->buf1 = calloc(board->nrows * board->ncols, sizeof(Cell));
	board->buffer = board->buf1;
	// mul r0, r2, r3 
	// mov r1, 1
	// bl calloc 
	// str r0, [r5, 16]

    board->buf2 = calloc(board->nrows * board->ncols, sizeof(Cell));
	board->next_buffer = board->buf2;
	// mul r0, r2, r3 
	// mov r1, 1
	// bl calloc 
	// str r0, [r5, 20]

	size_t row;
	size_t col;
	while (fscanf(file, "%zu %zu", &row, &col) != EOF) {
		if (row < board->nrows && col < board->ncols) {
			board->buffer[get_index(board->ncols, row, col)] = 1; 
		}
		else {
			fprintf(stderr, "Invalid index %zu %zu\n", row, col);
			return NULL;
		}
	}
	
	/*
	.Lloop:
	mov r0, r4
	ldr r1, =fmt
	add r2, fp, -row
	add r3, fp, -col
	bl fscanf
	cmp r0, EOF
	beq .endloop
		ldr r6, [fp, -row] --> row r6
		ldr r7, [fp, -col] --> col r7
		ldr r8, [r5, 12] --> ncols r8
		mov r0, r8
		mov r1, r6
		mov r2, r7
		bl get_index
		ldr r9, [r5, 16]
		mov r3, 1
		str r3, [r9, r0]
	b .Lloop
	.endloop:
	*/ 
    	
	fclose(file);	
    	return board;
	// mov r0, r4
	// bl fclose
	// mov r0, r5
}

/**
 * delete a board
 * note: this is a deep delete - STUDENT
 * the pointer to the boards structure should be set to NULL
 * after the memory is freed.
 */
void delete_board(Board **bpp) {
	free((*bpp)->buf1);
	free((*bpp)->buf2);
	free(*bpp);
	*bpp = NULL;
}

/**
 * set all the Cells to 0 - STUDENT
 */
void clear_board(Board *board) {
	for (size_t i = 0; i < board->nrows * board->ncols; i++) {
		board->buffer[i] = 0;
		board->next_buffer[i] = 0;
	}
}
/**
 * swap the current and next buffers
 */
void swap_buffers(Board *board) {
	Cell *temp = board->buffer;
	board->buffer = board->next_buffer;
	board->next_buffer = temp;
}

/**
 * get a cell index
 */
size_t get_index(size_t ncols, size_t row, size_t col) {
	return row * ncols + col;
}

