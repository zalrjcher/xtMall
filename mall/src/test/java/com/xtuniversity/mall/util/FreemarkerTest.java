package com.xtuniversity.mall.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerTest {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/xtmall?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {

        String mop = "complaint";
        String desc = "complaint";
        String tableName = "xt_complaint";
        String src = "src/main/java";
        String parentCatalogue = "model";
        String templatePath = "src/test/java/com/xtuniversity/mall/util";
        String controllerPath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/controller/";
        String idaoPath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/dao/";
        String daoPath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/dao/impl/";
        String entityPath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/entity/";
        String mapperPath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/mapper/";
        String servicePath = src + "/com/xtuniversity/mall/" + parentCatalogue + "/" + mop.toLowerCase() + "/service/";

        String jspPath = "src/main/webapp/WEB-INF/page/admin/" + mop.toLowerCase() + "/";

        List<String[]> tableInfos = DatabaseUtil.getTableColumnInfo(tableName);
        List<String[]> fieldInfos = sqlTypeConvert(tableInfos);

        generateBean(entityPath, templatePath, mop, desc, fieldInfos, parentCatalogue);
        generateMapper(mapperPath, templatePath, mop, desc, tableInfos, fieldInfos, tableName, parentCatalogue);
        generateDao(daoPath,templatePath,mop,desc,parentCatalogue);
        generateIDao(idaoPath,templatePath,mop,desc,parentCatalogue);
    }


    /**
     * 生成edit页面
     *
     * @param path
     * @param templatePath
     * @param mop
     * @param desc
     * @param fieldInfos
     * @throws Exception
     */
    public static void generateJspEdit(String path, String templatePath, String mop, String desc, List<String[]> fieldInfos) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("fieldInfos", fieldInfos);
        root.put("mop", mop.toLowerCase());
        root.put("desc", desc);
        generateTemplate(path, templatePath, mop, "jsp_edit.ftl", root);
    }

    /**
     * 生成list页面
     *
     * @param path
     * @param templatePath
     * @param mop
     * @param desc
     * @param fieldInfos
     * @throws Exception
     */
    public static void generateJspList(String path, String templatePath, String mop, String desc, List<String[]> fieldInfos) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("fieldInfos", fieldInfos);
        root.put("mop", mop.toLowerCase());
        root.put("desc", desc);
        generateTemplate(path, templatePath, mop, "jsp_list.ftl", root);
    }

    /**
     * 字段转换
     *
     * @param tableInfos
     * @return
     */
    public static List<String[]> sqlTypeConvert(List<String[]> tableInfos) {
        List<String[]> fieldInfos = new ArrayList<>();
        for (String[] infos : tableInfos) {
            String[] newInfos = new String[3];
            String columnName = infos[0];
            if (columnName.contains("_")) {
                columnName = SqlConvertModelUtil.replaceUnderlineAndfirstToUpper(columnName, "_", "");
            }
            if (columnName == null) {
                continue;
            }
            String columnType = SqlConvertModelUtil.sqlTypeConvertModelType(infos[1]);
            if (columnType == null) {
                continue;
            }
            newInfos[0] = columnName;
            newInfos[1] = columnType;
            newInfos[2] = infos[2];
            fieldInfos.add(newInfos);
        }
        return fieldInfos;
    }

    /**
     * 生成实体
     *
     * @param path
     * @param templatePath
     * @param mop
     * @param desc
     * @param fieldInfos
     * @throws Exception
     */
    public static void generateBean(String path, String templatePath, String mop, String desc, List<String[]> fieldInfos, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("fieldInfos", fieldInfos);
        root.put("desc", desc);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "entity.ftl", root);
    }

    public static void generateDao(String path, String templatePath, String mop, String desc, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("desc", desc);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "DaoImpl.ftl", root);
    }

    public static void generateIDao(String path, String templatePath, String mop, String desc, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("desc", desc);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "IDao.ftl", root);
    }

    public static void generateMapper(String path, String templatePath, String mop, String desc, List<String[]> tableInfos, List<String[]> fieldInfos, String tableName, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("tableName", tableName);

        Map<String, String> columnAndfieldMap = new HashMap<String, String>();
        // 数据库字段
        StringBuffer columnInfo = new StringBuffer();
        StringBuffer fieldInfo = new StringBuffer();
        StringBuffer updateInfo = new StringBuffer();
        for (int i = 0; i < tableInfos.size(); i++) {
            String column = tableInfos.get(i)[0];
            String field = fieldInfos.get(i)[0];
            columnAndfieldMap.put(column, field);
            columnInfo.append(column);
            fieldInfo.append("#{" + field + "}");
            if (!column.equals("id")) {
                updateInfo.append(column + "=").append("#{" + field + "}");
            }
            if (i != tableInfos.size() - 1) {
                columnInfo.append(",");
                fieldInfo.append(",");
                if (!column.equals("id")) {
                    updateInfo.append(",");
                }
            }
        }
        root.put("columnInfo", columnInfo.toString());
        root.put("fieldInfo", fieldInfo.toString());
        root.put("updateInfo", updateInfo.toString());
        root.put("columnAndfieldMap", columnAndfieldMap);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "Mapper.ftl", root);
    }

    public static void generateService(String path, String templatePath, String mop, String desc, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("desc", desc);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "Service.ftl", root);
    }

    public static void generateComtroller(String path, String templatePath, String mop, String desc, String parentCatalogue) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("desc", desc);
        root.put("parentCatalogue", parentCatalogue);
        generateTemplate(path, templatePath, mop, "Controller.ftl", root);
    }

    /**
     *
     * 模块代码快速生成模板
     *
     * @param args
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateTemplate(String path, String templatePath, String mop, String templateName, Map<String, Object> root) throws Exception {
        root.put("mop", mop);
        String mo = mop.substring(0, 1).toUpperCase() + mop.substring(1);
        Configuration cfg = new Configuration();
        // 从什么地方加载freemarker模板文件
        cfg.setDirectoryForTemplateLoading(new File(templatePath));

        // 设置对象包装器
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        // 设置异常处理器
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        // 定义Template对象
        Template template = cfg.getTemplate(templateName);

        // 定义输出
        if (templateName.equals("entity.ftl")) {
            templateName = templateName.replace("entity", "");
        }
        String outName = path + mo + templateName.replace(".ftl", templateName.equals("Mapper.ftl") ? ".xml" : ".java");
        if (templateName.equals("IDao.ftl")) {
            outName = path + "I" + mo + "Dao.java";
        } else if (templateName.equals("jsp_list.ftl")) {
            outName = path + mop + "_list.jsp";
        } else if (templateName.equals("jsp_edit.ftl")) {
            outName = path + mop + "_edit.jsp";
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outName)));

        template.process(root, out);
        System.out.println("Generate " + outName + " Ok !");
    }

}
