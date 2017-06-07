package proviz.connection.client;

import proviz.ProjectConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Burak on 5/12/17.§§
 */
public class TabletConnectionManager {

    private static TabletConnectionManager self;
    private TabletConnectionMethods tabletConnectionMethods;

    public static TabletConnectionManager getInstance()
    {
        if(self == null)
            self = new TabletConnectionManager();
        return self;

    }


    public TabletConnectionMethods getTabletConnectionMethods() {
        return tabletConnectionMethods;
    }

    public void setTabletConnectionMethods(TabletConnectionMethods tabletConnectionMethods) {
        this.tabletConnectionMethods = tabletConnectionMethods;
    }

    public void reConfigureClient()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://"+ProjectConstants.init().getTabletIpAddress()+":9997"+"/").addConverterFactory(GsonConverterFactory.create()).build();
        tabletConnectionMethods = retrofit.create(TabletConnectionMethods.class);
    }

    public TabletConnectionManager()
    {

        reConfigureClient();
    }


}
