package net.thinklog.common.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yiihoo
 */
public class XmlKit {
    /**
     * 微信下单，map to xml
     *
     * @param params 参数
     * @return String
     */
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StrKit.isBlank(value)) {
                continue;
            }
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    public static TreeMap<String, String> getXmlData(String txt, String tag) {
        String pattern = "<" + tag + ">(.+?)</" + tag + ">";
        TreeMap<String, String> listData = new TreeMap<>();
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(txt);
        while (m.find()) {
            listData.putAll(getXmlAttr(m.group(1)));
        }
        return listData;
    }

    private static Map<String, String> getXmlAttr(String txt) {
        Map<String, String> arr = new HashMap<>();
        String skey = null, sval = null;
        //闭合模式的写法
        String pattern = "<(?<XmlTag>[a-zA-Z0-9_]+)>(.+)</\\k<XmlTag>>";
        Pattern r = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(txt);
        while (m.find()) {
            skey = m.group(1);
            sval = m.group(2);
            if (sval.startsWith("<![CDATA[") && sval.endsWith("]]>")) {
                sval = sval.substring(9, sval.length() - 3);
            }
            arr.put(skey, sval);
        }
        return arr;
    }

    public static void main(String[] args) {
        String str = " <document>\n" +
                "<response id=\"response\"><head><Version>1.0.0</Version><Appid>2020040800000533</Appid><Function>ant.mybank.bkcloudfunds.balance.query</Function><RespTime>20200703175104</RespTime><RespTimeZone>UTC+8</RespTimeZone><ReqMsgId>29166d3ee5ea4232895847bfd824e196</ReqMsgId><Reserve></Reserve><SignType>RSA</SignType><InputCharset>UTF-8</InputCharset></head>" +
                "<body><RespInfo><ResultStatus>S</ResultStatus><RestCode>0000</RestCode><ResultMsg>业务处理成功</ResultMsg></RespInfo>" +
                "<IsvOrgId>202210000000000001569</IsvOrgId><MerchantId>226801000016687787768</MerchantId><BalanceList>W3sidG90YWxBbW91bnQiOjAsImFjY291bnRUeXBlIjoiQkFMQU5DRSIsImFjY291bnRFeHRlcm5hbiOTkwMzA3NjA5ODAyMDA5NjMwMDkiLCJjdXJyZW5jeSI6IkNOWSJ9LHsidG90YWxBbW91bnQiOjAsImFjY291bnRUeXBlIjoiRlJFRVpFIiwiYWNjb3VudEV4dGVybmFsTm8iOiI5OTAzMTc2MDE4NTAwMDg4MjAwNyIsImN1cnJlbmN5IjoiQ05ZIn0seyJ0b3RhbEFtb3VudCI6MTAwMCwiYWNjb3VudFR5cGUiOiJUUkFERV9ERVBPU0lUIiwiYWNjb3VudEV4dGVybmFsTm8iOiI5OTAzNDc2MDQwMTAwMDQwNDAwNSIsImN1cnJlbmN5IjoiQ05ZIn1d</BalanceList></body></response>\n" +
                "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "<SignedInfo>\n" +
                "<CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></CanonicalizationMethod>\n" +
                "<SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"></SignatureMethod>\n" +
                "<Reference URI=\"\">\n" +
                "<Transforms>\n" +
                "<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></Transform>\n" +
                "</Transforms>\n" +
                "<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></DigestMethod>\n" +
                "<DigestValue>q6aNS1k5H8tA4/qru9YkuVI5Vkk=</DigestValue>\n" +
                "</Reference>\n" +
                "</SignedInfo>\n" +
                "<SignatureValue>\n" +
                "LgxDfKcb/oTZYF9YDhpihGHIJbmARuA7hkm8ScDlyUohRYmAUjO1K/vCMtTHDrc5ep+LNPTd3BQX\n" +
                "CT3ah6yvltoGK+8G7w9soBQ3s1PA8yJvdDEg8gAEG/ZWpIWT0sK8OCmK5bGzubZtqrJf8j7l0Rz4\n" +
                "Tf3Oxcz9tMnFlGwBBjzrlvrznkoC/73KcSYfNgLBHvU+vlqRjK1DX8NdSaKiikwxRjBuGEnZvAmi\n" +
                "khHAV6Zk4RQ+7jU6K5/oHcpgm+I8thTnAb3FPbKLYIzrLaSoEObE3B35w1GyrPXLpN7SNaqfia2O\n" +
                "1TTV7H6gqvcRoExL8uNYhn17/H1kLdrCJC+3gA==\n" +
                "</SignatureValue>\n" +
                "</Signature></document>\n" +
                "\n";
        TreeMap<String, String> data = XmlKit.getXmlData(str, "body");
        System.out.println(data);
    }
}
