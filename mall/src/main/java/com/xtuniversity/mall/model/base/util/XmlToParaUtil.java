package com.xtuniversity.mall.model.base.util;

/**
 * ReadXmlUtil
 *
 * @author tangjie
 */
public class XmlToParaUtil {

    // xml文件节点全部转为大写
//    public static Element elementToUpper(Element ele){
//        ele.setName(ele.getName().toUpperCase());
//        List<Attribute> attrList = ele.getAttributes();
//        if(!attrList.isEmpty()){
//            for(int i=0, m = attrList.size(); i < m; i++){
//                Attribute attr = attrList.get(i);
//                attr.setName(attr.getName().toUpperCase());
//            }
//        }
//        List<Element> eleList = ele.getChildren();
//        if(!eleList.isEmpty()){
//            for(int i=0, m=eleList.size(); i<m; i++){
//                Element element = eleList.get(i);
//                element.setName(element.getName().toUpperCase());
//                elementToUpper(element);
//            }
//        }
//        return ele;
//    }
//
//    // xml文件中的值根据节点名称存放到Map
//    public static Map<String, String> getElementAll(Element ele){
//        Map<String, String> map = new HashMap<String, String>();
//        List<Attribute> attrList = ele.getAttributes();
//        if(!attrList.isEmpty()){
//            for(int i=0, m = attrList.size(); i < m; i++){
//                Attribute attr = attrList.get(i);
//                map.put(ele.getName().toUpperCase() + attr.getName().toUpperCase(), attr.getValue());
//            }
//        }
//        List<Element> eleList = ele.getChildren();
//        if(!eleList.isEmpty()){
//            for(int i=0, m=eleList.size(); i<m; i++){
//                Element element = eleList.get(i);
//                Map<String, String> eleMap = getElementAll(element);
//                mapPutAll(map, eleMap);
//            }
//        }else if(null != ele.getValue() && ele.getValue().length() > 0){
//            map.put(ele.getName().toUpperCase(), ele.getValue());
//        }
//        return map;
//    }
//
//    // 将源Map拷贝到目标Map当中
//    private static Map<String, String> mapPutAll(Map<String, String> target, Map<String, String> source){
//        int num = 1;
//        boolean bool = true;
//        Iterator<Map.Entry<String, String>> it = source.entrySet().iterator();
//        while(it.hasNext()){
//            bool = true; 
//            Map.Entry<String, String> entry = it.next();
//            if(null == target.get(entry.getKey())){
//                target.put(entry.getKey(), entry.getValue());
//            }else{
//                while(bool){
//                    String key = entry.getKey() + "_" + num;
//                    if(null == target.get(key)){
//                        target.put(key, entry.getValue());
//                        bool = false;
//                    }else{
//                        num ++;
//                    }
//                }
//            }
//        }
//        return target;
//    }
//    /**
//     * 解析xml,以map集合形式返回
//     *
//     * @param xmlStr
//     * @return
//     * @throws JDOMException
//     * @throws IOException
//     */
//    public static Map XmlStr2Map(String xmlStr) throws JDOMException, IOException {
//        Map resultMap = new HashMap();
//        xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
//        if (null == xmlStr || "".equals(xmlStr)) {
//            return null;
//        }
//        InputStream in = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
//        SAXBuilder builder = new SAXBuilder();
//        Document doc = builder.build(in);
//        Element root = doc.getRootElement();
//        List list = root.getChildren();
//        if (!list.isEmpty()) {
//            resultMap.put(root.getName(), getChildrenMap(list));
//        } else {
//            resultMap.put(root.getName(), root.getValue());
//        }
//        // 关闭流
//        in.close();
//        return resultMap;
//    }
//
//
//    /**
//     * 获取子结点的Map集合
//     *
//     * @param children
//     * @return
//     */
//    public static Map getChildrenMap(List children) {
//        Map resultMap = new HashMap();
//        if (!children.isEmpty()) {
//            Iterator it = children.iterator();
//            while (it.hasNext()) {                
//                Element e = (Element) it.next();
//                String key = e.getName();
//                List list = e.getChildren();
//                Map childMap = new HashMap();
//                if (!list.isEmpty()) {
//                    childMap = getChildrenMap(list);
//                }
//                //若map中已有则按照list处理
//                if (resultMap.get(key) != null) {
//                    List valueList = new ArrayList();
//                    Map newMap = new HashMap();
//                    if (childMap.size() > 0) {
//                        newMap.put(key, childMap);
//                    } else {
//                        newMap.put(key, e.getValue());
//                    }
//                    if (ArrayList.class.getName().equals(resultMap.get(key).getClass().getName())) {
//                        valueList.addAll((List) resultMap.get(key));
//                    } else {
//                        Map oldMap = new HashMap();
//                        oldMap.put(key, resultMap.get(key));
//                        valueList.add(oldMap);
//                    }
//                    valueList.add(newMap);
//
//                    resultMap.put(key, valueList);
//                } else {
//                    if (childMap.size() > 0) {
//                        resultMap.put(key, childMap);
//                    } else {
//                        resultMap.put(key, e.getValue());
//                    }
//                }
//            }
//        }
//        return resultMap;
//    }
//
//
//    public static void main(String[] args) throws IOException, JDOMException {
//        String path = "D:/IdeaPro/demo/src/main/resources/static/code.xml";
//        SAXBuilder saxBuilder = new SAXBuilder();
//        FileInputStream inputStream = new FileInputStream(path);
//        Document doc = saxBuilder.build(inputStream);
//        Element root = doc.getRootElement();
//        Map<String,String> map = getElementAll(root);
//        System.out.println(map);
//    }
}