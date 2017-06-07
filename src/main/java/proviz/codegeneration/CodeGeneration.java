package proviz.codegeneration;



import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import proviz.ProjectConstants;
import proviz.models.codegeneration.Code;
import proviz.models.codegeneration.Function;
import proviz.models.devices.Sensor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak on 9/29/16.
 */
public class CodeGeneration {
    private Configuration configuration;
    private HashMap<String,Object> inputs;
    private CodeGenerationTemplate codeGenerationTemplate;
    private Template template;

    public CodeGeneration(CodeGenerationTemplate codeGenerationTemplate) throws IOException, NullPointerException, TemplateException, IllegalArgumentException {

        this.codeGenerationTemplate = codeGenerationTemplate;
        inputs = new HashMap<>();
        if(ProjectConstants.templateConfiguration != null)
        configuration = ProjectConstants.templateConfiguration;
        else
            throw new  NullPointerException("Template Configuration can not be null");
        inputs.put("date",codeGenerationTemplate.getCreatedDate());

         template = null;


        switch (codeGenerationTemplate.getBoard().getDevice_type())
{
    case ARDUINO:
    {
        //Arduino
         template = configuration.getTemplate("arduino.proviz");
        break;
    }
    case RASPBERRY_PI:
    {
        // Raspberry Pi
        template = configuration.getTemplate("raspberrypi.proviz");

        break;
    }
    case BEAGLEBONE:
    {
        // BeagleBone
        template = configuration.getTemplate("beaglebone.proviz");
        break;
    }
    default:
    {
        new IllegalArgumentException("Invalid device type for code generation.");
    }

}

    }

    private void fillSensorCodes(ProjectConstants.DEVICE_TYPE device_type) throws IOException, TemplateException {
        ArrayList<Sensor> sensors = codeGenerationTemplate.getBoard().getSensors();
        StringWriter stringWriter = new StringWriter();
        for(Sensor sensor: sensors)
        {
            for(Code code: sensor.getCodes())
            {
                for(Function function: code.getFunctions())
                {
                    if(code.getEnvironment() == device_type ) {
                        HashMap<String, Object> hashMap = new HashMap();
                        hashMap.put("sensor", sensor);
                        Template t = new Template("name", new StringReader(function.getInnerCode()),
                                new Configuration());
                        t.process(hashMap, stringWriter);
                        function.setInnerCode(stringWriter.toString());
                    }
                }
            }

        }

    }

    public String createCode()
    {
        String returnedVal = "";
        StringWriter stringWriter = new StringWriter();
        try {
            fillSensorCodes(codeGenerationTemplate.getBoard().getDevice_type());
            inputs.put("board",codeGenerationTemplate.getBoard());
            inputs.put("AP_NAME","FIU_WiFi");
            inputs.put("AP_PASS","");
            inputs.put("SERVER_IP","10.109.149.102");
            inputs.put("PORT_NUMBER","5867");
            template.process(inputs,stringWriter);
            Template template1 = new Template("tmplt1",new StringReader(returnedVal),configuration);
            codeGenerationTemplate.getBoard().getSensors();
            template1.process(inputs,stringWriter);
            returnedVal = stringWriter.toString();

        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
return  returnedVal;
    }

}
