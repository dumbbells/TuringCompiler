/* In stack pictures: -- seperates the before and after views */
/* 					    \ (backslash) seperates the various objects */
class Instr {
	/* supervisor Instructions */
	public static final int SUPERVISOR = 0x00;
	public static final int HALT = 0x00;
	public static final int TRACE = 0x01;
	public static final int SYSCALL = 0x02;
	public static final int SYSRET = 0x03;
	
	/* SYSCALL codes */
	public static final int READ_INT = 0;
	public static final int PRINT_INT = 1;
	public static final int READ_CHAR = 2;
	public static final int PRINT_CHAR = 3;
	public static final int READ_LONG = 4;
	public static final int PRINT_LONG = 5;
	public static final int READ_FLOAT = 6;
	public static final int PRINT_FLOAT = 7;
	
	/* references to locations in the code space */
	public static final int PC_RELATIVE = 0x10;
	public static final int RGETW = 0x10;		/*arg = w_PC_offset; stack = --w_value*/
	public static final int RGETL = 0x11;		/*arg = w_PC_offset; stack = --l_value*/
	public static final int RGETQ = 0x12;		/*arg = w_PC_offset; stack = --q_value*/
	public static final int RGETB = 0x13;		/*arg = w_PC_offset; stack = --b_value*/
	public static final int RPUTW = 0x14;		/*arg = w_PC_offset; stack = w_value--*/
	public static final int RPUTL = 0x15;		/*arg = w_PC_offset; stack = l_value--*/
	public static final int RPUTQ = 0x16;		/*arg = w_PC_offset; stack = q_value--*/
	public static final int RPUTB = 0x17;		/*arg = w_PC_offset; stack = b_value--*/
	public static final int RGOTO = 0x18;		/*arg = w_PC_offset*/
	public static final int RGOSUB = 0x19;		/*arg = w_PC_offset; stack = --q_ret_addr*/
	public static final int RGOZ = 0x1A;		/*arg = w_PC_offset; stack = w_flag--*/
	public static final int RPUSHA = 0x1B;		/*arg = w_PC_offset; stack = --q_c_addr*/
	public static final int PUSHW = 0x1C;		/*arg = w_value; stack = --w_value*/
	public static final int PUSHL = 0x1D;		/*arg = l_value; stack = --l_value*/
	public static final int PUSHQ = 0x1E;		/*arg = q_value; stack = --q-value*/
	public static final int PUSHB = 0x1F;		/*arg = b_value; stack = --b_value*/
	public static final int CODE_STACK = 0x20;
	public static final int CGETW = 0x20;		/*stack = q_c_addr--w_value*/
	public static final int CGETL = 0x21;		/*stack = q_c_addr--l_value*/
	public static final int CGETQ = 0x22;		/*stack = q_c_addr--q_value*/
	public static final int CGETB = 0x23;		/*stack = q_c_addr--b_value*/
	public static final int CPUTW = 0x24;		/*stack = q_c_addr\w_value--*/
	public static final int CPUTL = 0x25;		/*stack = q_c_addr\l_value--*/
	public static final int CPUTQ = 0x26;		/*stack = q_c_addr\q_value--*/
	public static final int CPUTB = 0x27;		/*stack = q_c_addr\b_value--*/
	public static final int GOTO = 0x28;		/*stack = q_c_addr--*/
	public static final int RET = 0x28;		/*stack = q_c_addr--*/
	public static final int GOSUB = 0x29;		/*stack = q_c_addr--q_ret_addr*/
	public static final int GOZ = 0x2A;		/*stack = q_c_addr\w_flag--*/
	
