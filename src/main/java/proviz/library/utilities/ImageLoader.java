package proviz.library.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Burak on 1/16/17.
 */
public  class ImageLoader {

public static ImageIcon LoadImageIcon(String path)
{
    ImageIcon imageIcon = null;

        imageIcon = new ImageIcon(path);

    return imageIcon;
}
    public static ImageIcon LoadImageIconFromJAR(ZipEntry ze,ZipInputStream zipInputStream){
            int size = (int) ze.getSize();
            byte[] b = new byte[size];
            int rb = 0;
            int chunk = 0;
            while (((int) size - rb) > 0) {
                try {
                    chunk = zipInputStream.read(b, rb, (int) size - rb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (chunk == -1) {
                    break;
                }
                rb += chunk;
            }

        return new ImageIcon(b);
    }

}
