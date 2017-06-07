package proviz.codedistribution;

import proviz.ProjectConstants;

/**
 * Created by Burak on 2/1/17.
 */
public  abstract class CodeFlasher {
    protected String code;
    protected String temproraySourceCodePath;
    protected ProjectConstants.CONNECTION_TYPE connection_type;

    public abstract int FlashCode();
    protected abstract String createTempFile(String code);



}