	/* references to locations in the data space */
	public static final int DATA_INSTR = 0x30;
	public static final int GETSW = 0x30;		/*stack = w_d_addr--w_value*/
	public static final int GETSL = 0x31;		/*stack = w_d_addr--l_value*/
	public static final int GETSQ = 0x32;		/*stack = w_d_addr--q_value*/
	public static final int GETSB = 0x33;		/*stack = w_d_addr--b_value*/
	public static final int PUTSW = 0x34;		/*stack = w_d_addr\w_value--*/
	public static final int PUTSL = 0x35;		/*stack = w_d_addr\l_value--*/
	public static final int PUTSQ = 0x36;		/*stack = w_d_addr\q_value--*/
	public static final int PUTSB = 0x37;		/*stack = w_d_addr\b_value--*/
	public static final int GETFW = 0x38;		/*stack = q_d_addr--w_value*/
	public static final int GETFL = 0x39;		/*stack = q_d_addr--l_value*/
	public static final int GETFQ = 0x3A;		/*stack = q_d_addr--q_value*/
	public static final int GETFB = 0x3B;		/*stack = q_d_addr--b_value*/
	public static final int PUTFW = 0x3C;		/*stack = q_d_addr\w_value--*/
	public static final int PUTFL = 0x3D;		/*stack = q_d_addr\l_value--*/
	public static final int PUTFQ = 0x3E;		/*stack = q_d_addr\q_value--*/
	public static final int PUTFB = 0x3F;		/*stack = q_d_addr\b_value--*/
	
	/* references to locations in the frames on the stack */
	public static final int FRAME_INSTR = 0x40;
	public static final int LGETSW = 0x40;		/*stack = w_frame\w_offset--w_value*/
	public static final int LGETSL = 0x41;		/*stack = w_frame\w_offset--l_value*/
	public static final int LGETSQ = 0x42;		/*stack = w_frame\w_offset--q_value*/
	public static final int LGETSB = 0x43;		/*stack = w_frame\w_offset--b_value*/
	public static final int LPUTSW = 0x44;		/*stack = w_frame\w_offset\w_value--*/
	public static final int LPUTSL = 0x45;		/*stack = w_frame\w_offset\l_value--*/
	public static final int LPUTSQ = 0x46;		/*stack = w_frame\w_offset\q_value--*/
	public static final int LPUTSB = 0x47;		/*stack = w_frame\w_offset\b_value--*/
	public static final int LGETFW = 0x48;		/*stack = w_frame\q_offset--w_value*/
	public static final int LGETFL = 0x49;		/*stack = w_frame\q_offset--l_value*/
	public static final int LGETFQ = 0x4A;		/*stack = w_frame\q_offset--q_value*/
	public static final int LGETFB = 0x4B;		/*stack = w_frame\q_offset--b_value*/
	public static final int LPUTFW = 0x4C;		/*stack = w_frame\q_offset\w_value--*/
	public static final int LPUTFL = 0x4D;		/*stack = w_frame\q_offset\l_value--*/
	public static final int LPUTFQ = 0x4E;		/*stack = w_frame\q_offset\q_value--*/
	public static final int LPUTFB = 0x4F;		/*stack = w_frame\q_offset\b_value--*/
	
