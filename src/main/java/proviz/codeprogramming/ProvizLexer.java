package proviz.codeprogramming;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProvizLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "PINDATATRANSMISSION", "PINTYPE", 
		"SENSOR", "SENSORNAME", "FEATURE", "SENSORMAKE", "PINNAME", "STARTSWITHCAPITAL", 
		"UPPERCASESTRING", "LOWERCASESTRING", "INTEGER", "WS", "COMMENT"
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


	public ProvizLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Proviz.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\35\u0124\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u008e\n\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u00c7\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\5\24\u00f5\n\24\3\25\3\25\3\26\3\26\3\27\3\27\6\27"+
		"\u00fd\n\27\r\27\16\27\u00fe\3\30\6\30\u0102\n\30\r\30\16\30\u0103\3\31"+
		"\6\31\u0107\n\31\r\31\16\31\u0108\3\32\6\32\u010c\n\32\r\32\16\32\u010d"+
		"\3\33\6\33\u0111\n\33\r\33\16\33\u0112\3\33\3\33\3\34\3\34\3\34\3\34\7"+
		"\34\u011b\n\34\f\34\16\34\u011e\13\34\3\34\3\34\3\34\3\34\3\34\3\u011c"+
		"\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\35\3\2"+
		"\6\3\2C\\\3\2c|\3\2\62;\5\2\13\13\17\17\"\"\u0133\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5;\3\2\2\2\7=\3\2\2\2\tA\3"+
		"\2\2\2\13E\3\2\2\2\rG\3\2\2\2\17J\3\2\2\2\21c\3\2\2\2\23e\3\2\2\2\25g"+
		"\3\2\2\2\27j\3\2\2\2\31p\3\2\2\2\33r\3\2\2\2\35v\3\2\2\2\37\u008d\3\2"+
		"\2\2!\u00c6\3\2\2\2#\u00c8\3\2\2\2%\u00cf\3\2\2\2\'\u00f4\3\2\2\2)\u00f6"+
		"\3\2\2\2+\u00f8\3\2\2\2-\u00fa\3\2\2\2/\u0101\3\2\2\2\61\u0106\3\2\2\2"+
		"\63\u010b\3\2\2\2\65\u0110\3\2\2\2\67\u0116\3\2\2\29:\7?\2\2:\4\3\2\2"+
		"\2;<\7=\2\2<\6\3\2\2\2=>\7R\2\2>?\7k\2\2?@\7p\2\2@\b\3\2\2\2AB\7p\2\2"+
		"BC\7g\2\2CD\7y\2\2D\n\3\2\2\2EF\7*\2\2F\f\3\2\2\2GH\7+\2\2HI\7=\2\2I\16"+
		"\3\2\2\2JK\7x\2\2KL\7q\2\2LM\7k\2\2MN\7f\2\2NO\7\"\2\2OP\7R\2\2PQ\7k\2"+
		"\2QR\7p\2\2RS\7e\2\2ST\7q\2\2TU\7p\2\2UV\7h\2\2VW\7k\2\2WX\7i\2\2XY\7"+
		"w\2\2YZ\7t\2\2Z[\7c\2\2[\\\7v\2\2\\]\7k\2\2]^\7q\2\2^_\7p\2\2_`\7\"\2"+
		"\2`a\7*\2\2ab\7+\2\2b\20\3\2\2\2cd\7}\2\2d\22\3\2\2\2ef\7\177\2\2f\24"+
		"\3\2\2\2gh\7*\2\2hi\7+\2\2i\26\3\2\2\2jk\7\60\2\2kl\7c\2\2lm\7f\2\2mn"+
		"\7f\2\2no\7*\2\2o\30\3\2\2\2pq\7\60\2\2q\32\3\2\2\2rs\7u\2\2st\7g\2\2"+
		"tu\7v\2\2u\34\3\2\2\2vw\7x\2\2wx\7q\2\2xy\7k\2\2yz\7f\2\2z{\7\"\2\2{|"+
		"\7O\2\2|}\7c\2\2}~\7k\2\2~\177\7p\2\2\177\u0080\7*\2\2\u0080\u0081\7+"+
		"\2\2\u0081\36\3\2\2\2\u0082\u0083\7K\2\2\u0083\u0084\7P\2\2\u0084\u0085"+
		"\7R\2\2\u0085\u0086\7W\2\2\u0086\u008e\7V\2\2\u0087\u0088\7Q\2\2\u0088"+
		"\u0089\7W\2\2\u0089\u008a\7V\2\2\u008a\u008b\7R\2\2\u008b\u008c\7W\2\2"+
		"\u008c\u008e\7V\2\2\u008d\u0082\3\2\2\2\u008d\u0087\3\2\2\2\u008e \3\2"+
		"\2\2\u008f\u0090\7C\2\2\u0090\u0091\7p\2\2\u0091\u0092\7c\2\2\u0092\u0093"+
		"\7n\2\2\u0093\u0094\7q\2\2\u0094\u0095\7i\2\2\u0095\u0096\7R\2\2\u0096"+
		"\u0097\7k\2\2\u0097\u00c7\7p\2\2\u0098\u0099\7F\2\2\u0099\u009a\7k\2\2"+
		"\u009a\u009b\7i\2\2\u009b\u009c\7k\2\2\u009c\u009d\7v\2\2\u009d\u009e"+
		"\7c\2\2\u009e\u009f\7n\2\2\u009f\u00a0\7R\2\2\u00a0\u00a1\7k\2\2\u00a1"+
		"\u00c7\7p\2\2\u00a2\u00a3\7U\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7t\2\2"+
		"\u00a5\u00a6\7k\2\2\u00a6\u00a7\7c\2\2\u00a7\u00a8\7n\2\2\u00a8\u00a9"+
		"\7R\2\2\u00a9\u00aa\7k\2\2\u00aa\u00c7\7p\2\2\u00ab\u00ac\7K\2\2\u00ac"+
		"\u00ad\7\64\2\2\u00ad\u00ae\7e\2\2\u00ae\u00af\7D\2\2\u00af\u00b0\7w\2"+
		"\2\u00b0\u00c7\7u\2\2\u00b1\u00b2\7U\2\2\u00b2\u00b3\7r\2\2\u00b3\u00b4"+
		"\7k\2\2\u00b4\u00b5\7D\2\2\u00b5\u00b6\7w\2\2\u00b6\u00c7\7u\2\2\u00b7"+
		"\u00b8\7X\2\2\u00b8\u00b9\7e\2\2\u00b9\u00ba\7e\2\2\u00ba\u00bb\7R\2\2"+
		"\u00bb\u00bc\7k\2\2\u00bc\u00c7\7p\2\2\u00bd\u00be\7I\2\2\u00be\u00bf"+
		"\7t\2\2\u00bf\u00c0\7q\2\2\u00c0\u00c1\7w\2\2\u00c1\u00c2\7p\2\2\u00c2"+
		"\u00c3\7f\2\2\u00c3\u00c4\7R\2\2\u00c4\u00c5\7k\2\2\u00c5\u00c7\7p\2\2"+
		"\u00c6\u008f\3\2\2\2\u00c6\u0098\3\2\2\2\u00c6\u00a2\3\2\2\2\u00c6\u00ab"+
		"\3\2\2\2\u00c6\u00b1\3\2\2\2\u00c6\u00b7\3\2\2\2\u00c6\u00bd\3\2\2\2\u00c7"+
		"\"\3\2\2\2\u00c8\u00c9\7u\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7p\2\2\u00cb"+
		"\u00cc\7u\2\2\u00cc\u00cd\7q\2\2\u00cd\u00ce\7t\2\2\u00ce$\3\2\2\2\u00cf"+
		"\u00d0\5\61\31\2\u00d0&\3\2\2\2\u00d1\u00d2\7W\2\2\u00d2\u00d3\7r\2\2"+
		"\u00d3\u00d4\7r\2\2\u00d4\u00d5\7g\2\2\u00d5\u00d6\7t\2\2\u00d6\u00d7"+
		"\7D\2\2\u00d7\u00d8\7q\2\2\u00d8\u00d9\7w\2\2\u00d9\u00da\7p\2\2\u00da"+
		"\u00f5\7f\2\2\u00db\u00dc\7N\2\2\u00dc\u00dd\7q\2\2\u00dd\u00de\7y\2\2"+
		"\u00de\u00df\7g\2\2\u00df\u00e0\7t\2\2\u00e0\u00e1\7D\2\2\u00e1\u00e2"+
		"\7q\2\2\u00e2\u00e3\7w\2\2\u00e3\u00e4\7p\2\2\u00e4\u00f5\7f\2\2\u00e5"+
		"\u00e6\7D\2\2\u00e6\u00e7\7c\2\2\u00e7\u00e8\7w\2\2\u00e8\u00e9\7f\2\2"+
		"\u00e9\u00ea\7T\2\2\u00ea\u00eb\7c\2\2\u00eb\u00ec\7v\2\2\u00ec\u00f5"+
		"\7g\2\2\u00ed\u00ee\7V\2\2\u00ee\u00ef\7k\2\2\u00ef\u00f0\7o\2\2\u00f0"+
		"\u00f1\7g\2\2\u00f1\u00f2\7Q\2\2\u00f2\u00f3\7w\2\2\u00f3\u00f5\7v\2\2"+
		"\u00f4\u00d1\3\2\2\2\u00f4\u00db\3\2\2\2\u00f4\u00e5\3\2\2\2\u00f4\u00ed"+
		"\3\2\2\2\u00f5(\3\2\2\2\u00f6\u00f7\5-\27\2\u00f7*\3\2\2\2\u00f8\u00f9"+
		"\5/\30\2\u00f9,\3\2\2\2\u00fa\u00fc\t\2\2\2\u00fb\u00fd\t\3\2\2\u00fc"+
		"\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2"+
		"\2\2\u00ff.\3\2\2\2\u0100\u0102\t\2\2\2\u0101\u0100\3\2\2\2\u0102\u0103"+
		"\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\60\3\2\2\2\u0105"+
		"\u0107\t\3\2\2\u0106\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0106\3\2"+
		"\2\2\u0108\u0109\3\2\2\2\u0109\62\3\2\2\2\u010a\u010c\t\4\2\2\u010b\u010a"+
		"\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e"+
		"\64\3\2\2\2\u010f\u0111\t\5\2\2\u0110\u010f\3\2\2\2\u0111\u0112\3\2\2"+
		"\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115"+
		"\b\33\2\2\u0115\66\3\2\2\2\u0116\u0117\7\61\2\2\u0117\u0118\7,\2\2\u0118"+
		"\u011c\3\2\2\2\u0119\u011b\13\2\2\2\u011a\u0119\3\2\2\2\u011b\u011e\3"+
		"\2\2\2\u011c\u011d\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u011f\3\2\2\2\u011e"+
		"\u011c\3\2\2\2\u011f\u0120\7,\2\2\u0120\u0121\7\61\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0123\b\34\2\2\u01238\3\2\2\2\f\2\u008d\u00c6\u00f4\u00fe\u0103"+
		"\u0108\u010d\u0112\u011c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}