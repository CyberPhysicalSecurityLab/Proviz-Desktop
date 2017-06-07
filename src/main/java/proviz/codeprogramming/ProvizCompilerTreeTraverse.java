package proviz.codeprogramming;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.devicedatalibrary.DataManager;
import proviz.models.Bound;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Board;
import proviz.models.devices.Pin;
import proviz.models.devices.Sensor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Burak on 10/27/16.
 */
public class ProvizCompilerTreeTraverse implements ProvizListener {

    private CodeGenerationTemplate codeGenerationTemplate;
    private ArrayList<Pin> temproraryPinList;
    private ArrayList<Sensor> temprorarySensorList;
    public ProvizCompilerTreeTraverse(CodeGenerationTemplate codeGenerationTemplate){
        this.codeGenerationTemplate = codeGenerationTemplate;
        temproraryPinList = new ArrayList<>();
        temprorarySensorList = new ArrayList<>();
    }

    @Override
    public void enterPinTypeSelection(ProvizParser.PinTypeSelectionContext ctx) {
        Pin.PINTYPE pintype = null;
        ArrayList<Pin> pinArrayList = null;

    for(Pin pin: temproraryPinList)
    {
        if(pin.getPreferredVariableName().compareTo(ctx.PINNAME().toString()) ==0)
        {
            pintype = pin.getType();
            break;
        }
    }
        if(pintype == Pin.PINTYPE.analog)
        {
            pinArrayList = codeGenerationTemplate.getBoard().getAnalogPins();
        }
        else  if(pintype == Pin.PINTYPE.comm)
        {
            pinArrayList = codeGenerationTemplate.getBoard().getSerialPins();
        }
        else  if(pintype == Pin.PINTYPE.digital)
        {
            pinArrayList = codeGenerationTemplate.getBoard().getDigitalPins();
        }
        else if (pintype == Pin.PINTYPE.i2c)
        {
            pinArrayList = codeGenerationTemplate.getBoard().getI2CPins();
        }
        else if (pintype == Pin.PINTYPE.spibus)
        {
            pinArrayList = codeGenerationTemplate.getBoard().getSPIBusPins();
        }
        if(pinArrayList != null)
        {
            for(Pin singlePin: pinArrayList )
            {
                if(singlePin.getPreferredVariableName() != null && singlePin.getPreferredVariableName().compareTo(ctx.PINNAME().toString()) == 0)
                {
                    if(ctx.PINDATATRANSMISSION().toString().compareTo(Pin.DATA_DIRECTION.INPUT.toString()) == 0)
                    {
                        singlePin.setDataDirection(Pin.DATA_DIRECTION.INPUT);
                    }
                    else  if(ctx.PINDATATRANSMISSION().toString().compareTo(Pin.DATA_DIRECTION.OUTPUT.toString()) == 0)
                    {
                        singlePin.setDataDirection(Pin.DATA_DIRECTION.OUTPUT);
                    }
                    break;
                }
            }
        }

    }

    @Override
    public void exitPinTypeSelection(ProvizParser.PinTypeSelectionContext ctx) {

    }

    @Override
    public void enterPinSelection(ProvizParser.PinSelectionContext ctx) {

        String pinType = ctx.PINTYPE().toString();

        ArrayList<Pin> targetPinList = null;

        if(pinType.compareTo("AnalogPin") == 0)
        {
            targetPinList = codeGenerationTemplate.getBoard().getAnalogPins();

        }
        else if(pinType.compareTo("DigitalPin") == 0)
        {
            targetPinList = codeGenerationTemplate.getBoard().getDigitalPins();
        }
        else if(pinType.compareTo("SerialPin") == 0)
        {
            targetPinList = codeGenerationTemplate.getBoard().getSerialPins();
        }
        else if(pinType.compareTo("I2cBus") == 0)
        {
            targetPinList = codeGenerationTemplate.getBoard().getI2CPins();
        }
        else if(pinType.compareTo("SpiBus") == 0)
        {
            targetPinList = codeGenerationTemplate.getBoard().getSPIBusPins();
        }
        else if(pinType.compareTo("VccPin") == 0)
        {
           //todo vcc
        }
        else if(pinType.compareTo("GroundPin") == 0)
        {
            //todo ground
        }

       if(targetPinList != null)
       {
           for(Pin pin: targetPinList)
           {
               if(pin.getOrder() == Integer.parseInt(ctx.INTEGER().toString()))
               {
                    pin.setAvailable(false);
                   pin.setPreferredVariableName(ctx.PINNAME().toString());
                   temproraryPinList.add(pin);
                   break;
               }
           }
       }


    }

    @Override
    public void exitPinSelection(ProvizParser.PinSelectionContext ctx) {

    }

    @Override
    public void enterPinDefinition(ProvizParser.PinDefinitionContext ctx) {

    }

    @Override
    public void exitPinDefinition(ProvizParser.PinDefinitionContext ctx) {

    }

    @Override
    public void enterPinConfiguration(ProvizParser.PinConfigurationContext ctx) {

    }