	/* arithmetic and logical Instructions */
	public static final int MATH_INSTR = 0x50;
	public static final int ADDI = 0x50;		/*stack = int1\int2--int1+int2*/
	public static final int ADDL = 0x51;		/*stack = l_int1\l_int2--l_int1+l_int2*/
	public static final int ADDF = 0x52;		/*stack = float1\float2--float1+float2*/
	public static final int ADDC = 0x53;		/*stack = char1\char2--char1+char2*/
	public static final int SUBI = 0x54;		/*stack = int1\int2--int1-int2*/
	public static final int SUBL = 0x55;		/*stack = l_int1\l_int2--l_int1-l_int2*/
	public static final int SUBF = 0x56;		/*stack = float1\float2--float1-float2*/
	public static final int SUBC = 0x57;		/*stack = char1\char2--char1-char2*/
	public static final int MULI = 0x58;		/*stack = int1\int2--int1*int2*/
	public static final int MULL = 0x59;		/*stack = l_int1\l_int2--l_int1*l_int2*/
	public static final int MULF = 0x5A;		/*stack = float1\float2--float1*float2*/
	public static final int MULC = 0x5B;		/*stack = char1\char2--char1*char2*/
	public static final int DIVI = 0x5C;		/*stack = int1\int2--int1/int2*/
	public static final int DIVL = 0x5D;		/*stack = l_int1\l_int2--l_int1/l_int2*/
	public static final int DIVF = 0x5E;		/*stack = float1\float2--float1/float2*/
	public static final int DIVC = 0x5F;		/*stack = char1\char2--char1/char2*/
	public static final int LOGICAL_INSTR = 0x60;
	public static final int ANDW = 0x60;		/*stack = word1\word2--word1_bit_and_word2*/
	public static final int ANDL = 0x61;		/*stack = long1\long2--long1_bit_and_long2*/
	public static final int ANDQ = 0x62;		/*stack = quad1\quad2--quad1_bit_and_quad2*/
	public static final int ANDB = 0x63;		/*stack = byte1\byte2--byte1_bit_and_byte2*/
	public static final int ORW = 0x64;		/*stack = word1\word2--word1_bit_or_word2*/
	public static final int ORL = 0x65;		/*stack = long1\long2--long1_bit_or_long2*/
	public static final int ORQ = 0x66;		/*stack = quad1\quad2--quad1_bit_or_quad2*/
	public static final int ORB = 0x67;		/*stack = byte1\byte2--byte1_bit_or_byte2*/
	public static final int XORW = 0x68;		/*stack = word1\word2--word1_bit_xor_word2*/
	public static final int XORL = 0x69;		/*stack = long1\long2--long1_bit_xor_long2*/
	public static final int XORQ = 0x6A;		/*stack = quad1\quad2--quad1_bit_xor_quad2*/
	public static final int XORB = 0x6B;		/*stack = byte1\byte2--byte1_bit_xor_byte2*/
	public static final int NOTW = 0x6C;		/*stack = word--bit_not_of_word*/
	public static final int NOTL = 0x6D;		/*stack = long--bit_not_of_long*/
	public static final int NOTQ = 0x6E;		/*stack = quad--bit_not_of_quad*/
	public static final int NOTB = 0x6F;		/*stack = byte--bit_not_of_byte*/
	public static final int SHIFT_TEST = 0x70;
	public static final int CSFTW = 0x70;		/*stack = w_value\amt--w_value*/
	public static final int CSFTL = 0x71;		/*stack = l_value\amt--l_value*/
	public static final int CSFTQ = 0x72;		/*stack = q_value\amt--q_value*/
	public static final int CSFTB = 0x73;		/*stack = b_value\amt--b_value*/
	public static final int ASFTW = 0x74;		/*stack = w_value\amt--w_value*/
	public static final int ASFTL = 0x75;		/*stack = l_value\amt--l_value*/
	public static final int ASFTQ = 0x76;		/*stack = q_value\amt--q_value*/
	public static final int ASFTB = 0x77;		/*stack = b_value\amt--b_value*/
	public static final int TSTEQI = 0x78;		/*stack = int--w_flag*/
	public static final int TSTEQL = 0x79;		/*stack = l_int--w_flag*/
	public static final int TSTEQF = 0x7A;		/*stack = float--w_flag*/
	public static final int TSTEQC = 0x7B;		/*stack = char--w_flag*/
	public static final int TSTLTI = 0x7C;		/*stack = int--w_flag*/
	public static final int TSTLTL = 0x7D;		/*stack = l_int--w_flag*/
	public static final int TSTLTF = 0x7E;		/*stack = float--w_flag*/
	public static final int TSTLTC = 0x7F;		/*stack = char--w_flag*/
	
