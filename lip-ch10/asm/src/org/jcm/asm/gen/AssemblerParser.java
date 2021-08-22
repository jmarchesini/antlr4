// Generated from /home/jmarchesini/dev/src/github/antlr4/lip-ch10/asm/src/org/jcm/asm/Assembler.g4 by ANTLR 4.9.1
package org.jcm.asm.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AssemblerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, REG=8, ID=9, FUNC=10, 
		INT=11, CHAR=12, STRING=13, FLOAT=14, WS=15, NEWLINE=16;
	public static final int
		RULE_program = 0, RULE_globals = 1, RULE_functionDeclaration = 2, RULE_instr = 3, 
		RULE_operand = 4, RULE_label = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "globals", "functionDeclaration", "instr", "operand", "label"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'.globals'", "'.def'", "':'", "'args'", "'='", "','", "'locals'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "REG", "ID", "FUNC", 
			"INT", "CHAR", "STRING", "FLOAT", "WS", "NEWLINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Assembler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    protected void gen(Token instrToken) {;}
	    protected void gen(Token instrToken, Token operandToken) {;}
	    protected void gen(Token instrToken, Token oToken1, Token oToken2) {;}
	    protected void gen(Token instrToken, Token oToken1, Token oToken2, Token oToken3) {;}
	    protected void checkForUnresolvedReferences() {;}
	    protected void defineFunction(Token idToken, int nargs, int nlocals) {;}
	    protected void defineDataSize(int n) {;}
	    protected void defineLabel(Token idToken) {;}

	public AssemblerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public GlobalsContext globals() {
			return getRuleContext(GlobalsContext.class,0);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public List<InstrContext> instr() {
			return getRuleContexts(InstrContext.class);
		}
		public InstrContext instr(int i) {
			return getRuleContext(InstrContext.class,i);
		}
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(AssemblerParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(AssemblerParser.NEWLINE, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(12);
				globals();
				}
				break;
			}
			setState(19); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(19);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(15);
					functionDeclaration();
					}
					break;
				case 2:
					{
					setState(16);
					instr();
					}
					break;
				case 3:
					{
					setState(17);
					label();
					}
					break;
				case 4:
					{
					setState(18);
					match(NEWLINE);
					}
					break;
				}
				}
				setState(21); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << ID) | (1L << NEWLINE))) != 0) );
			 checkForUnresolvedReferences(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalsContext extends ParserRuleContext {
		public Token INT;
		public TerminalNode INT() { return getToken(AssemblerParser.INT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(AssemblerParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(AssemblerParser.NEWLINE, i);
		}
		public GlobalsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globals; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterGlobals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitGlobals(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitGlobals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalsContext globals() throws RecognitionException {
		GlobalsContext _localctx = new GlobalsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_globals);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(25);
				match(NEWLINE);
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(T__0);
			setState(32);
			((GlobalsContext)_localctx).INT = match(INT);
			setState(33);
			match(NEWLINE);
			 defineDataSize((((GlobalsContext)_localctx).INT!=null?Integer.valueOf(((GlobalsContext)_localctx).INT.getText()):0)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public Token name;
		public Token a;
		public Token n;
		public TerminalNode NEWLINE() { return getToken(AssemblerParser.NEWLINE, 0); }
		public TerminalNode ID() { return getToken(AssemblerParser.ID, 0); }
		public List<TerminalNode> INT() { return getTokens(AssemblerParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(AssemblerParser.INT, i);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functionDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(T__1);
			setState(37);
			((FunctionDeclarationContext)_localctx).name = match(ID);
			setState(38);
			match(T__2);
			setState(39);
			match(T__3);
			setState(40);
			match(T__4);
			setState(41);
			((FunctionDeclarationContext)_localctx).a = match(INT);
			setState(42);
			match(T__5);
			setState(43);
			match(T__6);
			setState(44);
			match(T__4);
			setState(45);
			((FunctionDeclarationContext)_localctx).n = match(INT);
			setState(46);
			match(NEWLINE);
			 defineFunction(((FunctionDeclarationContext)_localctx).name, (((FunctionDeclarationContext)_localctx).a!=null?Integer.valueOf(((FunctionDeclarationContext)_localctx).a.getText()):0), (((FunctionDeclarationContext)_localctx).n!=null?Integer.valueOf(((FunctionDeclarationContext)_localctx).n.getText()):0)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstrContext extends ParserRuleContext {
		public Token ID;
		public OperandContext operand;
		public OperandContext a;
		public OperandContext b;
		public OperandContext c;
		public TerminalNode ID() { return getToken(AssemblerParser.ID, 0); }
		public TerminalNode NEWLINE() { return getToken(AssemblerParser.NEWLINE, 0); }
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public InstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterInstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitInstr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitInstr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstrContext instr() throws RecognitionException {
		InstrContext _localctx = new InstrContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_instr);
		try {
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				((InstrContext)_localctx).ID = match(ID);
				setState(50);
				match(NEWLINE);
				 gen(((InstrContext)_localctx).ID); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				((InstrContext)_localctx).ID = match(ID);
				setState(53);
				((InstrContext)_localctx).operand = operand();
				setState(54);
				match(NEWLINE);
				 gen(((InstrContext)_localctx).ID,(((InstrContext)_localctx).operand!=null?(((InstrContext)_localctx).operand.start):null)); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				((InstrContext)_localctx).ID = match(ID);
				setState(58);
				((InstrContext)_localctx).a = operand();
				setState(59);
				match(T__5);
				setState(60);
				((InstrContext)_localctx).b = operand();
				setState(61);
				match(NEWLINE);
				 gen(((InstrContext)_localctx).ID,(((InstrContext)_localctx).a!=null?(((InstrContext)_localctx).a.start):null),(((InstrContext)_localctx).b!=null?(((InstrContext)_localctx).b.start):null)); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(64);
				((InstrContext)_localctx).ID = match(ID);
				setState(65);
				((InstrContext)_localctx).a = operand();
				setState(66);
				match(T__5);
				setState(67);
				((InstrContext)_localctx).b = operand();
				setState(68);
				match(T__5);
				setState(69);
				((InstrContext)_localctx).c = operand();
				setState(70);
				match(NEWLINE);
				 gen(((InstrContext)_localctx).ID,(((InstrContext)_localctx).a!=null?(((InstrContext)_localctx).a.start):null),(((InstrContext)_localctx).b!=null?(((InstrContext)_localctx).b.start):null),(((InstrContext)_localctx).c!=null?(((InstrContext)_localctx).c.start):null)); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AssemblerParser.ID, 0); }
		public TerminalNode REG() { return getToken(AssemblerParser.REG, 0); }
		public TerminalNode FUNC() { return getToken(AssemblerParser.FUNC, 0); }
		public TerminalNode INT() { return getToken(AssemblerParser.INT, 0); }
		public TerminalNode CHAR() { return getToken(AssemblerParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(AssemblerParser.STRING, 0); }
		public TerminalNode FLOAT() { return getToken(AssemblerParser.FLOAT, 0); }
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterOperand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitOperand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << REG) | (1L << ID) | (1L << FUNC) | (1L << INT) | (1L << CHAR) | (1L << STRING) | (1L << FLOAT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public Token ID;
		public TerminalNode ID() { return getToken(AssemblerParser.ID, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssemblerListener ) ((AssemblerListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssemblerVisitor ) return ((AssemblerVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			((LabelContext)_localctx).ID = match(ID);
			setState(78);
			match(T__2);
			 defineLabel(((LabelContext)_localctx).ID); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22T\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\5\2\20\n\2\3\2\3\2\3\2\3\2\6\2"+
		"\26\n\2\r\2\16\2\27\3\2\3\2\3\3\7\3\35\n\3\f\3\16\3 \13\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\5\5L\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\2\2\b\2\4\6\b\n"+
		"\f\2\3\3\2\n\20\2V\2\17\3\2\2\2\4\36\3\2\2\2\6&\3\2\2\2\bK\3\2\2\2\nM"+
		"\3\2\2\2\fO\3\2\2\2\16\20\5\4\3\2\17\16\3\2\2\2\17\20\3\2\2\2\20\25\3"+
		"\2\2\2\21\26\5\6\4\2\22\26\5\b\5\2\23\26\5\f\7\2\24\26\7\22\2\2\25\21"+
		"\3\2\2\2\25\22\3\2\2\2\25\23\3\2\2\2\25\24\3\2\2\2\26\27\3\2\2\2\27\25"+
		"\3\2\2\2\27\30\3\2\2\2\30\31\3\2\2\2\31\32\b\2\1\2\32\3\3\2\2\2\33\35"+
		"\7\22\2\2\34\33\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37!\3"+
		"\2\2\2 \36\3\2\2\2!\"\7\3\2\2\"#\7\r\2\2#$\7\22\2\2$%\b\3\1\2%\5\3\2\2"+
		"\2&\'\7\4\2\2\'(\7\13\2\2()\7\5\2\2)*\7\6\2\2*+\7\7\2\2+,\7\r\2\2,-\7"+
		"\b\2\2-.\7\t\2\2./\7\7\2\2/\60\7\r\2\2\60\61\7\22\2\2\61\62\b\4\1\2\62"+
		"\7\3\2\2\2\63\64\7\13\2\2\64\65\7\22\2\2\65L\b\5\1\2\66\67\7\13\2\2\67"+
		"8\5\n\6\289\7\22\2\29:\b\5\1\2:L\3\2\2\2;<\7\13\2\2<=\5\n\6\2=>\7\b\2"+
		"\2>?\5\n\6\2?@\7\22\2\2@A\b\5\1\2AL\3\2\2\2BC\7\13\2\2CD\5\n\6\2DE\7\b"+
		"\2\2EF\5\n\6\2FG\7\b\2\2GH\5\n\6\2HI\7\22\2\2IJ\b\5\1\2JL\3\2\2\2K\63"+
		"\3\2\2\2K\66\3\2\2\2K;\3\2\2\2KB\3\2\2\2L\t\3\2\2\2MN\t\2\2\2N\13\3\2"+
		"\2\2OP\7\13\2\2PQ\7\5\2\2QR\b\7\1\2R\r\3\2\2\2\7\17\25\27\36K";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}