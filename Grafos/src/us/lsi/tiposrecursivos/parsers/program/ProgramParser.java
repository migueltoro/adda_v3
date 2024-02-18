// Generated from Program.g4 by ANTLR 4.9.3
package us.lsi.tiposrecursivos.parsers.program;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProgramParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, BOOL=33, TYPE=34, INT=35, DOUBLE=36, ID=37, STR=38, WS=39;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_formal_parameters = 2, RULE_formal_parameter = 3, 
		RULE_sentence = 4, RULE_block = 5, RULE_exp = 6, RULE_real_parameters = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "formal_parameters", "formal_parameter", "sentence", 
			"block", "exp", "real_parameters"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Program'", "'Block'", "':'", "'='", "'('", "')'", "','", "'if'", 
			"'else'", "'while'", "'return'", "'{'", "'}'", "'int'", "'double'", "'str'", 
			"'bool'", "'sqrt'", "'+'", "'-'", "'!'", "'*'", "'/'", "'%'", "'<='", 
			"'<'", "'>='", "'>'", "'!='", "'&&'", "'||'", "'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "BOOL", "TYPE", 
			"INT", "DOUBLE", "ID", "STR", "WS"
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
	public String getGrammarFileName() { return "Program.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ProgramParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public Formal_parametersContext formal_parameters() {
			return getRuleContext(Formal_parametersContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitProgram(this);
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
			setState(16);
			match(T__0);
			setState(17);
			formal_parameters();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(18);
				declaration();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			match(T__1);
			setState(25);
			block();
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

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FunDeclarationContext extends DeclarationContext {
		public Token id;
		public Token type;
		public BlockContext body;
		public Formal_parametersContext formal_parameters() {
			return getRuleContext(Formal_parametersContext.class,0);
		}
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(ProgramParser.TYPE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitFunDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarDeclarationContext extends DeclarationContext {
		public Token id;
		public Token type;
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(ProgramParser.TYPE, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitVarDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		int _la;
		try {
			setState(40);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new VarDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(27);
				((VarDeclarationContext)_localctx).id = match(ID);
				setState(28);
				match(T__2);
				setState(29);
				((VarDeclarationContext)_localctx).type = match(TYPE);
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(30);
					match(T__3);
					setState(31);
					exp(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new FunDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				((FunDeclarationContext)_localctx).id = match(ID);
				setState(35);
				formal_parameters();
				setState(36);
				match(T__2);
				setState(37);
				((FunDeclarationContext)_localctx).type = match(TYPE);
				setState(38);
				((FunDeclarationContext)_localctx).body = block();
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

	public static class Formal_parametersContext extends ParserRuleContext {
		public Formal_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal_parameters; }
	 
		public Formal_parametersContext() { }
		public void copyFrom(Formal_parametersContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Formal_parameter_emptyContext extends Formal_parametersContext {
		public Formal_parameter_emptyContext(Formal_parametersContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitFormal_parameter_empty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Formal_parameter_not_emptyContext extends Formal_parametersContext {
		public List<Formal_parameterContext> formal_parameter() {
			return getRuleContexts(Formal_parameterContext.class);
		}
		public Formal_parameterContext formal_parameter(int i) {
			return getRuleContext(Formal_parameterContext.class,i);
		}
		public Formal_parameter_not_emptyContext(Formal_parametersContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitFormal_parameter_not_empty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formal_parametersContext formal_parameters() throws RecognitionException {
		Formal_parametersContext _localctx = new Formal_parametersContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_formal_parameters);
		int _la;
		try {
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new Formal_parameter_emptyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				match(T__4);
				setState(43);
				match(T__5);
				}
				break;
			case 2:
				_localctx = new Formal_parameter_not_emptyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(T__4);
				setState(45);
				formal_parameter();
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(46);
					match(T__6);
					setState(47);
					formal_parameter();
					}
					}
					setState(52);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(53);
				match(T__5);
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

	public static class Formal_parameterContext extends ParserRuleContext {
		public Token id;
		public Token type;
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(ProgramParser.TYPE, 0); }
		public Formal_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitFormal_parameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formal_parameterContext formal_parameter() throws RecognitionException {
		Formal_parameterContext _localctx = new Formal_parameterContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_formal_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			((Formal_parameterContext)_localctx).id = match(ID);
			setState(58);
			match(T__2);
			setState(59);
			((Formal_parameterContext)_localctx).type = match(TYPE);
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

	public static class SentenceContext extends ParserRuleContext {
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
	 
		public SentenceContext() { }
		public void copyFrom(SentenceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IfSentenceContext extends SentenceContext {
		public BlockContext trueBlock;
		public BlockContext elseBlock;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfSentenceContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitIfSentence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AsignSentenceContext extends SentenceContext {
		public Token id;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public AsignSentenceContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitAsignSentence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnSentenceContext extends SentenceContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ReturnSentenceContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitReturnSentence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileSentenceContext extends SentenceContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileSentenceContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitWhileSentence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_sentence);
		int _la;
		try {
			setState(81);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new AsignSentenceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				((AsignSentenceContext)_localctx).id = match(ID);
				setState(62);
				match(T__3);
				setState(63);
				exp(0);
				}
				break;
			case T__7:
				_localctx = new IfSentenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				match(T__7);
				setState(65);
				match(T__4);
				setState(66);
				exp(0);
				setState(67);
				match(T__5);
				setState(68);
				((IfSentenceContext)_localctx).trueBlock = block();
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8) {
					{
					setState(69);
					match(T__8);
					setState(70);
					((IfSentenceContext)_localctx).elseBlock = block();
					}
				}

				}
				break;
			case T__9:
				_localctx = new WhileSentenceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(73);
				match(T__9);
				setState(74);
				match(T__4);
				setState(75);
				exp(0);
				setState(76);
				match(T__5);
				setState(77);
				block();
				}
				break;
			case T__10:
				_localctx = new ReturnSentenceContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(79);
				match(T__10);
				setState(80);
				exp(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class BlockContext extends ParserRuleContext {
		public DeclarationContext declarations;
		public SentenceContext sentences;
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(T__11);
			setState(87);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(84);
					((BlockContext)_localctx).declarations = declaration();
					}
					} 
				}
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(91); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(90);
				((BlockContext)_localctx).sentences = sentence();
				}
				}
				setState(93); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << ID))) != 0) );
			setState(95);
			match(T__12);
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

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StrExprContext extends ExpContext {
		public TerminalNode STR() { return getToken(ProgramParser.STR, 0); }
		public StrExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitStrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExprContext extends ExpContext {
		public Token op;
		public ExpContext right;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public UnaryExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntExprContext extends ExpContext {
		public TerminalNode INT() { return getToken(ProgramParser.INT, 0); }
		public IntExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitIntExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TernaryExprContext extends ExpContext {
		public ExpContext guard;
		public ExpContext left;
		public ExpContext right;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TernaryExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitTernaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExprContext extends ExpContext {
		public ExpContext left;
		public Token op;
		public ExpContext right;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public BinaryExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallExprContext extends ExpContext {
		public Token name;
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public Real_parametersContext real_parameters() {
			return getRuleContext(Real_parametersContext.class,0);
		}
		public CallExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprContext extends ExpContext {
		public TerminalNode BOOL() { return getToken(ProgramParser.BOOL, 0); }
		public BoolExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ParenExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoubleExpContext extends ExpContext {
		public TerminalNode DOUBLE() { return getToken(ProgramParser.DOUBLE, 0); }
		public DoubleExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitDoubleExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdExprContext extends ExpContext {
		public Token id;
		public TerminalNode ID() { return getToken(ProgramParser.ID, 0); }
		public IdExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitIdExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(98);
				((UnaryExprContext)_localctx).op = match(T__13);
				setState(99);
				match(T__4);
				setState(100);
				((UnaryExprContext)_localctx).right = exp(0);
				setState(101);
				match(T__5);
				}
				break;
			case 2:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(103);
				((UnaryExprContext)_localctx).op = match(T__14);
				setState(104);
				match(T__4);
				setState(105);
				((UnaryExprContext)_localctx).right = exp(0);
				setState(106);
				match(T__5);
				}
				break;
			case 3:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				((UnaryExprContext)_localctx).op = match(T__15);
				setState(109);
				match(T__4);
				setState(110);
				((UnaryExprContext)_localctx).right = exp(0);
				setState(111);
				match(T__5);
				}
				break;
			case 4:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113);
				((UnaryExprContext)_localctx).op = match(T__16);
				setState(114);
				match(T__4);
				setState(115);
				((UnaryExprContext)_localctx).right = exp(0);
				setState(116);
				match(T__5);
				}
				break;
			case 5:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				((UnaryExprContext)_localctx).op = match(T__17);
				setState(119);
				match(T__4);
				setState(120);
				((UnaryExprContext)_localctx).right = exp(0);
				setState(121);
				match(T__5);
				}
				break;
			case 6:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << T__20))) != 0)) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(124);
				((UnaryExprContext)_localctx).right = exp(14);
				}
				break;
			case 7:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				match(T__4);
				setState(126);
				exp(0);
				setState(127);
				match(T__5);
				}
				break;
			case 8:
				{
				_localctx = new CallExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(129);
				((CallExprContext)_localctx).name = match(ID);
				setState(130);
				match(T__4);
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << BOOL) | (1L << INT) | (1L << DOUBLE) | (1L << ID) | (1L << STR))) != 0)) {
					{
					setState(131);
					real_parameters();
					}
				}

				setState(134);
				match(T__5);
				}
				break;
			case 9:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				((IdExprContext)_localctx).id = match(ID);
				}
				break;
			case 10:
				{
				_localctx = new DoubleExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(136);
				match(DOUBLE);
				}
				break;
			case 11:
				{
				_localctx = new IntExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(137);
				match(INT);
				}
				break;
			case 12:
				{
				_localctx = new StrExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(138);
				match(STR);
				}
				break;
			case 13:
				{
				_localctx = new BoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				match(BOOL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(163);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(142);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(143);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(144);
						((BinaryExprContext)_localctx).right = exp(14);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(145);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(146);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__18 || _la==T__19) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(147);
						((BinaryExprContext)_localctx).right = exp(13);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(148);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(149);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(150);
						((BinaryExprContext)_localctx).right = exp(12);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(151);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(152);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__3 || _la==T__28) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(153);
						((BinaryExprContext)_localctx).right = exp(11);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(154);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(155);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__29 || _la==T__30) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(156);
						((BinaryExprContext)_localctx).right = exp(10);
						}
						break;
					case 6:
						{
						_localctx = new TernaryExprContext(new ExpContext(_parentctx, _parentState));
						((TernaryExprContext)_localctx).guard = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(157);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(158);
						match(T__31);
						setState(159);
						((TernaryExprContext)_localctx).left = exp(0);
						setState(160);
						match(T__2);
						setState(161);
						((TernaryExprContext)_localctx).right = exp(9);
						}
						break;
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Real_parametersContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public Real_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitReal_parameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_parametersContext real_parameters() throws RecognitionException {
		Real_parametersContext _localctx = new Real_parametersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_real_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			exp(0);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(169);
				match(T__6);
				setState(170);
				exp(0);
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 13);
		case 1:
			return precpred(_ctx, 12);
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3)\u00b3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\7\2"+
		"\26\n\2\f\2\16\2\31\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3#\n\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\5\3+\n\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4\63\n\4\f\4\16"+
		"\4\66\13\4\3\4\3\4\5\4:\n\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6J\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6T\n\6\3\7\3"+
		"\7\7\7X\n\7\f\7\16\7[\13\7\3\7\6\7^\n\7\r\7\16\7_\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0087\n"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008f\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00a6\n\b\f"+
		"\b\16\b\u00a9\13\b\3\t\3\t\3\t\7\t\u00ae\n\t\f\t\16\t\u00b1\13\t\3\t\2"+
		"\3\16\n\2\4\6\b\n\f\16\20\2\b\3\2\25\27\3\2\30\32\3\2\25\26\3\2\33\36"+
		"\4\2\6\6\37\37\3\2 !\2\u00c9\2\22\3\2\2\2\4*\3\2\2\2\69\3\2\2\2\b;\3\2"+
		"\2\2\nS\3\2\2\2\fU\3\2\2\2\16\u008e\3\2\2\2\20\u00aa\3\2\2\2\22\23\7\3"+
		"\2\2\23\27\5\6\4\2\24\26\5\4\3\2\25\24\3\2\2\2\26\31\3\2\2\2\27\25\3\2"+
		"\2\2\27\30\3\2\2\2\30\32\3\2\2\2\31\27\3\2\2\2\32\33\7\4\2\2\33\34\5\f"+
		"\7\2\34\3\3\2\2\2\35\36\7\'\2\2\36\37\7\5\2\2\37\"\7$\2\2 !\7\6\2\2!#"+
		"\5\16\b\2\" \3\2\2\2\"#\3\2\2\2#+\3\2\2\2$%\7\'\2\2%&\5\6\4\2&\'\7\5\2"+
		"\2\'(\7$\2\2()\5\f\7\2)+\3\2\2\2*\35\3\2\2\2*$\3\2\2\2+\5\3\2\2\2,-\7"+
		"\7\2\2-:\7\b\2\2./\7\7\2\2/\64\5\b\5\2\60\61\7\t\2\2\61\63\5\b\5\2\62"+
		"\60\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66"+
		"\64\3\2\2\2\678\7\b\2\28:\3\2\2\29,\3\2\2\29.\3\2\2\2:\7\3\2\2\2;<\7\'"+
		"\2\2<=\7\5\2\2=>\7$\2\2>\t\3\2\2\2?@\7\'\2\2@A\7\6\2\2AT\5\16\b\2BC\7"+
		"\n\2\2CD\7\7\2\2DE\5\16\b\2EF\7\b\2\2FI\5\f\7\2GH\7\13\2\2HJ\5\f\7\2I"+
		"G\3\2\2\2IJ\3\2\2\2JT\3\2\2\2KL\7\f\2\2LM\7\7\2\2MN\5\16\b\2NO\7\b\2\2"+
		"OP\5\f\7\2PT\3\2\2\2QR\7\r\2\2RT\5\16\b\2S?\3\2\2\2SB\3\2\2\2SK\3\2\2"+
		"\2SQ\3\2\2\2T\13\3\2\2\2UY\7\16\2\2VX\5\4\3\2WV\3\2\2\2X[\3\2\2\2YW\3"+
		"\2\2\2YZ\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2\\^\5\n\6\2]\\\3\2\2\2^_\3\2\2\2_"+
		"]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\7\17\2\2b\r\3\2\2\2cd\b\b\1\2de\7\20\2"+
		"\2ef\7\7\2\2fg\5\16\b\2gh\7\b\2\2h\u008f\3\2\2\2ij\7\21\2\2jk\7\7\2\2"+
		"kl\5\16\b\2lm\7\b\2\2m\u008f\3\2\2\2no\7\22\2\2op\7\7\2\2pq\5\16\b\2q"+
		"r\7\b\2\2r\u008f\3\2\2\2st\7\23\2\2tu\7\7\2\2uv\5\16\b\2vw\7\b\2\2w\u008f"+
		"\3\2\2\2xy\7\24\2\2yz\7\7\2\2z{\5\16\b\2{|\7\b\2\2|\u008f\3\2\2\2}~\t"+
		"\2\2\2~\u008f\5\16\b\20\177\u0080\7\7\2\2\u0080\u0081\5\16\b\2\u0081\u0082"+
		"\7\b\2\2\u0082\u008f\3\2\2\2\u0083\u0084\7\'\2\2\u0084\u0086\7\7\2\2\u0085"+
		"\u0087\5\20\t\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3"+
		"\2\2\2\u0088\u008f\7\b\2\2\u0089\u008f\7\'\2\2\u008a\u008f\7&\2\2\u008b"+
		"\u008f\7%\2\2\u008c\u008f\7(\2\2\u008d\u008f\7#\2\2\u008ec\3\2\2\2\u008e"+
		"i\3\2\2\2\u008en\3\2\2\2\u008es\3\2\2\2\u008ex\3\2\2\2\u008e}\3\2\2\2"+
		"\u008e\177\3\2\2\2\u008e\u0083\3\2\2\2\u008e\u0089\3\2\2\2\u008e\u008a"+
		"\3\2\2\2\u008e\u008b\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008d\3\2\2\2\u008f"+
		"\u00a7\3\2\2\2\u0090\u0091\f\17\2\2\u0091\u0092\t\3\2\2\u0092\u00a6\5"+
		"\16\b\20\u0093\u0094\f\16\2\2\u0094\u0095\t\4\2\2\u0095\u00a6\5\16\b\17"+
		"\u0096\u0097\f\r\2\2\u0097\u0098\t\5\2\2\u0098\u00a6\5\16\b\16\u0099\u009a"+
		"\f\f\2\2\u009a\u009b\t\6\2\2\u009b\u00a6\5\16\b\r\u009c\u009d\f\13\2\2"+
		"\u009d\u009e\t\7\2\2\u009e\u00a6\5\16\b\f\u009f\u00a0\f\n\2\2\u00a0\u00a1"+
		"\7\"\2\2\u00a1\u00a2\5\16\b\2\u00a2\u00a3\7\5\2\2\u00a3\u00a4\5\16\b\13"+
		"\u00a4\u00a6\3\2\2\2\u00a5\u0090\3\2\2\2\u00a5\u0093\3\2\2\2\u00a5\u0096"+
		"\3\2\2\2\u00a5\u0099\3\2\2\2\u00a5\u009c\3\2\2\2\u00a5\u009f\3\2\2\2\u00a6"+
		"\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\17\3\2\2"+
		"\2\u00a9\u00a7\3\2\2\2\u00aa\u00af\5\16\b\2\u00ab\u00ac\7\t\2\2\u00ac"+
		"\u00ae\5\16\b\2\u00ad\u00ab\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3"+
		"\2\2\2\u00af\u00b0\3\2\2\2\u00b0\21\3\2\2\2\u00b1\u00af\3\2\2\2\20\27"+
		"\"*\649ISY_\u0086\u008e\u00a5\u00a7\u00af";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}