	/* stack manipulation Instructions */
	public static final int STACK_MANIP = 0x80;
	public static final int DUPW = 0x80;		/*stack = w_value--w_value\w_value*/
	public static final int DUPL = 0x81;		/*stack = l_value--l_value\l_value*/
	public static final int DUPQ = 0x82;		/*stack = q_value--q_value\q_value*/
	public static final int DUPO = 0x83;		/*stack = o_value--o_value\o_value*/
	public static final int SWAPW = 0x84;		/*stack = word1\word2--word2\word1*/
	public static final int SWAPL = 0x85;		/*stack = long1\long2--long2\long1*/
	public static final int SWAPQ = 0x86;		/*stack = quad1\quad2--quad2\quad1*/
	public static final int SWAPO = 0x87;		/*stack = octa1\octa2--octa2\octa1*/
	public static final int CHSPS = 0x88;		/*stack = w_value-- */
	public static final int CHSPF = 0x89;		/*stack = q_value-- */
	public static final int NEWFR = 0x8A;		/*arg = w_frame; stack = --q_ofp <set fp>*/
	public static final int RESFR = 0x8B;		/*arg = w_frame; stack = stuff\q_ofp\local--stuff*/
	
	/* format conversion Instructions */
	public static final int CONVERT = 0x90;
	public static final int ADRD = 0x90;		/*stack = q_d_addr--q_universal_addr*/
	public static final int CVTIL = 0x91;		/*stack = int--long*/
	public static final int CVTIF = 0x92;		/*stack = int--float*/
	public static final int CVTIC = 0x93;		/*stack = int--char*/
	public static final int CVTLI = 0x94;		/*stack = long--int*/
	public static final int ZEXWQ = 0x95;		/*stack = w_value--q_value*/
	public static final int CVTLF = 0x96;		/*stack = long--float*/
	public static final int CVTLC = 0x97;		/*stack = long--char*/
	public static final int CVTFI = 0x98;		/*stack = float--int*/
	public static final int CVTFL = 0x99;		/*stack = float--long*/
	public static final int ADRC = 0x9A;		/*stack = q_c_addr--q_universal_addr*/
	public static final int CVTFC = 0x9B;		/*stack = float--char*/
	public static final int CVTCI = 0x9C;		/*stack = char--int*/
	public static final int CVTCL = 0x9D;		/*stack = char--long*/
	public static final int CVTCF = 0x9E;		/*stack = char--float*/
	public static final int ADRF = 0x9F;		/*stack = w_frame\q_offset--q_universal_addr*/
	
	/* Directives */
	public static final int ENTRY = 1;
	public static final int CODE = 2;
	public static final int DATA = 3;
	
	/* Instruction record in the file "instr.c" */
	String name;
	int value, no_arg, arg_size = -1;
	
	public Instr(String name, int value, int no_arg, int arg_size) {
		this.name = name;
		this.value = value;
		this.no_arg = no_arg;
		this.arg_size = arg_size;
		
	}
	public Instr(String name, int value, int no_arg) {
		this.name = name;
		this.value = value;
		this.no_arg = no_arg;
	}
	
