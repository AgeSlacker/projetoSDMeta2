package webserver.model;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import rmiserver.IServer;
import rmiserver.Page;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SearchBean {
    private IServer server;
    private ArrayList<Page> searchResults = new ArrayList<>();
    private ArrayList<Page> translatedResults = new ArrayList<>();
    private ArrayList<Page> originalResults = new ArrayList<>();
    private boolean translated = false;
    private String searchTerms;
    private String facebookShareLink = "";

    public SearchBean() {
        System.getProperties().put("java.security.policy", "policy.all");
        try {
            server = (IServer) Naming.lookup("//localhost:7000/RMIserver");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }

    public boolean getTranslated() {
        return this.translated;
    }

    public String getFacebookShareLink() {
        return facebookShareLink;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms.toLowerCase();
        System.out.println("Setting search terms to " + searchTerms);

        // Gerar o link partilhavel
        String facebookUrl = "https://www.facebook.com/dialog/share";
        String appId = "2951392934911594";
        String redirectUri = "http://ucbusca.com/index.action";
        String href = "http://ucbusca.com:8080/searchResults.action?searchBean.searchTerms=";

        StringBuilder sb = new StringBuilder();
        sb.append(facebookUrl)
                .append("?app_id=" + appId)
                .append("&display=touch")
                .append("&redirect_uri=" + redirectUri)
                .append("&href=" + href)
                .append(searchTerms.replaceAll(" ", "+"));
        this.facebookShareLink = sb.toString();
    }

    public void setSearchResults(String user) throws RemoteException {
        this.translated = false;
        System.out.println("Setting search results");
        this.searchResults = server.search(null, searchTerms.split(" "), user, 0);
        this.originalResults = this.searchResults;
    }

    public ArrayList<Page> getSearchResults() throws RemoteException { // TODO am i going to throw all exceptions?
        /*
        System.out.println("Getting search results");
        for (Page p : searchResults) {
            System.out.println(p.getName() + p.getUrl() + p.getDescription());
        }*/
        return this.searchResults;
    }

    public void doTranslation() {
        String urlSt = "https://translate.yandex.net/api/v1.5/tr/detect";
        String apiKey = "trnsl.1.1.20191214T152425Z.4b49731b6600250b.40d9db71eee4bedcab23cd0ce0142ac4588f6612";
        translatedResults.clear();
        for (Page p : originalResults) {
            String titleLang = getLanguage(p.getName());
            String newTitle = translateText(p.getName(), "pt") + " [Original em " + titleLang + " ]";
            String descLang = getLanguage(p.getDescription());
            String translatedDescription = translateText(p.getDescription(), "pt");
            System.out.println("Detected lang :" + titleLang + " " + descLang);
            translatedResults.add(new Page(p.getUrl(), newTitle, translatedDescription));
        }
        this.translated = true;
        this.searchResults = translatedResults;
    }

    public String getLanguage(String text) {
        String urlSt = "https://translate.yandex.net/api/v1.5/tr/detect";
        String apiKey = "trnsl.1.1.20191214T152425Z.4b49731b6600250b.40d9db71eee4bedcab23cd0ce0142ac4588f6612";
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(urlSt)
                    .append("?key=" + apiKey)
                    .append("&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));
            URL url = null;
            url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            //connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-agent", "SD_OA_meta2");
            connection.connect();
            String message = connection.getResponseMessage();
            if (connection.getResponseCode() >= 300) {
                //TODO get info about the erro
            }
            InputSource inputSource = new InputSource(connection.getInputStream());

            XPath xpath = XPathFactory.newInstance().newXPath();

            NodeList node = (NodeList) xpath.evaluate("//@lang", inputSource, XPathConstants.NODESET);
            connection.disconnect();
            if (node.getLength() >= 1)
                return node.item(0).getNodeValue();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String translateText(String text, String lang) {
        String urlSt = "https://translate.yandex.net/api/v1.5/tr/translate";
        String apiKey = "trnsl.1.1.20191214T152425Z.4b49731b6600250b.40d9db71eee4bedcab23cd0ce0142ac4588f6612";
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(urlSt)
                    .append("?key=" + apiKey)
                    .append("&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()))
                    .append("&lang=" + lang);
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("User-agent", "SD_OA_meta2");
            connection.connect();
            String message = connection.getResponseMessage();
            if (connection.getResponseCode() >= 300) {
                //TODO get info about the erro
            }
            InputSource inputSource = new InputSource(connection.getInputStream());

            XPath xpath = XPathFactory.newInstance().newXPath();

            NodeList node = (NodeList) xpath.evaluate("/Translation/text", inputSource, XPathConstants.NODESET);
            connection.disconnect();
            if (node.getLength() >= 1)
                return node.item(0).getFirstChild().getNodeValue();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return "";

    }

    public void resetTranslation() {
        this.searchResults = originalResults;
        this.translated = false;
    }
}
