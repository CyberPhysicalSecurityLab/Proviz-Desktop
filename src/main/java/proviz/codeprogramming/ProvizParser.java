package proviz.codeprogramming;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProvizParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, PINDATATRANSMISSION=15, 
		PINTYPE=16, SENSOR=17, SENSORNAME=18, FEATURE=19, SENSORMAKE=20, PINNAME=21, 
		STARTSWITHCAPITAL=22, UPPERCASESTRING=23, LOWERCASESTRING=24, INTEGER=25, 
		WS=26, COMMENT=27;
	public static final int
		RULE_pinTypeSelection = 0, RULE_pinSelection = 1, RULE_pinDefinition = 2, 
		RULE_pinConfiguration = 3, RULE_pinConfigurationBlock = 4, RULE_entireCode = 5, 
		RULE_mainMethod = 6, RULE_block = 7, RULE_blockstatements = 8, RULE_statement = 9, 
		RULE_sensorCreate = 10, RULE_sensorPinBind = 11, RULE_sensorDetailChange = 12, 
		RULE_mainMethodDeclaration = 13;
	public static final String[] ruleNames = {
		"pinTypeSelection", "pinSelection", "pinDefinition", "pinConfiguration", 
		"pinConfigurationBlock", "entireCode", "mainMethod", "block", "blockstatements", 
		"statement", "sensorCreate", "sensorPinBind", "sensorDetailChange", "mainMethodDeclaration"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "';'", "'Pin'", "'new'", "'('", "');'", "'void Pinconfiguration ()'", 
		"'{'", "'}'", "'()'", "'.add('", "'.'", "'set'", "'void Main()'", null, 
		null, "'sensor'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "PINDATATRANSMISSION", "PINTYPE", "SENSOR", "SENSORNAME", 
		"FEATURE", "SENSORMAKE", "PINNAME", "STARTSWITHCAPITAL", "UPPERCASESTRING", 
		"LOWERCASESTRING", "INTEGER", "WS", "COMMENT"
	};
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
	public String getGrammarFileName() { return "Proviz.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ProvizParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PinTypeSelectionContext extends ParserRuleContext {
		public TerminalNode PINNAME() { return getToken(ProvizParser.PINNAME, 0); }
		public TerminalNode PINDATATRANSMISSION() { return getToken(ProvizParser.PINDATATRANSMISSION, 0); }
		public PinTypeSelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pinTypeSelection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterPinTypeSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitPinTypeSelection(this);
		}
	}

	public final PinTypeSelectionContext pinTypeSelection() throws RecognitionException {
		PinTypeSelectionContext _localctx = new PinTypeSelectionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pinTypeSelection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(PINNAME);
			setState(29);
			match(T__0);
			setState(30);
			match(PINDATATRANSMISSION);
			setState(31);
			match(T__1);
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

	public static class PinSelectionContext extends ParserRuleContext {
		public TerminalNode PINNAME() { return getToken(ProvizParser.PINNAME, 0); }
		public TerminalNode PINTYPE() { return getToken(ProvizParser.PINTYPE, 0); }
		public TerminalNode INTEGER() { return getToken(ProvizParser.INTEGER, 0); }
		public PinSelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pinSelection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterPinSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitPinSelection(this);
		}
	}

	public final PinSelectionContext pinSelection() throws RecognitionException {
		PinSelectionContext _localctx = new PinSelectionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_pinSelection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(T__2);
			setState(34);
			match(PINNAME);
			setState(35);
			match(T__0);
			setState(36);
			match(T__3);
			setState(37);
			match(PINTYPE);
			setState(38);
			match(T__4);
			setState(39);
			match(INTEGER);
			setState(40);
			match(T__5);
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

	public static class PinDefinitionContext extends ParserRuleContext {
		public List<PinSelectionContext> pinSelection() {
			return getRuleContexts(PinSelectionContext.class);
		}
		public PinSelectionContext pinSelection(int i) {
			return getRuleContext(PinSelectionContext.class,i);
		}
		public PinDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pinDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterPinDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitPinDefinition(this);
		}
	}

	public final PinDefinitionContext pinDefinition() throws RecognitionException {
		PinDefinitionContext _localctx = new PinDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_pinDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				pinSelection();
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__2 );
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

	public static class PinConfigurationContext extends ParserRuleContext {
		public PinConfigurationBlockContext pinConfigurationBlock() {
			return getRuleContext(PinConfigurationBlockContext.class,0);
		}
		public PinConfigurationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pinConfiguration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterPinConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitPinConfiguration(this);
		}
	}

	public final PinConfigurationContext pinConfiguration() throws RecognitionException {
		PinConfigurationContext _localctx = new PinConfigurationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_pinConfiguration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__6);
			setState(48);
			pinConfigurationBlock();
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

	public static class PinConfigurationBlockContext extends ParserRuleContext {
		public List<PinTypeSelectionContext> pinTypeSelection() {
			return getRuleContexts(PinTypeSelectionContext.class);
		}
		public PinTypeSelectionContext pinTypeSelection(int i) {
			return getRuleContext(PinTypeSelectionContext.class,i);
		}
		public PinConfigurationBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pinConfigurationBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterPinConfigurationBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitPinConfigurationBlock(this);
		}
	}

	public final PinConfigurationBlockContext pinConfigurationBlock() throws RecognitionException {
		PinConfigurationBlockContext _localctx = new PinConfigurationBlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_pinConfigurationBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(T__7);
			setState(52); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(51);
				pinTypeSelection();
				}
				}
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==PINNAME );
			setState(56);
			match(T__8);
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

	public static class EntireCodeContext extends ParserRuleContext {
		public PinDefinitionContext pinDefinition() {
			return getRuleContext(PinDefinitionContext.class,0);
		}
		public PinConfigurationContext pinConfiguration() {
			return getRuleContext(PinConfigurationContext.class,0);
		}
		public MainMethodContext mainMethod() {
			return getRuleContext(MainMethodContext.class,0);
		}
		public EntireCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entireCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterEntireCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitEntireCode(this);
		}
	}

	public final EntireCodeContext entireCode() throws RecognitionException {
		EntireCodeContext _localctx = new EntireCodeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_entireCode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			pinDefinition();
			setState(59);
			pinConfiguration();
			setState(60);
			mainMethod();
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

	public static class MainMethodContext extends ParserRuleContext {
		public MainMethodDeclarationContext mainMethodDeclaration() {
			return getRuleContext(MainMethodDeclarationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public MainMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainMethod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterMainMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitMainMethod(this);
		}
	}

	public final MainMethodContext mainMethod() throws RecognitionException {
		MainMethodContext _localctx = new MainMethodContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_mainMethod);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			mainMethodDeclaration();
			setState(63);
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

	public static class BlockContext extends ParserRuleContext {
		public BlockstatementsContext blockstatements() {
			return getRuleContext(BlockstatementsContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(T__7);
			setState(66);
			blockstatements();
			setState(67);
			match(T__8);
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

	public static class BlockstatementsContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockstatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockstatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterBlockstatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitBlockstatements(this);
		}
	}

	public final BlockstatementsContext blockstatements() throws RecognitionException {
		BlockstatementsContext _localctx = new BlockstatementsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_blockstatements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << SENSOR) | (1L << SENSORNAME))) != 0)) {
				{
				{
				setState(69);
				statement();
				}
				}
				setState(74);
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

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SensorCreateContext sensorCreate() {
			return getRuleContext(SensorCreateContext.class,0);
		}
		public SensorDetailChangeContext sensorDetailChange() {
			return getRuleContext(SensorDetailChangeContext.class,0);
		}
		public SensorPinBindContext sensorPinBind() {
			return getRuleContext(SensorPinBindContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		try {
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				sensorCreate();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				sensorDetailChange();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(78);
				sensorPinBind();
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

	public static class SensorCreateContext extends ParserRuleContext {
		public TerminalNode SENSOR() { return getToken(ProvizParser.SENSOR, 0); }
		public TerminalNode SENSORNAME() { return getToken(ProvizParser.SENSORNAME, 0); }
		public TerminalNode SENSORMAKE() { return getToken(ProvizParser.SENSORMAKE, 0); }
		public SensorCreateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sensorCreate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterSensorCreate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitSensorCreate(this);
		}
	}

	public final SensorCreateContext sensorCreate() throws RecognitionException {
		SensorCreateContext _localctx = new SensorCreateContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_sensorCreate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(SENSOR);
			setState(82);
			match(SENSORNAME);
			setState(83);
			match(T__0);
			setState(84);
			match(T__3);
			setState(85);
			match(SENSORMAKE);
			setState(86);
			match(T__9);
			setState(87);
			match(T__1);
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

	public static class SensorPinBindContext extends ParserRuleContext {
		public TerminalNode SENSORNAME() { return getToken(ProvizParser.SENSORNAME, 0); }
		public TerminalNode PINNAME() { return getToken(ProvizParser.PINNAME, 0); }
		public SensorPinBindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sensorPinBind; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterSensorPinBind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitSensorPinBind(this);
		}
	}

	public final SensorPinBindContext sensorPinBind() throws RecognitionException {
		SensorPinBindContext _localctx = new SensorPinBindContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_sensorPinBind);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(SENSORNAME);
			setState(90);
			match(T__10);
			setState(91);
			match(PINNAME);
			setState(92);
			match(T__5);
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

	public static class SensorDetailChangeContext extends ParserRuleContext {
		public TerminalNode SENSORNAME() { return getToken(ProvizParser.SENSORNAME, 0); }
		public TerminalNode FEATURE() { return getToken(ProvizParser.FEATURE, 0); }
		public TerminalNode INTEGER() { return getToken(ProvizParser.INTEGER, 0); }
		public SensorDetailChangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sensorDetailChange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterSensorDetailChange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitSensorDetailChange(this);
		}
	}

	public final SensorDetailChangeContext sensorDetailChange() throws RecognitionException {
		SensorDetailChangeContext _localctx = new SensorDetailChangeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_sensorDetailChange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(SENSORNAME);
			setState(95);
			match(T__11);
			setState(96);
			match(T__12);
			setState(97);
			match(FEATURE);
			setState(98);
			match(T__0);
			setState(99);
			match(INTEGER);
			setState(100);
			match(T__1);
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

	public static class MainMethodDeclarationContext extends ParserRuleContext {
		public MainMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainMethodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).enterMainMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProvizListener ) ((ProvizListener)listener).exitMainMethodDeclaration(this);
		}
	}

	public final MainMethodDeclarationContext mainMethodDeclaration() throws RecognitionException {
		MainMethodDeclarationContext _localctx = new MainMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_mainMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__13);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\35k\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\6\4.\n\4\r\4\16\4/\3\5\3\5\3\5\3\6\3\6\6\6\67"+
		"\n\6\r\6\16\68\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n"+
		"\7\nI\n\n\f\n\16\nL\13\n\3\13\3\13\3\13\3\13\5\13R\n\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\2\2\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\2"+
		"b\2\36\3\2\2\2\4#\3\2\2\2\6-\3\2\2\2\b\61\3\2\2\2\n\64\3\2\2\2\f<\3\2"+
		"\2\2\16@\3\2\2\2\20C\3\2\2\2\22J\3\2\2\2\24Q\3\2\2\2\26S\3\2\2\2\30[\3"+
		"\2\2\2\32`\3\2\2\2\34h\3\2\2\2\36\37\7\27\2\2\37 \7\3\2\2 !\7\21\2\2!"+
		"\"\7\4\2\2\"\3\3\2\2\2#$\7\5\2\2$%\7\27\2\2%&\7\3\2\2&\'\7\6\2\2\'(\7"+
		"\22\2\2()\7\7\2\2)*\7\33\2\2*+\7\b\2\2+\5\3\2\2\2,.\5\4\3\2-,\3\2\2\2"+
		"./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\7\3\2\2\2\61\62\7\t\2\2\62\63\5\n"+
		"\6\2\63\t\3\2\2\2\64\66\7\n\2\2\65\67\5\2\2\2\66\65\3\2\2\2\678\3\2\2"+
		"\28\66\3\2\2\289\3\2\2\29:\3\2\2\2:;\7\13\2\2;\13\3\2\2\2<=\5\6\4\2=>"+
		"\5\b\5\2>?\5\16\b\2?\r\3\2\2\2@A\5\34\17\2AB\5\20\t\2B\17\3\2\2\2CD\7"+
		"\n\2\2DE\5\22\n\2EF\7\13\2\2F\21\3\2\2\2GI\5\24\13\2HG\3\2\2\2IL\3\2\2"+
		"\2JH\3\2\2\2JK\3\2\2\2K\23\3\2\2\2LJ\3\2\2\2MR\5\20\t\2NR\5\26\f\2OR\5"+
		"\32\16\2PR\5\30\r\2QM\3\2\2\2QN\3\2\2\2QO\3\2\2\2QP\3\2\2\2R\25\3\2\2"+
		"\2ST\7\23\2\2TU\7\24\2\2UV\7\3\2\2VW\7\6\2\2WX\7\26\2\2XY\7\f\2\2YZ\7"+
		"\4\2\2Z\27\3\2\2\2[\\\7\24\2\2\\]\7\r\2\2]^\7\27\2\2^_\7\b\2\2_\31\3\2"+
		"\2\2`a\7\24\2\2ab\7\16\2\2bc\7\17\2\2cd\7\25\2\2de\7\3\2\2ef\7\33\2\2"+
		"fg\7\4\2\2g\33\3\2\2\2hi\7\20\2\2i\35\3\2\2\2\6/8JQ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}