	public static Instr[] getInstructionArray() {
		Instr instr[] = {
		new Instr("HALT",   0x00, 0),
		new Instr("TRACE",  0x01, 0),
		new Instr("SYSCALL",  0x02, 0),
		new Instr("SYSRET",  0x03, 0),
		new Instr("<resvd>",  0x04, 0),
		new Instr("<resvd>",  0x05, 0),
		new Instr("<resvd>",  0x06, 0),
		new Instr("<resvd>",  0x07, 0),
		new Instr("<resvd>",  0x08, 0),
		new Instr("<resvd>",  0x09, 0),
		new Instr("<resvd>",  0x0A, 0),
		new Instr("<resvd>",  0x0B, 0),
		new Instr("<resvd>",  0x0C, 0),
		new Instr("<resvd>",  0x0D, 0),
		new Instr("<resvd>",  0x0E, 0),
		new Instr("<resvd>",  0x0F, 0),
		
		new Instr("RGETW",  0x10, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGETL",  0x11, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGETQ",  0x12, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGETB",  0x13, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RPUTW",  0x14, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RPUTL",  0x15, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RPUTQ",  0x16, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RPUTB",  0x17, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGOTO",  0x18, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGOSUB",  0x19, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RGOZ",   0x1A, 1, 2),  /*arg = w_PC_offset*/
		new Instr("RPUSHA",  0x1B, 1, 2),  /*arg = w_PC_offset*/
		new Instr("PUSHW",  0x1C, 1, 2),  /*arg = w_value*/
		new Instr("PUSHL",  0x1D, 1, 4),  /*arg = l_value*/
		new Instr("PUSHQ",  0x1E, 1, 8),  /*arg = q_value*/
		new Instr("PUSHB",  0x1F, 1, 1),  /*arg = b_value*/
		
		new Instr("CGETW",  0x20, 0),
		new Instr("CGETL",  0x21, 0),
		new Instr("CGETQ",  0x22, 0),
		new Instr("CGETB",  0x23, 0),
		new Instr("CPUTW",  0x24, 0),
		new Instr("CPUTL",  0x25, 0),
		new Instr("CPUTQ",  0x26, 0),
		new Instr("CPUTB",  0x27, 0),
		new Instr("GOTO",   0x28, 0),
		new Instr("GOSUB",  0x29, 0),
		new Instr("GOZ",   0x2A, 0),
		new Instr("<undef>",  0x28, 0),
		new Instr("<undef>",  0x2C, 0),
		new Instr("<undef>",  0x2D, 0),
		new Instr("<undef>",  0x2E, 0),
		new Instr("<undef>",  0x2F, 0),
		
		new Instr("GETSW",  0x30, 0),
		new Instr("GETSL",  0x31, 0),
		new Instr("GETSQ",  0x32, 0),
		new Instr("GETSB",  0x33, 0),
		new Instr("PUTSW",  0x34, 0),
		new Instr("PUTSL",  0x35, 0),
		new Instr("PUTSQ",  0x36, 0),
		new Instr("PUTSB",  0x37, 0),
		new Instr("GETFW",  0x38, 0),
		new Instr("GETFL",  0x39, 0),
		new Instr("GETFQ",  0x3A, 0),
		new Instr("GETFB",  0x3B, 0),
		new Instr("PUTFW",  0x3C, 0),
		new Instr("PUTFL",  0x3D, 0),
		new Instr("PUTFQ",  0x3E, 0),
		new Instr("PUTFB",  0x3F, 0),
		
		new Instr("LGETSW",  0x40, 0),
		new Instr("LGETSL",  0x41, 0),
		new Instr("LGETSQ",  0x42, 0),
		new Instr("LGETSB",  0x43, 0),
		new Instr("LPUTSW",  0x44, 0),
		new Instr("LPUTSL",  0x45, 0),
		new Instr("LPUTSQ",  0x46, 0),
		new Instr("LPUTSB",  0x47, 0),
		new Instr("LGETFW",  0x48, 0),
		new Instr("LGETFL",  0x49, 0),
		new Instr("LGETFQ",  0x4A, 0),
		new Instr("LGETFB",  0x4B, 0),
		new Instr("LPUTFW",  0x4C, 0),
		new Instr("LPUTFL",  0x4D, 0),
		new Instr("LPUTFQ",  0x4E, 0),
		new Instr("LPUTFB",  0x4F, 0),
		
		new Instr("ADDI",   0x50, 0),
		new Instr("ADDL",   0x51, 0),
		new Instr("ADDF",   0x52, 0),
		new Instr("ADDC",   0x53, 0),
		new Instr("SUBI",   0x54, 0),
		new Instr("SUBL",   0x55, 0),
		new Instr("SUBF",   0x56, 0),
		new Instr("SUBC",   0x57, 0),
		new Instr("MULI",   0x58, 0),
		new Instr("MULL",   0x59, 0),
		new Instr("MULF",   0x5A, 0),
		new Instr("MULC",   0x5B, 0),
		new Instr("DIVI",   0x5C, 0),
		new Instr("DIVL",   0x5D, 0),
		new Instr("DIVF",   0x5E, 0),
		new Instr("DIVC",   0x5F, 0),
		new Instr("ANDW",   0x60, 0),
		new Instr("ANDL",   0x61, 0),
		new Instr("ANDQ",   0x62, 0),
		new Instr("ANDB",   0x63, 0),
		new Instr("ORW",   0x64, 0),
		new Instr("ORL",   0x65, 0),
		new Instr("ORQ",   0x66, 0),
		new Instr("ORB",   0x67, 0),
		new Instr("XORW",   0x68, 0),
		new Instr("XORL",   0x69, 0),
		new Instr("XORQ",   0x6A, 0),
		new Instr("XORB",   0x6B, 0),
		new Instr("NOTW",   0x6C, 0),
		new Instr("NOTL",   0x6D, 0),
		new Instr("NOTQ",   0x6E, 0),
		new Instr("NOTB",   0x6F, 0),
		new Instr("CSFTW",  0x70, 0),
		new Instr("CSFTL",  0x71, 0),
		new Instr("CSFTQ",  0x72, 0),
		new Instr("CSFTB",  0x73, 0),
		new Instr("ASFTW",  0x74, 0),
		new Instr("ASFTL",  0x75, 0),
		new Instr("ASFTQ",  0x76, 0),
		new Instr("ASFTB",  0x77, 0),
		new Instr("TSTEQI",  0x78, 0),
		new Instr("TSTEQL",  0x79, 0),
		new Instr("TSTEQF",  0x7A, 0),
		new Instr("TSTEQC",  0x7B, 0),
		new Instr("TSTLTI",  0x7C, 0),
		new Instr("TSTLTL",  0x7D, 0),
		new Instr("TSTLTF",  0x7E, 0),
		new Instr("TSTLTC",  0x7F, 0),
		
		new Instr("DUPW",   0x80, 0),
		new Instr("DUPL",   0x81, 0),
		new Instr("DUPQ",   0x82, 0),
		new Instr("DUPO",   0x83, 0),
		new Instr("SWAPW",  0x84, 0),
		new Instr("SWAPL",  0x85, 0),
		new Instr("SWAPQ",  0x86, 0),
		new Instr("SWAPO",  0x87, 0),
		new Instr("CHSPS",  0x88, 0),
		new Instr("CHSPF",  0x89, 0),
		new Instr("NEWFR",  0x8A, 1, 2),  /*arg = w_frame*/
		new Instr("RESFR",  0x8B, 1, 2),  /*arg = w_frame*/
		new Instr("<resvd>",  0x8C, 0),
		new Instr("<resvd>",  0x8D, 0),
		new Instr("<resvd>",  0x8E, 0),
		new Instr("<resvd>",  0x8F, 0),
		new Instr("ADRD",   0x90, 0),
		new Instr("CVTIL",  0x91, 0),
		new Instr("CVTIF",  0x92, 0),
		new Instr("CVTIC",  0x93, 0),
		new Instr("CVTLI",  0x94, 0),
		new Instr("ZEXWQ",  0x95, 0),
		new Instr("CVTLF",  0x96, 0),
		new Instr("CVTLC",  0x97, 0),
		new Instr("CVTFI",  0x98, 0),
		new Instr("CVTFL",  0x99, 0),
		new Instr("ADRC",   0x9A, 0),
		new Instr("CVTFC",  0x9B, 0),
		new Instr("CVTCI",  0x9C, 0),
		new Instr("CVTCL",  0x9D, 0),
		new Instr("CVTCF",  0x9E, 0),    
		new Instr("ADRF",  0x9F, 0)
	};
	return instr;
}
}
