package proviz.codeprogramming;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProvizParser}.
 */
public interface ProvizListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProvizParser#pinTypeSelection}.
	 * @param ctx the parse tree
	 */
	void enterPinTypeSelection(ProvizParser.PinTypeSelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#pinTypeSelection}.
	 * @param ctx the parse tree
	 */
	void exitPinTypeSelection(ProvizParser.PinTypeSelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#pinSelection}.
	 * @param ctx the parse tree
	 */
	void enterPinSelection(ProvizParser.PinSelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#pinSelection}.
	 * @param ctx the parse tree
	 */
	void exitPinSelection(ProvizParser.PinSelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#pinDefinition}.
	 * @param ctx the parse tree
	 */
	void enterPinDefinition(ProvizParser.PinDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#pinDefinition}.
	 * @param ctx the parse tree
	 */
	void exitPinDefinition(ProvizParser.PinDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#pinConfiguration}.
	 * @param ctx the parse tree
	 */
	void enterPinConfiguration(ProvizParser.PinConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#pinConfiguration}.
	 * @param ctx the parse tree
	 */
	void exitPinConfiguration(ProvizParser.PinConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#pinConfigurationBlock}.
	 * @param ctx the parse tree
	 */
	void enterPinConfigurationBlock(ProvizParser.PinConfigurationBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#pinConfigurationBlock}.
	 * @param ctx the parse tree
	 */
	void exitPinConfigurationBlock(ProvizParser.PinConfigurationBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#entireCode}.
	 * @param ctx the parse tree
	 */
	void enterEntireCode(ProvizParser.EntireCodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#entireCode}.
	 * @param ctx the parse tree
	 */
	void exitEntireCode(ProvizParser.EntireCodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void enterMainMethod(ProvizParser.MainMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void exitMainMethod(ProvizParser.MainMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ProvizParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ProvizParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#blockstatements}.
	 * @param ctx the parse tree
	 */
	void enterBlockstatements(ProvizParser.BlockstatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#blockstatements}.
	 * @param ctx the parse tree
	 */
	void exitBlockstatements(ProvizParser.BlockstatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ProvizParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ProvizParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#sensorCreate}.
	 * @param ctx the parse tree
	 */
	void enterSensorCreate(ProvizParser.SensorCreateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#sensorCreate}.
	 * @param ctx the parse tree
	 */
	void exitSensorCreate(ProvizParser.SensorCreateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#sensorPinBind}.
	 * @param ctx the parse tree
	 */
	void enterSensorPinBind(ProvizParser.SensorPinBindContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#sensorPinBind}.
	 * @param ctx the parse tree
	 */
	void exitSensorPinBind(ProvizParser.SensorPinBindContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#sensorDetailChange}.
	 * @param ctx the parse tree
	 */
	void enterSensorDetailChange(ProvizParser.SensorDetailChangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#sensorDetailChange}.
	 * @param ctx the parse tree
	 */
	void exitSensorDetailChange(ProvizParser.SensorDetailChangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProvizParser#mainMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMainMethodDeclaration(ProvizParser.MainMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProvizParser#mainMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMainMethodDeclaration(ProvizParser.MainMethodDeclarationContext ctx);
}