    @Override
    public void exitPinConfiguration(ProvizParser.PinConfigurationContext ctx) {

    }

    @Override
    public void enterPinConfigurationBlock(ProvizParser.PinConfigurationBlockContext ctx) {

    }

    @Override
    public void exitPinConfigurationBlock(ProvizParser.PinConfigurationBlockContext ctx) {

    }

    @Override
    public void enterEntireCode(ProvizParser.EntireCodeContext ctx) {

    }

    @Override
    public void exitEntireCode(ProvizParser.EntireCodeContext ctx) {

    }

    @Override
    public void enterMainMethod(ProvizParser.MainMethodContext ctx) {

    }

    @Override
    public void exitMainMethod(ProvizParser.MainMethodContext ctx) {
        for(Sensor sensor:temprorarySensorList)
        {
            DataManager.getInstance().activeSensors.add(sensor);
        }
        codeGenerationTemplate.getBoard().setSensors(temprorarySensorList);
    }

    @Override
    public void enterBlock(ProvizParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(ProvizParser.BlockContext ctx) {

    }

    @Override
    public void enterBlockstatements(ProvizParser.BlockstatementsContext ctx) {

    }

    @Override
    public void exitBlockstatements(ProvizParser.BlockstatementsContext ctx) {

    }

    @Override
    public void enterStatement(ProvizParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(ProvizParser.StatementContext ctx) {

    }

    @Override
    public void enterSensorCreate(ProvizParser.SensorCreateContext ctx) {
        ObjectMapper objectMapper = new ObjectMapper();
        Sensor sensor = null;
    if(ctx.SENSOR().toString().compareTo("sensor") == 0)
    {
        String sensorFileName ="sensors/"+ctx.SENSORMAKE().toString() +".json";
        try {
       sensor = objectMapper.readValue(new File(ClassLoader.getSystemResource(sensorFileName).getFile()), Sensor.class);
            sensor.setParentBoard(codeGenerationTemplate.getBoard());
            sensor.setVariableBounds();
            sensor.clearPins();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  sensor.setVariableTypeName(ctx.SENSORNAME().toString());

        temprorarySensorList.add(sensor);

    }
    }

    @Override
    public void exitSensorCreate(ProvizParser.SensorCreateContext ctx) {

    }

    @Override
    public void enterSensorPinBind(ProvizParser.SensorPinBindContext ctx) {
        for(Sensor sensor: temprorarySensorList)
        {
            if(sensor.getVariableTypeName().compareTo(ctx.SENSORNAME().toString()) == 0)
            {
                for(Pin pin: temproraryPinList)
                {
                   if(pin.getPreferredVariableName().compareTo(ctx.PINNAME().toString()) == 0)
                   {
                       bindPins2BoardSensor(sensor,pin);
                       break;
                   }
                }
                break;
            }
        }
    }

    @Override
    public void exitSensorPinBind(ProvizParser.SensorPinBindContext ctx) {

    }

    @Override
    public void enterSensorDetailChange(ProvizParser.SensorDetailChangeContext ctx) {
        Sensor previousVersionSensor = null;
        for(Sensor sensor : temprorarySensorList)
        {
           for(Variable variable:sensor.getVariables())
            {
                if(variable.isCommunicationVariable()) {
                    if (ctx.FEATURE().toString().compareTo("UpperBound") == 0) {
                        variable.getBound().setUpperValue(Double.parseDouble(ctx.INTEGER().toString()));
                        variable.getBound().setActive(true);

                    } else if (ctx.FEATURE().toString().compareTo("LowerBound") == 0) {
                        variable.getBound().setLowerValue(Double.parseDouble(ctx.INTEGER().toString()));
                        variable.getBound().setActive(true);

                    }
                }
            }
        }
         if (ctx.FEATURE().toString().compareTo("BaudRate") == 0) {
            codeGenerationTemplate.getBoard().setSampleRate(Integer.parseInt(ctx.INTEGER().toString()));
        }
    }

    @Override
    public void exitSensorDetailChange(ProvizParser.SensorDetailChangeContext ctx) {

    }

    @Override
    public void enterMainMethodDeclaration(ProvizParser.MainMethodDeclarationContext ctx) {

    }

    @Override
    public void exitMainMethodDeclaration(ProvizParser.MainMethodDeclarationContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }

    private Bound boundCreate()
    {
        Bound bound = new Bound();
        bound.setActive(true);
        return bound;
    }
    private boolean isAlreadyAdded(Sensor sensor)
    {
     ArrayList<Sensor> sensorList = codeGenerationTemplate.getBoard().getSensors();

        for(Sensor singleSensor : sensorList)
        {
            if(singleSensor == sensor)
            {
                return true;
            }
        }
        return false;
    }

    private void bindPins2BoardSensor(Sensor sensor, Pin pin)
    {
        Board board = codeGenerationTemplate.getBoard();

        for(Sensor singleSensor : board.getSensors())
        {
            if(sensor.getVariableTypeName().compareTo(singleSensor.getVariableTypeName()) == 0)
            {

                sensor.addPin(pin);


                break;
            }
        }
    }
}
