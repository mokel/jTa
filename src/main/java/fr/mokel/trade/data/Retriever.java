package fr.mokel.trade.data;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;



public class Retriever {
    
    private HttpClient client;
    
    private String url;
    
    private String webPage;
    
    public Retriever(String url) {
        this(null, 0, null, null, url);
    }
    
    public Retriever(String proxyUrl, int port, String login, String pass, String url) {
        client = new HttpClient();
        this.url = url;
        if(login != null && pass != null){
            client.getHostConfiguration().setProxy(proxyUrl, port);
            Credentials defaultcreds = new UsernamePasswordCredentials(login, pass);
            client.getState().setProxyCredentials(AuthScope.ANY, defaultcreds);
        }
    }

    public void load(){
        GetMethod httpget = new GetMethod(url);
        try { 
            client.executeMethod(httpget);
            InputStream is = httpget.getResponseBodyAsStream();
            webPage = IOUtils.toString(is);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
    }
    
    public String getString(String whatToFind, int lengthToReturn) {
        String result = null;
      int index = webPage.indexOf(whatToFind);
      if(index > 0){
          result = webPage.subSequence(index + whatToFind.length(), index + whatToFind.length() + lengthToReturn).toString();
      }
      return result;
    }

    /**
     * 
     */
    public String getWebPage() {
        return webPage;
    }
}
